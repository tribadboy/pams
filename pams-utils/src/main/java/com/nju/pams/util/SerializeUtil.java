package com.nju.pams.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化与反序列化工具 <br>
 * @author yangyueyang
 */
public class SerializeUtil {
	
	/**
	 * 将对象序列化为字节数组
	 * @param obj  obj应实现java.io.Serializable接口
	 * @return
	 */
	public static byte[] serialize(Object obj) {   
		byte[] bytes = null;    
		ByteArrayOutputStream bos = new ByteArrayOutputStream();    
		try {    
			ObjectOutputStream oos = new ObjectOutputStream(bos);    
			oos.writeObject(obj);    
			oos.flush();    
			bytes = bos.toByteArray();    
			oos.close();    
			bos.close();    
		} catch (IOException ex) {    
			ex.printStackTrace();    
		}    
		return bytes;    
	}    
		  
	/**
	 * 将字节数组反序列化为Object对象
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {  
		Object obj = null;    
		try {  
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);    
			ObjectInputStream ois = new ObjectInputStream(bis);    
			obj = ois.readObject();    
			ois.close();    
			bis.close();    
		} catch (IOException ex) {    
			ex.printStackTrace();    
		} catch (ClassNotFoundException ex) {    
			ex.printStackTrace();    
		}    
		return obj;    
	}  
		       
}