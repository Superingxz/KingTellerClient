package com.kingteller.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.kingteller.client.bean.common.CommonSelectData;

/**
 * 一些固定数据的设置工具类
 * 
 * @author 王定波
 * 
 */
public class CommonSelcetUtils {
	// 通用的radio 01
	public final static int radios01 = 0;
	private final static String[] radios01Text = { "是", "否" };
	private final static String[] radios01Value = { "1", "0" };

	// 工作类别   添加"客服报废"  "COPY_LOG""拷贝日志""COPY_VIDEO""拷贝录像""MODIFY_ITEM""项目改造"
	public final static int workType = 1;
	public final static String[] workTypeText = { "故障维护", "软件升级", "调试机器",
			"PM", "开通机器", "账务处理", "培训", "接机", "移机", "退机", "安装机器", "检查机器", "其他", "部件加装","客服报废","硬件改造","停机"};
	
	
	
	public final static String[] workTypeValue = { "NORMAL", "SOFT_UPGRAD",
			"DEBUG", "PM", "START", "ACCOUNTANT", "TRAINING",
			"RECEIVE_MACHINE", "MOVE_MACHINE", "BACK_MACHINE", "SETUP_MACHINE",
			"CHECK_MACHINE", "OTHER", "ADD_COMPONENT","SCRAP_MACHINE","MODIFY_COMPONENT","STOP_MACHINE"};
	
	//工作类别 （新调整）
		public final static int workType2 = 24;
		
		public final static String[] workTypeText2 = {"故障维护","PM","开通机器","调试机器","移机","客户协助","退机","客服报废","工程项目类"
			,"加装升级项目类"};
	public final static String[] workTypeValue2 = {"NORMAL","PM","START","DEBUG","MOVE_MACHINE","CUSTOMER_ASSISTANCE"
			,"BACK_MACHINE","SCRAP_MACHINE","ENGINEERING_PROJECT","INSTALL_UPGRAD"};
	
	
	// 是否新机开通
	public final static int workTypeNewMove = 2;
	private final static String[] workTypeNewMoveText = { "移机开通", "新机开通" };
	private final static String[] workTypeNewMoveValue = { "MOVE", "NEW" };

	
	
	// 机器是否正常服务     机器带故障运行-3
	public final static int workFinishFlag = 3;
	private final static String[] workFinishFlagText = { "正常对外", "停机",
			"未上线停机", "请选择" };
	private final static String[] workFinishFlagValue = { "0", "1", "3",
			"99" };
	
	// 机器是否正常服务     机器带故障运行-3
		public final static int workFinishFlag2 = 25;
		private final static String[] workFinishFlagText2 = { "正常对外", "机器带故障运行",
				"暂停服务", "请选择" };
		private final static String[] workFinishFlagValue2 = { "0", "2", "4",
				"99" };
	
	// 本次故障原因
	public final static int troubleReasonCode = 4;
	private final static String[] troubleReasonCodeText = { "人为破坏", "银行人员操作不当", "设备运行故障", "其他", "新机原因"};  //其它 其他
	private final static String[] troubleReasonCodeValue = { "0", "1", "2", "3", "4"};
	
	
	//本次故障原因（2.2.14）
	public final static int troubleReasonCode2 = 27;
	private final static String[] troubleReasonCodeText2 = { "全部", "人为原因", "设备运行故障","自然灾害", "外部原因所致"};  //其它 其他
	private final static String[] troubleReasonCodeValue2 = { "-1", "0", "2", "5", "6"};
	
	
	// 通用的radio 12
	public final static int radios12 = 5;
	private final static String[] radios12Text = { "是", "否" };
	private final static String[] radios12Value = { "1", "2" };

	// 服务评价
	public final static int serveAssessResultCode = 6;
	private final static String[] serveAssessResultCodeText = { "满意", 
			"一般", "不满意" };
	private final static String[] serveAssessResultCodeValue = { "0",  "2",
			"3" };
	
	
	// 服务方式
	public final static int servType = 7;
	private final static String[] servTypeText = { "现场服务", "电话服务", "第三方处理" };
	private final static String[] servTypeValue = { "SITE_SERV", "TEL_SERV",
			"OTHER_SERV" };

