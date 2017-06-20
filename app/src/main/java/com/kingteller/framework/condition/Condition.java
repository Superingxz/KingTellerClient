package com.kingteller.framework.condition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import com.kingteller.framework.utils.StringUtil;

/**
 * 搜索条件封装类
 * 
 * @author tangql
 * @date 2010-08-02
 */
@SuppressWarnings("unchecked")
public class Condition {
	public static final String SP1 = "|*|";
	public static final String SP2 = "|@|";
	public static final String CONDITION = "condition";
	public static final String INDEX = "CONDITION_INDEX";
	public static final String REQUEST_RESULT = "ecsidePageResult";
	public static final String SORT_DIR_ASC = "asc";
	public static final String SORT_DIR_DESC = "desc";
	private String oriCondString = "";// 用于保持原始的条件字符串

	public Condition() {
		String defaultPageSize = "10";
		if (StringUtil.isEmpty(defaultPageSize)) {
			defaultPageSize = "10";
		}
		this.pageSize = Integer.parseInt(defaultPageSize);
	}

	private List condList = new ArrayList();// 存放条件对象，可以是FieldCond或string
	// private List fieldCondList=new ArrayList();//存放条件对象，是FieldCond
	private String sortField = "";// 排序字段
	private String sortDir = "asc";// 排序方向

	private String groupBy;// 分组函数;

	private boolean isSkipFromDesktop = false; // 是否是从首页跳转
	private int pageSize = 50;
	private int currentPage = 1;
	private long totalCount = 0;
	private String tableName;
	private String containerId;
	private boolean spell = true;// 传递一个消息给dbhelper，是否会组装sql语句附加在查询语句后面
	private boolean forceWhere = false;// 传递一个消息给dbhelper，必须在sql语句后加where 1=1
	private boolean sumTotal = true;// 传递一个消息，是否计算总记录数，默认为true，计算，false为不计算，会影响totalCount的值

	/*
	 * true表示该条件是从通过页面组装过来的，而非程序构造，默认为true，当为false的时候，获取原始条件字符串的时候，
	 * condition会重新组装，true的时候就直接返回界面传递过来的字符串
	 */
	private boolean fromPage = true;
	private String curUserId; // 当前登录系统的userid
	private String curOrgId; // 当前登录系统的orgid

	public boolean isSkipFromDesktop() {
		return isSkipFromDesktop;
	}

	public void setSkipFromDesktop(boolean isSkipFromDesktop) {
		this.isSkipFromDesktop = isSkipFromDesktop;
	}

	public boolean isFromPage() {
		return fromPage;
	}

	public void setFromPage(boolean fromPage) {
		this.fromPage = fromPage;
	}

	public boolean isForceWhere() {
		return forceWhere;
	}

	public void setForceWhere(boolean forceWhere) {
		this.forceWhere = forceWhere;
	}

	public boolean isSpell() {
		return spell;
	}

