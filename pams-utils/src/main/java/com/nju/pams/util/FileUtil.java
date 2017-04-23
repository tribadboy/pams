package com.nju.pams.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileUtil {
	
	/**
	 * 删除某个文件
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {  
	    File file = new File(path);  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        return true;
	    }  
	    return false;  
	}  
	
	/**
	 * 读取txt文件内容
	 * @param filePath
	 * @param encode
	 * @return
	 */
	public static String readTxtFile(String filePath, String encode){
		StringBuffer strBuf = new StringBuffer();
        try {
        	File file = new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
            	InputStreamReader read = new InputStreamReader(new FileInputStream(file),encode);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	strBuf.append(lineTxt);
                }
                read.close();
            }else{
            	System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return strBuf.toString();
	}
     
	
}
