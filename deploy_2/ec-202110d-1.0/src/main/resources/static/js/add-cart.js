'use strict';
$(function() {
	$(function() {
		
	    let token = $("meta[name='_csrf']").attr("content");
	    let header = $("meta[name='_csrf_header']").attr("content");
	    
	  	$(document).ajaxSend(function(e, xhr, options) {
	        xhr.setRequestHeader(header, token);
	        });

	$('#add-btn').on('click',function(){
			
				$.ajax({
					url : 'http://localhost:8080/add-cart-api/addCart',
					type : 'POST',
					dataType : 'json',
					processData: false,
					data :  $('#add-cart').serialize(),
					async : true
				// 非同期で処理を行う
				}).done(function(data) {
					// コンソールに取得データを表示
					console.log(data);
					console.dir(JSON.stringify(data));
					$('#cart-size').text(data.size);
					$('#cart-success').show();
					$('#cart-success').fadeOut(1000);
						
				}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
					alert('エラーが発生しました！');
					console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
					console.log('textStatus     : ' + textStatus);
					console.log('errorThrown    : ' + errorThrown.message);
				
				});
			
		});
	
})});