	public void setSpell(boolean spell) {
		this.spell = spell;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 快速增加一个条件，默认为字段为字符串，可指定是否模糊查询
	 * 
	 * @param fieldName字段名
	 *            ，默认为字符串
	 * @param value
	 *            值
	 * @param queryType
	 *            查询类型，1为精确查询，0为模糊查询
	 */
	// public void add(String fieldName,String value,String queryType){
	// FieldCond fieldCond=new FieldCond(fieldName,value,queryType);
	// if(!validateRepeat(fieldCond)){
	// this.add(fieldCond);
	// }
	// }
	/**
	 * 快速增加一个字符串条件
	 * 
	 * @param fieldName字段名
	 *            ，默认为字符串
	 * @param value
	 *            值
	 */
	public void add(String fieldName, String value) {
		FieldCond fieldCond = new FieldCond(fieldName, value,
				FieldCond.QUERY_TYPE_EQ);
		if (!validateRepeat(fieldCond)) {
			this.add(fieldCond);
		}
	}

	/**
	 * 快速增加一个查询条件
	 * 
	 * @param fieldName字段名
	 * @param value
	 * @param type字段类型
	 *            ，三种：string,date,number
	 */
	public void add(String fieldName, String value, String type) {
		int intType = DataTypes.VARBINARY;
		if ("date".equals(type)) {
			intType = DataTypes.DATE;
		} else if ("number".equals(type)) {
			intType = DataTypes.NUMBER;
		}
		FieldCond fieldCond = new FieldCond(fieldName, intType,
				FieldCond.QUERY_TYPE_EQ, value);
		if (!validateRepeat(fieldCond)) {
			this.add(fieldCond);
		}
	}

	/**
	 * 快速增加一个不等于的条件，默认为字符串
	 * 
	 * @param fieldName
	 * @param value
	 */
	// public void addNotEQ(String fieldName,String value){
	// FieldCond fieldCond=new
	// FieldCond(fieldName,DataTypes.VARCHAR,FieldCond.NOT_EQ,value);
	// if(!validateRepeat(fieldCond)){
	// this.add(fieldCond);
	// }
	// }
	// /**
	// * 快速增加一个数字类型不等于的条件
	// * @param fieldName
	// * @param value
	// */
	// public void addNumberNotEQ(String fieldName,String value){
	// FieldCond fieldCond=new
	// FieldCond(fieldName,DataTypes.NUMBER,FieldCond.NOT_EQ,value);
	// if(!validateRepeat(fieldCond)){
	// this.add(fieldCond);
	// }
	// }
	// /**
	// * 快速增加一个数字类型的条件，操作类型为“=”
	// * @param fieldName
	// * @param value
	// */
	// public void addNumber(String fieldName,String value){
	// FieldCond fieldCond=new
	// FieldCond(fieldName,DataTypes.NUMBER,FieldCond.EQ,value);
	// if(!validateRepeat(fieldCond)){
	// this.add(fieldCond);
	// }
	// }
	// /**
	// * 增加一个is null条件
	// * @param fieldName
	// */
	// public void addNull(String fieldName){
	// FieldCond fieldCond=new
	// FieldCond(fieldName,DataTypes.VARCHAR,FieldCond.IS,"null");
	// if(!validateRepeat(fieldCond)){
	// this.add(fieldCond);
	// }
	// }
	// /**
	// * 增加一个字符串区间条件
	// * @param strCond
	// */
	// public void addBetween(String fieldName,String value1,String value2){
	// FieldCond fieldCond=new
	// FieldCond(fieldName,DataTypes.VARCHAR,FieldCond.LARGE_LESS_EQ,value1,value2);
	// if(!validateRepeat(fieldCond)){
	// this.add(fieldCond);
	// }
	// }
	/**
	 * 增加一个数值区间条件
	 * 
	 * @param strCond
	 */
	public void addNumberBetween(String fieldName, String value1, String value2) {
		FieldCond fieldCond = new FieldCond(fieldName, DataTypes.NUMBER,
				FieldCond.LARGE_LESS_EQ, value1, value2);
		if (!validateRepeat(fieldCond)) {
			this.add(fieldCond);
		}
	}

	/**
	 * 增加一个日期区间条件
	 * 
	 * @param strCond
	 */
	public void addDateBetween(String fieldName, String value1, String value2) {
		FieldCond fieldCond = new FieldCond(fieldName, DataTypes.DATE,
				FieldCond.LARGE_LESS_EQ, value1, value2);
		if (!validateRepeat(fieldCond)) {
			this.add(fieldCond);
		}
	}

	// /**
	// * 增加一个is not null条件
	// * @param fieldName
	// */
	// public void addNotNull(String fieldName){
	// FieldCond fieldCond=new
	// FieldCond(fieldName,DataTypes.VARCHAR,FieldCond.ISNOT,"null");
	// if(!validateRepeat(fieldCond)){
	// this.add(fieldCond);
	// }
	// }
	// /**
	// * 快速增加一个日期类型的条件，操作类型为“=”
	// * @param fieldName
	// * @param value
	// */
	// public void addDate(String fieldName,String value){
	// FieldCond fieldCond=new
	// FieldCond(fieldName,DataTypes.DATE,FieldCond.EQ,value);
	// if(!validateRepeat(fieldCond)){
	// this.add(fieldCond);
	// }
	// }
	/**
	 * 增加一个or 条件的组,value以逗号分隔
	 * 
	 * @param fieldName
	 * @param value
	 * @strType 类型，string or date or number or timestamp
	 */
	// private void addOrGroup(String fieldName,String value,String strType){
	// int type=DataTypes.VARCHAR;
	// if("number".equalsIgnoreCase(strType)){
	// type=DataTypes.NUMBER;
	// }else if("date".equalsIgnoreCase(strType)){
	// type=DataTypes.DATE;
	// }else if("timestamp".equalsIgnoreCase(strType)){
	// type=DataTypes.TIMESTAMP;
	// }
	// String[] arrValue=value.trim().split(",");
	// //以分割的值构造FieldCond
	// for(int k=0;k<arrValue.length;k++){
	// FieldCond fieldCond=new
	// FieldCond(fieldName,type,FieldCond.EQ,arrValue[k],"");
	// fieldCond.setAndOr("or");
	// fieldCond.setGroupName(fieldName.trim());//以字段名称作为组名
	// if(!validateRepeat(fieldCond)){
	// this.add(fieldCond);
	// }
	// }
	// }

	/**
	 * 增加一个字符型的 or 条件组,value以逗号分隔
	 * 
	 * @param fieldName
	 * @param value
	 */
	// public void addOrGroup(String fieldName,String value){
	// addOrGroup(fieldName,value,"string");
	// }
	// /**
	// * 增加一个数字类型的or 条件组,value以逗号分隔
	// * @param fieldName
	// * @param value
	// */
	// public void addNumberOrGroup(String fieldName,String value){
	// addOrGroup(fieldName,value,"number");
	// }
	// /**
	// * 增加一个日期类型的 or 条件组,value以逗号分隔
	// * @param fieldName
	// * @param value
	// */
	// public void addDateOrGroup(String fieldName,String value){
	// addOrGroup(fieldName,value,"date");
	// }
	// /**
	// * 增加一个时间戳类型的 or 条件组,value以逗号分隔
	// * @param fieldName
	// * @param value
	// */
	// public void addTimeStampOrGroup(String fieldName,String value){
	// addOrGroup(fieldName,value,"date");
	// }
	/**
	 * 增加一个条件
	 * 
	 * @param fieldCond
	 */
	public void add(FieldCond fieldCond) {
		if (StringUtil.isEmpty(fieldCond.getValue1())
				&& StringUtil.isEmpty(fieldCond.getValue2())
				|| this.isSkipFromDesktop) {
			return;
		}
		if (!validateRepeat(fieldCond)) {
			// this.condList.add(fieldCond);
			if (fieldCond instanceof FieldCond) {
				if ("or".equals(fieldCond.getAndOr())) {
					boolean insertFlag = false;
					// 找到该组的条件，插入到后面
					for (int i = this.condList.size() - 1; i >= 0; i--) {
						Object objTmp = this.condList.get(i);
						if (!(objTmp instanceof FieldCond))
							continue;
						FieldCond field = (FieldCond) objTmp;
						if (fieldCond.getGroupName().equals(
								field.getGroupName())) {
							this.condList.add(i + 1, fieldCond);
							insertFlag = true;
							break;
						}
					}
					if (!insertFlag) {
						this.condList.add(fieldCond);
					}

				} else {
					this.condList.add(fieldCond);
				}
			}
		}

	}

	/**
	 * 增加一个组装后的条件
	 * 
	 * @param strCond
	 *            //
	 */
	public void addLike(String fieldName, String strCond) {
		if (!StringUtil.isEmpty(strCond)) {
			FieldCond fieldCond = new FieldCond(fieldName, DataTypes.VARCHAR,
					FieldCond.LIKE, strCond);
			if (!validateRepeat(fieldCond)) {
				this.add(fieldCond);
			}
		}
	}

	/**
	 * 增加一个组装后的条件
	 * 
	 * @param strCond
	 */
	public void add(String strCond) {
		if (!StringUtil.isEmpty(strCond)) {
			this.condList.add(strCond);
		}
	}

	// /**
	// * 增加一个FieldCond集合
	// * @param fieldCondList
	// */
	// public void add(List fieldCondList){
	// for(int i=0;i<fieldCondList.size();i++){
	// FieldCond fieldCond =(FieldCond)fieldCondList.get(i);
	// this.add(fieldCond);
	// }
	// }
	/**
	 * 根据字段名获取FieldCond
	 * 
	 * @param fieldName
	 * @return
	 */
	public FieldCond get(String fieldName) {
		FieldCond ret = null;
		for (int i = 0; i < condList.size(); i++) {
			Object object = condList.get(i);
			if (object instanceof FieldCond) {
				FieldCond fieldCond = (FieldCond) object;
				if (fieldName.equalsIgnoreCase(fieldCond.getFieldName())) {
					ret = fieldCond;
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * 如果字段的值是一个or组条件，那么该方法返回以逗号分隔的值
	 * 
	 * @param fieldName
	 * @return
	 */
	public String getGroupValue(String fieldName) {
		StringBuffer sb = new StringBuffer();
		// FieldCond ret=null;
		for (int i = 0; i < condList.size(); i++) {
			Object object = condList.get(i);
			if (object instanceof FieldCond) {
				FieldCond fieldCond = (FieldCond) object;
				if (fieldName.equalsIgnoreCase(fieldCond.getFieldName())) {
					sb.append(fieldCond.getValue1()).append(",");
				}
			}
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 如果字段的值是一个or组条件，那么该方法返回FieldCond数组
	 * 
	 * @param fieldName
	 * @return
	 */
	public FieldCond[] getGroup(String fieldName) {
		List retList = new ArrayList();
		for (int i = 0; i < condList.size(); i++) {
			Object object = condList.get(i);
			if (object instanceof FieldCond) {
				FieldCond fieldCond = (FieldCond) object;
				if (fieldName.equalsIgnoreCase(fieldCond.getFieldName())) {
					retList.add(fieldCond);
				}
			}
		}
		FieldCond[] retArr = new FieldCond[retList.size()];
		retList.toArray(retArr);
		return retArr;
	}

	/**
	 * 移除一个条件
	 * 
	 * @param fieldName
	 */
	public void remove(String fieldName) {
		for (int i = 0; i < condList.size(); i++) {

			Object object = condList.get(i);
			if (object instanceof FieldCond) {
				FieldCond fieldCond = (FieldCond) object;
				if (StringUtil.isNotEmpty(fieldName)
						&& fieldName.equalsIgnoreCase(fieldCond.getFieldName())) {
					condList.remove(fieldCond);
					i--;
				}
			}
		}
	}

	/**
	 * 判断是否有重复的条件
	 * 
	 * @param fieldCond
	 * @return
	 */
	private boolean validateRepeat(FieldCond fieldCond) {
		boolean ret = false;
		for (int i = 0; i < this.condList.size(); i++) {
			Object object = condList.get(i);
			if (object instanceof FieldCond) {
				FieldCond fCond = (FieldCond) object;
				if (fieldCond.getFieldName().equalsIgnoreCase(
						fCond.getFieldName())
						&& fieldCond.getOpt().equalsIgnoreCase(fCond.getOpt())
				// &&fieldCond.getValue1().equals(fCond.getValue1())
				// &&StringUtil.nullToEmpty(fieldCond.getValue2()).equals(StringUtil.nullToEmpty(fCond.getValue2()))
				) {
					if (fieldCond.getOpt().equalsIgnoreCase(fieldCond.IN)) {
						break;
					} else {
						ret = true;
						break;
					}
				}
			}
		}
		return ret;
	}

	/**
	 * 设定排序字段
	 * 
	 * @param sortField
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public List getFieldCondList() {
		return this.condList;
	}

	public String getSortField() {
		return this.sortField;
	}

	public String getSortDir() {
		return sortDir;
	}

	/**
	 * 设定排序方向
	 * 
	 * @param sortDir
	 */
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

	public int getPageSize() {
		return pageSize == 0 ? 50 : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取组装好的条件
	 * 
	 * @return
	 */
	public String getSqlWhere() {
		StringBuffer sb = new StringBuffer("");
		// 使用LinkedHashMap，使其取出有一定得先后顺序
		// Map groupMap=new LinkedHashMap();
		String preGroupName = "";
		for (int i = 0; i < this.condList.size(); i++) {
			Object fieldCondObj = this.condList.get(i);
			if (fieldCondObj instanceof FieldCond) {
				FieldCond fieldCond = (FieldCond) fieldCondObj;
				// 假如分了组，则组合在一个括号里面
				if (!StringUtil.isEmpty(fieldCond.getGroupName())) {
					String groupName = fieldCond.getGroupName();
					if (groupName.indexOf("-") != -1) {
						groupName = groupName.split("-")[0];
					}
					if (!preGroupName.equals(groupName)) {// 分组的第一个，或换了下一个组
						if (!"".equals(preGroupName)) {// 表示两个组接着的情况，要先结束上一个组
							sb.append(") ");
						}
						// 开始下一组
						sb.append(" and (");
						String strCond = fieldCond.toSqlWhere();
						strCond = strCond
								.replaceFirst(fieldCond.getAndOr(), "");
						sb.append(strCond);

					} else {// 表示当前组结束
						sb.append(fieldCond.toSqlWhere());

					}
					// 如果只有一组并且该组只有一个元素，则关闭括号
					if (i == (this.condList.size() - 1)) {
						sb.append(") ");
					}
					preGroupName = groupName;// fieldCond.getGroupName();
					// List tmp=(List)groupMap.get(fieldCond.getGroupName());
					// if(tmp==null){
					// tmp=new ArrayList();
					// groupMap.put(fieldCond.getGroupName(), tmp);
					// }
					// tmp.add(fieldCond);//把分组条件先存放起来,显不组装条件
				} else {
					if (!"".equals(preGroupName)) {
						sb.append(") ");
					}
					sb.append(fieldCond.toSqlWhere());
					preGroupName = "";
				}
			} else {
				sb.append((String) fieldCondObj);
			}
		}
		// //接下来处理分组的条件，分组条件要用括号括起来
		// Iterator keySet=groupMap.keySet().iterator();
		//
		// while(keySet.hasNext()){
		// String key=(String)keySet.next();
		// //取出分组条件
		// List cList=(List)groupMap.get(key);
		// sb.append(" and (");
		// for(int i=0;i<cList.size();i++){
		// FieldCond fieldCond =(FieldCond)cList.get(i);
		// String strCond=fieldCond.toSqlWhere();
		// //如果是第一个，则需要把前面的 and 或 or去掉
		// if(i==0){
		// strCond=strCond.replaceFirst(fieldCond.getAndOr(), "");
		// }
		// sb.append(strCond);
		// }
		// sb.append(")");
		// }

		return sb.toString();
	}

	/**
	 * 获取组装好的条件，预编译的格式
	 * 
	 * @return
	 */
	public String getPreSqlWhere() {
		StringBuffer sb = new StringBuffer("");
		// 使用LinkedHashMap，使其取出有一定得先后顺序
		// Map groupMap=new LinkedHashMap();
		String preGroupName = "";
		for (int i = 0; i < this.condList.size(); i++) {
			Object fieldCondObj = this.condList.get(i);
			if (fieldCondObj instanceof FieldCond) {
				FieldCond fieldCond = (FieldCond) fieldCondObj;
				// 假如分了组，则组合在一个括号里面
				if (!StringUtil.isEmpty(fieldCond.getGroupName())) {
					String groupName = fieldCond.getGroupName();
					if (groupName.indexOf("-") != -1) {
						groupName = groupName.split("-")[0];
					}
					if (!preGroupName.equals(groupName)) {// 分组的第一个，或换了下一个组
						if (!"".equals(preGroupName)) {// 表示两个组接着的情况，要先结束上一个组
							sb.append(") ");
						}
						// 开始下一组
						sb.append(" and (");
						String strCond = fieldCond.toPreSqlWhere();
						strCond = strCond
								.replaceFirst(fieldCond.getAndOr(), "");
						sb.append(strCond);
					} else {// 表示当前组结束
						sb.append(fieldCond.toPreSqlWhere());
					}
					// 如果只有一组并且该组只有一个元素，则关闭括号
					if (i == (this.condList.size() - 1)) {
						sb.append(") ");
					}
					preGroupName = groupName;// fieldCond.getGroupName();
					// List tmp=(List)groupMap.get(fieldCond.getGroupName());
					// if(tmp==null){
					// tmp=new ArrayList();
					// groupMap.put(fieldCond.getGroupName(), tmp);
					// }
					// tmp.add(fieldCond);//把分组条件先存放起来,显不组装条件
				} else {
					if (!"".equals(preGroupName)) {
						sb.append(") ");
					}
					sb.append(fieldCond.toPreSqlWhere());
					preGroupName = "";
				}
			} else {
				sb.append((String) fieldCondObj);
			}
		}
		// //接下来处理分组的条件，分组条件要用括号括起来
		// Iterator keySet=groupMap.keySet().iterator();
		//
		// while(keySet.hasNext()){
		// String key=(String)keySet.next();
		// //取出分组条件
		// List cList=(List)groupMap.get(key);
		// sb.append(" and (");
		// for(int i=0;i<cList.size();i++){
		// FieldCond fieldCond =(FieldCond)cList.get(i);
		// String strCond=fieldCond.toSqlWhere();
		// //如果是第一个，则需要把前面的 and 或 or去掉
		// if(i==0){
		// strCond=strCond.replaceFirst(fieldCond.getAndOr(), "");
		// }
		// sb.append(strCond);
		// }
		// sb.append(")");
		// }

		return sb.toString();
	}

	public String getOriCondString() {

		// 先把条件还原
		// 非or条件处理
		// 先将条件分类，分别放到不同的容器
		Map map = new LinkedHashMap();
		for (int i = 0; i < this.condList.size(); i++) {
			FieldCond cond = (FieldCond) this.condList.get(i);
			String groupName = cond.getGroupName();
			if (StringUtil.isEmpty(groupName)) {// 假如组名为空，则为普通
				List normal = null;
				if (map.containsKey("normal")) {// 表示容器已经存在一般的条件
					// 取出，加入当前条件
					normal = (List) map.get("normal");
				} else {
					normal = new ArrayList();
					map.put("normal", normal);
				}
				normal.add(cond);
			} else {// 表示分组条件
				List groupList = null;
				String key = groupName + cond.getFieldName();
				if (map.containsKey(key)) {// 组名和字段名相同的划分为一个or组
					// 取出，加入当前条件
					groupList = (List) map.get(key);
				} else {
					groupList = new ArrayList();
					map.put(key, groupList);
				}
				groupList.add(cond);
			}
		}
		// 分组完毕，开始组装原始字符串
		StringBuffer sb = new StringBuffer("");
		// 先处理一般的条件
		List list = (List) map.get("normal");
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				FieldCond cond = (FieldCond) list.get(i);
				sb.append(cond.toOriString()).append(Condition.SP1);
			}
		}
		// 处理or条件
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			if (!"normal".equals(key)) {
				list = (List) map.get(key);
				String value = "";
				for (int i = 0; i < list.size(); i++) {
					FieldCond cond = (FieldCond) list.get(i);
					value += cond.getValue1() + ",";
				}
				value = value.substring(0, value.length() - 1);// 逗号分隔的值
				FieldCond condTmp = (FieldCond) list.get(0);
				String oriValue = condTmp.getValue1();// 保持原来的值
				condTmp.setValue1(value);// 设置新的值，用于转换成原始的条件字符串
				String oriString = condTmp.toOriString();
				condTmp.setValue1(oriValue);// 还原原来的值
				sb.append(oriString).append(Condition.SP1);
			}
		}
		// 处理分页信息
		sb.append(this.currentPage).append(Condition.SP2).append(this.pageSize)
				.append(Condition.SP1);
		// 处理容器标识
		if (!StringUtil.isEmpty(this.getContainerId())) {
			sb.append("container:").append(this.getContainerId())
					.append(Condition.SP1);
		}
		if (!StringUtil.isEmpty(this.getSortField())) {
			sb.append(this.getSortField());
			if (!StringUtil.isEmpty(this.getSortDir())) {
				sb.append(" ").append(this.getSortDir());
			}
			sb.append(Condition.SP1);
		}
		sb.setLength(sb.length() - Condition.SP1.length());
		this.oriCondString = sb.toString();
		return this.oriCondString;
	}

	public void setOriCondString(String oriCondString) {
		this.oriCondString = oriCondString;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String toString() {
		return this.getSqlWhere();
	}

	public String getCurUserId() {
		return curUserId;
	}

	public void setCurUserId(String curUserId) {
		this.curUserId = curUserId;
	}

	public String getCurOrgId() {
		return curOrgId;
	}

	public void setCurOrgId(String curOrgId) {
		this.curOrgId = curOrgId;
	}

	public boolean isSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(boolean sumTotal) {
		this.sumTotal = sumTotal;
	}

	/**
	 * 把条件转换成Map
	 * 
	 * @return
	 */
	public Map<String, Object> getCondMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < condList.size(); i++) {
			FieldCond fieldCond = (FieldCond) condList.get(i);
			int intType = fieldCond.getType();
			if (intType == DataTypes.DATE) { // 该条件为日期类型
				map.put(fieldCond.getFieldName(), DateUtil.stringToDate(
						fieldCond.getValue1(), DateUtil.yyyy_MM_ddHHMMSS));
			} else if (intType == DataTypes.NUMBER) {// 查询条件为整数类型
				map.put(fieldCond.getFieldName(),
						new Integer(fieldCond.getValue1()));
			} else if (intType == DataTypes.VARCHAR) {// 查询条件为字符类型
				if (FieldCond.IN.equalsIgnoreCase(fieldCond.getOpt())) {
					if (map.containsKey(fieldCond.getFieldName())) {
						String value = map.get(fieldCond.getFieldName())
								.toString()
								+ ",'"
								+ fieldCond.getValue1()
								+ "'";
						map.put(fieldCond.getFieldName(), value);
					} else {
						map.put(fieldCond.getFieldName(),
								"'" + fieldCond.getValue1() + "'");
					}
				} else {
					map.put(fieldCond.getFieldName(), fieldCond.getValue1());
				}
			} else if (intType == DataTypes.MONEY) {
				// 查询条件为金额时，需要将页面上输入的金额乘以100 ，单位转为分
				map.put(fieldCond.getFieldName(),
						(new BigDecimal(fieldCond.getValue1())
								.multiply(new BigDecimal("100"))).longValue());
			}
		}
		if (StringUtil.isNotEmpty(this.getSortField())) {
			map.put("orderFields",
					this.getSortField() + " " + this.getSortDir());
		}
		return map;
	}

	public static void main(String[] args) throws Exception {
		Condition con = new Condition();
		con.setCurrentPage(1);
		con.setPageSize(20);
		System.out.print(con.getOriCondString());
	}

}
