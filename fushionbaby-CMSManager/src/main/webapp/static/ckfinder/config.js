/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckfinder.com/license
*/

CKFinder.customConfig = function( config )
{
	// Define changes to default configuration here.
	// For the list of available options, check:
	// http://docs.cksource.com/ckfinder_2.x_api/symbols/CKFinder.config.html

	// Sample configuration options:
	config.uiColor = '#f7f5f4';
	config.language = 'zh-cn';
	config.removePlugins = 'basket,help';
	config.defaultSortBy = 'date';
	config.width = '800px'; // 宽度
    config.height = '300px'; // 高度
    
    config.filebrowserBrowseUrl = '/static/ckfinder/ckfinder.html';
    config.filebrowserImageBrowseUrl = '/static/ckfinder/ckfinder.html?type=Images';
    config.filebrowserFlashBrowseUrl = '/static/ckfinder/ckfinder.html?type=Flash';
    config.filebrowserUploadUrl = '/static/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
    config.filebrowserImageUploadUrl = '/static/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
    config.filebrowserFlashUploadUrl = '/static/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';
	
};
