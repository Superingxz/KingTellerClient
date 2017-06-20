package com.kingteller.client.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.kingteller.client.bean.db.AtmGroup;
import com.kingteller.client.bean.db.CommonMaintainInfo;
import com.kingteller.client.bean.db.LogsticReport;
import com.kingteller.client.bean.db.QRDotDelivery;
import com.kingteller.client.bean.db.QRDotMachine;
import com.kingteller.client.bean.db.QRDotMachineReplace;
import com.kingteller.client.bean.db.QROfflineDotMachine;
import com.kingteller.client.bean.db.QROfflineDotMachineReplace;
import com.kingteller.client.bean.db.Report;
import com.kingteller.framework.KingTellerDb;

import java.util.List;

/**
 * Created by Administrator on 16-2-26.
 */
public class KingTellerDbUtils {

    /**TODO: 数据库通用方法=============================================================================START**/

    public static KingTellerDb create(Context context){
        KingTellerDb db = KingTellerDb.create(context);
        return db;
    }

    /**
     * 查询所有数据
     * @param context
     * @param clazz
     * @return
     */
    public static <T> List<T> getCacheDataAll(Context context, Class<T> clazz) {
        try {
            KingTellerDb db = KingTellerDb.create(context);
            List<T> list = db.findAll(clazz);
            if (list == null)
                return null;
            else
                return list;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据(class)得到报告集合(data)数据
     * @param reportData
     * @param clazz
     * @return
     */
    public static <T> T getReportDataFromString(String reportData, Class<T> clazz) {
        try {
            if (reportData == null) return null;
            Gson gson = new Gson();
            T obj = gson.fromJson(reportData, clazz);
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取所有缓存数据数量
     * @param context
     * @param mClass
     * @return
     */
    public static int getAllCacheNum(Context context, Class<?>[] mClass){
        int num = 0;
        for(int i = 0; i < mClass.length; i++){
            List<?> mDataList = getCacheDataAll(context, mClass[i]);
            if(mDataList.size() > 0){
                num = num + mDataList.size();
            }
        }
        return num;
    }

    /**
     * 获取缓存数据类型
     * @param str 0：						其他数据类型
     * @param str 1：maintenance  			维护工单
     * @param str 2：otherMatter  			其他事物
     * @param str 3：logistics    			物流报告
     * @param str 4：clean					清洁报告
     * @param str 5：dotmachine    			网点机器录入
     * @param str 6：dotmachinereplace   	网点机器部件更换
     * @param str 7：dotdelivery				网点发货
     * @param str 8：dotmachine_offline				网点机器录入_离线
     * @param str 9：dotmachinereplace_offline		网点机器部件更换_离线
     * @param str
     */
    public static int getTypeValue(String str){
        if(str.equals("maintenance")) return 1; //"维护工单"
        if(str.equals("otherMatter")) return 2; //"其他事务"
        if(str.equals("logistics")) return 3;  //"物流报告"
        if(str.equals("clean")) return 4; //"清洁报告"
        if(str.equals("dotmachine")) return 5; //"网点机器录入"
        if(str.equals("dotmachinereplace")) return 6; //"网点机器部件更换"
        if(str.equals("dotdelivery")) return 7; //"网点发货"
        if(str.equals("dotmachine_offline")) return 8; //"网点机器录入_离线"
        if(str.equals("dotmachinereplace_offline")) return 9; //"网点机器部件更换_离线"
        if(str.equals("atmgroupuppic")) return 10; //"机器定位上传图片"
        return 0;
    }

    /**数据库通用方法=====================================================================================END**/



    /**TODO: 报告暂存=============================================================================START**/
    /**
     * 保存报告到数据库
     * @param context
     * @param reportId
     * @param reportType
     * @param reportData
     * @param reportTime
     * @param isSuccess
     */
    public static void saveReportToDataBase(Context context, String reportId, String reportType,
                                            String reportData, String reportOtherData, String reportTime, int isSuccess) {
        KingTellerDb db = KingTellerDb.create(context);
        Report rp = db.findById(reportId, Report.class);
        if (rp == null) {
            rp = new Report();
            rp.setOrderId(reportId);
            rp.setReportType(reportType);
            rp.setReportData(reportData);
            rp.setReportOtherData(reportOtherData);
            rp.setReportTime(reportTime);
            rp.setIsSuccess(isSuccess);
            db.save(rp);
        } else {
            rp.setOrderId(reportId);
            rp.setReportType(reportType);
            rp.setReportData(reportData);
            rp.setReportOtherData(reportOtherData);
            rp.setReportTime(reportTime);
            rp.setIsSuccess(isSuccess);
            db.update(rp);
        }

    }

    /**
     * 得到报告表(Report)数据  --  靠id
     * @param context
     * @param reportId
     */
    public static Report getReportFromDataBase(Context context, String reportId) {
        KingTellerDb db = KingTellerDb.create(context);
        Report report = db.findById(reportId, Report.class);
        return report;
    }

    /**
     * 得到报告表的json数据(ReportData)  --  靠id
     * @param context
     * @param reportId
     */
    public static String getReportDataFromDataBase(Context context, String reportId) {
        try {
            KingTellerDb db = KingTellerDb.create(context);
            Report report = db.findById(reportId, Report.class);
            if (report == null)
                return "";
            else
                return report.getReportData();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 删除报告数据   --   靠id
     * @param context
     * @param orderId
     */
    public static void deleteReportFromDataBase(Context context, String orderId) {
        KingTellerDb db = KingTellerDb.create(context);
        db.deleteById(Report.class, orderId);
    }

    
    
	/**
	 * 得到物流监控报告从数据库靠id
	 */
	public static String getLogsticReportDataFromDataBase(Context context,
			String rwdId) {
		try {
			KingTellerDb db = KingTellerDb.create(context);
			LogsticReport report = db.findById(rwdId, LogsticReport.class);
			if (report == null)
				return null;
			else
				return report.getAtacb();

		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 保存物流监控报告到数据库
	 */
	public static void saveLogsticReportToDataBase(Context context,String rwdId,
			int isSuccess,String atacb){
		KingTellerDb db = KingTellerDb.create(context);
		LogsticReport lrp = db.findById(rwdId, LogsticReport.class);
		if( lrp == null){
			lrp = new LogsticReport();
			lrp.setAtacb(atacb);
			lrp.setIsSuccess(isSuccess);
			lrp.setRwdId(rwdId);
			db.save(lrp);
		} else {
			lrp.setAtacb(atacb);
			lrp.setIsSuccess(isSuccess);
			lrp.setRwdId(rwdId);
			db.update(lrp);
		}
	}
	
	/**
	 * 删除物流监控报告
	 */
	public static void deleteLogsticReportFromDataBase(Context context, String rwdId) {
			KingTellerDb db = KingTellerDb.create(context);
			db.deleteById(LogsticReport.class, rwdId);

}
	
    /**报告暂存=========================================================================================END**/


	public static String getMaintainInfoClgcDataBase(KingTellerDb db,String maintainId){
		try{
			CommonMaintainInfo cmi = db.findById(maintainId, CommonMaintainInfo.class);
			if (cmi == null)
				return "";
			else
				return cmi.getMaintainData();
	
		}catch(Exception e){
			return "";
		}
	}

	/**
	 * 获取维护信息
	 */
	public static String getMaintainInfoDataBase(Context context, String maintainId) {
		try {
			KingTellerDb db = KingTellerDb.create(context);
			CommonMaintainInfo cmi = db.findById(maintainId, CommonMaintainInfo.class);
			if (cmi == null)
				return "";
			else
				return cmi.getMaintainData();
	
		} catch (Exception e) {
			
			return "";
		}
	}


	/**
	 * 保存维护信息到数据库
	 */
	public static void saveMaintainInfoDataBase(Context context,String maintainId
			,String maintainData,int isSuccess){
		KingTellerDb db = KingTellerDb.create(context);
		CommonMaintainInfo cmi = db.findById(maintainId, CommonMaintainInfo.class);
		if(cmi == null){
			cmi = new CommonMaintainInfo();
			cmi.setMaintainData(maintainData);
			cmi.setMaintainId(maintainId);
			cmi.setIsSuccess(isSuccess);
			db.save(cmi);
		}else{
			cmi = new CommonMaintainInfo();
			cmi.setMaintainData(maintainData);
			cmi.setMaintainId(maintainId);
			cmi.setIsSuccess(isSuccess);
			db.update(cmi);
		}
	}
	
	public static void saveMaintainInfoClgcDataBase(Context context, String maintainId, String maintainData,int isSuccess){
		KingTellerDb db = KingTellerDb.create(context);
		CommonMaintainInfo cmi = db.findById(maintainId, CommonMaintainInfo.class);
		if(cmi == null){
			cmi = new CommonMaintainInfo();
			cmi.setMaintainData(maintainData);
			cmi.setMaintainId(maintainId);
			cmi.setIsSuccess(isSuccess);
			db.save(cmi);
		}else{
			cmi = new CommonMaintainInfo();
			cmi.setMaintainData(maintainData);
			cmi.setMaintainId(maintainId);
			cmi.setIsSuccess(isSuccess);
			db.update(cmi);
		}
	}

    /**TODO: 网点机器部件扫描暂存=============================================================================START**/
    /**
     * 保存网点机器部件扫描数据   --  到数据库
     */
    public static void saveDotMachineToDataBase(Context context, String dotMachineId, String dotMachineType,
                                                String dotMachineData, String dotMachineOtherData, String dotMachineTime, int isSuccess) {
        KingTellerDb db = KingTellerDb.create(context);
        QRDotMachine dd = db.findById(dotMachineId, QRDotMachine.class);

        if (dd == null) {
            dd = new QRDotMachine();
            dd.setDotMachineId(dotMachineId);
            dd.setDotMachineType(dotMachineType);
            dd.setDotMachineData(dotMachineData);
            dd.setDotMachineOtherData(dotMachineOtherData);
            dd.setDotMachineTime(dotMachineTime);
            dd.setIsSuccess(isSuccess);
            db.save(dd);
        } else {
            dd.setDotMachineId(dotMachineId);
            dd.setDotMachineType(dotMachineType);
            dd.setDotMachineData(dotMachineData);
            dd.setDotMachineOtherData(dotMachineOtherData);
            dd.setDotMachineTime(dotMachineTime);
            dd.setIsSuccess(isSuccess);
            db.update(dd);
        }

    }

    /**
     * 得到网点机器部件扫描数据  --  靠id
     */
    public static QRDotMachine getDotMachineToDataBase(Context context, String dotMachineId) {
        KingTellerDb db = KingTellerDb.create(context);
        QRDotMachine dotMachine = db.findById(dotMachineId, QRDotMachine.class);
        if (dotMachine == null)
            return null;
        else
            return dotMachine;
    }

    /**
     * 删除网点机器部件扫描数据   --   靠id
     */
    public static void deleteDotMachineToDataBase(Context context, String dotMachineId) {
        KingTellerDb db = KingTellerDb.create(context);
        db.deleteById(QRDotMachine.class, dotMachineId);
    }


    /**网点机器部件扫描暂存==================================================================================END**/



    /**TODO: 网点机器部件扫描暂存_离线===========================================================================START**/
    /**
     * 保存网点机器部件扫描数据_离线   --  到数据库
     */
    public static void saveOfflineDotMachineToDataBase(Context context, String offlinedotMachineId, String offlinedotMachineType,
                                                       String offlinedotMachineData, String offlinedotMachineOtherData, String offlinedotMachineTime, int isSuccess) {
        KingTellerDb db = KingTellerDb.create(context);
        QROfflineDotMachine dd = db.findById(offlinedotMachineId, QROfflineDotMachine.class);

        if (dd == null) {
            dd = new QROfflineDotMachine();
            dd.setOfflinedotMachineId(offlinedotMachineId);
            dd.setOfflinedotMachineType(offlinedotMachineType);
            dd.setOfflinedotMachineData(offlinedotMachineData);
            dd.setOfflinedotMachineOtherData(offlinedotMachineOtherData);
            dd.setOfflinedotMachineTime(offlinedotMachineTime);
            dd.setIsSuccess(isSuccess);
            db.save(dd);
        } else {
            dd.setOfflinedotMachineId(offlinedotMachineId);
            dd.setOfflinedotMachineType(offlinedotMachineType);
            dd.setOfflinedotMachineData(offlinedotMachineData);
            dd.setOfflinedotMachineOtherData(offlinedotMachineOtherData);
            dd.setOfflinedotMachineTime(offlinedotMachineTime);
            dd.setIsSuccess(isSuccess);
            db.update(dd);
        }

    }

    /**
     * 得到网点机器部件扫描数据_离线  --  靠id
     */
    public static QROfflineDotMachine getOfflineDotMachineToDataBase(Context context, String offlinedotMachineId) {
        KingTellerDb db = KingTellerDb.create(context);
        QROfflineDotMachine offlinedotMachine = db.findById(offlinedotMachineId, QROfflineDotMachine.class);
        if (offlinedotMachine == null)
            return null;
        else
            return offlinedotMachine;
    }

    /**
     * 删除网点机器部件扫描数据_离线   --   靠id
     */
    public static void deleteOfflineDotMachineToDataBase(Context context, String offlinedotMachineId) {
        KingTellerDb db = KingTellerDb.create(context);
        db.deleteById(QROfflineDotMachine.class, offlinedotMachineId);
    }

    /**网点机器部件扫描暂存_离线==================================================================================END**/


    /**TODO: 网点机器部件更换暂存============================================================================START**/
    /**
     * 保存网点机器部件更换数据   --  到数据库
     */
    public static void saveDotMachineReplaceToDataBase(Context context, String dotMachineReplaceId,
                                                       String dotMachineReplaceType, String dotMachineReplaceData, String dotMachineReplaceOtherData,
                                                       String dotMachineReplaceTime, int isSuccess) {
        KingTellerDb db = KingTellerDb.create(context);
        QRDotMachineReplace dd = db.findById(dotMachineReplaceId, QRDotMachineReplace.class);

        if (dd == null) {
            dd = new QRDotMachineReplace();
            dd.setDotMachineReplaceId(dotMachineReplaceId);
            dd.setDotMachineReplaceType(dotMachineReplaceType);
            dd.setDotMachineReplaceData(dotMachineReplaceData);
            dd.setDotMachineReplaceOtherData(dotMachineReplaceOtherData);
            dd.setDotMachineReplaceTime(dotMachineReplaceTime);
            dd.setIsSuccess(isSuccess);
            db.save(dd);
        } else {
            dd.setDotMachineReplaceId(dotMachineReplaceId);
            dd.setDotMachineReplaceType(dotMachineReplaceType);
            dd.setDotMachineReplaceData(dotMachineReplaceData);
            dd.setDotMachineReplaceOtherData(dotMachineReplaceOtherData);
            dd.setDotMachineReplaceTime(dotMachineReplaceTime);
            dd.setIsSuccess(isSuccess);
            db.update(dd);
        }

    }

    /**
     * 得到网点机器部件更换数据  --  靠id
     */
    public static QRDotMachineReplace getDotMachineReplaceToDataBase(Context context, String dotMachineReplaceId) {
        KingTellerDb db = KingTellerDb.create(context);
        QRDotMachineReplace dotMachineReplace = db.findById(dotMachineReplaceId, QRDotMachineReplace.class);
        if (dotMachineReplace == null)
            return null;
        else
            return dotMachineReplace;
    }

    /**
     * 删除网点机器部件更换数据   --   靠id
     */
    public static void deleteDotMachineReplaceToDataBase(Context context, String dotMachineReplaceId) {
        KingTellerDb db = KingTellerDb.create(context);
        db.deleteById(QRDotMachineReplace.class, dotMachineReplaceId);
    }

    /**网点机器部件更换暂存=============================================================================END**/


    /**TODO: 网点机器部件更换暂存_离线============================================================================START**/
    /**
     * 保存网点机器部件更换数据_离线   --  到数据库
     */
    public static void saveOfflineDotMachineReplaceToDataBase(Context context, String offlinedotMachineReplaceId,
                                                              String offlinedotMachineReplaceType, String offlinedotMachineReplaceData, String offlinedotMachineReplaceOtherData,
                                                              String offlinedotMachineReplaceTime, int isSuccess) {
        KingTellerDb db = KingTellerDb.create(context);
        QROfflineDotMachineReplace dd = db.findById(offlinedotMachineReplaceId, QROfflineDotMachineReplace.class);

        if (dd == null) {
            dd = new QROfflineDotMachineReplace();
            dd.setOfflinedotMachineReplaceId(offlinedotMachineReplaceId);
            dd.setOfflinedotMachineReplaceType(offlinedotMachineReplaceType);
            dd.setOfflinedotMachineReplaceData(offlinedotMachineReplaceData);
            dd.setOfflinedotMachineReplaceOtherData(offlinedotMachineReplaceOtherData);
            dd.setOfflinedotMachineReplaceTime(offlinedotMachineReplaceTime);
            dd.setIsSuccess(isSuccess);
            db.save(dd);
        } else {
            dd.setOfflinedotMachineReplaceId(offlinedotMachineReplaceId);
            dd.setOfflinedotMachineReplaceType(offlinedotMachineReplaceType);
            dd.setOfflinedotMachineReplaceData(offlinedotMachineReplaceData);
            dd.setOfflinedotMachineReplaceOtherData(offlinedotMachineReplaceOtherData);
            dd.setOfflinedotMachineReplaceTime(offlinedotMachineReplaceTime);
            dd.setIsSuccess(isSuccess);
            db.update(dd);
        }

    }

    /**
     * 得到网点机器部件更换数据_离线  --  靠id
     */
    public static QROfflineDotMachineReplace getOfflineDotMachineReplaceToDataBase(Context context, String offlinedotMachineReplaceId) {
        KingTellerDb db = KingTellerDb.create(context);
        QROfflineDotMachineReplace offlinedotMachineReplace = db.findById(offlinedotMachineReplaceId, QROfflineDotMachineReplace.class);
        if (offlinedotMachineReplace == null)
            return null;
        else
            return offlinedotMachineReplace;
    }

    /**
     * 删除网点机器部件更换数据_离线   --   靠id
     */
    public static void deleteOfflineDotMachineReplaceToDataBase(Context context, String offlinedotMachineReplaceId) {
        KingTellerDb db = KingTellerDb.create(context);
        db.deleteById(QROfflineDotMachineReplace.class, offlinedotMachineReplaceId);
    }

    /**网点机器部件更换暂存_离线=============================================================================END**/


    /**TODO: 网点发货暂存=============================================================================START**/
    /**
     * 保存网点发货数据   --  到数据库
     */
    public static void saveDotDeliveryToDataBase(Context context, String dotDeliveryId, String dotDeliveryType,
                                                 String dotDeliveryData, String dotDeliveryOtherData , String dotDeliveryTime, int isSuccess) {
        KingTellerDb db = KingTellerDb.create(context);
        QRDotDelivery dd = db.findById(dotDeliveryId, QRDotDelivery.class);

        if (dd == null) {
            dd = new QRDotDelivery();
            dd.setDotDeliveryId(dotDeliveryId);
            dd.setDotDeliveryType(dotDeliveryType);
            dd.setDotDeliveryData(dotDeliveryData);
            dd.setDotDeliveryOtherData(dotDeliveryOtherData);
            dd.setDotDeliveryTime(dotDeliveryTime);
            dd.setIsSuccess(isSuccess);
            db.save(dd);
        } else {
            dd.setDotDeliveryId(dotDeliveryId);
            dd.setDotDeliveryType(dotDeliveryType);
            dd.setDotDeliveryData(dotDeliveryData);
            dd.setDotDeliveryOtherData(dotDeliveryOtherData);
            dd.setDotDeliveryTime(dotDeliveryTime);
            dd.setIsSuccess(isSuccess);
            db.update(dd);
        }

    }

    /**
     * 得到网点发货数据  --  靠id
     */
    public static QRDotDelivery getDotDeliveryToDataBase(Context context, String dotDeliveryId) {
        KingTellerDb db = KingTellerDb.create(context);
        QRDotDelivery dotDelivery = db.findById(dotDeliveryId, QRDotDelivery.class);
        if (dotDelivery == null)
            return null;
        else
            return dotDelivery;
    }

    /**
     * 删除网点发货数据   --   靠id
     */
    public static void deleteDotDeliveryToDataBase(Context context, String dotDeliveryId) {
        KingTellerDb db = KingTellerDb.create(context);
        db.deleteById(QRDotDelivery.class, dotDeliveryId);
    }
    /**网点发货暂存=============================================================================END**/


    /**TODO: 机器上传图片数据===========================================================================START**/
    /**
     * 保存机器上传图片数据   --  到数据库
     */
    public static void saveAtmGroupToDataBase(Context context, String atmGroupId, String atmGroupType,
                                                       String atmGroupJqBh, String atmGroupPicList, String atmGroupStrData, String reportTime) {
        KingTellerDb db = KingTellerDb.create(context);
        AtmGroup dd = db.findById(atmGroupId, AtmGroup.class);

        if (dd == null) {
            dd = new AtmGroup();
            dd.setAtmGroupId(atmGroupId);
            dd.setAtmGroupType(atmGroupType);
            dd.setAtmGroupJqBh(atmGroupJqBh);
            dd.setAtmGroupPicList(atmGroupPicList);
            dd.setAtmGroupStrData(atmGroupStrData);
            dd.setReportTime(reportTime);
            db.save(dd);
        } else {
            dd.setAtmGroupId(atmGroupId);
            dd.setAtmGroupType(atmGroupType);
            dd.setAtmGroupJqBh(atmGroupJqBh);
            dd.setAtmGroupPicList(atmGroupPicList);
            dd.setAtmGroupStrData(atmGroupStrData);
            dd.setReportTime(reportTime);
            db.update(dd);
        }

    }

    /**
     * 得到机器上传图片数据  --  靠id
     */
    public static AtmGroup getAtmGroupToDataBase(Context context, String atmGroupId) {
        KingTellerDb db = KingTellerDb.create(context);
        AtmGroup mAtmGroup = db.findById(atmGroupId, AtmGroup.class);
        if (mAtmGroup == null)
            return null;
        else
            return mAtmGroup;
    }

    /**
     * 删除机器上传图片数据   --   靠id
     */
    public static void deleteAtmGroupToDataBase(Context context, String atmGroupId) {
        KingTellerDb db = KingTellerDb.create(context);
        db.deleteById(AtmGroup.class, atmGroupId);
    }

    /**网点机器部件扫描暂存_离线==================================================================================END**/



}
