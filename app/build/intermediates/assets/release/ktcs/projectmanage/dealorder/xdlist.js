var myscroll;
var count;
loaded();
window.addEventListener("DOMContentLoaded",loaded,false);
function loaded(){
	var wrapper=document.querySelector('#wrapper');
	var content=document.querySelector('#thelist');
	
	var requestParam = getUrlRequest();
	var id = requestParam['id'];
	myscroll=new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,context+'/custmgr/projectmanage/queryMobilehandlePageList_handleprojectmobile.action?id='+id);
	myscroll.search();
	$("#loadingDiv").show();
	initData(id);//初始化数据，获取对应工单下的测试，变更信息，费用信息
}

$(function(){
	$("#serBtn").click(function(){
			var wrapper=document.querySelector('#wrapper');
			var content=document.querySelector('#thelist');
			var requestParam = getUrlRequest();
			var id = requestParam['id'];
			var jqbh = $("#jqno").val();
			
			myscroll=new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,context+'/custmgr/projectmanage/queryMobilehandlePageList_handleprojectmobile.action?id='+id+"&jqbh="+encodeURI(encodeURI(jqbh)));
			myscroll.search();
	})
	
})

function searchCond(){
	
	var wrapper=document.querySelector('#wrapper');
	var content=document.querySelector('#thelist');
	
	var requestParam = getUrlRequest();
	var id = requestParam['id'];
	
	var xdr = $("#xdr").val();
	var fwz = $("#fwz").val();
	var wdsbjc = $("#wdsbjc").val();
	var jqbh = $("#jqbh").val();
	
	myscroll=new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,context+'/custmgr/projectmanage/queryMobilehandlePageList_handleprojectmobile.action?id='+id+"&xdr="+encodeURI(encodeURI(xdr))+"&fwz="+encodeURI(encodeURI(fwz))+"&wdsbjc="+encodeURI(encodeURI(wdsbjc))+"&jqbh="+encodeURI(encodeURI(jqbh)));
	myscroll.search();
	$("#gb").click();
	cz();
}

function cz(){
	$("#jqbh").val("");
	$("#xdr").val("");
	$("#fwz").val("");
	$("#wdsbjc").val("");
}
/**
 * 初始化页面数据
 * @param {Object} id
 */
