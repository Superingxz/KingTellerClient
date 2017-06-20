
/**
 * 获得选中行的主键id的值
 */
var getSelectedPkVal = function(tabelId) {  
    var selectedId = $('#'+tabelId).jqGrid("getGridParam", "selrow"); 
	//获取主键字段名称
    if(selectedId && selectedId!='null'){
	    var rowData = $("#mytable").jqGrid("getRowData", selectedId);  
	    var pkField = $('#'+tabelId).jqGrid("getGridParam", "pkField") ;
    	return eval('rowData.'+pkField);
    }  
}; 

/**
 * 获得选中多行的主键ids数据
 */
var getSelectedIds = function(tabelId) {  
	var selectedId = $('#'+tabelId).jqGrid("getGridParam", "selarrrow");
	return selectedId;
//    if(selectedId && selectedId!='null'){
//    	alert(selectedId);
//    } 
}; 

var checkGridRequestBefore = function(){
	var openUrl   = contextPath + '/toCheckGridAuth.action';	
	$.ajax({
		url : openUrl,
		data : {action:'begin'},
		dataType : 'json',		
		type : 'POST',
		success : function(resultBean) {
			//alert("动态加载grid开始!");
		}
	});
};

var checkGridRequestAfter = function(){
	var openUrl   = contextPath + '/toCheckGridAuth.action';	
	$.ajax({
		url : openUrl,
		data : {action:'end'},
		dataType : 'json',		
		type : 'POST',
		success : function(resultBean) {
			//alert("动态加载grid加载成功!");
		}
	});
};