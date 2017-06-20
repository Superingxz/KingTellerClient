package com.kingteller.framework.condition;

import java.util.HashMap;
import java.util.Map;

import com.kingteller.framework.utils.StringUtil;



/**
 * 字段搜索条件封装类
 * 
 * @author tangql
 * @date 2010-08-02
 */
@SuppressWarnings("unchecked")
public class FieldCond {
	public final static String QUERY_TYPE_EQ = "1"; // 精确查询
	public final static String QUERY_TYPE_LIKE = "0"; // 模糊查询
	// 比较
	/**
	 * 等于
	 */
	public final static String EQ = "=";
	/**
	 * 不等于
	 */
	public final static String NOT_EQ = "<>";
	/**
	 * 小于
	 */
	public final static String LESS_THAN = "<";
	/**
	 * 小于等于
	 */
	public final static String LESS_EQ_THAN = "<=";
	/**
	 * 大于
	 */
	public final static String LARGE_THAN = ">";
	/**
	 * 大于等于
	 */
	public final static String LARGE_EQ_THAN = ">=";
	/**
	 * 大于并且小于，不包括边界
	 */
	public final static String LARGE_LESS = "LL";
	/**
	 * 不小于并且不大于，包括边界
	 */
	public final static String LARGE_LESS_EQ = "LLE";
	/**
	 * 在某个区间
	 */
	public final static String BETWEEN = "BETWEEN";
	/**
	 * 模糊搜索
	 */
	public final static String LIKE = "LIKE";
	/**
	 * 左边匹配，如aa%
	 */
	public final static String LLIKE = "LLIKE";
	/**
	 * 右边匹配，如%aa
	 */
	public final static String RLIKE = "RLIKE";
	/**
	 * IS操作符
	 */
	public final static String IS = "IS";
	/**
	 * IS NOT操作符
	 */
	public final static String ISNOT = "IS NOT";
	/**
	 * IN操作符
	 */
	public final static String IN = "IN";

	public final static Map DATA_TYPE_INT_MAP = new HashMap();
	public final static Map DATA_TYPE_STR_MAP = new HashMap();

	static {
		DATA_TYPE_INT_MAP.put(DataTypes.VARCHAR, "string");
		DATA_TYPE_INT_MAP.put(DataTypes.DATE, "date");
		DATA_TYPE_INT_MAP.put(DataTypes.TIMESTAMP, "timestamp");
		DATA_TYPE_INT_MAP.put(DataTypes.NUMBER, "number");
		DATA_TYPE_INT_MAP.put(DataTypes.BOOLEAN, "boolean");
		DATA_TYPE_STR_MAP.put("string", DataTypes.VARCHAR);
		DATA_TYPE_STR_MAP.put("date", DataTypes.DATE);
		DATA_TYPE_STR_MAP.put("timestamp", DataTypes.TIMESTAMP);
		DATA_TYPE_STR_MAP.put("number", DataTypes.NUMBER);
		DATA_TYPE_STR_MAP.put("boolean", DataTypes.BOOLEAN);
		DATA_TYPE_STR_MAP.put("keyword", DataTypes.VARCHAR); // 对全文检索关键字生效
	}

	public final static String switchTypeToStr(int fieldType) {
		return (String) DATA_TYPE_INT_MAP.get(fieldType);
	}

	public final static int switchTypeToInt(String fieldType) {
		Object obj = DATA_TYPE_STR_MAP.get(fieldType);
		if (obj == null) {
			return DataTypes.VARCHAR;
		}
		return (Integer) DATA_TYPE_STR_MAP.get(fieldType);
	}

	private String fieldName;// 字段名
	private String alias;// 字段别名
	private String title;// 显示名称

	private String value1;// 值1
	private String value2;// 值2
	private String value1Title = "";// 值1

	private String value2Title = "";// 值2
	private String relId1 = "";// 关联对象id1

