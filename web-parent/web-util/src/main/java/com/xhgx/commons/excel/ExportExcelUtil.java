package com.xhgx.commons.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @ClassName ExportExcelUtil
 * @Description 使用poi报表导出工具类 把poi的一个调用接口抽出来，便于导出功能的管理
 * @author 李彦伟 
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class ExportExcelUtil {

	/**
	 * 参数说明： list：list数据集合 hdNames：表头需要显示的名称 hds：表头对应的对象属性名称，和 hdNames一一对应
	 * xlsName：导出Excel的预定义名称 request: HttpServletRequest
	 * response：HttpServletResponse 通过数据构建Excel
	 * 
	 * @author 
	 * @throws Exception
	 * @date 2015-3-13 上午11:00:30
	 */
	public static <T> boolean outPutExcel(List<T> list, String[] hdNames,
			String[] hds, String xlsName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 创建工作薄
		Workbook wb = new HSSFWorkbook();
		// 创建工作表
		Sheet sheet = wb.createSheet(); 
		// 自适应宽度
		sheet.autoSizeColumn((short) 0);
		// 写入表头---Excel的第一行数据
		// 创建行
		Row nRow = sheet.createRow(0); 
		for (int i = 0; i < hdNames.length; i++) {
			// 创建单元格
			Cell nCell = nRow.createCell(i); 
			nCell.setCellValue(hdNames[i]);
		}

		// 写入每一行数据---一条记录就是一行数据
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < hds.length; j++) {
				// 得到列的值
				Object o = getFieldValue(list.get(i), hds[j]); 
				// 将值写入Excel
				data2Excel(sheet, o, i + 1, j); 
			}
		}
		return downloadExcel(wb, xlsName, request, response);
	}

	/**
	 * <b>导出list中map做载体的数据到excel</b><br>
	 * 参数说明：<br>
	 * list:存放了Map数据的集合<br>
	 * hdNames:表头列名<br>
	 * hds:对应表头的数据KEY<br>
	 * xlsName:导出文件名<br>
	 * 
	 * @author 
	 * @date 
	 */
	public static <T> boolean outPutExcelByMap(List<Map<String, Object>> list,
			String[] hdNames, String[] hds, String xlsName,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        
		// 创建工作薄
		Workbook wb = new HSSFWorkbook(); 
		// 创建工作表
		Sheet sheet = wb.createSheet(); 
		// 自适应宽度
		sheet.autoSizeColumn((short) 0); 
		// 写入表头---Excel的第一行数据
		// 创建行
		Row nRow = sheet.createRow(0); 
		for (int i = 0; i < hdNames.length; i++) {
			// 创建单元格
			Cell nCell = nRow.createCell(i); 
			nCell.setCellValue(hdNames[i]);
		}

		// 写入每一行数据---一条记录就是一行数据
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < hds.length; j++) {
				// 得到列的值
				Object o = list.get(i).get(hds[j]); 
			    // 将值写入Excel
				data2Excel(sheet, o, i + 1, j); 
			}
		}
		return downloadExcel(wb, xlsName, request, response);
	}

	/**
	 * Excels导出多个sheet
	 * 
	 * @author 
	 * @date 
	 */
	/** map:data,sheetName,hds,hdNames,xlsName;request,response*/
	public static <T> boolean outPutExcels(List<Map<String, Object>> list,
			String xlsName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 创建工作薄
		Workbook wb = new HSSFWorkbook(); 
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			// 创建工作表
			Sheet sheet = wb.createSheet(); 
			String sheetName = (String) map.get("sheetName");
			wb.setSheetName(i, sheetName);
			// 写入表头---Excel的第一行数据
			// 创建行
			Row nRow = sheet.createRow(0); 
			String[] hdNames = (String[]) map.get("hdNames");
			for (int j = 0; j < hdNames.length; j++) {
				// 创建单元格
				Cell nCell = nRow.createCell(j); 
				nCell.setCellValue(hdNames[j]);
			}

			// 写入每一行数据---一条记录就是一行数据
			@SuppressWarnings("unchecked")
			List<T> data = (List<T>) map.get("data");
			String[] hds = (String[]) map.get("hds");
			for (int j = 0; j < data.size(); j++) {
				for (int k = 0; k < hds.length; k++) {
					// 得到列的值
					Object o = getFieldValue(data.get(j), hds[k]); 
					// 将值写入Excel
					data2Excel(sheet, o, j + 1, k); 
				}
			}
		}

		return downloadExcel(wb, xlsName, request, response);
	}

	/**
	 * 传递一个Wookbook，给定文件名，以及request和response下载Excel文档
	 * 
	 * @author 
	 * @throws IOException
	 * @date 
	 */
	@SuppressWarnings("all")
	private static boolean downloadExcel(Workbook wb, String xlsName,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (request.getHeader("user-agent").indexOf("MSIE") != -1) {
			xlsName = java.net.URLEncoder.encode(xlsName, "utf-8") + ".xls";
		} else {
			xlsName = new String(xlsName.getBytes("utf-8"), "iso-8859-1")
					+ ".xls";
		}
		OutputStream os = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="
				+ xlsName);

		wb.write(os);
		return true;
	}

	/**
	 * 通过泛型实例对象得到某一字段值
	 * 
	 * @author 
	 * @date 
	 */
	private static <T> Object getFieldValue(T t, String fieldName)
			throws Exception {
		if (t == null) {
			return null;
		}
		Field field = t.getClass().getDeclaredField(fieldName);
		// 暴力反射
		field.setAccessible(true);
		// 得到字段数据的值
		Object o = field.get(t);
		return o;
	}

	/**
	 * 将数据写到Excel中
	 * 
	 * @author 
	 * @date 
	 */
	private static void data2Excel(Sheet sheet, Object o, Integer r, Integer c) {
		// 通过获得sheet中的某一列，有得到，没有创建
		Row nRow = sheet.getRow(r);
		if (nRow == null) {
			nRow = sheet.createRow(r);
		}
		// nRow.setColumnWidth(r, arg1);

		Cell nCell = nRow.createCell(c);

		// 根据不同类型进行转化，如有其它类型没有考虑周全的，使用发现的时候添加
		char type = 'x';
		if (o instanceof Integer) {
			type = 1;
		} else if (o instanceof Double) {
			type = 2;
		} else if (o instanceof Float) {
			type = 3;
		} else if (o instanceof String) {
			type = 4;
		} else if (o instanceof Date) {
			type = 5;
		} else if (o instanceof Calendar) {
			type = 6;
		} else if (o instanceof Boolean) {
			type = 7;
		} else if (o == null) {
			type = 8;
		}

		switch (type) {
		case 1:
			nCell.setCellValue((Integer) o);
			break;
		case 2:
			nCell.setCellValue((Double) o);
			break;
		case 3:
			nCell.setCellValue((Float) o);
			break;
		case 4:
			nCell.setCellValue((String) o);
			break;
		case 5:
			nCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(o));
			break;
		case 6:
			nCell.setCellValue((Calendar) o);
			break;
		case 7:
			nCell.setCellValue((Boolean) o);
			break;
		case 8:
			nCell.setCellValue("");
			break;
		default:
			nCell.setCellValue(o + "");
			break;
		}
	}
}
