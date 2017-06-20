
function DataList(tableId,url,param){

	this.tableId = tableId;
	this.url = url;
	this.param = param
	this.pageNum = 1;

}

DataList.prototype.refresh=function(direct){
	//this.updateContentLen(direct);
	this.checkIScroll();
	delete this.myScroll.waitLoadTop;
	this.myScroll.refresh();
}


//选择这一行时，选中radio
DataList.prototype.selectThisRow = function(obj, bankId, bankName) {
		var ra = $(obj).find("input[type=radio]");

		if (ra)
			ra.prop("checked", "checked");

		//保存选择的这一行的值
		$("#hiddenValue").val(
				"{'bankId':'" + bankId + "','bankName':'" + bankName + "'}");
	}

	DataList.prototype.queryPage=function(pageNum) {
		$("#form1")
				.attr(
						"action",
						appContext+"/saleCenter/saleBill/bankList_salesMobile.action;jsessionid="+sessionId+"?pageNum="
								+ pageNum);

		$("#form1").submit();

		$("#form1")
				.attr("action",
						appContext+"/saleCenter/saleBill/bankList_salesMobile.action;jsessionid="+sessionId);
	}
	
	//搜索
	DataList.prototype.search = function()
	{
		if($.trim($("#bankName").val()) != '')
		{
			this.getTableData("myTable",appContext+"/saleCenter/saleBill/bankList_salesMobile.action;jsessionid="+sessionId,"bankName="+$.trim($("#bankName").val()));
		}
	}

	
	//生成table
	DataList.prototype.generateTable = function(tableId,url)
	{
		
	}
	
	//加载数据
	DataList.prototype.load = function()
	{
		var self = this;
		$.ajax({
			type : "post",//使用get方法访问后台
			dataType : "jsonp",//返回json格式的数据
			jsonp:"jsonpcallback",
			url : self.url,//要访问的后台地址
			data : self.param,//要发送的数据
			complete : function() {
				$("#load").hide();
			},//AJAX请求完成时隐藏loading提示
			success : function(msg) {//msg为返回的数据，在这里做数据绑定

				var data = msg.rows;
			
				//清空表格数据
				$("#"+self.tableId+" tr:not(:first)").empty();

				for ( var i = 0; i < data.length; i++) {
					self.generateTr(data[i].bankId, data[i].bankCname);
				}
			}
		});
	}

	//生成表格行
	DataList.prototype.generateTr = function(bankId, bankName) {
		var self = this;
		var tr = $("<tr style='cursor: pointer;'></tr>");
		tr.bind("click", function() {
			self.selectThisRow(this, bankId, bankName);
		})

		var td1 = $("<td align='center'></td>");
		var radioInput = $("<input type='radio' name='optionCheckbox' id='"+bankId+"' value='"+bankId+"'>");
		radioInput.appendTo(td1);
		td1.appendTo(tr);
		
		var td2 = $("<td >"+bankName+"</td>");
		td2.appendTo(tr);

		tr.appendTo($("table"));

	}
	
	$(document).ready(function() {
		
		getTableData("myTable",appContext+"/saleCenter/saleBill/bankList_salesMobile.action;jsessionid="+sessionId);
		
	})