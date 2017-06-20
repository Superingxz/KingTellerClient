$(function(){
	 $("#loadingDiv").show();
    		var idtemp=window.location.search;
   			 var id = idtemp.substring(1,idtemp.length);
   			$.ajax({
				type : "post",//使用get方法访问后台
				dataType : "jsonp",//返回json格式的数据
				jsonp:"jsonpcallback",
				url : context+'/custmgr/projectmanage/queryWorderorderDetail_collectMobile.action',//要访问的后台地址
				data : 'orderid='+id,//要发送的数据
				complete : function() {
					$("#loadingDiv").hide();
				},//AJAX请求完成时隐藏loading提示
				success : function(orderList) {//orderList.rows[0]为返回的数据，在这里做数据绑定
					//+orderList.rows[0].rows
					var ssdq ="",ssqy="";
				 	for(var i=0;i<orderList.rows.length;i++){
				 	    $("#orderno").text(orderList.rows[0].orderNo);
						$("#content").text(orderList.rows[0].content);
						$("#type").text(orderList.rows[0].type);
						$("#emergency").text(orderList.rows[0].emergency);
						$("#gznum").text(orderList.rows[0].gznum);
						$("#completepercent").text(orderList.rows[0].completepercent);
						$("#planbegintimeStr").text(orderList.rows[0].planbegintimeStr);
						$("#planendtimeStr").text(orderList.rows[0].planendtimeStr);
						$("#xdbegintimeStr").text(orderList.rows[0].xdbegintimeStr);
						$("#xdendtimeStr").text(orderList.rows[0].xdendtimeStr);
						$("#wldhtime").text(orderList.rows[0].wldhtime);
						$("#remark").text(orderList.rows[0].remark);
						$("#completeNum").text(orderList.rows[0].completeNum);
						$("#needntCompleteNum").text(orderList.rows[0].needntCompleteNum);
						
						if(orderList.rows[i].ssdq != ssdq){
							$("#ssdq").after("<tr><td rowspan='2' style='vertical-align:middle;'>"+orderList.rows[i].ssdq+"</td><td>总"+orderList.rows[i].ssdqTotal+"台&nbsp;&nbsp;&nbsp;&nbsp;当前完成"+orderList.rows[i].ssdqPercent+"%"+"</td></tr><tr><td>无需完成"+orderList.rows[i].ssdqWxwccnt+"台&nbsp;&nbsp;&nbsp;&nbsp;已完成"+orderList.rows[i].ssdqYwccnt+"台</td></tr>");
							ssdq = orderList.rows[i].ssdq ;
						}
						if(orderList.rows[i].areaname!=ssqy){
							$("#ssqy").after("<tr><td rowspan='2' style='vertical-align:middle;'>"+orderList.rows[i].areaname+"</td><td>总"+orderList.rows[i].areaTotal+"台&nbsp;&nbsp;&nbsp;&nbsp;当前完成"+orderList.rows[i].areaPercent+"%"+"</td></tr><tr><td>无需完成"+orderList.rows[i].areaWxwccnt+"台&nbsp;&nbsp;&nbsp;&nbsp;已完成"+orderList.rows[i].areaYwcCnt+"台</td></tr>");
							ssqy=orderList.rows[i].areaname;
						}
						$("#ssbsc").after("<tr><td rowspan='2' style='vertical-align:middle;'>"+orderList.rows[i].ssbsc+"</td><td>总"+orderList.rows[i].bscTotal+"台&nbsp;&nbsp;&nbsp;&nbsp;当前完成"+orderList.rows[i].bscPercent+"%"+"</td></tr><tr><td>无需完成"+orderList.rows[i].ssbscWxwccnt+"台&nbsp;&nbsp;&nbsp;&nbsp;已完成"+orderList.rows[i].ssbscYwcCnt+"台</td></tr>");
						
						$("#dqspan").text("（总费用"+orderList.rows[i].ssdqMoney+"元）");
						$("#qyspan").text("（总费用"+orderList.rows[i].ssdqMoney+"元）");
						$("#bscspan").text("（总费用"+orderList.rows[i].ssqyMoney+"元）"); 
				 		}
					}
   				});

});
function scroll(){
   				//$("#backbtn").css("display","block");
   			};