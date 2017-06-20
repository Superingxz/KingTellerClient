loaded();


function getUrlRequest(){
        var url = location.search; //获取url中"?"符后的字串
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            if (str.indexOf("&") != -1) {
                strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
                }
            } else {
                var key = str.substring(0,str.indexOf("="));
                var value = str.substr(str.indexOf("=")+1);
                theRequest[key] = decodeURI(value);
            }
        }
        return theRequest;
}

function loaded(){
    var requestParam = getUrlRequest();
	var id = requestParam['id'];
	var url = context+'/custmgr/projectmanage/queryDealOrder_handleprojectmobile.action';
	$.ajax({
		type : "post",//使用get方法访问后台
		dataType : "jsonp",//返回json格式的数据
		jsonp:"jsonpcallback",
		url : url,//要访问的后台地址
		data : "orderid="+id,//要发送的数据
		success : function(result) {//orderList.rows[0]为返回的数据，在这里做数据绑定
			 for(var i=0; i<result.length; i++){
			 	if(i==0){
				 	var order = result[0][0];
				 	$("#orderno").html("工单号："+order.orderno);
				 	$("#content").html(order.content);
				 	$("#type").html(order.type);
				 	$("#emergency").html(order.emergency);
				 	$("#jhkssj").html(order.planbegintimeStr);
				 	$("#jhwcsj").html(order.planendtimeStr);
			 	}else if(i==1){
			 		var yj = result[1];
			 		var yjhtml = "";
			 		for(var j=0; j<yj.length; j++){
			 			if(yj[j].beforecontent!=""){
				 			yjhtml += "<table class='table table-bordered'>" +
				 			" <thead><tr style='background-color:#CCCCCC;'><th colspan='2'>硬件配置</th> </tr></thead>"+
				            "<tbody>" +
				            	"<tr><td>变更前</td><td>"+yj[j].beforecontent+"</td></tr>" +
				            	"<tr><td>变更后</td><td>"+yj[j].aftercontent+"</td></tr>" +
				            "</tbody>"+
				 			"</table>";
			 			}
			 		}
			 		$("#yjpz").html(yjhtml);
			 	}else if(i==2){
			 		var rj = result[2];
			 		var rjhtml = "";
			 		for(var j=0;j<rj.length; j++){
			 			if(rj[j].beforecontent!=""){
				 			rjhtml += "<table class='table table-bordered'>" +
				 			" <thead><tr style='background-color:#CCCCCC;'><th colspan='2'>软件配置</th> </tr></thead>"+
				            "<tbody>" +
				            	"<tr><td>变更类型</td><td>"+rj[j].expand1+"</td></tr>" +
				            	"<tr><td>变更前</td><td>"+rj[j].beforecontent+"</td></tr>" +
				            	"<tr><td>变更后</td><td>"+rj[j].aftercontent+"</td></tr>" +
				            "</tbody>"+
				 			"</table>";
			 			}
			 		}
			 		$("#rjpz").html(rjhtml);
			 	}else if(i==3){
			 		var test = result[3];
			 		var testhtml = "";
			 		for(var j=0; j<test.length; j++){
			 			if(test[j].content!=""){
				 			testhtml += "<table class='table table-bordered'>" +
					 			" <thead><tr style='background-color:#CCCCCC;'><th colspan='2'>测试内容</th> </tr></thead>"+
					            "<tbody>" +
					            	"<tr><td>测试内容</td><td>"+test[j].content+"</td></tr>" +
					            "</tbody>"+
					 			"</table>";
			 			}
			 		}
			 		$("#csnr").html(testhtml);
			 	}
			 	
			 }
			 for(var i=0;i<orderList.rows.length;i++){
			 		alert("工单号："+orderList.rows[0].content);
			 		
				}
	    }
			
	});
}
   

	
		
		