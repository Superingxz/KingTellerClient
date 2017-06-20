package com.kingteller.client.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.kingteller.client.bean.workorder.LoadBjglAtmpartsBean;
import com.kingteller.client.bean.workorder.LoadBjglAtmpartsWlBean;
import com.kingteller.client.bean.workorder.LoadClgcWorktypeBean;
import com.kingteller.client.bean.workorder.LoadHandleSubBean;
import com.kingteller.client.bean.workorder.LoadTroubleRemarkBean;
import com.kingteller.client.bean.workorder.LoadWlGeneralRelationBean;
import com.kingteller.client.bean.workorder.LoadWlInfoBean;
import com.kingteller.client.bean.workorder.LoadWlTreeInfoBean;

public class SQLiteHelper extends SQLiteOpenHelper{

	public final static String DB_PATH = "/data/data/com.kingteller/databases/";
	public final static String DB_NAME = "gzxx.db";
	private SQLiteDatabase database;
	private Context context;
	
	public SQLiteHelper(Context context){
		super(context, DB_NAME, null, 2);
		this.context = context;
	}
	
	public void createDatabase() throws Exception{
		/*this.getReadableDatabase();
		try{
			copyDatabase();
		} catch (IOException e) {
    		throw new Error("Error copying database");
    	}*/
		/*boolean dbExist = checkDataBase();
		if(dbExist){
		}else{*/
			this.getReadableDatabase();
			try{
				copyDatabase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
		//}
	}
	
	private boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			
		}catch(SQLiteException e){
			
		}
		if(checkDB != null){
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}
	
	private void copyDatabase() throws Exception{
		InputStream myInput = context.getAssets().open(DB_NAME);
		String outFileName = DB_PATH + DB_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		 
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
	}
	
	public String[] getMaxTimeArray(){
		SQLiteDatabase database = null;
		String[] maxTime = new String[10];
		String myPath = SQLiteHelper.DB_PATH + SQLiteHelper.DB_NAME;
		database = SQLiteDatabase.openOrCreateDatabase(myPath, null);
		
		Cursor c = database.rawQuery("select create_date from tb_kfwh_handle_sub order by create_date desc;",null);
		c.moveToFirst();
		maxTime[0] = c.getString(c.getColumnIndex("create_date"));
		
		c = database.rawQuery("select create_date from tb_kfwh_trouble_remark order by create_date desc;",null);
		c.moveToFirst();
		maxTime[1] = c.getString(c.getColumnIndex("create_date"));
		
		c = database.rawQuery("select modifyDateStr from tb_kfwh_worktype_handlesub order by modifyDateStr desc;",null);
		c.moveToFirst();
		maxTime[2] = c.getString(c.getColumnIndex("modifyDateStr"));
		
		c = database.rawQuery("select modifyDateStr from tb_sm_wl_info order by modifyDateStr desc;",null);
		c.moveToFirst();
		maxTime[3] = c.getString(c.getColumnIndex("modifyDateStr"));
		
		c = database.rawQuery("select modifyDateStr from tb_sm_wl_tree_info order by modifyDateStr desc;",null);
		c.moveToFirst();
		maxTime[4] = c.getString(c.getColumnIndex("modifyDateStr"));
		
		c = database.rawQuery("select create_date from tb_bj_wl_compatible_temp order by create_date desc;",null);
		c.moveToFirst();
		maxTime[5] = c.getString(c.getColumnIndex("create_date"));
		
		c = database.rawQuery("select update_date from tb_bjgl_atm_parts_config order by update_date desc;",null);
		c.moveToFirst();
		maxTime[6] = c.getString(c.getColumnIndex("update_date"));
		
		c = database.rawQuery("select update_date from tb_bjgl_atm_parts_config_wl order by update_date desc;",null);
		c.moveToFirst();
		maxTime[7] = c.getString(c.getColumnIndex("update_date"));
		
		c.close();
		
	/*	maxTime[0] = "2015-01-05";
		maxTime[1] = "2015-01-05";
		maxTime[2] = "2014-12-17";
		maxTime[3] = "2015-06-11";
		maxTime[4] = "2015-05-26";
		maxTime[5] = "2015-05-26";
		maxTime[6] = "2015-06-16 14:08";
		maxTime[7] = "2015-06-16 14:08";*/

		database.close();
		return maxTime;
	}
	
