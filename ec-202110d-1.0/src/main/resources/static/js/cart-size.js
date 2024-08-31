'use-strict'

$(function(){
	
	$(function() {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    })
		
	$.ajax({
			url : 'http://localhost:8080/cart-size/size',
			type : 'POST',
			dataType : 'json',
			data : {
				id : $('.user').val()
			},
			async : true
		// 非同期で処理を行う
		}).done(function(data) {
			// コンソールに取得データを表示
			console.log(data);
			console.dir(JSON.stringify(data));
			//返ってきた名前リストをレスポンスに渡す
			$('#cart-size').text(data.size);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert('エラーが発生しました！');
			console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
			console.log('textStatus     : ' + textStatus);
			console.log('errorThrown    : ' + errorThrown.message);
		
		});
		}
		);
		});