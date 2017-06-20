//The build will inline common dependencies into this file.

//For any third party dependencies, like jQuery, place them in the lib folder.

//Configure loading modules from the lib directory,
//except for 'app' ones, which are in a sibling
//directory.

requirejs.config({
	//这个设置是基于引用comon.js的页面来设置的.
    baseUrl: '../../../../../js/iscroll/lib',
	//基于baseUrl
    paths: {
		IScroll:'./iscroll/iscroll-probe',
		'IScrollLoadData':'./iscroll/iscroll-load-data'
    }
});
