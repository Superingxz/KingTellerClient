package com.kingteller.client.config;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;

/**
 * 通用参数设置
 * @author Administrator
 *
 */
public class KingTellerStaticConfig {
	
	/**全局	JSESSIONID	**/
    public static final String COOKIENAME = "JSESSIONID";

    /**主页	提醒：0		**/
    public static final int MAIN_WAITDOT = 0;
    /**主页	工作台：1		**/
    public static final int MAIN_FUNCTION = 1;
    /**主页	设置：0		**/
    public static final int MAIN_MORE = 2;

    /**主页	返回   广播Action   	标志	**/
    public static final String ISUP_UPSTATUSACTION = "com.kingteller.client.activity.MainActivity.UpStatusAction";
    /**主页	返回    更新页面    		标志	**/
    public static final String ISUP_MAINPAGE = "isup_mainpage";
    /**主页	返回   是否更新页面数据	标志	**/
    public static final String ISUP_PAGEDATA = "isup_pagedata";
    /**主页	返回   是否更新角标数据	标志	**/
    public static final String ISUP_CORNERDATA = "isup_cornerdata";


    /**后台服务 名 **/
    public static final String SERVICE_NAME = "com.kingteller.client.service.KingTellerService";
    /**后台服务 位置定时器 默认时间 3 分钟**/
    public static final int SERVICE_LOCATION_TIME = 3;


    /**默认  提醒 	 底部角标 为  0**/
    public static int WAITDO_BOTTOM_CORNER = 0;
    /**默认  工作台  	底部角标 为  0**/
    public static int FUNCTION_BOTTOM_CORNER = 0;
    /**默认  设置 	 底部角标 为  0**/
    public static int MORE_BOTTOM_CORNER = 0;
    /**默认  离线任务		底部角标 为  0**/
    public static int FUNCTION_OFFLINE_BOTTOM_CORNER = 0;

    /**=================我的工单=============**/
    /**默认  维护报告中	部件更换二维码	为	0**/
    public static int QR_DOTMACHINE_REPLACE_NUM = 0;
    /**默认  填写报告提交状态	为	false**/
    public static boolean WRITE_REPORT_SUBMISSION_STATE_1 = false;
    public static boolean WRITE_REPORT_SUBMISSION_STATE_2 = false;
    public static boolean WRITE_REPORT_SUBMISSION_STATE_3 = false;
    /**默认  审核报告提交状态	为	false**/
    public static boolean AUDIT_REPORT_SUBMISSION_STATE_1 = false;
    public static boolean AUDIT_REPORT_SUBMISSION_STATE_2 = false;
    /**默认  派单报告提交状态	为	false**/
    public static boolean SEND_REPORT_SUBMISSION_STATE_1 = false;
    public static boolean SEND_REPORT_SUBMISSION_STATE_2 = false;
    public static boolean SEND_REPORT_SUBMISSION_STATE_3 = false;

    /**=================二维码功能=============**/
    /**默认  网点发货			扫描列表	修改状态	为	false**/
    public static boolean QR_DOTDELIVERY_LISTDATA = false;
    /**默认  设备录入			扫描列表	修改状态	为	false**/
    public static boolean QR_DOTMACHINE_LISTDATA = false;
    /**默认  设设备部件更换		扫描列表	修改状态	为	false**/
    public static boolean QR_DOTMACHINEREPLACE_LISTDATA = false;
    /**默认  离线设备录入		扫描列表	修改状态	为	false**/
    public static boolean QR_DOTMACHINE_OFFLINE_LISTDATA = false;
    /**默认  离线设备部件更换	扫描列表	修改状态	为	false**/
    public static boolean QR_DOTMACHINEREPLACE_OFFLINE_LISTDATA = false;


    /**=================考勤审核=============**/
    /**默认  单据审批		是否成功 审批 或 退回     状态	为	false**/
    public static boolean OA_ADUIT_ATTENDANCE_ISUDATE = false;
    /**默认  单据提交  暂存	是否成功     状态	为	false**/
    public static boolean OA_WORK_ATTENDANCE_ISUDATE = false;

    /**主菜单*/
    public static final int MAINMENU = 1;
    /**地图菜单*/
    public static final int MAPMENU = 2;
    /**物流管理菜单*/
    public static final int WLJKMENU = 3;
    /**在线学习菜单*/
    public static final int ONLINEMENU = 4;
    /**二维码扫描菜单*/
    public static final int EWMMENU = 5;
	
	//默认图片宽度
	public static final int DefaultImgMaxWidth =600;
	//默认图片高度
	public static final int DefaultImgMaxHeight =600;
	
	
	
	
//	public static final int NUM_PAGE = 10;
//	public static final String WATIDOFILE = "waitdo";
//	


