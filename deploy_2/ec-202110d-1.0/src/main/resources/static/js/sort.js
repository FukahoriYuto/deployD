'use strict';

$(function(){
	
	$('#sort').on('change', function(){
		$('#searchSortNum').val($('#sort').val());
		$('#headerSearchForm').submit();
	});
	
	$('#search').on('click', function() {
		$('#searchSortNum').val($('#sort').val());
	});
	
});
