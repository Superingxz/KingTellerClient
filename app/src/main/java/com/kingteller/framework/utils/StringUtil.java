package com.kingteller.framework.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * 字符串操作工具类
 * 
 */
public class StringUtil {
	/**
	 * 去掉字符串的开头与结尾的空白
	 */
	public static String trim(String str) {
		return str.trim();
	}

	/**
	 * 去掉字符串数组中每个字符元素的开头与结尾的空白
	 */
	public static String[] trim(String[] strs) {
		if (isEmptyArray(strs)) {
			return strs;
		}
		for (int i = 0, len = strs.length; i < len; i++) {
			strs[i] = trim(strs[i]);
		}
		return strs;
	}

	/**
	 * 遍历对象中的属性，如果属于字符串属性，则去掉该值的空格
	 */
	public static Object trim(Object obj) {
		if (null == obj) {
			return obj;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		if (isEmptyArray(fields)) {
			return obj;
		}
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object value = field.get(obj);
				if (isNotStringClass(value)) {
					continue;
				}
				field.set(obj, trim(value.toString()));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	// /**
	// * 遍历Map中的对象，如果该对象属于字符串属性，则去掉该对象的空格
	// */
	// @SuppressWarnings("unchecked")
	// public static Map trim(Map map) {
	// if (isEmptyMap(map)) {
	// return map;
	// }
	// Set keys = map.keySet();
	// if (CollectionHelper.isEmpty(keys)) {
	// return map;
	// }
	// for (Object key : keys) {
	// Object value = map.get(key);
	// if (isNotStringClass(value)) {
	// continue;
	// }
	// map.put(key, trim(value.toString()));
	// }
	// return map;
	// }

	private static boolean isNotStringClass(Object value) {
		return null == value || value.getClass() != String.class;
	}

	/**
	 * 判断字符串是否为空值或者长度为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {

		return str == null || str.trim().length() == 0;
	}

	/**
	 * 判断字符串是否不为空(包括不为空和不为空字符串)
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断字符串是否可以转为数值
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNummeric(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return false;
		for (int i = 0; i < strLen; i++)
			if (!Character.isDigit(str.charAt(i)))
				return false;
		return true;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return true;
		for (int i = 0; i < strLen; i++)
			if (!Character.isWhitespace(str.charAt(i)))
				return false;

		return true;
	}

	/**
	 * 判断两个String类型数据是否不相等 如果两个字符串的值相同，则返回false 如果两个字符串的值不同，则返回true
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isChange(String str1, String str2) {
		if (isNotEmpty(str1) && isNotEmpty(str2) && str1.equals(str2)) {
			return false;
		} else if (isEmpty(str1) && isEmpty(str2)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断两个String类型数据是否相等 如果两个字符串的值相同，则返回true (如果两个字符串都为 空，也返回true )
	 * 如果两个字符串的值不同，则返回false
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquls(String str1, String str2) {
		if (isNotEmpty(str1) && isNotEmpty(str2) && str1.equals(str2)) {
			return true;
		} else if (isEmpty(str1) && isEmpty(str2)) {
			return true;
		} else
			return false;
	}

	/**
	 * 判断两个Date类型数据是否一样
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isChange(Date str1, Date str2) {
		if (null != str1 && null != str2 && str1.equals(str2)) {
			return false;
		} else if (null == str1 && null == str2) {
			return false;
		}
		return true;
	}

	/**
	 * 判断两个Long类型数据是否一样
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isChange(Long str1, Long str2) {
		if (null != str1 && null != str2 && str1.equals(str2)) {
			return false;
		} else if (null == str1 && null == str2) {
			return false;
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static String trimLeadingWhitespace(String str) {
		if (isEmpty(str))
			return str;
		StringBuffer buf;
		for (buf = new StringBuffer(str); buf.length() > 0
				&& Character.isWhitespace(buf.charAt(0)); buf.deleteCharAt(0))
			;
		return buf.toString();
	}

	public static String trimTrailingWhitespace(String str) {
		if (isEmpty(str))
			return str;
		StringBuffer buf;
		for (buf = new StringBuffer(str); buf.length() > 0
				&& Character.isWhitespace(buf.charAt(buf.length() - 1)); buf
				.deleteCharAt(buf.length() - 1))
			;
		return buf.toString();
	}

	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (isEmpty(str))
			return str;
		StringBuffer buf;
		for (buf = new StringBuffer(str); buf.length() > 0
				&& buf.charAt(0) == leadingCharacter; buf.deleteCharAt(0))
			;
		return buf.toString();
	}

	public static String trimTrailingCharacter(String str,
			char trailingCharacter) {
		if (isEmpty(str))
			return str;
		StringBuffer buf;
		for (buf = new StringBuffer(str); buf.length() > 0
				&& buf.charAt(buf.length() - 1) == trailingCharacter; buf
				.deleteCharAt(buf.length() - 1))
			;
		return buf.toString();
	}

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null)
			return false;
		if (str.startsWith(prefix))
			return true;
		if (str.length() < prefix.length()) {
			return false;
		} else {
			String lcStr = str.substring(0, prefix.length()).toLowerCase();
			String lcPrefix = prefix.toLowerCase();
			return lcStr.equals(lcPrefix);
		}
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null)
			return false;
		if (str.endsWith(suffix))
			return true;
		if (str.length() < suffix.length()) {
			return false;
		} else {
			String lcStr = str.substring(str.length() - suffix.length())
					.toLowerCase();
			String lcSuffix = suffix.toLowerCase();
			return lcStr.equals(lcSuffix);
		}
	}

	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0
				|| sub.length() == 0)
			return 0;
		int count = 0;
		int pos = 0;
		for (int idx = 0; (idx = str.indexOf(sub, pos)) != -1;) {
			count++;
			pos = idx + sub.length();
		}

		return count;
	}

	public static boolean equals(String str1, String str2) {
		return str1 != null ? str1.equals(str2) : str2 == null;
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 != null ? str1.equalsIgnoreCase(str2) : str2 == null;
	}

	public static String capitalize(String str) {
		int len;
		if (str == null || (len = str.length()) == 0)
			return str;
		else
			return (new StringBuffer(len))
					.append(Character.toUpperCase(str.charAt(0)))
					.append(str.substring(1)).toString();
	}

	public static String uncapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return str;
		else
			return (new StringBuffer(strLen))
					.append(Character.toLowerCase(str.charAt(0)))
					.append(str.substring(1)).toString();
	}

	public static String parseColumnName2PropertyName(String columnName) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		columnName = columnName.toLowerCase();
		for (int i = 0; i < columnName.length(); i++) {
			char ch = columnName.charAt(i);
			if (ch == '_')
				flag = true;
			else if (flag) {
				sb.append(Character.toUpperCase(ch));
				flag = false;
			} else {
				sb.append(ch);
			}
		}

		return sb.toString();
	}

	public static Long longFromDotIPStr(String ipStr) {
		if (ipStr == null)
			throw new IllegalArgumentException();
		StringBuffer sb = new StringBuffer();
		String tmp[] = (String[]) null;
		Long rst = null;
		tmp = ipStr.split("\\.");
		if (tmp.length != 4)
			return null;
		for (int i = 0; i < tmp.length; i++)
			if (tmp[i].length() == 1)
				sb.append("00").append(tmp[i]);
			else if (tmp[i].length() == 2)
				sb.append('0').append(tmp[i]);
			else if (tmp[i].length() == 3)
				sb.append(tmp[i]);
			else
				return null;

		try {
			rst = Long.valueOf(sb.toString());
		} catch (NumberFormatException e) {
			return null;
		}
		return rst;
	}

	/**
	 * 将对象转换为字符串 如果该对象为空，则返回null
	 * 
	 * @param obj
	 * @return
	 */
	public static String ObjectToString(Object obj) {
		if (obj != null)
			return obj.toString();
		else
			return null;
	}

	/**
	 * 将对象转换为字符串 如果该对象为空，则转为空字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String ObjectToStringNullToString(Object obj) {
		if (obj != null)
			return obj.toString();
		else
			return "";
	}

	public static Long addLongToString(String value, Long increment) {
		Long v = Long.valueOf(value);
		Long rst = Long.valueOf(v.longValue() + increment.longValue());
		return rst;
	}

	public static Long addStringToLong(Long value, String increment) {
		Long v = Long.valueOf(increment);
		Long rst = Long.valueOf(v.longValue() + value.longValue());
		return rst;
	}

	public static String handleNULL(String input) {
		return handleNULL(input, "");
	}

	public static String handleNULL(String input, String def) {
		if (input == null || input.trim().length() <= 0 || "".equals(input))
			return def;
		else
			return input.trim();
	}

	public static boolean contains(String a[], String s) {
		boolean bRetVal = false;
		for (int i = 0; i < a.length; i++) {
			if (!a[i].trim().equals(s.trim()))
				continue;
			bRetVal = true;
			break;
		}

		return bRetVal;
	}

	public static String convertA2BEncoding(String input, String fromEncoding,
			String toEncoding) throws UnsupportedEncodingException {
		return input != null ? new String(input.trim().getBytes(fromEncoding),
				toEncoding) : "";
	}

	private static boolean isEmptyArray(Object array[]) {
		return array == null || array.length == 0;
	}

	@SuppressWarnings("unchecked")
	private static boolean isEmptyMap(Map map) {
		return map == null || map.isEmpty();
	}

	/**
	 * null字符串转换成""，如果不为null则返回原值
	 * 
	 * @param value
	 * @return
	 */
	public final static String nullToEmpty(String value) {
		if (isEmpty(value)) {
			return "";
		}
		return value;
	}

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 *            表示生成字符串的长度
	 * @return
	 */
	public static String randomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 将字符串转换成数组
	 * 
	 * @param string
	 *            String
	 * @param split
	 *            分隔符
	 * @return
	 */
	public static String[] stringToArray(String string, String split) {
		StringTokenizer stString = new StringTokenizer(string, split);
		String[] array = new String[stString.countTokens()];
		int i = 0;
		while (stString.hasMoreTokens()) {
			array[i++] = stString.nextToken();
		}
		return array;
	}

	/**
	 * 将字符串转换成Map
	 * 
	 * @param string
	 *            String
	 * @param split
	 *            分隔符
	 * @return
	 */
	public static Map<String, String> stringToMap(String string, String split) {
		StringTokenizer stString = new StringTokenizer(string, split);
		Map<String, String> map = new HashMap<String, String>();
		while (stString.hasMoreTokens()) {
			String id = stString.nextToken();
			map.put(id, id);
		}
		return map;
	}

	/**
	 * 
	 * 将字符串数组 String[]转换为逗号分隔的字符串
	 * 
	 * @param fid的String
	 *            []
	 * @return
	 */
	public static String arrayToString(String[] fid) {
		String serperater = ",";
		return arrayToString(fid, serperater);
	}

	public static String arrayToString(String[] fid, String serperater) {
		String strfid = "";
		if (fid != null && fid.length > 0) {
			for (int i = 0; i < fid.length; i++) {
				if (fid[i] != null && fid[i].length() > 0) {
					strfid += fid[i] + serperater;
				}
			}
			if (strfid.length() > 0)
				strfid = strfid.substring(0, strfid.length() - 1);
		}
		return strfid;
	}

	/**
	 * 将整数格式化为指定长度的字符串
	 * 
	 * @param int intSeq 需要格式化的正整数
	 * @param int length 目标字符串的长度
	 * @return
	 */
	public static String formatStringByLength(int intSeq, int length) {
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		return String.format("%0" + length + "d", intSeq);
	}

	/**
	 * 将数组转换成 以逗号隔开的字符串 用于sql in () 语句:例如： '11','111','111'
	 * 
	 * @param str
	 * @return
	 */
	public static String getInSqlWhere(String[] str) {
		String retStr = "";
		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && str[i].length() > 0)
				retStr += "'" + str[i] + "'" + ",";
		}
		return retStr.length() > 0 ? retStr.substring(0,
				retStr.lastIndexOf(",")) : retStr;
	}

