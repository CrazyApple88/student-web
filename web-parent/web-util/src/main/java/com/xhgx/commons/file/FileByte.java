package com.xhgx.commons.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @ClassName FileByte
 * @Description 
 * @author 李彦伟
 * @date 2018年3月29日
 * @vresion 1.0
 */
public class FileByte {

	public static byte[] file2byte(String filePath)  
    {  
        byte[] buffer = null;  
        try  
        {  
            File file = new File(filePath);  
            if (!file.exists())  
            {  
                return null;
            }  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            byte[] b = new byte[1024];  
            int n;  
            while ((n = fis.read(b)) != -1)  
            {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        }  
        catch (FileNotFoundException e)  
        {  
            e.printStackTrace();
            return null;
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
            return null;
        }  
        return buffer;  
    }  
}
