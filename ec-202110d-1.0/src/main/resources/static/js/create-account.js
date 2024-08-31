'use strict';
$(function(){
	$(document).on('click','#get_address_btn',function(){
		$.ajax({
			url: 'http://zipcoda.net/api',
			dataType: 'jsonp',
			data:{
				zipcode:$('#zipcode').val()
			},
			async:true
		}).done(function(data){
			console.log(data);
			console.dir(JSON.stringify(data));
			$('#address').val(data.items[0].address);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert('郵便番号が不正です。');
			console.log('XMLHttpRequest :'+XMLHttpRequest.status);
			console.log('textStatus :'+textStatus);
			console.log('errorThrown :'+errorThrown.message);
		});
	});
});