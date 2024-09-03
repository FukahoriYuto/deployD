'use strict';
$(function() {
	
	$(function() {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
	
	$('#name').autocomplete({
		source : function(request, response){
		$.ajax({
			url : 'http://localhost:8080/auto-complete/list',
			type : 'POST',
			dataType : 'json',
			data : {
				//入力された文字を受け取る
				name : request.term,
				//ソートのための判定をする数字
				sortNum : $('#searchSortNum').val()
			},
			async : true
		// 非同期で処理を行う
		}).done(function(data) {
			// コンソールに取得データを表示
			console.log(data);
			console.dir(JSON.stringify(data));
			//返ってきた名前リストをレスポンスに渡す
			response(data);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
			alert('エラーが発生しました！');
			console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
			console.log('textStatus     : ' + textStatus);
			console.log('errorThrown    : ' + errorThrown.message);
		
		});
		}
	});
});
});