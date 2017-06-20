
define(['css!../../iscroll/css/iscroll-load-data.css','jquery','IScrollLoadData'],
		function (css,$,IScrollLoadData) {
			
	    //生成html
    	function dropTopAction(){
    		//var d=document.createDocumentFragment();
    		var fragment = $(document.createDocumentFragment());
	        var data = iscr.datas;
			if(data.length > 0)
			{
				for(var i = 0; i < data.length; i++)
				{
					var li= $("<li style='padding:2px 5px; height:100px;  ' ></li>");
		        	 
		        	var leftDiv = $("<div style='float:left;height:100%;overflow:hidden;width:80%;  line-height:16px;'></div>");
		          
		        	leftDiv.append("<input type='radio' style='display:none;'  name='idd' value='"+data[i].salebillId+"' >");
					leftDiv.append("<span style='display:block; height:15px; margin:3px 0px;overflow:hidden; ' >【" + data[i].salebillNumber + "】</span>");
		        	leftDiv.append("<span style='display:block; height:15px; margin:3px 6px;overflow:hidden; ' >" + data[i].salesMan + " 下单 ,时间:"+ data[i].createTimeStr +"</span>");
					leftDiv.append("<span style='display:block;height:15px;  margin:3px 6px;overflow:hidden;' >银行客户:" + data[i].bankName + "</span>");
					leftDiv.append("<span style='display:block;height:15px; margin:3px 6px;overflow:hidden; ' >机型:" + data[i].machineTypeCode + "  ,机器数量:" + data[i].machineCount + "</span>");
					leftDiv.append("<span style='display:block;height:15px;  margin:3px 6px;overflow:hidden;' >流程状态:" + data[i].workFlowName + "</span>");

		        	var rightDiv = $("<div style='float:right;padding-right:10px;height:100%; vertical-align:middle;  line-height:100px; vertical-align:center;'>");
		        	rightDiv.append("<a style='right:0; line-height:100px; ' href='javascript:;' > <img src='../../../../../img/right6.gif' > </a>");
		        	
					li.append(leftDiv);
					li.append(rightDiv);

					fragment.append(li);
					  
					li.bind('click', function (e) {
					//其他样式恢复
					$("li").css("background-color","#fafafa");
						//改变样式
					this.style.backgroundColor = "#eee";
					
					iscr.selectedData = iscr.loadDataByProperty('salebillId',$(this).find("input[type=radio]").val());
					
						if(dataState == 1)
						{
							//window.kingteller.setParam("id",iscr.selectedData.salebillId);
							window.kingteller.setParam("params","{'id':'"+iscr.selectedData.salebillId+"','salebillType':'"+iscr.selectedData.salebillType+"'}");
							
							window.location.href=decorateUrlHtml("sales_audit.html");
						}
						else if(dataState == 3)
						{
							window.kingteller.setParam("params","{'id':'"+iscr.selectedData.salebillId+"','salebillType':'"+iscr.selectedData.salebillType+"'}");
							window.location.href =  decorateUrlHtml('sales_sp_view.html');
						}
					
					});
					
					
				}
			}
			else
	        {
				var li= $("<li style='padding:2px 12px; height:30px; line-height:30px;margin:0px auto; vertical-align:middle; color:#ccc; ' >暂无数据</li>");
				fragment.append(li);
				
			}
			
	        return fragment[0];
    	}
    	
    //底部加载数据
    	function dropBottomAction(data){
    		
			
			var fragment = $(document.createDocumentFragment());
	         
			if(data.length > 0)
			{
				for(var i = 0; i < data.length; i++)
				{
					var li= $("<li style='padding:2px 5px; height:100px;  ' ></li>");
		        	 
		        	var leftDiv = $("<div style='float:left;height:100%;overflow:hidden;width:80%;  line-height:16px;'></div>");
		          
		        	leftDiv.append("<input type='radio' style='display:none;'  name='idd' value='"+data[i].salebillId+"' >");
					leftDiv.append("<span style='display:block; height:15px; margin:3px 0px;overflow:hidden; ' >【" + data[i].salebillNumber + "】</span>");
		        	leftDiv.append("<span style='display:block; height:15px; margin:3px 6px;overflow:hidden; ' >" + data[i].salesMan + " 提交了一个发机单 ,时间:"+ data[i].createTimeStr +"</span>");
					leftDiv.append("<span style='display:block;height:15px;  margin:3px 6px;overflow:hidden;' >银行客户:" + data[i].bankName + "</span>");
					leftDiv.append("<span style='display:block;height:15px; margin:3px 6px;overflow:hidden; ' >机型:" + data[i].machineTypeCode + "  ,机器数量:" + data[i].machineCount + "</span>");
					leftDiv.append("<span style='display:block;height:15px;  margin:3px 6px;overflow:hidden;' >流程状态:" + data[i].workFlowName + "</span>");

		        	var rightDiv = $("<div style='float:right;padding-right:10px;height:100%; vertical-align:middle;  line-height:100px; vertical-align:center;'>");
		        	//rightDiv.append("<a style='right:0; line-height:100px; ' href="+decorateUrlHtml("sales_sp_view.html?mainFlag=mySaleBillList&id="+data[i].salebillId)+" > <img src='../../../../../img/right6.gif' > </a>");
		        	rightDiv.append("<a style='right:0; line-height:100px; ' href='javascript:;' > <img src='../../../../../img/right6.gif' > </a>");
		        	
					li.append(leftDiv);
					li.append(rightDiv);

					fragment.append(li);
					  
					li.bind('click', function (e) {
					//其他样式恢复
					$("li").css("background-color","#fafafa");
						//改变样式
					this.style.backgroundColor = "#eee";
					
					iscr.selectedData = iscr.loadDataByProperty('salebillId',$(this).find("input[type=radio]").val());
					 
						if(dataState == 1)
						{
							//window.kingteller.setParam("id",iscr.selectedData.salebillId);
							window.kingteller.setParam("params","{'id':'"+iscr.selectedData.salebillId+"','salebillType':'"+iscr.selectedData.salebillType+"'}");
							
							window.location.href=decorateUrlHtml("sales_audit.html");
						}
						else if(dataState == 3)
						{
							window.kingteller.setParam("params","{'id':'"+iscr.selectedData.salebillId+"','salebillType':'"+iscr.selectedData.salebillType+"'}");
							window.location.href =  decorateUrlHtml('sales_sp_view.html');
						}
						
					});
					 
				}
			}
			else
	        {
				//var li= $("<li style='padding:2px 12px; height:30px; line-height:30px;margin:0px auto; vertical-align:middle; color:#ccc; ' >暂无数据</li>");
				//fragment.append(li);
				return null;
			}
			
	        return fragment[0];
			
    	}
    	
    	//弹出搜索框
    	function showSearchDiv() {
			$("#searchModal_contentDia").css("position", "absolute");
			$("#searchModal_contentDia").css("left", location.left + 5 + "px");
			$("#searchModal_contentDia").css("top", location.top + 20 + "px");
	
			$('#searchModal').modal('show');
		}
		
		
	
		//搜索数据
		function handleSearch() {
			 
			 
			var bankName = $("#bankName").val();		
			var salesMan = $.trim($("#salesMan").val());
			iscr.param = "bankName=" + encodeURIComponent(encodeURIComponent(bankName))+"&salebillType="+$("#salebillType").val() + "&salesMan=" + encodeURIComponent(encodeURIComponent(salesMan));
			 
			iscr.search();
			
		}
		
		//删除一行记录
		function deleteRow()
		{
			
			//未提交流程的记录可删除
			if(iscr.selectedData.flowStatus == -1 || iscr.selectedData.flowStatus == 1  || iscr.selectedData.flowStatus == 0 )
			{
				if(window.confirm("确认删除?"))
				{
					$.ajax({
					    type: "post",//使用get方法访问后台
					    url: appContext+'/saleCenter/saleBill/delete_salesMobile.action;jsessionid='+sessionId, 
					    data: 'saleBillId='+$("input[name='idd']:checked").val(), 
					    complete :function(){$("#load").hide();}, 
					    success: function(msg){
					    	alert("删除成功!");
					    	$("#thelist").empty();
					    	iscr.loadData();
					    }
					    });
				}
				
			}
			else
			{
				alert("已提交流程审核的记录不能删除!");
			}
			
		}
		
		//1:待审核列表 3:已审核列表
		var dataState;
		function clickTodoBtn()
		{
			$("#tabDiv a").css("background-color","white");
			$("#toDoBtn").css("background-color","#eee");
			
			 
			$("#thelist").empty();
			//$("#thelist").html("<div style='width:100%;heigth:30px;line-height:30px;text-align:center;'>正在加载~~</div>");
			
			dataState = 1;
			iscr.url = appContext+'/saleCenter/saleBill/querySalesBillForAudit_salesMobile.action;jsessionid='+sessionId;
			$("#loadingDiv").show();
			iscr.param = '';
			iscr.search();
			
			
		}
		
		 
		
		function clickDoneBtn()
		{
			$("#tabDiv a").css("background-color","white");
			 $("#doneBtn").css("background-color","#eee");
			 
			 
			 $("#thelist").empty();
			//$("#thelist").html("<div style='width:100%;heigth:30px;line-height:30px;text-align:center;'>正在加载~~</div>");
			
			 dataState = 3;
			iscr.url = appContext+'/saleCenter/saleBill/querySalesBillAudited_salesMobile.action;jsessionid='+sessionId;
			$("#loadingDiv").show();
			iscr.param = '';
			iscr.search();
			
			
		}
    	
    	var iscr;
    	function loaded(user,password){
    		var wrapper=document.querySelector('#wrapper');
    		var content=document.querySelector('#thelist');
    		
    		iscr = new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,appContext+'/saleCenter/saleBill/querySalesBillForAudit_salesMobile.action;jsessionid='+sessionId);
    		
    		//iscr.pullDownAction();
    	}
    	
    	var tddom= document.getElementsByTagName('li');
		
    	//长按时，弹出工具栏
		function doStuff() {
			$("#contentDia").css("position","absolute"); 
			$("#contentDia").css("left",location.left + 5 + "px"); 
			$("#contentDia").css("top",location.top + 20 + "px");
		  $('#myModal').modal('show');
		}
		
	
    $(function () {
    	
    	//得到请求的参数
		var request = {
			QueryString : function(val) {
				var uri = window.location.search;
				var re = new RegExp("" + val + "=([^&?]*)", "ig");
				return ((uri.match(re)) ? (uri.match(re)[0]
						.substr(val.length + 1)) : null);
			}
		}
		var user = request.QueryString("user");
		var password = request.QueryString("password");
		
    	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    	loaded(user,password);
    	
    	$("#searchBtn").click(handleSearch);
    	$("#showSearchBtn").click(showSearchDiv);
    	
    	$("#auditBtn").click(function(){
    		//window.location.href=appContext+"/production/market/saleCenter/saleBill/mobile/sales_audit.html?id="+iscr.selectedData.salebillId;
			window.kingteller.setParam("params","{'id':'"+iscr.selectedData.salebillId+"','salebillType':'"+iscr.selectedData.salebillType+"'}"); 
			window.location.href=decorateUrlHtml('sales_audit.html');
    	});
    	
    	$("#historyFlowBtn").click(function(){
    		var flowCode = '';
    		if(iscr.selectedData.salebillType == 1)
    		{
    			flowCode = 'saleBillFlowNoContract';
    		}
    		else if(iscr.selectedData.salebillType == 2)
    		{
    			flowCode = 'saleBillFlowContracted';
    		}
    		
			window.kingteller.setParam("params","{'busId':'"+iscr.selectedData.salebillId+"','flowCode':'"+flowCode+"'}"); 
    		window.location.href=decorateUrlHtml('flowHistory.html');
			 
	});
	
	
	$("#toDoBtn").click(function(){
			clickTodoBtn();
		});
		$("#doneBtn").click(function(){
			 clickDoneBtn();
		});
		
    	
		clickTodoBtn();
	
		
	
	
    });
});
