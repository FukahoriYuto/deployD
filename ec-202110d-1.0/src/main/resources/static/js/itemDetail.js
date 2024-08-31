'use strict'
$(function(){
	let itemPrice = $('#totalPrice').text();
	let optionPrice = 0;
	let optionLength = 0;
	let amount = $('#amount').val();
	$('#totalPrice').text(Number(itemPrice).toLocaleString());
	
	$('.form-check-input').on('change',function(){
		calcPrice();
	})

	$('#amount').on('change',function(){
		amount = $('#amount').val();
		calcPrice();
	})
	
	function calcPrice(){
		optionLength = $('.form-check-input:checked').length;
		optionPrice = 200;
		let onePrice = Number( itemPrice ) + optionLength * optionPrice;
		let totalPrice =onePrice*amount;
		$('#totalPrice').text(Number(totalPrice).toLocaleString());
		$('#quantity').val(onePrice);
	}

	
//	$('#add-btn').on('click',function(){
//		$('#add-cart').submit();
//	})

})