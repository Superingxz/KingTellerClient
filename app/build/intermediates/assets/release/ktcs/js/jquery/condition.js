/*************************************************/
//一套控件的集合：数据表格、分页、条件封装
//@author tangql
//@date 2010-08-09
/*************************************************/

/**
 * Condition类方法封装，结合标签使用
 * 只要某个form元素设置condition="1"则认为是个搜索条件域
 * 条件的字符串格式为|*|字段名|@|字段类型|@|比较符|@|值1|@|值2|*|
 */
Condition={};
Condition.CONDITION="CONDITION";
Condition.INDEX="CONDITION_INDEX";
Condition.sp1="|*|";//条件间的分隔符
Condition.sp2="|@|";//条件内的分割符
Condition.STRING="string";
Condition.NUMBER="number";
Condition.DATE="date";
Condition.TIMESTAMP="timestamp";
Condition.PAGESIZE="50";

//比较符
Condition.EQ="=";//等于
Condition.NOT_EQ="<>";//不等于
Condition.LESS_THAN="<";//小于
Condition.LESS_EQ_THAN="<=";//小于等于
Condition.LARGE_THAN=">";//大于
Condition.LARGE_EQ_THAN=">=";//大于等于
Condition.LARGE_LESS="LL";//大于并且小于，不包括边界
Condition.LARGE_LESS_EQ="LLE";//不小于并且不大于，包括边界
Condition.BETWEEN="BETWEEN";//在某个区间
Condition.LIKE="LIKE";//模糊搜索
Condition.LLIKE="LLIKE";//左边匹配，如aa%
Condition.RLIKE="RLIKE";//右边匹配，如%aa

//从第一页面搜索
Condition.postGrid=function(gridId,container){
	var strCond=Condition.parseCondition(container);
	//alert('111查询条件：'+strCond);
	var obj=jQuery("#"+gridId).getGridParam('postData');
	if(Boolean(obj)){
		obj.condition=strCond;
	}
	jQuery("#"+gridId).setPostData(obj);
	jQuery("#"+gridId).setGridParam({page:1});
	jQuery("#"+gridId).trigger("reloadGrid");
	//点击搜索的时候要把当前页重新置为1

	
};

//从当前页面搜索或者刷新当前页面
Condition.postGridInCurrenPage=function(gridId,container){
	var strCond=Condition.parseCondition(container);
	var obj=jQuery("#"+gridId).getGridParam('postData');
	obj.condition=strCond;
	jQuery("#"+gridId).setPostData(obj);
	jQuery("#"+gridId).trigger("reloadGrid");
	//点击搜索的时候要把当前页作参数传入 
};


/**
 * 解析搜索条件域，并提交数据
 * 当存在多个不同的搜索域时，包含搜索域的container需指定，可以是id或对象
 * @param obj form对象，如果不指定则取body的第一个form，如果指定一个字符串，则必须是提交的路径
 * @param target form提交的目标
 * @param container 包含条件域的容器对象或ID，当同一个界面有多组搜索条件域时使用，不设置则获取body所有condition=1的对象
 */
Condition.post=function(obj,target,container){
	var form=obj;
	if(form==null){
		form=document.forms[0];//获取第一个from
	}else{
		if(typeof(form)=="string"){//表示传过来的是一个提交路径
			var action=form;
			form=document.createElement("FORM");
			form.action=action;
			if(target!=null){
				form.target=target;
			}
			
		}
	}
	var hidObj=document.getElementById("hidden-"+Condition.CONDITION);
	if(hidObj==null){
		hidObj=document.createElement("INPUT");
		hidObj.type="hidden";
		hidObj.id="hidden-"+Condition.CONDITION;
		//hidObj.value=Condition.parseCondition(container);
		hidObj.name=Condition.CONDITION;
		form.appendChild(hidObj);
	}
	hidObj.value=Condition.parseCondition(container);
	
	if(hidObj.value=="-1"){
		alert("至少需要设置fieldName的值");
		return;
	}	
	/*
	var indexHidObj=document.getElementById("index-hidden-"+Condition.INDEX);
	if(indexHidObj==null){
		indexHidObj=document.createElement("INPUT");
		indexHidObj.type="hidden";
		indexHidObj.id="index-hidden-"+Condition.INDEX;
		//hidObj.value=Condition.parseCondition(container);
		indexHidObj.name=Condition.INDEX;
		form.appendChild(indexHidObj);
	}
	indexHidObj.value=Condition.parseConditionIndex(container);	
	*/
	form.method="post";
	form.submit();
	
	//DataGrid.showLoading();
};
/**
 * 通过程序设定某个html元素为搜索条件域，可解决某些jsp标签不能设定condition=1的问题
 * @param el html元素id或dom对象
 * @param paramObj 搜索定义，json格式如{fieldName:'值',fieldType:'值'}
 */
Condition.setObjParam=function(el,paramObj){
	
	if(typeof(el)=="string"){//el id
		el=document.getElementById(el);
	}
	
	if(paramObj){
		el.setAttribute("condition","1");
		if(paramObj.fieldName) el.setAttribute("fieldName", paramObj.fieldName);
		if(paramObj.fieldType) el.setAttribute("fieldType", paramObj.fieldType);
		if(paramObj.begin) el.setAttribute("begin", paramObj.begin);
		if(paramObj.end) el.setAttribute("end", paramObj.end);
		if(paramObj.opt) el.setAttribute("opt", paramObj.opt);
	}
	
}

