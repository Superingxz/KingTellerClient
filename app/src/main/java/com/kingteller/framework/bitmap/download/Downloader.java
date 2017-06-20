package com.kingteller.framework.bitmap.download;


/**
 * 
 * @author 王定波
 *
 */
public interface Downloader  {
	
	/**
	 * 请求网络的inputStream填充outputStream
	 * @param urlString
	 * @param outputStream
	 * @return
	 */
	public byte[] download(String urlString);
}
