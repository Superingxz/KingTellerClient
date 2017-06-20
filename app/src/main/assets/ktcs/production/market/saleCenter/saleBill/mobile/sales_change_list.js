
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
		        	var li= $("<li style='height:100px;'></li>");
		        	 
		        	var leftDiv = $("<div style='float:left;height:100px;'></div>");
		          
		        	leftDiv.append("<span style='display:block; height:15px; ' >[" + data[i].changeTypeName + '' + "]</span>" );
		        	leftDiv.append("<span style='display:block; height:15px;padding-left:10px;' >变更单编码:" + data[i].salebillNumber + "</span>");
		        	leftDiv.append("<span style='display:block; height:15px;padding-left:10px;' >审核状态:" + data[i].workFlowName + "</span>");
		        	leftDiv.append("<span style='display:block; height:15px;padding-left:10px;' >变更时间:" + data[i].createTimeStr + "</span>");
		        	leftDiv.append("<span style='display:block; height:15px;padding-left:10px;' >变更数量:" + data[i].machineCount + "</span>");
					
					//alert(data[i].changeType);
					if(data[i].changeType == 'SALEBILL_CHANGE_CONFIGURE')
					{
						leftDiv.append("<span style='display:block; height:15px;padding-left:10px;' >机器配置:" + data[i].machineConfiguration + "</span>");
					
					}
					else if(data[i].changeType == 'SALEBILL_CHANGE_MACHINETYPE')
					{
						leftDiv.append("<span style='display:block; height:15px;padding-left:10px;' >机型:" + data[i].machineTypeCode + "</span>");
					
					}
					
		        	var rightDiv = $("<div style='float:right;height:100%; vertical-align:middle;  line-height:100px;'>");
		        	//rightDiv.append("<a style='right:0;line-height:40px;' href='/ktcs/production/market/saleCenter/saleBill/mobile/my_sales_view.html?id="+data[i].salebillId+"' > <img src='../../../../../img/right6.gif' > </a>");
		        	
		        	li.append(leftDiv);
		        	li.append(rightDiv);

		        	fragment.append(li);
			          
					
						
					li.bind('click', function (e) {
					//其他样式恢复
					$("li").css("background-color","#fafafa");
						//改变样式
					this.style.backgroundColor = "#eee";
					
					iscr.selectedData = iscr.loadDataByProperty('salebillId',$(this).find("input[type=radio]").val());
					
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
		        	var li= $("<li ></li>");
		        	 
		        	var leftDiv = $("<div style='float:left;height:30px;'></div>");
		          
		        	leftDiv.append("<span style='display:block; height:15px; ' >" + data[i].title + '' + "</span>" );
		        	leftDiv.append("<span style='display:block;padding-left:10px;' >" + data[i].suggestion + "</span>");
		        	leftDiv.append("<span style='display:block;padding-left:10px;' >" + data[i].status + "</span>");
		        	leftDiv.append("<span style='display:block;padding-left:10px;' >" + data[i].handlerName + "</span>");

		        	var rightDiv = $("<div style='float:right;height:100%; vertical-align:middle;  line-height:60px;'>");
		        	//rightDiv.append("<a style='right:0;line-height:60px;' href='/ktcs/production/market/saleCenter/saleBill/mobile/my_sales_view.html?id="+data[i].salebillId+"' > > </a>");
		        	
		        	li.append(leftDiv);
		        	li.append(rightDiv);

		        	fragment.append(li);
			          
					var timer = null;
					var timerColor = null;
					
					li.bind('touchstart', function () {
						timer = setTimeout(doStuff,600);//这里设置时间
						var obj = this;
						timerColor = setTimeout(function(){
							$("li").css("background-color","#fafafa");
							obj.style.backgroundColor = "#ccc";
						},300);
					
					});
						
					li.bind('click', function (e) {
					//其他样式恢复
					$("li").css("background-color","#fafafa");
						//改变样式
					this.style.backgroundColor = "#ccc";
					
					iscr.selectedData = iscr.loadDataByProperty('salebillId',$(this).find("input[type=radio]").val());
					
					if(iscr.selectedData.flowStatus == '0'||iscr.selectedData.flowStatus == '1'||iscr.selectedData.flowStatus == '-1')
					{
						$("#editBtn").attr("disabled",false); 
						$("#deleteBtn").attr("disabled",false); 
					}
					else
					{
						$("#editBtn").attr("disabled",true); 
						$("#deleteBtn").attr("disabled",true); 
					}
					
					});
					
					li.bind('tap', function () {
						//其他样式恢复
						$("li").css("background-color","#fafafa");
							//改变样式
						this.style.backgroundColor = "#ccc";
						iscr.selectedData = iscr.loadDataByProperty('salebillId',$(this).find("input[type=radio]").val());
						
					});
						
					li.bind('touchend', function () {
						clearTimeout(timer);
						clearTimeout(timerColor);
					});
					
					li.bind('touchmove', function () {
						clearTimeout(timer);
						clearTimeout(timerColor);
					});
		        }
	        }
	        else
	        	return null;
    		
	        return fragment[0];
    		
    	}
    	
    	
    	var iscr;
    	function loaded(busId){
    		var wrapper=document.querySelector('#wrapper');
    		var content=document.querySelector('#thelist');
			
    		iscr = new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,appContext+'/saleCenter/saleBill/changeList_salesMobile.action;jsessionid='+sessionId+'?saleBillId='+busId);
    		iscr.loadMode = 'all';
    		
    		iscr.pullDownAction();
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
		var busId = window.kingteller.getParam("id");
				  
    	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
    	loaded(busId);
    	
    });
});
