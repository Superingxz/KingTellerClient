(function(window){
function IScrollLoadData(wrapperEl,contentEl,dropTopAction,dropBottomAction,limit,url){

	

	this.wrapper=wrapperEl;
	this.content=contentEl;
	this.pullDownEl=this.wrapper.querySelector('#scroller-pullDown');
	this.pullUpEl=this.wrapper.querySelector('#scroller-pullUp');
	this.scrollerEl=this.wrapper.querySelector('.scroller');
	this.dropTopAction=dropTopAction;
	this.dropBottomAction=dropBottomAction;
	this.limit=limit||30;
	
	//alert("iscroll-load-data ... 22222");

	
	//this.clickTop_bind=this.clickTop.bind(this);
	//alert("iscroll-load-data ... 22222_11111111111");

	//this.clickBottom_bind=this.clickBottom.bind(this);
	//alert("iscroll-load-data ... 22222_22222222222");

	
	this.pullUpLabel_Text='上翻更多...';
	this.pullDownLabel_Text='下拉刷新...';
	this.pullLoading_Text='加载中~~';
	this.releaseLoading_Text='松开加载~~';
	this.loading_top_flag=false;
	this.loading_bottom_flag=false;
	
	
	this.checkIScroll(true);
	

	this.createIScroll();


	this.url = url;		//远程url
	this.param = '';	//参数
	this.datas = null;	//数据集
	this.selectedData = null;	//点击选中的记录
	this.pageNum = 1;
	this.loadMode = 'page';	//加载方式 : page:分页加载 ，all:一次性全部加载
	
	//this.bottomRecord = null;	//最后一条底部数据
	//this.topRecord = null;	//最上面一条顶部数据
	
	//alert("iscroll-load-data ... end");
};
IScrollLoadData.prototype.refresh=function(direct){
	//this.updateContentLen(direct);
	this.checkIScroll();
	delete this.myScroll.waitLoadTop;
	this.myScroll.refresh();
}

IScrollLoadData.prototype.empty=function(direct){
	$(this.content).empty();
}

IScrollLoadData.prototype.updateContentLen=function(direct){
	var children=this.content.children;
	if(children.len>this.limit){
		if(direct=='bottom'){
			
		}else{
			
		}
	}
}
IScrollLoadData.prototype.checkIScroll=function(flag){
	//alert("checkIscroll ... 1111");
	var soh=this.content.offsetHeight+this.pullDownEl.offsetHeight+this.pullUpEl.offsetHeight;
	var woh=this.wrapper.clientHeight;
	var holder=this.scrollerEl.querySelector('.scroller-holder');
	//alert("checkIscroll ... 3333333333");
	if(soh<woh){
		if(!holder){
			var ul=document.createElement('ul');
			ul.className='scroller-holder';
			this.scrollerEl.appendChild(ul);
			holder=ul;
		}
		holder.style.height=(woh-soh+2)+'px';
	}else if(holder){
		this.scrollerEl.removeChild(holder);
	}
	
	//alert("checkIscroll ... 99999999999");
}

//查询数据
IScrollLoadData.prototype.search=function(){
	
	//初始化
	var self=this;
	this.pageNum = 1;
	this.datas = null;
	
	//从后台取数据
	this.loadData(function(msg){
		
		if(self.dropTopAction){
			//向下拉的时候，全部刷新数据
			self.empty();
			self.appendRow('top',self.dropTopAction());
		}
		
		self.refresh('top');
	
	});

};


//下拉时处理函数
IScrollLoadData.prototype.pullDownAction=function(){
	
	this.param = '';
	this.search();
	
};

//上拉时处理函数
IScrollLoadData.prototype.pullUpAction=function(){
	
	var self=this;
	//上拉时，只有分页加载时才响应
	if(this.loadMode == 'page')
	{
		self.pageNum += 1;
		
		//从后台取数据
		this.loadData(function(msg){
			if(self.dropBottomAction){
				//显示新记录数
				self.showRecordsSize('bottom',msg.rows.length);
				
				var showRecordsSizeTimer = setTimeout(function(){
					var c = self.dropBottomAction(msg.rows);
					//alert(c);
					if(c)
						self.appendRow('bottom',c);
					self.refresh();
				},800);
			}
			
		});
	}
	else
		self.refresh();;
	
};

//动态加载数据
IScrollLoadData.prototype.loadData=function(callback){
	var self=this;
	
	setTimeout(function(){
	//alert(self.pageNum);
	$.ajax({
		type: "post",//使用get方法访问后台
	    dataType: "jsonp",//返回json格式的数据
	    url: self.url,//要访问的后台地址
	    data: self.param+"&currentPage="+self.pageNum,//要发送的数据
		jsonp:"jsonpcallback",
		async:false,
	    success: function(msg){//msg为返回的数据，在这里做数据绑定
			//alert(msg);
			$("#loadingDiv").hide();
	    	if(self.datas == null)
	    		self.datas = msg.rows;
	    	else if(msg.rows.length > 0)
	    	{
	    		for(var i = 0; i < msg.rows.length; i++)
	    		{
	    			self.datas.push(msg.rows[i]);
	    		}
	    	}
			else if (msg.rows.length == 0)
			{
				self.pageNum = self.pageNum -1;	//如果当前页没有数据，则重新设置为前一页
			}
	    	
	    	callback(msg);
	    	
	    },
	error:function(msg){
		 
		$("#loadingDiv").hide();
		alert("出现错误!");
	}
	    });

	},500);
	
};

function jsonpcallback(data)
{
	
}

IScrollLoadData.prototype.loadDataByProperty=function(property,value){
	if(property)
	{
		var rows = this.datas;
		for(var i = 0; i < rows.length; i++)
		{
			var rowData = rows[i];
			for(var key in rowData){
				   if(key == property && rowData[property] == value)
				   {
					   return rowData;
				   }
				}
		}
	}
}

//把新的记录添加到列表组件中
IScrollLoadData.prototype.appendRow=function(direct,rowData){
	
	if(direct == 'top' && rowData != null)
	{
		var firstChild=this.content.querySelector(':first-child');
		if(firstChild)
			this.content.insertBefore(rowData,firstChild);
		else
			this.content.appendChild(rowData);
	}
	else if(direct == 'bottom' && rowData != null)
	{
		this.content.appendChild(rowData);
	}
	else if(direct == 'top' && rowData == null)
	{
		
	}

};

//显示新数据的条数
IScrollLoadData.prototype.showRecordsSize=function(direct,size){
	var self=this;
	if(direct == 'top')
	{
		if(size == 0)
			this.pullDownEl.querySelector('.pullDownLabel').innerText="暂无数据";
		else
			this.pullDownEl.querySelector('.pullDownLabel').innerText=size+"条新数据";
	}
	else if(direct == 'bottom')
	{
		if(size == 0)
			this.pullUpEl.querySelector('.pullUpLabel').innerText="暂无数据";
		else
			this.pullUpEl.querySelector('.pullUpLabel').innerText=size+"条数据";
	}
};

IScrollLoadData.prototype.clickTop=function(){
	var self=this;
	if(this.dropTopAction){
		this.dropTopAction(function(out){
			var firstChild=self.content.querySelector(':first-child');
			if(firstChild)
			  self.content.insertBefore(out,firstChild);
			else
			  self.content.appendChild(out);
			self.initIScroll(false);
			
		});
	}
};
IScrollLoadData.prototype.clickBottom=function(){
	var self=this;
	if(this.dropBottomAction){
		this.dropBottomAction(function(out){
			self.content.appendChild(out);
			self.initIScroll(false);
			
		});
	}
};
IScrollLoadData.prototype.initIScroll=function(flag){
	if(this.overflow_window){
		return;
	}
	
	if(this.scrollerEl.offsetHeight-this.pullDownEl.offsetHeight>=document.documentElement.clientHeight){
		this.wrapper.style.bottom='0px';
		this.wrapper.style.top=((-1)*this.pullDownEl.offsetHeight)+'px';
		//this.wrapper.style.top='0px';
		//alert(this.wrapper.style.top);
		this.pullUpEl.querySelector('.pullUpLabel').innerText=this.pullUpLabel_Text;
		this.pullDownEl.querySelector('.pullDownLabel').innerText=this.pullDownLabel_Text;
		this.pullUpEl.removeEventListener('click',this.clickBottom_bind);
		this.pullDownEl.removeEventListener('click',this.clickUp_bind);
		this.myScroll.refresh();
		this.overflow_window=true;
	}else{
		this.wrapper.style.bottom=(document.documentElement.clientHeight-this.scrollerEl.offsetHeight)+'px';
		this.pullUpEl.querySelector('.pullUpLabel').innerText='点击获取最近...';
		this.pullDownEl.querySelector('.pullDownLabel').innerText='点击获取最新...';
		if(flag){
			this.pullUpEl.addEventListener('click',this.clickBottom_bind);
			this.pullDownEl.addEventListener('click',this.clickTop_bind);
		}
	}
	
}
IScrollLoadData.prototype.createIScroll=function(){
	//alert("createIScroll ... 1111");

	var self=this;
	this.myScroll = new IScrollLoadData.IScroll(this.wrapper, {
		probeType: 2, mouseWheel: false,bindToWrapper:true,scrollY:true,click:true,scrollbars: true
	}).on('scroll',function(){
		//alert("scrolling..");
		if (this.y > 50 &&  
			(!self.pullDownEl.className.match('flip') && 
			 !self.pullDownEl.className.match('loading'))) {
			self.pullDownEl.className = 'flip';
			self.pullDownEl.querySelector('.pullDownLabel').innerHTML = self.releaseLoading_Text;
			this.waitLoadTop=self.pullDownEl.offsetHeight;
		} else if (this.y < 50 && self.pullDownEl.className.match('flip')) {
			self.pullDownEl.className = '';
			self.pullDownEl.querySelector('.pullDownLabel').innerHTML = self.pullDownLabel_Text;
			 delete this.waitLoadTop;
		} else if (this.y < (this.maxScrollY - 5) && 
				  (!self.pullUpEl.className.match('flip')&&
				   !self.pullUpEl.className.match('loading'))) {
			self.pullUpEl.className = 'flip';
			self.pullUpEl.querySelector('.pullUpLabel').innerHTML = self.releaseLoading_Text;
		} else if (this.y > (this.maxScrollY + 5) && self.pullUpEl.className.match('flip')) {
			self.pullUpEl.className = '';
			self.pullUpEl.querySelector('.pullUpLabel').innerHTML = self.pullUpLabel_Text;
		}
	}).on('scrollEnd',function(){
		//alert("scrolling end.");
		
		if (self.pullDownEl.className.match('flip')) {
			self.pullDownEl.className = 'loading';
			self.pullDownEl.querySelector('.pullDownLabel').innerHTML = self.pullLoading_Text;	
			self.pullDownAction();// Execute custom function (ajax call?)
			
		} else if (self.pullUpEl.className.match('flip')) {
			self.pullUpEl.className = 'loading';
			self.pullUpEl.querySelector('.pullUpLabel').innerHTML = self.pullLoading_Text;				
			self.pullUpAction();	// Execute custom function (ajax call?)
		}
	}).on('refresh',function(){
		//alert("refresh..");
		if (self.pullDownEl.className.match('loading')) {
			self.pullDownEl.className = '';
			self.pullDownEl.querySelector('.pullDownLabel').innerHTML = self.pullDownLabel_Text;
		} else if (self.pullUpEl.className.match('loading')) {
			self.pullUpEl.className = '';
			self.pullUpEl.querySelector('.pullUpLabel').innerHTML = self.pullUpLabel_Text;
		}
	});
	
	//alert("createIScroll ... 999999");
	
};
if("function" == typeof define &&  define.amd){
  define("IScrollLoadData", ['IScroll'], function(IScroll) {
        IScrollLoadData.IScroll=IScroll;
        return IScrollLoadData;
    });   
}else {
    IScrollLoadData.IScroll=window.IScroll;
    window.IScrollLoadData = IScrollLoadData;
}

})(window);