	/**
	 * xml特殊字符过滤
	 * 
	 * @param value
	 * @return
	 */
	public static String filter(String value) {
		if (value == null)
			return null;
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++)
			switch (content[i]) {
			case 60: // '<'
				result.append("&lt;");
				break;

			case 62: // '>'
				result.append("&gt;");
				break;

			case 38: // '&'
				result.append("&amp;");
				break;

			case 34: // '"'
				result.append("&quot;");
				break;

			case 39: // '\''
				result.append("&#39;");
				break;

			default:
				result.append(content[i]);
				break;
			}

		return result.toString();
	}

	/**
	 * 将字符串转为unicode 编码字符
	 * 
	 * @param str
	 * @return
	 */
	public static String toUnicode(String str) {
		char[] arChar = str.toCharArray();
		int iValue = 0;
		String uStr = "";
		for (int i = 0; i < arChar.length; i++) {
			iValue = (int) str.charAt(i);
			if (iValue <= 256) {
				uStr += "\\u00" + Integer.toHexString(iValue);
			} else {
				uStr += "\\u" + Integer.toHexString(iValue);
			}
		}
		return uStr;
	}

	/**
	 * unicode字符串转中文
	 * 
	 * @param dataStr
	 * @return
	 */
	private static String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			// System.out.println(end + ":" + charStr);
			char letter = (char) Integer.parseInt(charStr, 16);
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}

	public static void main(String[] args) {

		try {
			String encodeStr = toUnicode("日志打印机  8051发送打印字符串错");
			System.out.println(encodeStr);

			// int youNumber = 123456;
			// String str =formatStringByLength(youNumber,2);
			// System.out.println(str); // 0001
			// String filePath=FileUtil.CLASS_PATH+"/testMsg.xml";
			// String
			// fileContent=FileUtil.getFileContentByCharset(filePath,"GB2312");
			String strMesg = decodeUnicode(encodeStr);
			System.out.println(strMesg);

		} catch (Exception e) {

		}
	}

	/**
	 * 将字符转为2进制数据
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	public static String ipPortHexToUrl(String string) {
		String Ip = string;
		int i = Integer.parseInt(Ip.substring(0, 2), 16);
		int j = Integer.parseInt(Ip.substring(2, 4), 16);
		int k = Integer.parseInt(Ip.substring(4, 6), 16);
		int l = Integer.parseInt(Ip.substring(6, 8), 16);
		int m = Integer.parseInt(Ip.substring(8), 16);
		StringBuffer sss = new StringBuffer("");
		sss.append(i);
		sss.append(".");
		sss.append(j);
		sss.append(".");
		sss.append(k);
		sss.append(".");
		sss.append(l);
		sss.append(":");
		sss.append(m);
		return sss.toString();
	}

	public static String ipPortToHex(String ip, int port) {
		StringTokenizer token = new StringTokenizer(ip, ".");
		int ip1 = Integer.parseInt(token.nextToken().trim());
		int ip2 = Integer.parseInt(token.nextToken().trim());
		int ip3 = Integer.parseInt(token.nextToken().trim());
		int ip4 = Integer.parseInt(token.nextToken().trim());
		String result = (new StringBuilder(String.valueOf(toHex(ip1, 2))))
				.append(toHex(ip2, 2)).append(toHex(ip3, 2))
				.append(toHex(ip4, 2)).append(Integer.toHexString(port))
				.toString();
		return result;
	}

	private static String toHex(int num, int atLeastBit) {
		String hex = Integer.toHexString(num);
		int length = hex.length();
		if (length >= atLeastBit)
			return hex;
		for (int i = 0; i < atLeastBit - length; i++)
			hex = (new StringBuilder("0")).append(hex).toString();

		return hex;
	}

	// /***
	// * 将 s 进行转码为BASE64 编码的字符串
	// *
	// * @throws UnsupportedEncodingException
	// */
	// public static String getBASE64(String s) {
	// if (s == null)
	// return null;
	// try {
	// return (new sun.misc.BASE64Encoder()).encode(s.getBytes("UTF-8"));
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	//
	// /*** 将 BASE64 编码的字符串 s 进行解码 */
	// public static String getFromBASE64(String s) {
	// if (s == null)
	// return null;
	// BASE64Decoder decoder = new BASE64Decoder();
	// try {
	// byte[] b = decoder.decodeBuffer(s);
	// return new String(b, "UTF-8");
	// } catch (Exception e) {
	// return null;
	// }
	// }

	// 标示用户管理类型：区域或机构
	public static final String MGR_TYPE_ORG = "org";
	public static final String MGR_TYPE_AREA = "area";

	/**
	 * 生成查询用户管理机构或区域的SQL
	 * 
	 * @param userId
	 *            用户ID
	 * @param columnName
	 *            数据库列名
	 * @param mgrType
	 *            两种类型：区域或机构（area或org）
	 * 
	 * @return 生成sql格式示例：(BIG_AREA_ID = '111' or BIG_AREA_ID = '222' or ...)
	 */
	// public static String getUserMgrSql(String userId, String columnName,
	// String mgrType) {
	// // 从缓存中获取用户管理的机构或区域信息
	// IObjMgr objMgr = GlobeObjMgr.getInstance();
	// UserMgrOrgCacheMap mapObj = (UserMgrOrgCacheMap) objMgr
	// .getObj(CacheObjs.USER_MGR_ORG_CACHEMAP);
	// if (mapObj != null) {
	// Map<String, UserManageorgParam> map = mapObj.getUserMgrOrgMap();
	// UserManageorgParam param = map.get(userId);
	// if (param != null) {
	// StringBuffer sb = new StringBuffer();
	// String str = null;
	// // 获取用户管理区域
	// if (StringUtil.MGR_TYPE_AREA.equals(mgrType.toLowerCase())
	// && StringUtil.isNotEmpty(str = param.getAreaId())) {
	// String[] areaIds = StringUtil.stringToArray(str, ",");
	// sb.append("(");
	// for (String areaId : areaIds) {
	// sb.append(columnName).append(" = '").append(areaId)
	// .append("' or ");
	// }
	// sb.append(" 1=0 )");
	// } else if (StringUtil.MGR_TYPE_ORG.equals(mgrType.toLowerCase())
	// && StringUtil.isNotEmpty(str = param.getOrgId())) {
	// // 获取用户管理机构
	// String[] orgIds = StringUtil.stringToArray(str, ",");
	// sb.append("(");
	// for (String orgId : orgIds) {
	// sb.append(columnName).append(" = '").append(orgId)
	// .append("' or ");
	// }
	// sb.append(" 1=0 )");
	// }
	// return sb.toString();
	// }
	// }
	// return "";
	// }

	/**
	 * 获取用户管理机构或区域的字符串
	 * 
	 * @param userId
	 *            用户ID
	 * @param mgrType
	 *            两种类型：区域或机构（area或org）
	 * 
	 * @return 字符串： orgid1,orgid2,orgid3
	 */
	// public static String getUserGrantOrgStr(String userId,String mgrType) {
	// // 从缓存中获取用户管理的机构或区域信息
	// IObjMgr objMgr = GlobeObjMgr.getInstance();
	// UserMgrOrgCacheMap mapObj = (UserMgrOrgCacheMap) objMgr
	// .getObj(CacheObjs.USER_MGR_ORG_CACHEMAP);
	// if (mapObj != null) {
	// Map<String, UserManageorgParam> map = mapObj.getUserMgrOrgMap();
	// UserManageorgParam param = map.get(userId);
	// if (param != null) {
	// StringBuffer sb = new StringBuffer();
	// String str = null;
	// // 获取用户管理区域
	// if (StringUtil.MGR_TYPE_AREA.equals(mgrType.toLowerCase())
	// && StringUtil.isNotEmpty(str = param.getAreaId())) {
	// sb.append(str);
	// } else if (StringUtil.MGR_TYPE_ORG.equals(mgrType.toLowerCase())
	// && StringUtil.isNotEmpty(str = param.getOrgId())) {
	// // 获取用户管理机构
	// sb.append(str);
	// }
	// return sb.toString();
	// }
	// }
	// return "";
	// }

}
