var myscroll;
var count;
loaded();
function loaded(){
	var wrapper=document.querySelector('#wrapper');
	var content=document.querySelector('#thelist');
	myscroll=new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,10,context+'/custmgr/projectmanage/queryProjectSubmitList_toMobileReport.action');
	myscroll.search();	
	$("#loadingDiv").show();
}
/**
 * 下拉刷新
 * @return
 */
function dropTopAction(){
	var fragment = $(document.createDocumentFragment());
    var data = myscroll.datas;
    if(data.length > 0)
    {
    	
    	for(var i = 0; i < data.length; i++){
        	var li= $("<li style='padding:2px; height:70%;width:100%' ></li>");
        	count=i+1;
			var table="<table class=\"table table-bordered\"><thead><tr id=\"tr_"+count+"\" onclick=\"checkYY("+count+")\" style=\"background-color:#CCCCCC;\"><th style=\"10%\" ><input type=\"checkbox\"  name=\"ichkbox\" id=\"checkBox"+count+"\" value=\""+data[i].reportid+"\"/>"+count+"</th>"
			table+="<th>&nbsp;"+data[i].orderno+"<a style=\"float:right;margin-right:3%\" href='orderdetailmsg.html?reportId="+data[i].reportid+"'>查看</a></th>"

			table+="</tr></thead><tbody><TR><td  colspan='2'>机器编号：&nbsp;&nbsp;"+data[i].jqbh+"</td></TR>"
			
			if(data[i].reportremark!=null&&data[i].reportremark!=""){
				if(data[i].reportremark.length<=30){
					table+="<tr><td colspan='2'>"+data[i].reportremark+"</td></tr>"
				}else{
					var dt = data[i].reportremark.substring(1,30)+"......";
					table+="<tr><td colspan='2'>"+dt+"</td></tr>"
				}
			}else{
				table+="<tr><td colspan='2'>暂无报告备注说明</td></tr>"
			}
			table +="<tr><td colspan='2'>提交人："+data[i].reportuser+"</td></tr>";
			table +="<tr><td colspan='2'>流程状态：&nbsp;&nbsp;"+data[i].workflowStr+"</td></tr>";
			table+="<tr><td colspan='2'>总费用&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].totalmoney+"元</td></tr></tbody></table>";
			li.append(table);
        	fragment.append(li);
        }
    	
    }
    else
    {
		var li= $("<li style='padding:2px 12px; height:30px; line-height:30px;margin:0px auto; vertical-align:middle; color:#ccc; ' >暂无数据</li>");
		fragment.append(li);
	}
	
    return fragment[0];
	 
}
/**
 * 上啦刷新
 * @param data
 * @return
 */
