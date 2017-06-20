/**
 * 2013-09-11
 * sun
 */
$(function() {
    // 按回车搜索
    $(".searchpanel").keypress(function(e) {
	if (e.keyCode == 13) {
	    doQuery();
	}
    });
    // class为requireInput的加红色标示
    $(function() {
	var emMark = '<em style="color:red;">*</em>';
	$("td.requireInput").append(emMark);

	$(".valid_require").each(function() {
	    $(this).parents("td:first").prev("td").append(emMark);
	});
    });
});

/**
 * 添加提示边框
 * 
 * @param param
 */
function addAlarmBorder(param) {
    param.css("border-color", "red");
    param.parent("div").css("border-color", "red");
}

/**
 * 移除提示边框
 * 
 * @param param
 */
function removeAlarmBorder(param) {
    param.css("border-color", "");
    param.parent("div").css("border-color", "");
}

/**
 * 查询自修单
 */
function doQuery() {
    Condition.postGrid('mytable');
}

/**
 * 取消，关闭窗口
 */
function cancel() {
    window.close();
}

/**
 * 重置GridTable
 */
function reset() {
    $("#mytable").resetSelection();
}

/**
 * 清空数据
 */
function clear() {
    $("#mytable").clearGridData(true);
}
/**
 * 设置Gird表格为单选模式 需要设置Grid属性 beforeSelectRow="checkOneRow"
 * 
 * @param rowid
 * @returns
 */
function checkOneRow(rowid) {
    var check = $("#jqg_mytable_" + rowid).attr("checked");
    $("#mytable").resetSelection();
    $(".cbox").removeAttr("checked");
    return !check;
}

/**
 * 是否选择一行记录
 */
function choiceOneRow(selectedIds) {
    if (selectedIds.length != 1) {
	alert("请选择一条记录！");
	return false;
    } else {
	return true;
    }
}

/**
 * 获取最后选择行的ID
 */
function lastSelRow() {
    return $("#mytable").jqGrid("getGridParam", "selrow");
}

/**
 * 查看处理流程
 */
function viewFlowCommon(flowCode) {
    var selectedIds = getSelectedIds('mytable');
    if (!choiceOneRow(selectedIds))
	return;
    var busId = selectedIds[0];
    var openUrl = context + '/common/workflow/history.jsp?flowCode=' + flowCode
	    + '&busId=' + busId;
    openWindow(openUrl, 850, 550);
}

/**
 * 返回选择的数据
 */
function returnSelectRowData(rowid) {
    var rowData = $("#mytable").getRowData(rowid);
    window.returnValue = rowData;
    window.close();
}

/**
 * 获取表单字段序列
 */
function getFormParam(formName) {
    return $("#" + formName).serialize();
}

/**
 * 展示表单数据
 */
function showFormParam(formParam) {
    var reg = new RegExp("&", "g");
    var paramStr = ("FormParam:\n" + formParam).replace(reg, "\n");
    return paramStr;
}

/**
 * 通用异步Ajax 操作后刷新数据，关闭页面
 */
function commonAjax(url, data, succMsg, failMsg) {
    $.ajax({
	url : url,
	type : "post",
	dataType : "json",
	data : data,
	success : function(result) {
	    if (result.successful) {
		if(Boolean(succMsg)){
		    alert(succMsg);
		}
		window.returnValue = "success";
		window.close();
	    } else {
		alert(failMsg + "：" + result.message);
	    }
	},
	error : function() {
	    alert("系统异常！");
	}
    });
}

/**
 * 通用异步Ajax,不关闭窗口
 */
function commonAjaxNoClose(url, data, succMsg, failMsg) {
    $.ajax({
	url : url,
	type : "post",
	dataType : "json",
	data : data,
	success : function(result) {
	    if (result.successful) {
		if(Boolean(succMsg)){
		    alert(succMsg);
		}
		window.returnValue = "success";
	    } else {
		if(Boolean(failMsg)){
		    alert(failMsg + "：" + result.message);
		}
	    }
	},
	error : function(result) {
	    alert("系统异常:" + result.message);
	}
    });
}
/**
 * 通用异步Ajax,完成后刷新当前窗口
 */
function commonAjaxCurr(url, data, succMsg, failMsg) {
    $.ajax({
	url : url,
	type : "post",
	dataType : "json",
	data : data,
	success : function(result) {
	    if (result.successful) {
		alert(succMsg);
		doQuery();
	    } else {
		alert(failMsg + "：" + result.message);
	    }
	},
	error : function(result) {
	    alert("系统异常:" + result.message);
	}
    });
}

/**
 * 通用异步Ajax 操作后刷新父窗口数据，关闭当前页面
 */
function commonAjaxOpener(url, data, succMsg, failMsg) {
    $.ajax({
	url : url,
	type : "post",
	dataType : "json",
	data : data,
	success : function(result) {
	    if (result.successful) {
		if(Boolean(succMsg)){
		    alert(succMsg);
		}
		opener.doQuery();
		window.close();
	    } else {
		if(Boolean(failMsg)){
		    alert(failMsg + "：" + result.message);
		}
	    }
	},
	error : function(result) {
	    alert("系统异常:" + result.message);
	}
    });
}

/**
 * 跨域Ajax访问，使用jsonp
 */
function commonAjaxDomain(url, data, succMsg, failMsg){
    commonAjaxDomainCallback(url, data, succMsg, failMsg, null, null, null, null);
}

/**
 * 跨域Ajax访问，使用jsonp，添加回调函数
 */