	private Object param = null;// 增加一个Object属性，用于传递一些特殊参数，在特殊情况下使用

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public String getRelId1() {
		return relId1;
	}

	public void setRelId1(String relId1) {
		this.relId1 = relId1;
	}

	public String getRelId2() {
		return relId2;
	}

	public void setRelId2(String relId2) {
		this.relId2 = relId2;
	}

	private String relId2 = "";// 关联对象id2

	private int type;// 字段类型，使用DataTypes定义的值
	private String opt;// 操作符，使用常量定义
	private String oriOpt;// 当自动转变操作符时，用于保持原来的操作符
	private String andOr = "and";// 默认为and
	// 多个条件分为一组时，具有相同的组名,组名规则如下:如果组名是该条件字段名，则表示该字段自成一组，
	// 如果groupName设置为“组名-本条件字段名”，表示和其他字段组成一个or条件组
	private String groupName = "";

	private String index = "0";// 条件顺序
	private String show = "1";// 是否参与组合成条件描述

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getGroupName() {
		String retGroupName = groupName;
		// if(!StringUtil.isEmpty(retGroupName)){
		// if(retGroupName.indexOf("-")!=-1){//表示和其他组合成一个or组
		// //截取真正的组名
		// retGroupName=retGroupName.split("-")[0];
		// }
		// }
		return retGroupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAndOr() {
		return andOr;
	}

	public void setAndOr(String andOr) {
		this.andOr = andOr;
	}

	private boolean change = false;
	private String changeValue;

	public FieldCond() {

	}

	public FieldCond(String fieldName, int type, String opt, String value1,
			String value2) {
		this.fieldName = fieldName;
		this.type = type;
		this.opt = opt;
		this.value1 = StringUtil.nullToEmpty(value1);
		this.value2 = StringUtil.nullToEmpty(value2);
		switchOpt();
	}

	public FieldCond(String fieldName, int type, String opt, String value1) {
		this.fieldName = fieldName;
		this.type = type;
		this.opt = opt;
		this.value1 = StringUtil.nullToEmpty(value1);
		switchOpt();
	}

	/**
	 * 对字符串字段的一个快速定义
	 * 
	 * @param fieldName
	 * @param value1
	 */
	public FieldCond(String fieldName, String value1) {
		this.fieldName = fieldName;
		this.type = DataTypes.VARCHAR;
		this.opt = EQ;
		this.value1 = StringUtil.nullToEmpty(value1);
		switchOpt();
	}

	public FieldCond(String fieldName, String value1, String queryType) {
		this.fieldName = fieldName;
		this.type = DataTypes.VARCHAR;
		this.opt = QUERY_TYPE_EQ.equals(queryType) ? EQ : LIKE;
		this.value1 = StringUtil.nullToEmpty(value1);
		switchOpt();
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
		switchOpt();
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
		switchOpt();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String toString() {
		return toSqlWhere();
	}

	public String getValue1Title() {
		return value1Title;
	}

	public void setValue1Title(String value1Title) {
		this.value1Title = value1Title;
	}

	public String getValue2Title() {
		return value2Title;
	}

	public void setValue2Title(String value2Title) {
		this.value2Title = value2Title;
	}

	/**
	 * 获取条件描述
	 * 
	 * @return
	 */
	public String getDescription() {

		String desc = this.title
				+ ":"
				+ (StringUtil.isEmpty(this.value1Title) ? this.value1
						: this.value1Title);
		if (!StringUtil.isEmpty(this.value2)) {
			desc = desc
					+ "至"
					+ (StringUtil.isEmpty(this.value2Title) ? this.value2
							: this.value2Title);
		}
		return desc;
	}

	/**
	 * 返回组装好的条件
	 * 
	 * @return
	 */
	public String toSqlWhere() {
		if (StringUtil.isEmpty(this.value1) && StringUtil.isEmpty(this.value2)) {
			return "";
		}
		String logic = " and ";
		if ("or".equals(this.andOr)) {
			logic = " or ";
		}
		String tmpValue1 = this.type == DataTypes.VARCHAR ? removeLawless(this.value1)
				: this.value1;
		String tmpValue2 = this.type == DataTypes.VARCHAR ? removeLawless(this.value2)
				: this.value2;
		StringBuffer sb = new StringBuffer(logic);
		if (LIKE.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" like  '%")
					.append((this.change ? this.changeValue : tmpValue1))
					.append("%' ");
		} else if (LLIKE.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" like  '")
					.append((this.change ? this.changeValue : tmpValue1))
					.append("%' ");
		} else if (RLIKE.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" like  '%")
					.append((this.change ? this.changeValue : tmpValue1))
					.append("' ");
		} else if (BETWEEN.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" between  ")
					.append(switchValue(tmpValue1)).append(" and ")
					.append(switchValue(tmpValue2));
		} else if (LARGE_LESS.equalsIgnoreCase(this.opt)) {
			sb.append(switchValue(tmpValue1)).append("<")
					.append(this.fieldName).append(" and  ")
					.append(this.fieldName).append("<")
					.append(switchValue(tmpValue2));
		} else if (LARGE_LESS_EQ.equalsIgnoreCase(this.opt)) {
			sb.append(switchValue(tmpValue1)).append("<=")
					.append(this.fieldName).append(" and  ")
					.append(this.fieldName).append("<=")
					.append(switchValue(tmpValue2));
		} else if (IS.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" IS NULL ");
		} else if (ISNOT.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" IS NOT NULL ");
		} else {
			sb.append(this.fieldName)
					.append(this.opt)
					.append(switchValue((this.change ? this.changeValue
							: tmpValue1)));
		}
		if ("and".equals(sb.toString().trim())) {
			sb.setLength(0);
		}
		return sb.toString();
	}

	/**
	 * 返回组装好的条件，预编译格式
	 * 
	 * @return
	 */
	public String toPreSqlWhere() {
		if (StringUtil.isEmpty(this.value1) && StringUtil.isEmpty(this.value2)) {
			return "";
		}
		String logic = " and ";
		if ("or".equals(this.andOr)) {
			logic = " or ";
		}
		StringBuffer sb = new StringBuffer(logic);
		// 处理一些特殊的比较符
		if (LIKE.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" like  '%'||?||'%'");
		} else if (LLIKE.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" like  ''||?||'%'");
		} else if (RLIKE.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" like  '%'||?||''");
		} else if (BETWEEN.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" between  ? and ?");
		} else if (LARGE_LESS.equalsIgnoreCase(this.opt)) {
			sb.append(switchValue(this.value1)).append("<")
					.append(this.fieldName).append(" and  ")
					.append(this.fieldName).append("<")
					.append(switchValue(this.value2));
		} else if (LARGE_LESS_EQ.equalsIgnoreCase(this.opt)) {
			sb.append("?<=").append(this.fieldName).append(" and  ")
					.append(this.fieldName).append("<=?");
		} else if (IS.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" IS NULL ");
		} else if (ISNOT.equalsIgnoreCase(this.opt)) {
			sb.append(this.fieldName).append(" IS NOT NULL ");
		} else {
			sb.append(this.fieldName).append(this.opt).append("?");
		}
		return sb.toString();
	}

	/**
	 * 将当前条件对象还原为原始构造字符串，
	 * 如“fieldName|@|type|@|opt|@|value1|@|value2|@|valueTitle1
	 * |@|valueTitle2|@|relId1|@|relId2|@|groupName”
	 * 
	 * @return
	 */
	public String toOriString() {
		// String sp1=Condition.SP1;
		String sp2 = Condition.SP2;
		StringBuffer sb = new StringBuffer("");
		sb.append(this.getFieldName()).append(sp2);// 字段名
		sb.append(switchTypeToStr(this.type)).append(sp2);// 数据类型
		sb.append(
				StringUtil.isEmpty(this.getOriOpt()) ? FieldCond.EQ : this
						.getOriOpt()).append(sp2);// 操作符
		sb.append(this.getValue1()).append(sp2);// 第一个值
		sb.append(StringUtil.isEmpty(this.getValue2()) ? " " : this.getValue2())
				.append(sp2);// 第二个值
		sb.append(
				StringUtil.isEmpty(this.getValue1Title()) ? " " : this
						.getValue1Title()).append(sp2);// 第一个值标题
		sb.append(
				StringUtil.isEmpty(this.getValue2Title()) ? " " : this
						.getValue1Title()).append(sp2);// 第二个值标题
		sb.append(StringUtil.isEmpty(this.getRelId1()) ? " " : this.getRelId1())
				.append(sp2);// 第一个值标题关联ID
		sb.append(StringUtil.isEmpty(this.getRelId2()) ? " " : this.getRelId2())
				.append(sp2);// 第二个值标题关联ID
		sb.append(StringUtil.isEmpty(this.getGroupName()) ? " " : this
				.getGroupName());// 组名
		return sb.toString();
	}

	/**
	 * 根据不同类型的转换成对应的值
	 * 
	 * @param value
	 * @return
	 */
	private String switchValue(String value) {
		if (this.type == DataTypes.VARCHAR || this.type == DataTypes.CHAR) {
			return "'" + value + "'";
		} else if (this.type == DataTypes.DATE) {
			if (value.length() < 10) {// 添加月份转换
				return "to_date('" + value.substring(0, 7) + "','yyyy-MM')";
			} else {
				return "to_date('" + value.substring(0, 10) + "','yyyy-MM-dd')";
			}
		} else if (this.type == DataTypes.TIMESTAMP) {
			return "to_date('" + value + "','yyyy-MM-dd HH24:mi:ss')";
		}
		return value;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

	public String getChangeValue() {
		return changeValue;
	}

	public void setChangeValue(String changeValue) {
		this.changeValue = changeValue;
	}

	public String getOriOpt() {
		return oriOpt;
	}

	public void setOriOpt(String oriOpt) {
		this.oriOpt = oriOpt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private void switchOpt() {
		if (BETWEEN.equalsIgnoreCase(this.opt)) {
			if ((!StringUtil.isEmpty(this.value1))
					&& StringUtil.isEmpty(this.value2)) {
				this.opt = LARGE_EQ_THAN;
			} else if (StringUtil.isEmpty(this.value1)
					&& (!StringUtil.isEmpty(this.value2))) {
				this.oriOpt = this.opt;
				this.opt = LESS_EQ_THAN;
				this.change = true;
				this.changeValue = this.value2;
			}
		} else if (LARGE_LESS.equalsIgnoreCase(this.opt)) {
			if ((!StringUtil.isEmpty(this.value1))
					&& StringUtil.isEmpty(this.value2)) {
				this.opt = LARGE_THAN;
			} else if (StringUtil.isEmpty(this.value1)
					&& (!StringUtil.isEmpty(this.value2))) {
				this.oriOpt = this.opt;
				this.opt = LESS_THAN;
				this.change = true;
				this.changeValue = this.value2;
			}
		} else if (LARGE_LESS_EQ.equalsIgnoreCase(this.opt)) {
			if ((!StringUtil.isEmpty(this.value1))
					&& StringUtil.isEmpty(this.value2)) {
				this.opt = LARGE_EQ_THAN;
			} else if (StringUtil.isEmpty(this.value1)
					&& (!StringUtil.isEmpty(this.value2))) {
				this.oriOpt = this.opt;
				this.opt = LESS_EQ_THAN;
				this.change = true;
				this.changeValue = this.value2;
			}
		}else{
			this.oriOpt = this.opt;
		}
	}

	private String removeLawless(String str) {
		if (StringUtil.isEmpty(str)) {
			return "";
		}

		return str.replaceAll("'", "''");
	}
}