function initData(id){
	var requestParam = getUrlRequest();
	var id = requestParam['id'];
	var testurl = context+'/custmgr/projectmanage/queryMobileInitData_handleprojectmobile.action?id='+id;
	$.ajax({
		url : testurl,
		type : 'POST',
		data : null,
		dataType : "jsonp",
		jsonp:"jsonpcallback",
		success : function(result) {
			if(result != null && result.length > 0){
				for(var i=0; i<result.length; i++){
					if(i==0){//硬件配置
						var yjpz = result[0];
						$("#yjpz").html("");
						var yphtml = "<table class='table table-condensed'>";
						for(var j=0; j<yjpz.length; j++){
						if(yjpz[j].beforecontent!=""){
						
							var wlmap = yjpz[j].wlMap;
							var wlselect = "";
							if(wlmap.length>0){
								wlselect = "<select  name='machinechange.wlId' onchange='setwlname("+j+",0)' id='awl_id_"+j+"' class='form-control'>"
								+"<option value='-1' selected>-请选择-</option>";
								for(var n=0; n<wlmap.length;n++){
									wlselect += "<option value='"+wlmap[n].id+"'>"+wlmap[n].name+"</option>"
								}
								wlselect += "</select>";
							}
							yphtml += "<tr>" +
											"<td><b>变更前：</b>" +
											"<input  id='wlName_"+j+"' type='hidden' name='machinechange.aftercontent'  value=''>"+
											"<input id='simplecode"+j+"' name='machinechange.simplecode' type='hidden' value='"+yjpz[i].simplecode+"' >"+
											"<input id='wltypId"+j+"' type='hidden' name='machinechange.id' value='"+yjpz[i].id+"'>"+
											"<input type='hidden' name='machinechange.changetype' value='"+yjpz[i].changetype+"'/>"+
											"</td>"+
											"<td>"+yjpz[j].beforecontent+"</td>"+
											"<td><b>变更后：</b></td>"+
											"<td>"+wlselect+"</td>"+
									  "</tr>"
							}
						}
						
						$("#yjpz").append(yphtml+"</table>")
					}else if(i==1){//软件配置
						var rjpz = result[1];
						$("#rjpz").html("");
						var rphtml = "<table class='table table-condensed'>";
						for(var j=0; j<rjpz.length; j++){
						 if(rjpz[j].beforecontent!=""){
						 	var wlmap = rjpz[j].wlMap;
							var wlselect = "";
							if(wlmap.length>0){
								wlselect = "<select name='machinechange.wlId' onchange='setwlname("+j+",1)' id='rjawl_id_"+j+"' class='form-control'>" +
								"<option value='-1' selected>-请选择-</option>";
								for(var n=0; n<wlmap.length;n++){
									wlselect += "<option value='"+wlmap[n].id+"'>"+wlmap[n].name+"</option>";
								}
								wlselect += "</select><input  id='rjwlName_"+j+"' type='hidden' name='machinechange.aftercontent'  value=''>";
							}else{
								wlselect = "<input type='text' class='form-control' name='machinechange.aftercontent' required='required' placeholder='必填'>"+
											"<input type='hidden' name='machinechange.wlId' value='' />";
							}
							
							rphtml += "<tr>" +
											"<td><b>变更前：</b>" +
											"<input id='rjsimplecode"+j+"' name='machinechange.simplecode' type='hidden' value='"+rjpz[j].simplecode+"' >"+
											"<input id='rjwltypId"+j+"' type='hidden' name='machinechange.id' value='"+rjpz[j].id+"'>"+
											"<input type='hidden' name='machinechange.changetype' value='"+rjpz[j].changetype+"'/>"+
											"</td>"+
											"<td>"+rjpz[j].beforecontent+"</td>"+
											"<td><b>变更后：</b></td>"+
											"<td>"+wlselect+"</td>"+
									  "</tr>"
						 
						 }
							
						}
						
						$("#rjpz").append(rphtml+"</table>")
					}else if(i==2){//测试内容
						var csnr = result[2];
						$("#testsize").val(csnr.length);
						$("#csnr").html("");
						var csnrhtml = "";
						var csnrhtml = "<table class='table table-condensed'>";
						
						for(var j=0; j<csnr.length; j++){
							if(csnr[j].content!=""){
								csnrhtml += "<tr>" +
										"<td><b>测试：</b></td>"+
										"<td style='width:45%'><input type='text' class='form-control'  value='"+csnr[j].content+"' ></td>"+
										"<td style='width:45%'><label class='radio-inline'>" +
										"  <input type='radio' checked name='isTest"+(j+1)+"' id='inlineRadio1' value='1'> 是" +
										"</label>" +
										"<label class='radio-inline'>" +
										"  <input type='radio' name='isTest"+(j+1)+"'  id='inlineRadio2' value='0'> 否" +
										"</label><input type='hidden' name='testid"+(j+1)+"' value='"+csnr[j].id+"'/>"+
										"<input type='hidden' name='testcnt"+(j+1)+"' value='"+csnr[j].content+"'/></td>"+
										"</tr>";
							}
						
						}
						$("#csnr").append(csnrhtml+"</table>")
					}else if(i==3){//费用类型信息
						var fyxx = result[3];
						var trhtml = "";
						var selects = "<select  class='form-control' onchange='getsubFee(this)'><option value='-1' selected>-请选择-</option>";
						for(var j=0; j<fyxx.length;j++){
							selects += "<option value='"+fyxx[j].id+"'>"+fyxx[j].name+"</option>"
						}
						selects += "</select>"
						trhtml += "<tr>" +
									"<td style='width:28%'>"+selects+"</td>"+
									"<td style='width:20%'>工具</td>"+
									"<td style='width:25%'><div class='controls' ><input type='number' name='epvo.costFeeStr' class='form-control'  placeholder='数字'></div></td>"+
									"<td><input type='text' class='form-control' name='epvo.descript' placeholder='必填' ></td>"+
									"<td><span class='glyphicon glyphicon-remove' onclick='delLine(this)'></span></td>"+
								  "</tr>";
						var t = $("#tblTable").find("tr:gt(0)").remove();
						$("#tblTable").append(trhtml);
						$("#template").html("");
						$("#template").html(trhtml);
						
					} else if(i==4){
						var dlr = result[4];
						$("#dlr").val(dlr);
					}
				}
			}
		},
		error:function(ex){
			alert("出现异常");
		}
	});
}

