
define(['css!../../iscroll/css/iscroll-load-data.css','jquery','IScrollLoadData'],
		function (css,$,IScrollLoadData) {
			
	    //生成html
    	function dropTopAction(){
	
    		 
    		var fragment = $(document.createDocumentFragment());
	        var data = iscr.datas;
			 
	        if(data.length > 0)
	        {
	        	for(var i = 0; i < data.length; i++)
		        {
		        	var li= $("<li style='padding:2px 5px; height:100px;  ' ></li>");
		        	 
		        	var leftDiv = $("<div style='float:left;height:100%;overflow:hidden;width:80%;  line-height:16px;'></div>");
		          
					if(data[i].salebillTypeCreate == '2')
					{
						leftDiv.append("<span style='display:block; height:15px; margin:3px 0px;ov	erflow:hidden; ' >【变更单:" + data[i].salebillNumber + "】</span>");
					}
					else
						leftDiv.append("<span style='display:block; height:15px; margin:3px 0px;ov	erflow:hidden; ' >【" + data[i].salebillNumber + "】</span>");
					
					leftDiv.append("<input type='radio' style='display:none;'  name='idd' value='"+data[i].salebillId+"' >");
		        	
		        	leftDiv.append("<span style='display:block; height:15px; margin:3px 6px;overflow:hidden; ' >" + data[i].salesMan + " 下单 ,时间:"+ data[i].createTimeStr +"</span>");
					leftDiv.append("<span style='display:block;height:15px;  margin:3px 6px;overflow:hidden;' >银行客户:" + data[i].bankName + "</span>");
					leftDiv.append("<span style='display:block;height:15px; margin:3px 6px;overflow:hidden; ' >机型:" + data[i].machineTypeCode + "  ,机器数量:" + data[i].machineCount + "</span>");
					leftDiv.append("<span style='display:block;height:15px;  margin:3px 6px;overflow:hidden;' >流程状态:" + data[i].workFlowName + "</span>");

		        	var rightDiv = $("<div style='float:right;padding-right:10px;height:100%; vertical-align:middle;  line-height:100px; vertical-align:center;'>");
					
		        	rightDiv.append("<a style='right:0; line-height:100px; ' href="+decorateUrlHtml("my_sales_view.html")+" > <img src='../../../../../img/right6.gif' > </a>");
		        	
					
		        	li.append(leftDiv);
		        	li.append(rightDiv);

		        	fragment.append(li);
			           
					li.bind('click', function (e) {
						//其他样式恢复
						$("li").css("background-color","#fafafa");
						//$("li:not(':first,:last')").css("background-color","#fafafa");
							//改变样式
						$(this).css("background-color","#eee");
						
						iscr.selectedData = iscr.loadDataByProperty('salebillId',$(this).find("input[type=radio]").val());
						 
						if(iscr.selectedData.flowStatus == '0'||iscr.selectedData.flowStatus == '1'||iscr.selectedData.flowStatus == '-1')
						{
							$("#editBtn").attr("disabled",false); 
							$("#deleteBtn").attr("disabled",false);
							
							if(iscr.selectedData.flowStatus == '1')	//退回的单可以查看流程
								$("#historyFlowBtn").attr("disabled",false); 
							else
								$("#historyFlowBtn").attr("disabled",true); 
						}
						else
						{
							$("#editBtn").attr("disabled",true); 
							$("#deleteBtn").attr("disabled",true); 
							$("#historyFlowBtn").attr("disabled",false); 
						}
						
						window.kingteller.setParam("id",iscr.selectedData.salebillId);
						
					
					});
					
					li.bind('tap', function () {
						//其他样式恢复
						$("li").css("background-color","#fafafa");
							//改变样式
						this.style.backgroundColor = "#eee";
						iscr.selectedData = iscr.loadDataByProperty('salebillId',$(this).find("input[type=radio]").val());
						
						window.kingteller.setParam("id",iscr.selectedData.salebillId);
						
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
    		
    		//var d=document.createDocumentFragment();
    		var fragment = $(document.createDocumentFragment());
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
		        	rightDiv.append("<a style=' line-height:100px; ' href="+decorateUrlHtml("my_sales_view.html")+" > <img src='../../../../../img/right6.gif' > </a>");
		        	
		        	li.append(leftDiv);
		        	li.append(rightDiv);

		        	fragment.append(li);
			          
					
						
					li.bind('click', function (e) {
					//其他样式恢复
					$("li").css("background-color","#fafafa");
						//改变样式
					this.style.backgroundColor = "#eee";
					
					iscr.selectedData = iscr.loadDataByProperty('salebillId',$(this).find("input[type=radio]").val());
					
					if(iscr.selectedData.flowStatus == '0'||iscr.selectedData.flowStatus == '1'||iscr.selectedData.flowStatus == '-1')
					{
						$("#editBtn").attr("disabled",false); 
						$("#deleteBtn").attr("disabled",false);
						$("#historyFlowBtn").attr("disabled",true); 
					}
					else
					{
						$("#editBtn").attr("disabled",true); 
						$("#deleteBtn").attr("disabled",true); 
						$("#historyFlowBtn").attr("disabled",false); 
					}
					
					});
					
					li.bind('tap', function () {
						//其他样式恢复
						$("li").css("background-color","#fafafa");
							//改变样式
						this.style.backgroundColor = "#eee";
						iscr.selectedData = iscr.loadDataByProperty('salebillId',$(this).find("input[type=radio]").val());
						
					});
						
					
		        }
	        }
	        else
	        	return null;
    		
	        return fragment[0];
    		
    	}
    	
    	//弹出搜索框
    	function showSearchDiv() {
			$("#searchModal_contentDia").css("position", "absolute");
			$("#searchModal_contentDia").css("left", location.left + 5 + "px");
			$("#searchModal_contentDia").css("top", location.top + 20 + "px");
	
			$('#searchModal').modal('show');
			
			//加载数据,流程状态
			if($("#flowStatus option").length <= 1)
			{
				$.ajax({
					    type: "post",//使用get方法访问后台
						dataType : "jsonp",//返回json格式的数据
						jsonp:"jsonpcallback",
					    url: appContext+'/sysmgr/dict/queryDictListJsonP_Dictmgr.action;jsessionid='+sessionId, 
					    data: 'dictTypecode=SALEBILL_WORKFLOW', 
					    complete :function(){$("#load").hide();}, 
					    success: function(msg){
					    	
							 var rows = msg.rows;
							 for(var i = 0; i < rows.length; i++)
							 {
								$("#flowStatus").append("<option value='"+rows[i].dictCode+"'>"+rows[i].dictName+"</option>");
							 }
					    }
				});
			}
			
		}
	
		//搜索数据
		function handleSearch() {
		 
			var params = $("#searchForm").serialize();
			params = decodeURIComponent(params,true);
				//在进行编码
			params = encodeURI(encodeURI(params));
			iscr.param = params;
		 
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
						dataType : "jsonp",//返回json格式的数据
						jsonp:"jsonpcallback",
					    url: appContext+'/saleCenter/saleBill/delete_salesMobile.action;jsessionid='+sessionId, 
					    data: 'saleBillId='+iscr.selectedData.salebillId, 
					    complete :function(){$("#load").hide();}, 
					    success: function(msg){
					    	alert("删除成功!");
					    	$("#thelist").empty();
					    	iscr.pullDownAction();
					    }
					    });
				}
				
			}
			else
			{
				alert("已提交流程审核的记录不能删除!");
			}
			
		}
    	
    	var iscr;
    	function loaded(user,password){
    		var wrapper=document.querySelector('#wrapper');
    		var content=document.querySelector('#thelist');
    		 
    		iscr = new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,appContext+'/saleCenter/saleBill/queryMySalesBill_salesMobile.action;jsessionid='+sessionId);
    		 
    	}
    	
    	var tddom= document.getElementsByTagName('li');
		
    	//长按时，弹出工具栏
		function doStuff() {
			$("#contentDia").css("position","absolute"); 
			$("#contentDia").css("left",location.left + 5 + "px"); 
			$("#contentDia").css("top",location.top + 20 + "px");
		  $('#myModal').modal('show');
		}
		
		function clickTodoBtn()
		{
			$("#tabDiv a").css("background-color","white");
			$("#toDoBtn").css("background-color","#eee");
			
			//iscr.param = 'dataState=1';
			
			//清除thelist
			$("#thelist").empty();
			//$("#thelist").html("<div style='width:100%;heigth:30px;line-height:30px;text-align:center;'>正在加载~~</div>");
			
			iscr.url = appContext+'/saleCenter/saleBill/queryMySalesBill_salesMobile.action;jsessionid='+sessionId + '?dataState=1';
			
			$("#loadingDiv").show();
			iscr.param = '';
			iscr.search();
			
		}
		
		function clickDoingBtn()
		{
			 $("#tabDiv a").css("background-color","white");
			 $("#doingBtn").css("background-color","#eee");
			 $("#thelist").empty();
			//$("#thelist").html("<div style='width:100%;heigth:30px;line-height:30px;text-align:center;'>正在加载~~</div>");
			 
			iscr.url = appContext+'/saleCenter/saleBill/queryMySalesBill_salesMobile.action;jsessionid='+sessionId + '?dataState=2';
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
			
			iscr.url = appContext+'/saleCenter/saleBill/queryMySalesBill_salesMobile.action;jsessionid='+sessionId + '?dataState=3';
			$("#loadingDiv").show();
			iscr.param = '';
			iscr.search();
		}
		

//	var remoteIp = '';
//	var remotePort = '';
//	var appContext = '';
//	var localPath = 'F:/Workspace/nativehtml';
    $(function () {
    	
    	//得到请求的参数
  
		 
		$("#editBtn").click(function(){
    	 
			window.location.href="sales_update.html";
    	});
    	$("#addBtn").click(function(){
    		window.location.href=decorateUrlHtml('sales_add.html');
    	});

		 
    	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			
    	loaded(user,password);
    	
		 
    	$("#searchBtn").click(handleSearch);
    	$("#showSearchBtn").click(showSearchDiv);
    	
    	$("#deleteBtn").click(deleteRow);
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
		$("#doingBtn").click(function(){
			clickDoingBtn();
		});
    	
		clickTodoBtn();
    	
    });
});
