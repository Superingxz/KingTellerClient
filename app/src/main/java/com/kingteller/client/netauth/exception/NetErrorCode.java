package com.kingteller.client.netauth.exception;

/**
 * @Title NetErrorCode
 * @Package com.kingteller.client.netauth.exception
 * @Description NetErrorCode数据处理返回的错误和代码
 * @author 王定波
 * @date 2014-6-16 16:30
 * @version V1.0
 */
public class NetErrorCode {
	public final static int DATAERROR = -2;
	public final static String DATAERRORMSG = "数据错误";
	
	public final static int AUTHERROR = -1;
	public final static String AUTHERRORMSG = "登陆失效,请重新登陆";
	
	public final static int NETERROR = 0;
	public final static String NETERRORMSG = "网络错误或者服务器无法连接";
}