	/**数据存储 参数配置 */
    public final static class SHARED_PREFERENCES {
        public final static String USER = "user";

        public final static String COOKIE = "cookie";

        public final static String CONFIG = "config";

        public final static String ADDRESS = "address";

        public final static String DEFAULTUSER = "defaultuser";
    }

	/**缓存文件夹路径 参数配置 */
    public final static class CACHE_PATH {

        public final static String SD_DATA = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/kingteller/data";
        //文件存储位置
        public final static String SD_DOWNLOAD = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/kingteller/download";
        //日志存储位置
        public final static String SD_LOG = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/kingteller/log";
        //图片存储位置
        public final static String SD_KTIMAGE = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/kingteller/ktimage";
        //图片缓存位置
        public final static String SD_IMAGECACHE = Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + "/kingteller/imagecache";
    }
	
	/** 获取屏幕尺寸 */
	public final static class SCREEN {
		public static int Width = 0;
		public static int Height = 0;
	}

	
	/**===============通用返回标记值=================**/
	
	public final static int RESULT_LOCATION = 201;
	public final static int RESULT_LOCATION_FAIL = 204;
	public final static int RESULT_LOCATION_GDFAIL = 205;
	public final static int RESULT_LOCATION_GDSUCCESS = 206;
	public final static int LOCATION_FAIL = 1120;

	public final static int REQUEST_ONCAR = 101;
	public final static int REQUEST_OFFCAR = 102;

	public final static int REQUEST_ONATMCODE = 103;
	public final static int REQUEST_OFFATMCODE = 104;
	public final static int REQUEST_STAFFSEARCH = 105;
	public final static int REQUEST_PASSENGER = 106;
	public final static int REQUEST_SETNAVLINE = 107;
	public final static int REQUEST_GETMAPADDRESS = 108;
	public final static int SELECT_PIC_BY_TACK_PHOTO = 109;
	public final static int SELECT_PIC_BY_PICK_PHOTO = 110;
	public final static int SELECT_FILE = 112;
	public final static int REQUEST_ADDATM = 111;
	public final static int REQUEST_FEETYPE = 113;//获取费用类型
	public final static int REQUEST_MODE = 114;//获取交通工具
	
	public final static int REQUEST_BJWL=115;//备件物料
	public final static int REQUEST_GZBJ=116;//故障部件
	public final static int REQUEST_JQZD=117;//机器字段
	
	public final static int PIC_SELECT_NUM = 201;//图片选择   返回
	public final static int PIC_DELETE_NUM = 202;//图片修改   返回
	
	public final static int NotifiactionPushID = 301;
	public final static int SELECT_SERVICE=501;//服务站
	public static final int SELECT_ATMCODE = 502;//ATM号
	public final static int SELECT_BJMK=503;//故障模块
	public final static int SELECT_GZMS=504;//故障描述
	public final static int SELECT_CLGC=505;//处理过程
	
	public final static int SELECT_WDDZ = 506;
	
	public final static int SELECT_PROVENCE = 507;//选择省份
	public final static int SELECT_CITY = 508;//选择城市
	public final static int SELECT_DISTICT = 509;//选择县区
	public final static int SELECT_BACK = 510;
	
	public final static int UPLOAD_PIC = 511;//上传图片
	
	public final static int SELECT_HANDLESUB = 601;
	
	public final static int SELECT_RAPAIRSENDORDER = 602;
	
	public final static int SELECT_XZRY = 603;
	public final static int SELECT_CXFWZRY = 604;
	public final static int SELECT_FYLX = 605;
	public final static int SELECT_PIC_UPLOAD= 606;
	public final static int SELECT_PIC_TAKE = 607;
	public final static int SELECT_HANDLESUB_XM = 608;
	
	public final static int QRCODE_NUM = 701;//扫描结果
	public final static int QRCODE_FHSM_BACK = 703;//发货
	public final static int QRCODE_SHSM_BACK = 704;//收货
	public final static int QRCODE_DJSFHSM_BACK = 705;//登记收发货
	public final static int QRCODE_WDJQ_BACK = 706;//网点机器
	public final static int QRCODE_BXSM_BACK = 707;//保修
	public final static int QRCODE_ZS_BACK = 708;//扫描结果
	public final static int QRCODE_PICTURE_NUM = 710;//扫描选择图片返回
	
	public final static int OACODE_SELECT_PEOPLE_NUM = 801;//考勤 选择人员返回
	public final static int OACODE_SELECT_LEAVEFROM_NUM = 802;//考勤 选择请假单返回
}
