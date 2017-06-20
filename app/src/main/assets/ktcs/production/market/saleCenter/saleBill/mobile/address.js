
//选择这一行时，选中radio
	function selectThisRowAddress(obj, id, name) {
		var ra = $(obj).find("input[type=radio]");

		if (ra)
			ra.prop("checked", "checked");
		
		//保存选择的这一行的值
		$("#hiddenValue").val(
				"{'addressId':'" + id + "','address':'" + name + "'}");
				
		handSelect();
	}

	function queryPageAddressList(redirect) {
		
		//alert(currentPage);
		if(1 == redirect)
			currentPage++;
		else if(-1 == redirect)
			currentPage--;
		getTableDataAddress("myTable",appContext+"/saleCenter/saleBill/addressList_salesMobile.action;jsessionid="+sessionId,"address="+encodeURI(encodeURI($.trim($(".modal-body #address").val())))+"&pageNum="+currentPage);
	
	}
	
	//搜索
	function searchAddressList()
	{
		
		getTableDataAddress("myTable",appContext+"/saleCenter/saleBill/addressList_salesMobile.action;jsessionid="+sessionId,"address="+encodeURI(encodeURI($.trim($(".modal-body #address").val()))));
	
	}

	
	//生成table
	function generateTable(tableId,url)
	{
		
	}
	
	//加载数据
	var currentPage ;
	function getTableDataAddress(tableId,url,param)
	{
	
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
				currentPage = msg.page;
				var totalPage = msg.total;
				
				//清空表格数据
				$("#"+tableId+" tr:not(:first)").remove();

				for ( var i = 0; i < data.length; i++) {
					generateTrAddress(data[i].id, data[i].reAddress,tableId);
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
	function generateTrAddress(id, name,tableId) {
	
	 

		var tr = $("<tr style='cursor: pointer;line-height:25px;'></tr>");
		tr.bind("click", function() {
			selectThisRowAddress(this, id, name);
		})

		var td1 = $("<td align='center'></td>");
		var radioInput = $("<input type='radio' name='optionCheckbox' id='"+id+"' value='"+id+"'>");
		radioInput.appendTo(td1);
		td1.appendTo(tr);
		
		var td2 = $("<td >"+name+"</td>");
		td2.appendTo(tr);
		
		tr.appendTo($("#"+tableId));

	}
	
	