
	 

	function showModal(funcName,srcPath,title,selectId) {
	 
		var divContent = $("#myModalBank").find(".modal-body").find("#body-content");
		 
		divContent.html($("#"+selectId).html());
	 
		divContent.find("table").attr("id","myTable");
		
		if(selectId == 'bankList')
		{
			
			getTableDataBank("myTable",appContext+"/saleCenter/saleBill/bankList_salesMobile.action;jsessionid="+sessionId);
		}
		else if(selectId == 'contractList')
		{
		
			getTableDataContract("myTable",
								appContext+"/saleCenter/saleBill/contractList_salesMobile.action;jsessionid="+sessionId);
		}
		else if(selectId == 'machineTypeList')
		{
	
			getTableDataMachineType("myTable",
								appContext+"/saleCenter/saleBill/machineTypeList_salesMobile.action;jsessionid="+sessionId);
			 
		}
		else if(selectId == 'orgTopList')
		{
	
			getTableDataOrgTopList("myTable",
								appContext+"/saleCenter/saleBill/orgTopList_salesMobile.action;jsessionid="+sessionId);
		}
		else if(selectId == 'templateList')
		{
	
			getTableDataTemplateList("myTable",
								appContext+"/saleCenter/saleBill/templateList_salesMobile.action;jsessionid="+sessionId);
		}
		else if(selectId == 'addressList')
		{
	
			getTableDataAddress("myTable",
								appContext+"/saleCenter/saleBill/addressList_salesMobile.action;jsessionid="+sessionId);
		}
		
		 
		$('#myModalBank').modal({
			show : true
		});
		$('#funcName').val(funcName);
		$('#myModalLabel').text(title);
		
		//alert($('#mytable').html());
		 
	}

	function handSelect() {
		var fn = $('#funcName').val();
		$('#myModalBank').modal('hide');
		//$(".container").show();
		eval(fn)($("#myModalBank").find('#hiddenValue').val());

	}
	
	function closeModal()
	{
		$('#myModalBank').modal('hide');
	}

	 