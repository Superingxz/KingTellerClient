	 $("#loadingDiv").show();
	  var idtemp=window.location.search;
	  if(idtemp==null||idtemp==""||idtemp=="undefined"){
		alert("数据出错,请联系管理员...");
		return false;
	  }
	 var id = idtemp.substring(10,idtemp.length);
	 if(id==null||id==""||id=="undefined"){
		 alert("数据出错,请联系管理员...");
			return false;
	 }
	$.ajax({
		type : "post",//使用get方法访问后台
		dataType : "jsonp",//返回json格式的数据
		jsonp:"jsonpcallback",
		url : context+'/custmgr/projectmanage/queryReportorderDetail_toMobileReport.action',//要访问的后台地址
		data : 'reportId='+id,//要发送的数据
		complete : function() {
			$("#loadingDiv").hide();
		},//AJAX请求完成时隐藏loading提示
		success : function(orderList) {//orderList.rows[0]为返回的数据，在这里做数据绑定
			 	for(var i=0;i<orderList.rows.length;i++){
			 		document.getElementById("orderno").innerHTML="工单号："+orderList.rows[0].orderno;
			 		document.getElementById("content").innerHTML=orderList.rows[0].content;
			 		document.getElementById("type").innerHTML=orderList.rows[0].type;
			 		document.getElementById("area").innerHTML=orderList.rows[0].areaname;
			 		document.getElementById("fwz").innerHTML=orderList.rows[0].ssbsc;
			 		document.getElementById("emergency").innerHTML=orderList.rows[0].emergency;
			 		document.getElementById("jhwcsj").innerHTML=orderList.rows[0].planendtime;
			 		document.getElementById("jhkssj").innerHTML=orderList.rows[0].planbegintime;
			 		document.getElementById("jqbh").innerHTML=orderList.rows[0].jqbh;
			 		document.getElementById("jqdz").innerHTML=orderList.rows[0].wddz;
			 		document.getElementById("jqgsr").innerHTML=orderList.rows[0].jqgsrusername;
			 		document.getElementById("whbgbzsm").innerHTML=orderList.rows[0].remark;
			 		document.getElementById("xdkssj").innerHTML=orderList.rows[0].xdbegintime;
			 		document.getElementById("xdjssj").innerHTML=orderList.rows[0].xdendtime;
			 		document.getElementById("xdr").innerHTML=orderList.rows[0].createuser;
			 		document.getElementById("lczt").innerHTML=orderList.rows[0].workflow;
			 		document.getElementById("fyzh").innerHTML=orderList.rows[0].totalmoney+"元";
				}
			}
		});