	// 工作类别--其他事物
	public final static int workTypeOtherMatter = 8;
	private final static String[] workTypeOtherMatterText = { "全部", "送资料",
			"送合同", "银行开会", "拿钥匙", "交水电费", "协助其他部门", "其他", "洽谈合同", "签合同", "移机",
			"退机", "装机", "谈判", "选点", "网点巡逻" };
	private final static String[] workTypeOtherMatterValue = { "ALL",
			"SEND_DATA", "SEND_CONT", "BANK_MEET", "GET_LOCK", "CHARGE_FEES",
			"HELP_OTHER", "OTHER", "QTHT", "QHT", "YJ", "TJ", "ZJ", "TP", "XD",
			"WDXL" };

	// 工作类别--物流
	public final static int workTypeLogistic = 9;
	private final static String[] workTypeLogisticText = { "退机", "货运", "其他方式",
			"发机", "移机" };
	private final static String[] workTypeLogisticValue = { "backMachine",
			"transferGoods", "other", "sendMachine", "moveMachine" };

	// 接机类型
	public final static int receiverType = 10;
	private final static String[] receiverTypeText = { "客户", "银行", "销售", "运营",
			"其他" };
	private final static String[] receiverTypeValue = { "KH", "YH", "XS", "YY",
			"QT" };

	
	// 是否新机开通
	public final static int workTypeNewMove1 = 11;
	private final static String[] workTypeNewMove1Text = { "移机开通", "移走机器" };
	private final static String[] workTypeNewMove1Value = { "MOVE", "MOVE_OUT" };
		
	// 费用类型
	//"非维护车费""402881cd42318c1c0142319644360010"
	public final static int feeType = 12;
	private final static String[] feeTypeText = { "车费", "车船费",
			"出差补助", "出差住宿", "住宿节约补助" };
	private final static String[] feeTypeValue = {
			"402881cd42318c1c014231961501000f",
			"ff808081444d876901444e09a6ed1c97",
			"ff808081444d876901444e0ab7bf1cf2",
			"ff808081444d876901444e0a227a1cbc",
			"ff808081444d876901444e0bfe3c1d66" };

	
	//停机原因
	public final static int stopReasonType = 13;
	private final static String[] stopReasonTypeText = { "客服原因", "备件原因","运营原因", "销售原因", "其他原因", "已开通", "已移机后(未开通)", "撤机","移机停机" ,"未上线"};
	private final static String[] stopReasonTypeValue = { "SERVICE_REASON",
			"REPAIRS_REASON", "OPERATION_REASON", "SALES_REASON",
			"OTHER_REASON", "START_REASON", "MOVE_REASON", "BACK_REASON" ,"MOVESTOP_REASON","NOTLINE_REASON"};
	
	//暂停服务对应的原因(对停机原因修改)
	public final static int stopReasonType2 = 26;
	private final static String[] stopReasonTypeText2 = {"技术原因","备件原因","销售原因",  "银行原因", "撤机","移机停机" ,"未上线"};
	private final static String[] stopReasonTypeValue2 = {"TECHNOLOGY_REASON", "REPAIRS_REASON","SALES_REASON",
		"BANK_REASON", "BACK_REASON","MOVESTOP_REASON","NOTLINE_REASON"};
	
	public final static int costType = 14;
	private final static String [] costTypeText = {"保养维修费","货车加油费","路桥费","停车费","测试","车队外出住宿费"};
	private final static String [] costTypeValue = {"ff80808145a69c8f0145a6c95b960988","ff8080814327f92801432cbd55964211",
		   "ff8080814327f92801432cbdf7a7423d","ff8080814327f92801432cbe74d84261","402881ef49c599260149c5e228240004","ff8080814327f92801432cbf58064293"};
	//其他事务流程状态
	public final static int swlbType= 15;
	private final static String []swlbTypeText = {"已派单","确认接收","开始","完成"};
	private final static String []swlbTypeValue = {"swlb_yp","swlb_js","swlb_ks","swlb_wc"};
	
	public final static int sqxzType = 16;
	private final static String[] sqxzTypeText = {"市区","县乡镇"};
	private final static String[] sqxzTypeValue = {"1","2"};
	
	public final static int tdyyType = 17;
	private final static String[] tdyyTypeText = {"停电","断网","其他"};
	private final static String[] tdyyTypeValue = {"1","2","3"};
	
	public final static int CzType = 18;
	private final static String[] CzTypeText = {"下次执行","确认执行","无需执行"};
	private final static String[] CzTypeValue = {"1","2","3"};
	
