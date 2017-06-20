var myscroll;
var count;
loaded();
window.addEventListener("DOMContentLoaded",loaded,false);
function loaded(){
	var wrapper=document.querySelector('#wrapper');
	var content=document.querySelector('#thelist');
	myscroll=new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,context+'/custmgr/projectmanage/queryMobileProjectHandleList_handleprojectmobile.action');
	myscroll.search();
	
	initSearch();
}

$(function(){
	$("#serBtn").click(function(){
			var wrapper=document.querySelector('#wrapper');
			var content=document.querySelector('#thelist');
			
			var orderno = $("#gdh").val();
			
			myscroll=new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,context+'/custmgr/projectmanage/queryMobileProjectHandleList_handleprojectmobile.action?orderno='+orderno);
			myscroll.search();
	})
	
})

function searchCond(){
	var wrapper=document.querySelector('#wrapper');
	var content=document.querySelector('#thelist');
	
	var orderno = $("#orderno").val();
	var crateuser = $("#crateus").val();
	var xmlx = $("#xmlx option:selected").val();
	
	myscroll=new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,context+'/custmgr/projectmanage/queryMobileProjectHandleList_handleprojectmobile.action?orderno='+orderno+"&createuser="+crateuser+"&xmlx="+encodeURI(encodeURI(xmlx)));
	myscroll.search();
	$("#gb").click();
	cz();
}

function initSearch(){
	var testurl = context+'/custmgr/projectmanage/queryMobileInitSearchData_handleprojectmobile.action';
	$.ajax({
		url : testurl,
		type : 'POST',
		data : null,
		dataType : "jsonp",
		jsonp:"jsonpcallback",
		success : function(result) {
			$("#xmlxsel").html("");
			var htm = "<select class='form-control' id='xmlx' style='height:10px' name='xmlx'><option value=''>全部</option>";
			for(var key in result){
				htm += "<option value='"+key+"'>"+result[key]+"</option>";
			}
			$("#xmlxsel").html(htm+"</select>");
		}
	});
}

function cz() {
	$("#orderno").val("");
	$("#crateus").val("");
	$("#xmlx").val("");
	
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
			var table="<table class='table table-hover'  ><thead><tr style='background-color:#CCCCCC;'><th width='20px;'>"+count+"</th><th>" + data[i].orderno + "&nbsp;&nbsp;<a href='xdlist.html?id="+data[i].id+"'>销单</a>&nbsp;&nbsp;<a href='orderdetail.html?id="+data[i].id+"'><span class='glyphicon glyphicon-eye-open'></span></a></th></tr></thead><tbody><tr><td colspan='2'>"+data[i].content+"</td></tr><tr><td colspan='2'>"+ data[i].type + "&nbsp;&nbsp;&nbsp;"+data[i].emergency+"&nbsp;&nbsp;&nbsp;"+data[i].createuser+"</td></tr><tr><td colspan='2'>" + data[i].planbegintime + "-->"+data[i].planendtime+"</td></tr></tbody></table>";
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

function xdcoop(id){
	var url = "xdlist.html?id="+id;
	 window.location.href = url;
}
function openWindow(url){
	var options="width="+screen.availWidth+",height="+screen.availHeight+",";
	options+="status=yes,scrollbars=yes,,resizable=yes,location=no,menubar=no,toolbar=no,directories=no,top="+top+",left="+left;
	alert(url)
	window.open(url,"",options);
}
function dropBottomAction(data){
	var fragment = $(document.createDocumentFragment());
    if(data.length > 0)
    {
    	for(var i = 0; i < data.length; i++)
        {
        	var li= $("<li style='padding:2px 5px; height:200px;  ' ></li>");
        	count=++count;
			var table="<table class='table table-hover'  ><thead><tr style='background-color:#CCCCCC;'><th width='20px;'>"+count+"</th><th>" + data[i].orderno + "&nbsp;&nbsp;<a href='xdlist.html?id="+data[i].id+"'>销单</a>&nbsp;&nbsp;<a href='orderdetail.html?id="+data[i].id+"'><span class='glyphicon glyphicon-eye-open'></span></a></th></tr></thead><tbody><tr><td colspan='2'>"+data[i].content+"</td></tr><tr><td colspan='2'>"+ data[i].type + "&nbsp;&nbsp;&nbsp;"+data[i].emergency+"&nbsp;&nbsp;&nbsp;"+data[i].createuser+"</td></tr><tr><td colspan='2'>" + data[i].planbegintime + "-->"+data[i].planendtime+"</td></tr></tbody></table>";
        	fragment.append(li);
        }
    }
    else
    	{
    	return null;
    	}
	
    return fragment[0];
}