	public void insertHandleSub(List<LoadHandleSubBean> ldsbList,String handleSubId) throws SQLException, UnsupportedEncodingException{
		if(ldsbList.size() <= 0){
			return;
		}
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		for(int i=0;i < ldsbList.size();i ++){
			LoadHandleSubBean ldsb = ldsbList.get(i);
			database.execSQL("delete from tb_kfwh_handle_sub where _id = '"+ldsb.getId()+"'");
			database.execSQL("insert into tb_kfwh_handle_sub values ('"+ldsb.getId()+"','"+ldsb.getHandleSub()+"','"
					+ldsb.getRemark()+"','"+ldsb.getCreateDateStr()+"','"+ldsb.getCreateUserid()+"','"+ldsb.getStatus()+"','"
					+ldsb.getReId()+"')");
		}
		database.close();
	}
	
	public void insertTroubleRemark(List<LoadTroubleRemarkBean> ltrbList,String troubleRemarkId) throws SQLException, UnsupportedEncodingException{
		if(ltrbList.size() <= 0){
			return;
		}
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);	
		String update_flag = "";
		String query_type = "1";
		for(int i=0;i < ltrbList.size();i ++){
			LoadTroubleRemarkBean ltrb = ltrbList.get(i);
			database.execSQL("delete from tb_kfwh_trouble_remark where _id = '"+ltrb.getId()+"'");
			database.execSQL("insert into tb_kfwh_trouble_remark values ('"+ltrb.getId()+"','"+ltrb.getTroubleRemark()+"','"
					+ltrb.getRemark()+"','"+ltrb.getCreateDateStr()+"','"+ltrb.getCreateUserid()+"',"+ltrb.getStatus()+",'"
					+ltrb.getParentId()+"','"+ltrb.getTwoLevelId()+"','"+ltrb.getThreeLevelId()+"',"+ltrb.getIndexnum()+",'"
					+update_flag+"','"+query_type+"','"+ltrb.getPathId()+"','"+ltrb.getPathName()+"')");
		}
		database.close();
	}
	
	public void insertWorkTypeHandleSub(List<LoadClgcWorktypeBean> cwtbList,String clgcWorkTypeId) throws SQLException, UnsupportedEncodingException{
		if(cwtbList.size() <= 0){
			return;
		}
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		String modify_date = "";
		for(int i=0;i < cwtbList.size();i ++){
			LoadClgcWorktypeBean cwtb = cwtbList.get(i);
			database.execSQL("delete from tb_kfwh_worktype_handlesub where _id = '"+cwtb.getId()+"'");
			String sql = "insert into tb_kfwh_worktype_handlesub values ('"+cwtb.getId()+"','"+cwtb.getHandleSub()+"','"
					+cwtb.getRemark()+"','"+cwtb.getParentId()+"','"+modify_date+"','"+cwtb.getModifyUserid()+"','"
					+cwtb.getModifyDateStr()+"')";
			database.execSQL(sql);
		}
		database.close();
	}
	
	public void insertSmWlInfo(List<LoadWlInfoBean> swibList,String wlInfoId) throws SQLException, UnsupportedEncodingException{
		if(swibList.size() <= 0){
			return;
		}
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		String pplb = "Y";
		for(int i=0;i < swibList.size();i ++){
			LoadWlInfoBean swib = swibList.get(i);
			database.execSQL("delete from tb_sm_wl_info where _id = '"+swib.getWlInfoId()+"'");
			database.execSQL("insert into tb_sm_wl_info values ('"+swib.getWlInfoId()+"','"+swib.getNewCode()+"','"
					+swib.getOldCode()+"','"+swib.getWlName()+"','"+swib.getK3Name()+"','"+swib.getModelNo()+"','"
					+swib.getTypeId()+"','"+swib.getStatus()+"','"+swib.getWlDesc()+"','"+swib.getCpNewcode()+"','"
					+swib.getTypeflag()+"','"+swib.getBaseUnit()+"','"+swib.getModifyDateStr()+"','"
					+pplb+"')");
		}
		database.close();
	}
	
	public void insertSmWlTreeInfo(List<LoadWlTreeInfoBean> wtibList,String wlTreeInfoId) throws SQLException, UnsupportedEncodingException{
		if(wtibList.size() <= 0){
			return;
		}
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		for(int i=0;i < wtibList.size();i ++){
			LoadWlTreeInfoBean wtib = wtibList.get(i);
			database.execSQL("delete from tb_sm_wl_tree_info where _id = '"+wtib.getId()+"'");
			database.execSQL("insert into tb_sm_wl_tree_info values ('"+wtib.getId()+"','"+wtib.getNewCode()+"','"
					+wtib.getWlIndex()+"','"+wtib.getParentId()+"','"+wtib.getPathid()+"','"+wtib.getNodeLevel()+"','"
					+wtib.getPathname()+"','"+wtib.getWlInfoId()+"','"+wtib.getModifyDateStr()+"')");
		}
		database.close();
	}

	public void insertSmWlCompatibleTempInfo(List<LoadWlGeneralRelationBean> wlTygxList, String wlTreeInfoId) throws SQLException, UnsupportedEncodingException{
		if(wlTygxList.size() <= 0){
			return;
		}
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		String operate_date = "";
		for(int i=0;i < wlTygxList.size();i ++){
			LoadWlGeneralRelationBean wlTygx = wlTygxList.get(i);
			database.execSQL("delete from tb_bj_wl_compatible_temp where temp_id = '" + wlTygx.getTempId() + "'");
			database.execSQL("insert into tb_bj_wl_compatible_temp values ('"+wlTygx.getTempId()+"','"+wlTygx.getOperateDateStr()+"','"
					+wlTygx.getCreateUserId()+"','"+wlTygx.getTempStatus()+"','"+operate_date+"','"+wlTygx.getOperateUserId()+"','"
					+wlTygx.getWlInfoId()+"','"+wlTygx.getWlTypeId()+"','"+wlTygx.getRemark()+"')");
		}
		database.close();
	}
	
	public void insertAtmPartsConfigListInfo(List<LoadBjglAtmpartsBean> bjbpgxList, String bjbpInfoId) throws SQLException, UnsupportedEncodingException{
		if(bjbpgxList.size() <= 0){
			return;
		}
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		for(int i=0;i < bjbpgxList.size();i ++){
			LoadBjglAtmpartsBean bjbpgx = bjbpgxList.get(i);
			database.execSQL("delete from tb_bjgl_atm_parts_config where config_id = '" + bjbpgx.getConfigId() + "'");
			database.execSQL("insert into tb_bjgl_atm_parts_config values ('"+bjbpgx.getConfigId()+"','"+bjbpgx.getConfigName()+"','"
					+bjbpgx.getNodeLevel()+"','"+bjbpgx.getParentId()+"','"+bjbpgx.getStatus()+"','"+bjbpgx.getIndexNum()+"','"
					+bjbpgx.getIsScan()+"','"+bjbpgx.getRemark()+"','"+bjbpgx.getCreateDateStr()+"','"+bjbpgx.getCreateUserId()+"','"
					+bjbpgx.getConfigPath()+"','"+bjbpgx.getIsJixin()+"','"+bjbpgx.getIsMany()+"','"+bjbpgx.getUpdateDateStr()+"')");
		}
		database.close();
	}
	
	public void insertAtmPartsConfigWlInfo(List<LoadBjglAtmpartsWlBean> bjwlgxList, String bjwlInfoId) throws SQLException, UnsupportedEncodingException{
		if(bjwlgxList.size() <= 0){
			return;
		}
		String myPath = SQLiteHelper.DB_PATH+SQLiteHelper.DB_NAME;
		SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(myPath,null);
		for(int i=0;i < bjwlgxList.size();i ++){
			LoadBjglAtmpartsWlBean bjwlgx = bjwlgxList.get(i);
			database.execSQL("delete from tb_bjgl_atm_parts_config_wl where config_form_id = '" + bjwlgx.getConfigFormId() + "'");
			database.execSQL("insert into tb_bjgl_atm_parts_config_wl values ('"+bjwlgx.getConfigFormId()+"','"+bjwlgx.getConfigId()+"','"
					+bjwlgx.getWlInfoId()+"','"+bjwlgx.getNewCode()+"','"+bjwlgx.getWlName()+"','"+bjwlgx.getRemark()+"','"
					+bjwlgx.getCreateDateStr()+"','"+bjwlgx.getCreateUserId()+"','"+bjwlgx.getStatus()+"','"+bjwlgx.getUpdateDateStr()+"')");
		}
		database.close();
	}
	
	
	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		if(database != null){
			database.close();
		}
		super.close();
	}
	
	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}
	
	public String changeStr(String str){
		return str.replace("-", "/");
	}
	
	public String getUtf8(String str) throws UnsupportedEncodingException{
		String description = null;
		description = new String(str.getBytes("gbk"), "gbk");
		return description;
	}
	
	/**
	 * CREATE TABLE KTCS.TB_KFWH_TROUBLE_REMARK
(
  _ID              VARCHAR2(32 BYTE)             NOT NULL,
  TROUBLE_REMARK  VARCHAR2(200 BYTE),
  REMARK          VARCHAR2(200 BYTE),
  CREATE_DATE     DATE,
  CREATE_USERID   VARCHAR2(32 BYTE),
  STATUS          NUMBER(1),
  PARENT_ID       VARCHAR2(32 BYTE),
  TWO_LEVEL_ID    VARCHAR2(32 BYTE),
  THREE_LEVEL_ID  VARCHAR2(32 BYTE),
  INDEXNUM        NUMBER(1),
  UPDATE_FLAG     NUMBER(2)
)
	 * */
	
	/**
	 * create table TB_KFWH_WORKTYPE_HANDLESUB
(
  _ID            VARCHAR2(32) not null,
  HANDLE_SUB    VARCHAR2(500),
  REMARK        VARCHAR2(200),
  PARENT_ID     VARCHAR2(32),
  MODIFY_DATE   DATE,
  MODIFY＿USERID VARCHAR2(50)
)



create table TB_KFWH_HANDLE_SUB
(
  _ID            VARCHAR2(32) not null,
  HANDLE_SUB    VARCHAR2(200),
  REMARK        VARCHAR2(200),
  CREATE_DATE   DATE,
  CREATE_USERID VARCHAR2(32),
  STATUS        NUMBER(1),
  RE_ID         VARCHAR2(32)
)


create table TB_KFWH_TROUBLE_REMARK
(
  _ID             VARCHAR2(32) not null,
  TROUBLE_REMARK VARCHAR2(200),
  REMARK         VARCHAR2(200),
  CREATE_DATE    DATE,
  CREATE_USERID  VARCHAR2(32),
  STATUS         NUMBER(1),
  PARENT_ID      VARCHAR2(32),
  TWO_LEVEL_ID   VARCHAR2(32),
  THREE_LEVEL_ID VARCHAR2(32),
  INDEXNUM       NUMBER(1),
  UPDATE_FLAG    NUMBER(2)
)


create table TB_SM_WL_TREE_INFO
(
  _ID            VARCHAR2(32) not null,
  NEW_CODE      VARCHAR2(30),
  WL_INDEX      NUMBER(6),
  PARENT_ID     VARCHAR2(32),
  PATHID        VARCHAR2(1000),
  NODE_LEVEL    NUMBER(4),
  PATHNAME      VARCHAR2(1000),
  WL_INFO_ID    VARCHAR2(32) not null,
  MODIFY_DATE   DATE,
  MODIFY＿USERID VARCHAR2(50)
)

create table TB_SM_WL_INFO
(
  _ID    VARCHAR2(32) primary key,
  NEW_CODE      VARCHAR2(30),
  OLD_CODE      VARCHAR2(30),
  WL_NAME       VARCHAR2(200),
  K3_NAME       VARCHAR2(200),
  MODEL_NO      VARCHAR2(200),
  TYPE_ID       VARCHAR2(32),
  STATUS        Integer,
  WL_DESC       VARCHAR2(200),
  CP_NEWCODE    VARCHAR2(200),
  TYPEFLAG      NUMBER(2),
  BASE_UNIT     VARCHAR2(50),
  PRICE         NUMBER(8),
  MODIFY_DATE   DATETime,
  MODIFY＿USERID VARCHAR2(50)
)
	 * */
	
}
