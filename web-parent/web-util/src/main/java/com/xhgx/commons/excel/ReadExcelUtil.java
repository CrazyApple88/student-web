package com.xhgx.commons.excel;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @ClassName ReadExcelUtil
 * @Description 
 * http://blog.csdn.net/eguid_1/article/details/50918548
 * --2016.3.23 解析EXCEL文档1.2 支持xlsx和xls文档解析，全面兼容OFFICE所有EXCEL版本文件
 * --2016.3.21 解析EXCEL文档1.1 支持xls文档解析
 * @author eguid
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class ReadExcelUtil {
	
	private static final Object T = null;

	/**
	 * 按照给定的字段进行解析 如给定数组：{id,name,sal,date}
	 * 
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<Map<String,String>> parseByMap(InputStream input, String[] fields) throws InvalidFormatException, IOException {
		Map<String,String> mapFristCell = new HashMap<String,String>();
		for (String string : fields) {
			mapFristCell.put(string.trim(), "1");
		}
		Workbook wb = createWorkbook(input);
		Sheet sheet = wb.getSheetAt(0);
		// 列
		Cell cell = null;
		// 暂时存放
		String data = null;
		// 最大行数
		int maxRowNum = sheet.getLastRowNum();
		// 最大列数
		int maxCellNum = sheet.getRow(0).getLastCellNum();
		List<Map<String,String>> listMap = new ArrayList<Map<String,String>>();
		
		Map<Integer,String> mapCell = new HashMap<Integer,String>();
		// 最大行数循环
		for (int j = 0; j <= maxRowNum; j++) {
			System.out.println("解析第： "+ j + " 行");
			Map<String,String> mapStr = new HashMap<String,String>();
			// 最大列数循环
			for (int i = 0; i < maxCellNum; i++) {
				// 获取第j行第i列的值
				cell = sheet.getRow(j).getCell(i);
				// 转换成字符串
				if(cell == null || "".equals(cell)){
					data = "";
				}else{
					data = getValue4Cell(cell);
				}
				// 如果标题与给定字段对应,则记录值;否则进入下个整列
				if (j == 0) {
					if (ishave(mapFristCell, data)) {
						// 如果是能和传过来的第一行数组名称对应
						mapCell.put(i, data);
					}
				} else {
					if(mapCell.get(i) != null){
						mapStr.put(mapCell.get(i), data);
					}
				}
			}
			if(mapStr.size() > 0){
				listMap.add(mapStr);
			}
		}
		return listMap;
	}
	

	/**
	 * 按照给定的对象字段进行解析
	 * 
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static <T> List<T> parseByList(InputStream input, String[] fields, String[] hds, List<T> list) throws InvalidFormatException, IOException {
		try {
			/*BeanUtils.copyProperties(source, target);
			
			Object o   = z.class.newInstance();*/
			if(fields.length != hds.length){
				return null;
			}
			Map<String,String> mapFristCell = new HashMap<String,String>();
			for (int i = 0; i < fields.length; i++) {
				mapFristCell.put(fields[i], hds[i]);
			}
			
//			for (String string : fields) {
//				mapFristCell.put(string.trim(), true);
//			}
			Workbook wb = createWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);
			// 列
			Cell cell = null;
			// 暂时存放
			String data = null;
			// 最大行数
			int maxRowNum = sheet.getLastRowNum();
			// 最大列数
			int maxCellNum = sheet.getRow(0).getLastCellNum();
	
			Map<Integer,String> mapCell = new HashMap<Integer,String>();
			// 最大行数循环
			for (int j = 0; j <= maxRowNum; j++) {
				Object tEntity = null;
				System.out.println("解析第： "+ j + " 行");
				// 最大列数循环
				for (int i = 0; i < maxCellNum; i++) {
					// 获取第j行第i列的值
					cell = sheet.getRow(j).getCell(i);
					// 转换成字符串
					data = getValue4Cell(cell);
					// 如果标题与给定字段对应,则记录值;否则进入下个整列
					if (j == 0) {
						if (ishave(mapFristCell, data)) {
							// 如果是能和传过来的第一行数组名称对应
							mapCell.put(i, data);
						}
					} else {
						if(mapCell.get(i) != null){
							if(tEntity == null){
								tEntity = new Object();
							}
//							setFieldValue(tEntity,mapFristCell.get(mapCell.get(i)), data);
							invoke(tEntity,mapFristCell.get(mapCell.get(i)), data);
						}
					}
				}
				if(tEntity != null){
					list.add((T) tEntity);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

	/**
	 * 通过泛型实例对象得到某一字段值
	 * 
	 * @author 
	 * @date 
	 */
	private static <T> T setFieldValue(T t, String fieldName, Object val)
			throws Exception {
		if (t == null) {
			return null;
		}
		Field field = t.getClass().getDeclaredField(fieldName);
		// 暴力反射
		field.setAccessible(true);
		// 塞入字段数据的值
		field.set(t,val);
		return t;
	}
	
	/**
	 * 
	 * <b>方法名</b>：invoke<br>
	 * <b>功能</b>：执行指定的方法<br>
	 * 
	 * @author <font color='blue'>zohan</font>
	 * @date 2012-9-19 下午01:59:33
	 * @param obj
	 *            当前对象
	 * @param methodName
	 *            指定的方法名称
	 * @param args
	 *            指定方法的参数
	 * @return
	 */
	public static Object invoke(Object obj, String methodName, Object... args) {
		try {
			Method method = null;
			if (null == args || args.length == 0) {
				method = obj.getClass().getMethod(methodName);
			} else {
				method = obj.getClass().getMethod(methodName, args.getClass());
			}
			Object result = method.invoke(obj, args);
			return result;
		} catch (SecurityException e) {
         // log.error("安全异常:" + e.getMessage());
			e.getMessage();
		} catch (NoSuchMethodException e) {
        // log.error(e.getMessage());
			e.getMessage();
		} catch (IllegalArgumentException e) {
        // log.error("参数不正确：" + e.getMessage());
			e.getMessage();
		} catch (IllegalAccessException e) {
        // log.error(e.getMessage());
			e.getMessage();
		} catch (InvocationTargetException e) {
			e.getMessage();
       // log.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 是否有此字段
	 * 
	 * @param fields
	 * @param field
	 * @return
	 */
	private static boolean ishave(Map<String,String> map, String field) {
		if (field == null || map == null || map.size() < 1) {
			return false;
		}
		
		if (map.get(field.trim()) != null){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * 解析 第一行是标题行 第二行以后都是内容 例如： id sex name 1 男 王 2 女 李
	 * 
	 * 
	 * 解析后的map格式： key value 0 List()一行 1 List()一行 2 List()一行
	 * 
	 * 例如： 0 [id , name, sex, sal , date] 1 [1.0, wang, 1.0, 1000.0, 42287.0] 2
	 * [2.0, liang, 1.0, 1001.0, 42288.0]
	 * 
	 * @param file
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static Map parse1(InputStream input) throws IOException,
			InvalidFormatException {
		// 提取并创建工作表
		Workbook wb = createWorkbook(input);
		// 获取sheet页第0页
		Sheet sheet = wb.getSheetAt(0);
		// 获取行迭代器
		Iterator rows = sheet.rowIterator();
		// 解析出来的数据存放到这个map里面，value套了一层List，key用于存放标题，List用于存放标题下的所有数据
		Map<String, List<String>> map = new HashMap<String, List<String>>();
        
		// 标题
		String title = null;
		// 行数
		int rowindex = 0;
		// 列数
		int cellindex = 0;
		// 用于暂存数据
		String data = null;
		while (rows.hasNext()) {
			List<String> list = new ArrayList<String>();
			cellindex = 0;
			// 获取行中数据
			Row row = (Row) rows.next();
			// 获取列迭代器
			Iterator cells = row.cellIterator();

			while (cells.hasNext()) {
				// 获取列中数据
				Cell cell = (Cell) cells.next();
				// 获取每个单元格的值
				// 将标题下的内容放到list中
				list.add(getValue4Cell(cell));
			}
			// 将解析完的一列数据压入map
			map.put("" + rowindex++, list);
		}

		return map;
	}

	/**
	 * 把默认的格式转换成这种格式 id [1,2,3,4,5] name [wang,liang,eguid,qq,yy]
	 * 
	 * @param map
	 *            map格式：Map<String,List<String>>
	 * @return Map<String,List<String>>
	 */
	public static Map<String, List<String>> format(Map<String, List<String>> map) {
		Map<String, List<String>> newmap = new HashMap<String, List<String>>();
		// 获取标题行有多少列
		String[] titles = new String[map.get("0").size()];
		int index = 0;
		// 获取所有标题
		for (String s : map.get("0")) {
			titles[index++] = s;
		}
		// 控制List
		for (int i = 0; i < titles.length; i++) {
			List<String> newlist = new ArrayList<String>();
			// 控制map
			for (int j = 1; j < map.size(); j++) {
				newlist.add(map.get(j + "").get(i));
			}
			newmap.put(titles[i], newlist);
		}
		return newmap;
	}

	/**
	 * 解析文件名后缀
	 * 
	 * @return
	 */
	private static String parseFileSuffix(File file) {
		String fileName = file.getName();
		return fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
	}

	/**
	 * 提取文件并创建工作表
	 * 
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	private static Workbook createWorkbook(InputStream input)
			throws InvalidFormatException, IOException {
		// 如果文件不存在，抛出文件没找到异常
        // InputStream input = new FileInputStream(file);

		Workbook wb = null;
		// 如果创建工作表失败会抛出IO异常
		wb = WorkbookFactory.create(input);
		return wb;
	}

	/**
	 * 提取单元格中的值
	 */
	private static String getValue4Cell(Cell cell) {
		String data = null;
        // System.out.println("cell.getCellType()==="+cell.getCellType());
		
		switch (cell.getCellType()) {
		 // 数字、时间
		case Cell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				data = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
			}else{
				// 如果是数字,则修改单元格类型为String,然后返回String.这样就保证数字不被格式化了. 
				cell.setCellType(Cell.CELL_TYPE_STRING);
				data = String.valueOf(cell.getStringCellValue());
			}
			break;
			// 字符串
		case Cell.CELL_TYPE_STRING: 
			data = String.valueOf(cell.getStringCellValue());
			break;
			// Boolean
		case Cell.CELL_TYPE_BOOLEAN: 
			data = String.valueOf(cell.getBooleanCellValue());
			break;
			// 公式
		case Cell.CELL_TYPE_FORMULA: 
			data = String.valueOf(cell.getCellFormula());
			break;
			// 空值
		case Cell.CELL_TYPE_BLANK: 
			data = String.valueOf("");
			break;
			// 故障
		case Cell.CELL_TYPE_ERROR: 
			System.out.println(" 故障");
			break;
		default:
			System.out.print("未知类型  ");
			break;
		}
		return data;
	}

	/**
	 * 用于关闭流（暂不用）
	 * 
	 * @throws IOException
	 */
	private void closeAll(Closeable... closes) throws IOException {
		if (closes == null) {
			return;
		}
		if (closes.length < 1) {
			return;
		}
		for (Closeable c : closes) {
			if (c != null) {
				try {
					c.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
	}

	/**
	 * 格式： key value
	 * 
	 * id [1.0, 2.0, 3.0, 4.0, 5.0] sex [1.0, 1.0, 1.0, 0.0, 0.0] name [wang,
	 * liang, eguid, qq, yy] date [42287.0, 42288.0, 42289.0, 42290.0, 42291.0]
	 * sal [1000.0, 1001.0, 1002.0, 1003.0, 1004.0]
	 * 
	 * @throws InvalidFormatException
	 */

	public static void test1() throws IOException, InvalidFormatException {
//		Map<String, List<String>> map = parse1(new File("测试.xlsx"));
//		Map<String, List<String>> newmap = format(map);
//		for (Entry<String, List<String>> e : newmap.entrySet()) {
//			System.out.println(e.getKey());
//			System.out.println(e.getValue());
//		}
	}

	/**
	 * 格式： key value 0 [id, name, sex, sal, date] 1 [1.0, wang, 1.0, 1000.0,
	 * 42287.0] 2 [2.0, liang, 1.0, 1001.0, 42288.0] 3 [3.0, eguid, 1.0, 1002.0,
	 * 42289.0] 4 [4.0, qq, 0.0, 1003.0, 42290.0] 5 [5.0, yy, 0.0, 1004.0,
	 * 42291.0]
	 * 
	 * @throws InvalidFormatException
	 */
	public static void test2() throws IOException, InvalidFormatException {
//		Map<String, List<String>> map = parse1(new File("测试.xlsx"));
//		for (Entry<String, List<String>> e : map.entrySet()) {
//			System.out.println(e.getKey());
//			System.out.println(e.getValue());
//		}
	}

	public static void main(String[] args) throws IOException,
			InvalidFormatException {
		// System.out.println(parseFileSuffix(new File("测试.xlsx")));
		// test1();
		// test2();
		File file = new File("C:/Users/window/Desktop/测试002.xlsx");
		String[] hds = { "userName", "realName", "phone", "mobile", "idCard", "address", "empNo", "email"};
		String[] hdNames = { "用户名", "真实姓名", "电话", "手机", "身份证", "地址", "工号", "email"};
//		List<UserEntity> list = new ArrayList<UserEntity>();
//		list = parseByList(new FileInputStream(file), hdNames, hds, list);
		List<Map<String,String>> list = parseByMap(new FileInputStream(file), hdNames);
		System.out.println(list);
	}
}