function commonAjaxDomainCallback(url, data, succMsg, failMsg, errorMsg, successFun, failFun, errorFun){
    $.ajax({
	url : url + "?callback=?",
	type : "get",
	async : false,
	dataType : "jsonp",
	data : data,
	success : function(result) {
	    if(result.successful){
		if(Boolean(succMsg)){
		    alert(succMsg);
		}
		if(Boolean(successFun)){
		    eval(successFun);
		}
	    }else{
		if(Boolean(failMsg)){
		    alert(failMsg);
		}
		if(Boolean(failFun)){
		    eval(failFun);
		}
	    }
	},
	error : function(result) {
	    if(Boolean(errorMsg)){
		alert(errorMsg);
	    }
	    if(Boolean(errorFun)){
		eval(errorFun);
	    }
	}
    });
}

/**
 * 隐藏指定标签 使用可变参数
 */
function hideTagById() {
    for ( var i = 0; i < arguments.length; i++) {
	$("#" + arguments[i]).hide();
    }
}

/**
 * 置灰指定标签及内容 使用可变参数
 */
function disabledTagById() {
    for ( var i = 0; i < arguments.length; i++) {
	$("#" + arguments[i]).find(":input,div,td")
		.attr("disabled", "disabled");
    }
}

/**
 * 置灰指定标签及内容 使用可变参数
 */
function disabledTag(tag) {
    tag.find(":input,div,td").attr("disabled", "disabled");
}

/**
 * 获取选择的记录（单条）
 */
function getSelectedId() {
    var selectedIds = getSelectedIds("mytable");
    if (choiceOneRow(selectedIds)) {
	return selectedIds[0];
    } else {
	return "";
    }
}

/**
 * 是否有记录
 */
function hasData() {
    var tableName = Boolean(arguments[0]) ? arguments[0] : "mytable";
    var rowIds = $("#"+tableName).getDataIDs();
    return rowIds.length > 0;
}

/**
 * 转到通用的添加一个物料页面 返回添加的物料
 */
function toCommonAddOneWlPage() {
    var url = context + "/custmgr/common/toAddOneWlPage_common.action";
    return openShowModal(url, 700, 380);
}

/**
 * 表单字段验证
 * valid_requeir ：验证是否为空
 * valid_number{10,4} ：验证小数，小数点前6位，小数点后4位精度
 */
function doValidate() {
    var msg = new Array();
    var requireMsg = "数据不完整！";
    var numberMsg = "数字不合法！";
    $(":input[class*='valid_']")
	    .each(
		    function() {
			var style = $(this).attr("class");
			var val = "";
			if($(this).attr("tagName") == "textarea"){
			    val = $.trim($(this).text());
			}else{
			    val = $.trim($(this).val());
			}
			var msgStr = msg.join("");

			// 验证是否为空
			var validRequire = true;
			if (style.indexOf("valid_require") >= 0
				&& !Boolean(val)) {
			    validRequire = false;
			    if (msgStr.indexOf(requireMsg) < 0) {
				msg.push(requireMsg);
			    }
			}

			// 验证数字或小数
			var validNumber = true;
			var index = style.indexOf("valid_number");
			if (Boolean(val) && index >= 0) {
			    var rexPrecision = new RegExp("(?!valid_number{)\\d{1,2},\\d{1,2}","g");
			    var precision = style.match(rexPrecision);
			    if (Boolean(precision)) {
				// 验证小数
				var strs = precision[0].split(",");
				var len = parseInt(strs[0]);	// 长度
				var pre = parseInt(strs[1]);	// 精度
				// 判断是否符合小数格式
				var rexDecimals = new RegExp("^\\d{1," 
					+ (len-pre) + "}(\\.\\d{0,"
					+ pre +"})?$", "g");
				if (!rexDecimals.test(val)) {
				    validNumber = false;
				    if (msgStr.indexOf(numberMsg) < 0) {
					msg.push(numberMsg);
				    }
				}
			    } else {
				// 验证数字
				var rexNumber = new RegExp("^[1-9]\\d*$", "g");
				if (!rexNumber.test(val)) {
				    validNumber = false;
				    if (msgStr.indexOf(numberMsg) < 0) {
					msg.push(numberMsg);
				    }
				}
			    }
			}
			// 根据验证结果改变边框颜色
			if (validRequire && validNumber) {
			    removeAlarmBorder($(this));
			} else {
			    addAlarmBorder($(this));
			}
		    });

    // 如果验证未通过，则显示错误信息
    var msgLength = msg.length;
    if (msgLength > 0) {
	alert(msg.join("\n"));
	// 光标移到第一个不匹配的输入框
	$("input[style*='BORDER-BOTTOM-COLOR: red']").not(".Wdate").first().focus();
    }
    return msgLength == 0;
}

/**
 * 判断GridTable 中是否有编辑行
 */
function isGridEdit(tableName){
    tableName = Boolean(tableName)?tableName:"mytable";
    return  $("#"+tableName + " .editable:input:visible").length > 0;
}

/**
 * 验证流程状态
 * 
 * @param acceptanceId
 * @returns {Boolean}
 */
function doValidFlowStatus(gridRowId,msg) {
    if (arguments.length > 2) {
	var argArray = Array.prototype.slice.call(arguments);
	argArray = argArray.slice(2); // 删除前两项
	var rowData = $("#mytable").getRowData(gridRowId);
	var flowStatus = rowData.flowStatus;
	var argStr = "," + argArray.join(",") + ",";
	if (Boolean(flowStatus) && argStr.indexOf("," + flowStatus + ",") >= 0) {
	    return true;
	} else if(Boolean(msg)){
	    alert(msg);
	}
    }
    return false;
}