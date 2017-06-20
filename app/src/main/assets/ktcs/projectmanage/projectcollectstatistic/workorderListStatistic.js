var myscroll;
var count;
var content='';
loaded();
function loaded(){
	var wrapper=document.querySelector('#wrapper');
	var content=document.querySelector('#thelist');
	myscroll=new IScrollLoadData(wrapper,content,dropTopAction,dropBottomAction,30,context+'/custmgr/projectmanage/collectWorkorder_collectMobile.action');
	myscroll.search();
	$("#loadingDiv").show();
}
var tddom= document.getElementsByTagName('li');
//生成html

$(function(){
	$("#serBtn").click(function(){
			var params = "";
			var orderno = $("#gdh").val();
			params += "&orderno=" + orderno;
			params = decodeURIComponent(params,true);
			//在进行编码
			params = encodeURI(encodeURI(params));
			myscroll.param = params;
		 
			myscroll.search();
	})
	
})

function dropTopAction(){
	 
	var fragment = $(document.createDocumentFragment());
    var data = myscroll.datas;
    if(data.length > 0)
    {
    	
    	for(var i = 0; i < data.length; i++){
			
			if(data[i].content.length>15){
    			content=data[i].content.substr(0,15)+"...";
    		}else{
    			content=data[i].content;
    		}
			
        	var li= $("<li style='padding:2px 5px; height:200px;  ' ></li>");
        	count=i+1;
			var table="<table class='table table-bordered'><thead><tr style='background-color:#CCCCCC;'><th width='20px;'>"+count+"</th><th>" + data[i].orderNo + "&nbsp;&nbsp;<a href='workorderdetailstatistic.html?"+data[i].workorderId+"' style=\"float:right;margin-right:3%\">&nbsp;&nbsp;查看</a></th></tr></thead><tbody><tr><td colspan='2'>"+content+"</td></tr><tr><td colspan='2'>"+ data[i].type + "&nbsp;&nbsp;&nbsp;"+data[i].emergency+"</td></tr><tr><td colspan='2'>总" + data[i].gznum + "台&nbsp;&nbsp;&nbsp;当前完成"+data[i].completepercent+"%</td></tr><tr><td colspan='2'>总费用"+data[i].totalMoney+"元</td></tr></tbody></table>";
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
function dropBottomAction(data){
	var fragment = $(document.createDocumentFragment());
    if(data.length > 0)
    {
    	for(var i = 0; i < data.length; i++)
        {	
        	if(data[i].content.length>15){
    			content=data[i].content.substr(0,15)+"...";
    		}else{
    			content=data[i].content;
    		}
        	var li= $("<li style='padding:2px 5px; height:200px;  ' ></li>");
        	count=++count;
    		var table="<table class='table table-bordered'><thead><tr style='background-color:#CCCCCC;'><th width='20px;'>"+count+"</th><th>" + data[i].orderNo + "&nbsp;&nbsp;<a href='workorderdetailstatistic.html?"+data[i].workorderId+"' style=\"float:right;margin-right:3%\">&nbsp;&nbsp;查看</a></th></tr></thead><tbody><tr><td colspan='2'>"+content+"</td></tr><tr><td colspan='2'>"+ data[i].type + "&nbsp;&nbsp;&nbsp;"+data[i].emergency+"</td></tr><tr><td colspan='2'>总" + data[i].gznum + "台&nbsp;&nbsp;&nbsp;当前完成"+data[i].completepercent+"%</td></tr><tr><td colspan='2'>总费用"+data[i].totalMoney+"元</td></tr></tbody></table>";
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


window.addEventListener("DOMContentLoaded",loaded,false);


//$("#searchBtn").click(handleSearch);

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
	
	$("#orderno").val('');
	$("#gznum").val('');
	$("#type").val('');
	$("#orderstatus").val('');
	$("#emergency").val('');
}
var height = window.innerHeight;
	//	window.onresize = function () {
//			var temp = window.innerHeight;
//			$("#searchIn").css("WebkitTransform","scale("+(temp/height)*0.65+")");
//		}

function showSearchDiv() {
			$("#searchModal_contentDia").css("position", "absolute");
			$("#searchModal_contentDia").css("left", location.left + 5 + "px");
			$("#searchModal_contentDia").css("top", location.top + 20 + "px");
	
			$('#searchModal').modal('show');
			
			}
function checkNum(){
	var gznum=$("#gznum").val();
	var reg = /^\d+$/;
	if(!reg.test(gznum)){
		$("#gznum").val("");
	}
}