function dropBottomAction(data){
	var fragment = $(document.createDocumentFragment());
    if(data.length > 0)
    {
    	for(var i = 0; i < data.length; i++)
        {
        	var li= $("<li style='padding:2px; height:70%;width:100%  '></li>");
        	count=++count;
        	var table="<table class=\"table table-bordered\"><thead><tr id=\"tr_"+count+"\" onclick = \"checkYY("+count+")\" style=\"background-color:#CCCCCC;\"><th style=\"10%\"><input type=\"checkbox\"  name=\"ichkbox\" id=\"checkBox"+count+"\" value=\""+data[i].reportid+"\"/>"+count+"</th>"
			table+="<th>&nbsp;"+data[i].orderno+"<a style=\"float:right;margin-right:3%\" href='orderdetailmsg.html?reportId="+data[i].reportid+"'>查看</a></th>"

			table+="</tr></thead><tbody><TR><td  colspan='2'>机器编号：&nbsp;&nbsp;"+data[i].jqbh+"</td></TR>"
			
			if(data[i].reportremark!=null&&data[i].reportremark!=""){
				if(data[i].reportremark.length<=30){
					table+="<tr><td colspan='2'>"+data[i].reportremark+"</td></tr>"
				}else{
					var dt = data[i].reportremark.substring(1,30)+"......";
					table+="<tr><td colspan='2'>"+dt+"</td></tr>"
				}
			}else{
				table+="<tr><td colspan='2'>暂无报告备注说明</td></tr>"
			}
        	table +="<tr><td colspan='2'>提交人：&nbsp;"+data[i].reportuser+"</td></tr>";
			table +="<tr><td colspan='2'>流程状态：&nbsp;&nbsp;"+data[i].workflowStr+"</td></tr>";
			table +="<tr><td colspan='2'>总费用&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].totalmoney+"元</td></tr></tbody></table>";
    		li.append(table);
        	fragment.append(li);
        }
    }else{
    	return null;
	}
	
    return fragment[0];
}

	function checkYY(ID){
			$("#tr_"+ID).on("click", function(){//为按钮绑定click事件
			   function doFinished(){
		           var flag = document.getElementById("checkBox"+ID).checked;
					if(flag){
						document.getElementById("checkBox"+ID).checked=false;
					}else{
						document.getElementById("checkBox"+ID).checked=true;
					}
		       }
			   setTimeout(doFinished,"10");//启动一个定时器，模拟登陆过程
		   })
		}

	//全选和取消
	function selectAll(){
		var allCheckBoxs=document.getElementsByName("ichkbox");
		var desc = document.getElementById("selectBtn").innerHTML;
		if(desc=="全选"){   
			document.getElementById("selectBtn").innerHTML="取消";    
			for(var i=0;i<allCheckBoxs.length;i++)    {      
				allCheckBoxs[i].checked=true;
			}      
		}else{     
			document.getElementById("selectBtn").innerHTML="全选";     
			for(var i=0;i<allCheckBoxs.length;i++){    
				allCheckBoxs[i].checked=false;     
				} 
		}
	}
	
	//填写审批意见
	function audit($id){
		var text = document.getElementById("textarea").value;
		if($id=='1'){
			var chestr = selectedOrder();
			if(chestr==null||chestr==""||chestr=="undefined"){
				alert("请选择要审批的维护报告...");
				return false;
			}
			//异步提交审批
			var isOk = getTableData(context+'/custmgr/projectmanage/submitAudit_toMobileReport.action',"reportIds="+chestr+"&suggest="+text);
			myscroll.search();
			$('#myModal').modal('hide');
		}else if($id=='2'){
			var chestr = selectedOrder();
			if(chestr==null||chestr==""||chestr=="undefined"){
				alert("请选择要审批的维护报告...");
				return false;
			}
			if(text==""||text==null||text=="undefined"){
				alert("请填写审核意见...");
				return false;
			}
			//异步提交审批
	        var isOk = getTableData(context+'/custmgr/projectmanage/submitAudit_toMobileReport.action',"reportIds="+chestr+"&suggest="+text+"&back=1");
			myscroll.search();
			$('#myModal').modal('hide');
	      }if($id=='3'){
	    	  $('#myModal').modal('hide');
		  }
	}


	//查询选中的维护报告的ID集合，并且返回
	function selectedOrder(){
		var str=document.getElementsByName("ichkbox");
		var objarray=str.length;
		var chestr="";
		for (var i=0;i<objarray;i++){
		  if(str[i].checked == true){
			  if(chestr==""){
				  chestr = str[i].value;
			  }else{
				  chestr+=","+str[i].value;
			  }
		  }
		}
		return chestr;
	}

	//异步提交审批
	function getTableData(url,param){
		var flag = false;
		param = encodeURI(encodeURI(param));
		$.ajax({
			type : "post",//使用get方法访问后台
			dataType : "jsonp",//返回json格式的数据
			url : url,//要访问的后台地址
			data : param,//要发送的数据
			jsonp:"jsonpcallback",
			success : function(msg) {//msg为返回的数据，在这里做数据绑定
				if(msg.code=='success'){
					alert('审批成功...');
				}else{
					alert('操作失败...');
				}
			}
		});
		return flag;
	}
	
	function showSearchDiv() {
		$("#searchModal_contentDia").css("position", "absolute");
		$("#searchModal_contentDia").css("left", location.left + 5 + "px");
		$("#searchModal_contentDia").css("top", location.top + 20 + "px");
		$('#searchModal').modal('show');
	}
	//搜索数据
function handleSearch() {
	var params = $("#searchForm").serialize();
	params = decodeURIComponent(params,true);
		//在进行编码
	params = encodeURI(encodeURI(params));
	myscroll.param = params;
	myscroll.search();
	$('#searchModal').modal('hide');
	$("#loadingDiv").show();
}