/**
 * 解析搜索条件域，解析成特定格式的字符串，提供给后台解析成FieldCond对象
 * 当存在多个不同的搜索域时，包含搜索域的container需指定，可以是id或对象
 */
Condition.parseCondition=function(container){
	var arrCondObj=null;
	if(container!=null){
		if(typeof(container)=="string"){
			container="#"+container;
		}
		arrCondObj=jQuery(container).find("[fieldName]");
	}else{
		arrCondObj=jQuery("[fieldName]");
	}

	var arrTmp=[];
	var strCond="";
	for(var i=0;i<arrCondObj.length;i++){
		
		var fieldName=arrCondObj[i].getAttribute("fieldName");

		if(fieldName==null){		
			return "-1";
		}
		var type=arrCondObj[i].getAttribute("fieldType");//string或number
		if(type==null){
			type=Condition.STRING;
		}
		var value=arrCondObj[i].value;
		//有些条件框设置的是一些提示的信息，不能作为正式的查询条件，该类条件初始会设置init=true，如果输入了新的值，则init=false
		if(arrCondObj[i].init!=null&&arrCondObj[i].init=="1"){
			value="";
		}
		var opt=arrCondObj[i].getAttribute("opt");
		if(opt==null){
			opt=Condition.EQ;
		}
		/*
		if(opt==null){
			if(value.indexOf("%")!=-1&&type=Condition.STRING){//字符串带%表示模糊查询
				opt=Condition.LIKE;
			}else{
				opt=Condition.EQ;
			}
		}else{
			if(value.indexOf("%")!=-1&&type=Condition.STRING){//字符串带%表示模糊查询
				opt=Condition.LIKE;
			}			
		}
		*/
		var relId=arrCondObj[i].relId;
		var valueTitle="";
		if(relId==null){
			//对于一些值-标题的条件，如果是select元素展示的，帮其自动设置valueTitle,即保存其值对应的标题
			if(arrCondObj[i].tagName=="SELECT"){
				var selOpt=arrCondObj[i].options[arrCondObj[i].selectedIndex];
				valueTitle=selOpt.innerText;
			}	
			relId=" ";	
		}else{
			var valueTitleObj=document.getElementById(relId);
			if(valueTitleObj!=null){
				valueTitle=valueTitleObj.value;
			}else{
				relId=" ";
			}
		}
		var groupName=arrCondObj[i].groupName;
		if(groupName==null){
			groupName=" ";
		}
		var beginFlag=arrCondObj[i].getAttribute("begin");
		if(beginFlag!=null){//表示该条件是某个区间的，并且是开始值			
			//先不组装,放到某个临时数组里面	
			if(value.length>0){
				if(arrTmp[fieldName]==null){				
					arrTmp[fieldName]=fieldName+Condition.sp2+type+Condition.sp2+opt+Condition.sp2+value+Condition.sp2+"value2"+Condition.sp2+valueTitle+Condition.sp2+"valueTitle2"+Condition.sp2+relId+Condition.sp2+"relId2"+Condition.sp2+groupName+Condition.sp1;
				}else{
					var tmpCond=arrTmp[fieldName];
					tmpCond.replace(Condition.sp2+"value1"+Condition.sp2,Condition.sp2+value+Condition.sp2);
					tmpCond.replace(Condition.sp2+"valueTitle1"+Condition.sp2,Condition.sp2+valueTitle+Condition.sp2);
					tmpCond.replace(Condition.sp2+"relId1"+Condition.sp2,Condition.sp2+relId+Condition.sp2);
					strCond+=tmpCond;
				}			
			}
			continue;			
		}
		var endFlag=arrCondObj[i].getAttribute("end");
		if(endFlag!=null){//从临时数组找到对应的field
			if(value.length>0){
				if(arrTmp[fieldName]==null){
					arrTmp[fieldName]=fieldName+Condition.sp2+type+Condition.sp2+opt+Condition.sp2+"value1"+Condition.sp2+value+Condition.sp2+"valueTitle1"+Condition.sp2+valueTitle+Condition.sp2+"relId1"+Condition.sp2+relId+Condition.sp2+groupName+Condition.sp1;
				}else{
					var tmpCond=arrTmp[fieldName];
					tmpCond=tmpCond.replace(Condition.sp2+"value2"+Condition.sp2,Condition.sp2+value+Condition.sp2);
					tmpCond=tmpCond.replace(Condition.sp2+"valueTitle2"+Condition.sp2,Condition.sp2+valueTitle+" "+Condition.sp2);
					tmpCond=tmpCond.replace(Condition.sp2+"relId2"+Condition.sp1,Condition.sp2+relId+Condition.sp1);
					strCond=strCond+tmpCond;
				}
				
			}
			continue;
		}
		//单值条件

		strCond+=fieldName+Condition.sp2+type+Condition.sp2+opt+Condition.sp2+value+Condition.sp2+" "+Condition.sp2+valueTitle+Condition.sp2+" "+Condition.sp2+relId+Condition.sp2+" "+Condition.sp2+groupName+Condition.sp1;
	}
	if(strCond!=""){//截去最后一个sp1
		//strCond=strCond.substring(0,(strCond.length-Condition.sp1.length));
		if(container!=null){
			var containerId=container;
			if(typeof(containerId)!="string"){
				containerId=containerId.id;
			}
			strCond=strCond+"container:"+containerId
		}
	}
	return strCond;
}

/**
 * 将参数组装成Cond格式
 */
function getCondValue(params){
	return condValue;
}