function checkform(){
	var i = 0;
	$("#form .form-control").each(function(val){
		var thisval = $(this).val();
		
		if(thisval=="" || thisval=="-1"){
			$(this).focus();
			i++;
		}
	})
	
	if(i==0){
		return true;
	}else{
		alert("不能为空！");
		return false;
	}
}

function setwlname(index,status) {
	if(status==0){
		var name = $("#awl_id_"+index).find("option:selected").text();
		$("#wlName_"+index).val(name);
	}else if(status==1){
		var name = $("#rjawl_id_"+index).find("option:selected").text();
		$("#rjwlName_"+index).val(name);
	}
}

//获取费用类型	
	function getsubFee(obj){
		//$("#typeId").attr("value",val);
		var val = $(obj).val();
		var subfee = $(obj).parent().parent().find("td:eq(1)");
		$.ajax({
            type: "post",
            url: context + "/custmgr/projectmanage/getFeeTree_handleprojectmobile.action?id="+val,
			data : null,
			dataType : "jsonp",
			jsonp:"jsonpcallback",
            success: function (result) {
              var html = "<select  onchange='setSencond(this)' class='form-control' ><option value='-1'>-请选择-</option>";
              for(var i=0; i<result.length; i++){
                 html += "<option value='"+result[i].id+"'>"+result[i].name+"</option>"
              }
              
              $(subfee).html(html+"</select>");
            },
            error: function (msg) {
                alert("error");
            }
        });
		
	}
	
	/**
	 * 费用类型及方式
	 * @param {Object} obj
	 */
	function setSencond(obj){
		var val = $(obj).val();
		var faremode = $(obj).parent().parent().find("td:eq(1)");
		faremode.html("");
		faremode.append("<input type='hidden' name='epvo.typeId' value='"+val+"'/>");
		$.ajax({
            type: "post",
            url: context + "/custmgr/projectmanage/getFeeMode_handleprojectmobile.action?id="+val,
			data : null,
			dataType : "jsonp",
			jsonp:"jsonpcallback",
            success: function (result) {
              var html = "<select class='form-control'  name='epvo.costDesc'><option value='-1'>-请选择-</option>";
              for(var i=0; i<result.length; i++){
                 html += "<option value='"+result[i].id+"'>"+result[i].modeName+"</option>"
              }
              
              $(faremode).append(html+"</select>");
            },
            error: function (msg) {
                alert("error");
            }
        });

	}

