
//选择这一行时，选中radio
function selectThisRowTemplateList(obj, id, name, bankId, bankName, cooperationType,
		machineType, machineTypeCode, price, senderAddress,senderAddressId, bankContact,
		bankPhone, receiverName, receiverPhone, contractId, contractNumber,
		salebillType, saleBillTypeName, machineConfiguration,
		specialRequirement, remark,orgTopId,orgTopName,material) {
	var ra = $(obj).find("input[type=radio]");

	if (ra)
		ra.prop("checked", "checked");

	//保存选择的这一行的值
	$("#hiddenValue")
			.val(
					"{'id':'"
							+ id
							+ "','name':'"
							+ name
							+ "','bankId':'"
							+ bankId
							+ "','bankName':'"
							+ bankName
							+ "','cooperationType':'"
							+ cooperationType
							+ "','machineType':'"
							+ machineType
							+ "','machineTypeCode':'"
							+ machineTypeCode
							+ "','price':'"
							+ price
							+ "','senderAddress':'"
							+ senderAddress
							+ "','senderAddressId':'"
							+ senderAddressId
							+ "','bankContact':'"
							+ bankContact
							+ "','bankPhone':'"+bankPhone+"','receiverName':'"+receiverName+"','receiverPhone':'"
							+ receiverPhone + "','contractId':'"
							+ contractId + "','contractNumber':'"
							+ contractNumber + "','salebillType':'"
							+ salebillType + "','saleBillTypeName':'"
							+ saleBillTypeName
							+ "','machineConfiguration':'"
							+ machineConfiguration
							+ "','specialRequirement':'"
							+ specialRequirement + "','remark':'" + remark
							+ "','orgTopId':'"+orgTopId+"','orgTopName':'"+orgTopName+"','material':'"+material+"'}");
							
	handSelect();
	
}


	function queryPageTemplateList(redirect) {
		
		if(1 == redirect)
			currentPage++;
		else if(-1 == redirect)
			currentPage--;
		
		getTableDataTemplateList(
					"myTable",
					appContext+"/saleCenter/saleBill/templateList_salesMobile.action;jsessionid="+sessionId,
					"templateName=" + encodeURI(encodeURI($.trim($(".modal-body #templateName").val()))) + "&pageNum=" + currentPage);
		
	}

	//搜索
	function searchTemplateList() {
		getTableDataTemplateList(
					"myTable",
					appContext+"/saleCenter/saleBill/templateList_salesMobile.action;jsessionid="+sessionId,
					"templateName=" + encodeURI(encodeURI($.trim($(".modal-body #templateName").val()))));
	}

	//生成table
	function generateTable(tableId, url) {

	}

	//加载数据
	var currentPage ;
	function getTableDataTemplateList(tableId, url, param) {
		$.jsonp({
			type : "post",//使用get方法访问后台
			dataType : "jsonp",//返回json格式的数据
			callbackParameter:"jsonpcallback",
			url : url,//要访问的后台地址
			data : param,//要发送的数据
			timeout:8000,
			beforeSend:function(XMLHttpRequest){ 
				$("#loading2").show();
			}, 
			complete : function() {
				$("#loading2").hide();
			},//AJAX请求完成时隐藏loading提示
			error:function(XMLHttpRequest, textStatus, errorThrown) {  
				$("#loading2").hide();
				if(textStatus == 'timeout')
				{
					alert("很抱歉,连接超时,请稍候重试!");
				}
				else
				{
					alert("出现异常!");
				}
				
			}, 
			success : function(msg) {//msg为返回的数据，在这里做数据绑定

				var data = msg.rows;
				var data = msg.rows;
				currentPage = msg.page;
				var totalPage = msg.total;
				
				if(data.length == 0)
				{
					$("#myModalBank").find(".initTr td").text("暂无数据");
					return;
				}

				//清空表格数据
				$("#"+tableId+" tr:not(:first)").remove();
				
				for ( var i = 0; i < data.length; i++) {
					generateTrTemplateList(data[i].salebillTemplateId, data[i].templateName,data[i].bankId,data[i].bankName,data[i].cooperationType,data[i].machineType,data[i].machineTypeCode,data[i].price,data[i].senderAddress,data[i].senderAddressId,data[i].bankContact,data[i].bankPhone,data[i].receiverName,data[i].receiverPhone,data[i].contractId,data[i].contractNumber,data[i].salebillType,data[i].saleBillTypeName,data[i].machineConfiguration,data[i].specialRequirement,data[i].remark,data[i].orgTopId,data[i].orgTopName,data[i].material,tableId);
				}
				
				if(totalPage > 1)
				{
					if(totalPage == currentPage)
					{
						$(".modal-body #nextPage").hide();
						$(".modal-body #prePage").show();
					}
					else if(totalPage > currentPage)
					{
						$(".modal-body #nextPage").show();
						
					}
					
					if(currentPage == 1)
					{
						$(".modal-body #prePage").hide();
					}
					else
						$(".modal-body #prePage").show();
					
				}
				else
				{
					$(".modal-body #nextPage").hide();
					$(".modal-body #prePage").hide();
				}
			}
		});
	}

	//生成表格行
	function generateTrTemplateList(id, name, bankId, bankName, cooperationType,
			machineType, machineTypeCode, price, senderAddress,senderAddressId, bankContact,
			bankPhone, receiverName, receiverPhone, contractId, contractNumber,
			salebillType, saleBillTypeName, machineConfiguration,
			specialRequirement, remark,orgTopId,orgTopName,material,tableId) {

		var tr = $("<tr style='cursor: pointer;'></tr>");
		tr.bind("click", function() {
			selectThisRowTemplateList(this,id, name, bankId, bankName, cooperationType,
					machineType, machineTypeCode, price, senderAddress,senderAddressId, bankContact,
					bankPhone, receiverName, receiverPhone, contractId, contractNumber,
					salebillType, saleBillTypeName, machineConfiguration,
					specialRequirement, remark,orgTopId,orgTopName,material);
		})

		var td1 = $("<td align='center'></td>");
		var radioInput = $("<input type='radio' name='optionCheckbox' id='"+id+"' value='"+id+"'>");
		radioInput.appendTo(td1);
		td1.appendTo(tr);

		var td2 = $("<td >" + name + "</td>");
		td2.appendTo(tr);
 
		tr.appendTo($("#"+tableId));
	}