	public final static int projectWorkType = 19;
	private final static String[] projectWorkTypeText = {"PM","硬件改造","软件升级","市场单加装","缺料加装","工程","其他"};
	private final static String[] projectWorkTypeValue = {"1","2","3","4","5","6","7"};
	
	public final static int ordertypeType = 20;
	private final static String[] ordertypeText = {"项目","维护"};
	private final static String[] ordertypeValue = {"1","2"};
	
	//加班单 类型
	public final static int overTimeType = 21;
	private final static String[] overTimeTypeText = {"个人","多人"};
	private final static String[] overTimeTypeValue = {"0","1"};
	
	//加班补偿类型    
	public final static int overTimeWard = 22;
	private final static String[] overTimeWardText = {"调休","计薪"};
	private final static String[] overTimeWardValue = {"0","1"};

	//维护信息 备件更换情况
	public final static int changeSituation = 23;
	private final static String[] changeSituationText = {"原配置正确","原配置错误且已修改","无相应配置"};
	private final static String[] changeSituationValue = {"0","1","2"};
	
	/**
	 * 获取select的方法
	 * 
	 * @param type
	 * @return
	 */
	public static List<CommonSelectData> getSelectList(int type) {
		String[] text = null, value = null;
		switch (type) {
		case radios01:
			text = radios01Text;
			value = radios01Value;
			break;
		case workType:
			text = workTypeText;
			value = workTypeValue;
			break;
		case workTypeNewMove:
			text = workTypeNewMoveText;
			value = workTypeNewMoveValue;
			break;
		case workFinishFlag:
			text = workFinishFlagText;
			value = workFinishFlagValue;
			break;
		case radios12:
			text = radios12Text;
			value = radios12Value;
			break;
		case troubleReasonCode:
			text = troubleReasonCodeText;
			value = troubleReasonCodeValue;
			break;
		case serveAssessResultCode:
			text = serveAssessResultCodeText;
			value = serveAssessResultCodeValue;
			break;
		case servType:
			text = servTypeText;
			value = servTypeValue;
			break;
		case workTypeOtherMatter:
			text = workTypeOtherMatterText;
			value = workTypeOtherMatterValue;
			break;
		case workTypeLogistic:
			text = workTypeLogisticText;
			value = workTypeLogisticValue;
			break;
		case receiverType:
			text = receiverTypeText;
			value = receiverTypeValue;
			break;
		case workTypeNewMove1:
			text = workTypeNewMove1Text;
			value = workTypeNewMove1Value;
			break;
		case feeType:
			text = feeTypeText;
			value = feeTypeValue;
			break;
		case stopReasonType:
			text = stopReasonTypeText;
			value = stopReasonTypeValue;
			break;
		case costType:
			text = costTypeText;
			value = costTypeValue;
			break;
		case swlbType:
			text = swlbTypeText;
			value = swlbTypeValue;
			break;
		case sqxzType:
			text = sqxzTypeText;
			value = sqxzTypeValue;
			break;
		case tdyyType:
			text = tdyyTypeText;
			value = tdyyTypeValue;
			break;
		case CzType:
			text = CzTypeText;
			value = CzTypeValue;
			break;
		case projectWorkType:
			text = projectWorkTypeText;
			value = projectWorkTypeValue;
			break;
		case ordertypeType:
			text = ordertypeText;
			value = ordertypeValue;
			break;
		case overTimeType:
			text = overTimeTypeText;
			value = overTimeTypeValue;
			break;
		case overTimeWard:
			text = overTimeWardText;
			value = overTimeWardValue;
			break;
		case changeSituation:
			text = changeSituationText;
			value = changeSituationValue;
			break;
		case workType2:
			text = workTypeText2;
			value = workTypeValue2;
			break;
		case workFinishFlag2:
			text = workFinishFlagText2;
			value = workFinishFlagValue2;
			break;
		case stopReasonType2:
			text = stopReasonTypeText2;
			value = stopReasonTypeValue2;
			break;
		case troubleReasonCode2:
			text = troubleReasonCodeText2;
			value = troubleReasonCodeValue2;
			break;
		default:
			break;
		}
		int length = text.length;
		List<CommonSelectData> lists = new ArrayList<CommonSelectData>();
		for (int i = 0; i < length; i++) {
			CommonSelectData data = new CommonSelectData();
			data.setText(text[i]);
			data.setValue(value[i]);
			lists.add(data);
		}

		return lists;
	}
}
