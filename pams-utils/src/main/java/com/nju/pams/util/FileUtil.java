package com.nju.pams.util;

import java.io.File;

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
	
}