var tddom= document.getElementsByTagName('li');
//生成html
function dropTopAction(){
	var fragment = $(document.createDocumentFragment());
    var data = myscroll.datas;
    if(data.length > 0){
    	for(var i = 0; i < data.length; i++){
        	var li= $("<li style='padding:2px 5px; height:200px;  ' ></li>");
        	count=i+1;
        	//&nbsp;&nbsp;<a href='"+context+"/custmgr/projectmanage/projectmobile/workorderdetailstatistic.jsp?"+data[i].workorderId+"'>&nbsp;&nbsp;查看</a>
			var table="<table class='table table-bordered' onclick='isSelected(this,&#39;"+data[i].id+"&#39;,&#39;"+data[i].orderid+"&#39;,&#39;"+data[i].dostatus+"&#39;,&#39;"+data[i].createuser+"&#39;)'><thead><tr style='background-color:#CCCCCC;'><th width='20px;'>"+count+"</th><th>" + data[i].orderno + "</th></tr></thead><tbody><tr><td colspan='2'>"+data[i].jqbh+"</td></tr><tr><td colspan='2'>"+  data[i].areaname + "&nbsp;&nbsp;&nbsp;"+data[i].ssbsc+"</td></tr><tr><td colspan='2'>"+data[i].wdsbjc+"&nbsp;&nbsp;&nbsp;" +data[i].createuser +"【"+data[i].dostatusStr+"】</td></tr></tbody></table>";
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

function isSelected(tab,id,orderid,status,createuser){
	var flag = true;
	if(flag){
		var border = $(tab).attr("style");
		if(border == undefined){
			$("#thelist table").each(function(i){
				$(this).removeAttr("style");
			});
			$("#hideid").val(id);
			$("#orderid").val(orderid);
			$("#dostatus").val(status);
			$("#loginuser").val(createuser);
			$(tab).css("border","2px red solid");
		}else {
			$(tab).removeAttr("style");
			$("#hideid").val("");
		}
	}
	flag=false;
	
}

function dropBottomAction(data){
	var fragment = $(document.createDocumentFragment());
    if(data.length > 0)
    {
    	for(var i = 0; i < data.length; i++)
        {
        	var li= $("<li style='padding:2px 5px; height:200px;  ' ></li>");
        	count=++count;
			var table="<table class='table table-bordered' onclick='isSelected(this,&#39;"+data[i].id+"&#39;,&#39;"+data[i].orderid+"&#39;,&#39;"+data[i].dostatus+"&#39;,&#39;"+data[i].createuser+"&#39;)'><thead><tr style='background-color:#CCCCCC;'><th width='20px;'>"+count+"</th><th>" + data[i].orderno + "</th></tr></thead><tbody><tr><td colspan='2'>"+data[i].jqbh+"</td></tr><tr><td colspan='2'>"+  data[i].areaname + "&nbsp;&nbsp;&nbsp;"+data[i].ssbsc+"</td></tr><tr><td colspan='2'>"+data[i].wdsbjc+"&nbsp;&nbsp;&nbsp;" +data[i].createuser +"【"+data[i].dostatusStr+"】</td></tr></tbody></table>";
			//var table="<table class='table table-bordered' onclick='isSelected(this,&#39;"+data[i].id+"&#39;,&#39;"+data[i].orderid+"&#39;,&#39;"+data[i].dostatus+"&#39;)'><thead><tr style='background-color:#CCCCCC;'><th width='20px;'>"+count+"</th><th>单号【" + data[i].orderno + "】&nbsp;&nbsp;<a href='"+context+"/custmgr/projectmanage/projectmobile/workorderdetailstatistic.jsp?"+data[i].workorderId+"'>&nbsp;&nbsp;查看</a></th></tr></thead><tbody><tr><td colspan='2'>"+data[i].jqbh+"</td></tr><tr><td colspan='2'>"+  data[i].areaname + "&nbsp;&nbsp;&nbsp;"+data[i].ssbsc+"</td></tr><tr><td colspan='2'>"+data[i].wdsbjc+"&nbsp;&nbsp;&nbsp;" +data[i].createuser +"【"+data[i].dostatusStr+"】</td></tr></tbody></table>";
    		li.append(table);
        	fragment.append(li);
        }
    }
    else
    	{
    	return null;
    	}
	
    return fragment[0];
}

/**
 * 销单操作方法
 */
function startHandle(){//开始销单操作
	var keyId = $("#hideid").val();
	var status = $("#dostatus").val();
	var orderid = $("#orderid").val();
	if(keyId != ""){
		doHandle("1",keyId,status,orderid);
	}else{
		alert("请选择一行数据")
	}
}

$('#myModal').on('show.bs.modal', function (e) {//绑定事件在显示弹出框前
			var createuser = $("#loginuser").val();
			var dlr = $("#dlr").val();
			var xd = $("#xd").val();
		    var reportstatus = $("#dostatus").val();
		    if(createuser!=dlr){
		    	alert("与开始销单人不一致");
		    	return false;    	
		    }else if(reportstatus==""){
				alert("还未开始销单");
				return false;
			}else if(reportstatus==undefined){
				alert("请选择一条数据");
				return false;
			}else if(reportstatus==1){
				$("#reportRemark").val("");
				initData();
				$("#reportstatus").val(xd);
				
			}else if(reportstatus==2){
				alert("当前状态为无需销单");
				return false;
			}else if(reportstatus==3){
				alert("当前状态为完成销单");
				return false;
			}
})
function pauseHandle() {//无需销单操作
	$("#xd").val(2);
	
}

function endHandle(){//完成销单操作
	$("#xd").val(3);
}

function doHandle(val,id,status,orderid){
	var confirmStr = "";
	if(val==1){//开始销单
		confirmStr='确认开始销单吗？';
		if(status==""){
			
		}else if(status==1){
			alert("操作失败：开始销单状态中！")
			return false;
		}else if(status==2){
			alert("操作失败：无需销单状态中！");
			return false;
		}else if(status==3){
			alert("操作失败：完成销单状态中！");
			return false;
		}
	}
	
	if(confirm(confirmStr)){
		var url = context+'/custmgr/projectmanage/doMobileHandleOrder_handleprojectmobile.action?orderid='+orderid+'&id='+id+'&status='+val;
		$.ajax({
			url : url,
			type : 'POST',
			data : null,
			dataType : "jsonp",
			jsonp:"jsonpcallback",
			success : function(result) {
				if(result.successful){
					alert("操作成功！");
					//$('#closewin').click();
					myscroll.search();
				}else{
					alert(result.message);
				}
			},
			error:function(ex){
				alert("出现异常");
			}
		});
	}
}

/**
 * 提交报告
 */
function submitReport(){
	if(checkform()){
		 $("#loadingDiv").show();
		var data = "";
		data = $("#form").serialize();
		data = decodeURIComponent(data,true);
		data = encodeURI(encodeURI(data));
		var url = context+'/custmgr/projectmanage/doMobileHandleOrder_handleprojectmobile.action';
		$.ajax({
				url : url,
				type : 'post',
				data : data,
				dataType : "jsonp",
				jsonp:"jsonpcallback",
				complete : function() {
					$("#loadingDiv").hide();
				},
				success : function(result) {
					if(result.successful){
						alert("操作成功！");
						$('#myModal').modal('hide')
						myscroll.search();
					}else{
						alert(result.message);
					}
				},
				error:function(ex){
					alert("出现异常");
				}
			});
	}
}

//新增一行费用信息
function addLine(val){
	var len = $("#tblTable").find("tr").length;
	var template = $("#template").find("tr").clone();
	
	$("#tblTable").append(template);
	
}

//删除费用
function delLine(val){
	$(val).parent().parent().remove();
}

function setMode(val){
	var typeId = $("#typeId"+val).val();
	var url = context + "/custmgr/projectmanage/fetchModeListByTypeId_handleprojectmobile.action?typeId="+typeId;
	$.ajax({
		url : url,
		async : false,
		cache : false,
		type : 'POST',
		data : null,
		dataType : "jsonp",
		jsonp:"jsonpcallback",
		success : function(resultBean) {
			$("#mode"+val).html("");
			var html = "<select name='epvo.costDesc' ><option value='-1'>-请选择-</option>"
			if (resultBean!=null){
				for(var i=0; i<resultBean.length; i++){
					html += "<option value='"+resultBean[i].id+"'>"+resultBean[i].modeName+"</option>";
				}
				html += "</select>";
				$("#mode"+val).html(html);
			} else {
				
			}
		}
	});
}


/*function addatt(){
	var length = $("#attTable").find("tr").length;
	length = parseInt(length)+1;
	html="<tr id='tr_"+length+"'>"+
		 "<td align='right' class='input_title'>文件 ：</td>"+
		 "<td ><input type='file' name='upload' size='10'/></td>"+
		 "<td align='right' style='width: 20%;'>说明：</td>"+
		 "<td >"+
		 "<textarea id='attachDesc"+length+"' name='apvo.attachDesc' rows='3' cols='20' ></textarea>"+
		 "</td>"+
		 "<td style='width: 20%;'>"+
		 "<img src='"+contextPath+"/themes/blue/images/delete.gif' border='0'"+
		 "title='删除本行' style='cursor: pointer;' onclick='delatt("+length+")'/>"+
		 "<img src='"+contextPath+"/themes/blue/images/add2.gif' width='16' height='16'"+
		 "style='cursor: hand; margin-right: 150px;' title='添加一行' border='0' onclick='addatt()'>"+
		 "</td>"+
	"</tr>";
	$("#attTable").append(html);
}

function delatt(length){
	$("#tr_"+length).remove();
}*/

/**
 * 获取url参数值
 * @return {TypeName} 
 */
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
