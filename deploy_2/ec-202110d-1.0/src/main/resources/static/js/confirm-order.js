function methodCredit() {
	document.getElementById("creditInfo").style.display = "block";
};

function methodCash() {
	document.getElementById("creditInfo").style.display = "none";
};


$(function() {

	let visa = new RegExp(/^4[0-9]{12}(?:[0-9]{3})?$/);
	let mastercard = new RegExp(/^5[1-5][0-9]{14}$/);
	let americanexpress = new RegExp(/^3[47][0-9]{13}$/);
	let Diners = new RegExp(/^3(?:0[0-5]|[68][0-9])[0-9]{11}$/);
	let discover = new RegExp(/^6(?:011|5[0-9]{2})[0-9]{12}$/);
	let jcb = new RegExp(/^(?:2131|1800|35\d{3})\d{11}$/);


	$('#credit').on('click', function() {
		$('#creditInfo').show();
	})

	$('#cash').on('click', function() {
		$('#creditInfo').hide();
	})



	$('#submit-card').on('click', function() {

		let name = $('#name-field').val();
		let card = $('#card-field').val();

		if (visa.test(card) || mastercard.test(card || americanexpress.test(card) || Diners.test(card) || discover.test(card) || jcb.test(card))) {
			$('#card-validate').hide();
			$('#card-field').prop('disabled', true);
		} else {
			$('#card-validate').show();
			return;
		}
		if (name) {
			$('#name-field').prop('disabled', true);
			$('#name-validate').hide();
		} else {
			$('#name-validate').show();
			return;
		}

		$('#check-mark').show();
	});
})

const today = new Date();
$(function() {
	$("#dateTime").datetimepicker({
		dateFormat: "yy-mm-dd",
		numberOfMonths: 3,
		minDate: today,
		minTime: "09:00",
		maxTime: "19:00",
	});
});



