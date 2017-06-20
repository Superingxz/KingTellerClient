package com.kingteller.client.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import com.kingteller.client.config.KingTellerStaticConfig;
import com.kingteller.client.view.log.L;

/**
 * 文件工具类
 * @author 王定波
 *
 */
public final class KingTellerFileUtils {

	/**
	 * 判断文件是否存在
	 * @param filePath
	 * @return
	 */
	public static boolean FileExistPath(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @return
	 */
	public static boolean FileExistName(String fileName) {
		File file = new File(KingTellerStaticConfig.CACHE_PATH.SD_DATA +"/"+ fileName);
		return file.exists();
	}
	
	/**
	 * 读取文件
	 */
	public static String readFile(String fileName) {
		String res = null;
		try {
			File file = new File(KingTellerStaticConfig.CACHE_PATH.SD_DATA +"/"+ fileName);
			FileInputStream fis = new FileInputStream(file);

			int length = fis.available();

			byte[] buffer = new byte[length];
			fis.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fis.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return res;
	}

	/**
	 * 写文件
	 */
	public static void writeFile(String fileName, String write_str) {

		try {
			File file = new File(KingTellerStaticConfig.CACHE_PATH.SD_DATA +"/"+fileName);

			FileOutputStream fos;
			fos = new FileOutputStream(file);
			byte[] bytes = write_str.getBytes();
			fos.write(bytes);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 删除文件
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
				L.e("file", "文件删除成功!" + file.getName());
			} 
		} else {
			L.e("file", "文件不存在!" + file.getName());
		}
	}
	
	
	/**
	 * 删除目录下的文件   若大于10则删除
	 * @return 
	 */ 
	public static void deletePathTextFile(String path, int num) {
         File[] fl = new File(path).listFiles();
         if(fl.length >= num){
        	 for (int i = 0; i < fl.length; i++) { 
            	 deleteFile(fl[i]);
             }
         }
        
	}
	
	/**
	 * 删除目录下的jpg文件   ： 合成图片
	 * @return 
	 */ 
	public static void deletePathJpgFile(String path) {
         File[] fl = new File(path).listFiles();
         
         for (int i = 0; i < fl.length; i++) { 
        	 if(fl[i].toString().endsWith(".jpg")){
        		 deleteFile(fl[i]);
             }
         }
	}
	
	
//
//	/**
//	 * 读取Asset下的文件
//	 * @param context
//	 * @param fileName
//	 * @return
//	 * @throws IOException
//	 */
//	public static String readAssetFile(Context context, String fileName)
//			throws IOException {
//
//		String res = "";
//		InputStream in = context.getResources().getAssets().open(fileName);
//
//		int length = in.available();
//		byte[] buffer = new byte[length];
//		in.read(buffer);
//		res = EncodingUtils.getString(buffer, "UTF-8");
//
//		return res;
//
//	}

}
