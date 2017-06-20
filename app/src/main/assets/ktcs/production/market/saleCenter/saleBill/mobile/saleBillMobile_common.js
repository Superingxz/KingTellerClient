//配置参数
var localPath = 'F:/Workspace/nativehtml';

var remoteIp = '';
var remotePort = '';
var appContext = '';
var user;
var password;
var sessionId;
var roleCode;

var context='';	
 
 function decorateUrlHtml(url)
 {
	var uu = url + "&remoteIp="+remoteIp+"&remotePort="+remotePort+"&sessionId="+sessionId;

	if(url.indexOf('?') == -1)
	{
		uu = url + "?1=1&remoteIp="+remoteIp+"&remotePort="+remotePort+"&sessionId="+sessionId;
	}
	
	return url;
 }
 

 $(function () {
	
	 //得到请求的参数
		var request = {
			QueryString : function(val) {
				var uri = window.location.search;
				var re = new RegExp("" + val + "=([^&?]*)", "ig");
				return ((uri.match(re)) ? (uri.match(re)[0]
						.substr(val.length + 1)) : null);
			}
		}
		 
		remoteIp = request.QueryString("remoteIp");
		remotePort = request.QueryString("remotePort");
		sessionId = request.QueryString("sessionId");
		roleCode = request.QueryString("roleCode");
		
		if(remoteIp == null || remoteIp == '')
		{
			remoteIp = window.kingteller.getRemoteIp();
		}
		 if(remotePort == null || remotePort == '')
		{
			remotePort = window.kingteller.getRemotePort();
		}
		if(sessionId == null || sessionId == '')
		{
			sessionId = window.kingteller.getSessionId();
		}
		if(roleCode == null || roleCode == '')
		{
			roleCode = window.kingteller.getRole();
		}
		  
		
		appContext = "http://" + remoteIp+":" + remotePort + "/ktcs";
		context=appContext;
		
 });