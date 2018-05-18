	var startDate = new Date();
	var endDate = new Date();
	var minYear;
	var minMonth;
	var minDateOfMonth;
	var maxYear;
	var maxMonth;
	var maxDateOfMonth;
	var minDate;
	var maxDate;
	const minTime = "T17:00";
	const maxTime = "T04:00";
	function dateEntered() {
		document.getElementById('datevalidity').removeAttribute("hidden");
		// get start date from the user
		this.startDate = new Date($('#date').val());
		this.endDate = new Date($('#date').val());
		let $checkinTimeEl = $('#checkin-time');
		let checkoutTimeEl = $('#checkout-time');
		let buttonEl = $('#button');
		if (startDate instanceof Date && isFinite(startDate)) {

			// set the end date based off the begin date
			endDate.setDate(endDate.getDate() + 1);

			minMonth = startDate.getUTCMonth() > 8 ? (startDate.getUTCMonth() + 1) : "0" + (startDate.getUTCMonth() + 1);
			minDateOfMonth = startDate.getUTCDate() >= 10 ? (startDate.getUTCDate()) : "0" + (startDate.getUTCDate());

			maxMonth = endDate.getUTCMonth() > 8 ? (endDate.getUTCMonth() + 1) : "0" + (endDate.getUTCMonth() + 1);
			maxDateOfMonth = (endDate.getUTCDate()) >= 10 ? (endDate.getUTCDate()) : "0" + (endDate.getUTCDate());

			minDate = startDate.getUTCFullYear() + "-" + minMonth + "-" + minDateOfMonth + minTime;
			maxDate = startDate.getUTCFullYear() + "-" + maxMonth + "-" + maxDateOfMonth + maxTime;

			$('#checkin-time').attr("min", minDate);
			$('#checkin-time').attr("max", maxDate)


			$('#checkout-time').attr("min", minDate);
			$('#checkout-time').attr("max", maxDate)

			$('#checkin-time').val(minDate);
			$('#checkout-time').val(maxDate);


			$('#checkin-time').removeAttr("disabled");
			$('#checkout-time').removeAttr("disabled");
			$('#button').removeAttr("disabled");
		} else {
			$('#checkin-time').attr("disabled", "disabled");
			$('#checkout-time').attr("disabled", "disabled");
			$('#button').attr("disabled", "disabled");
			$('#checkin-time').val("yyyy-MM-ddThh:mm");
			$('#checkout-time').val("yyyy-MM-ddThh:mm");
		}
	}

	function validate() {
		$('#totalSalary').hide();
		$('#totalSalary').text("");
		let checkinTimeEl = new Date($('#checkin-time').val());
		let checkoutTimeEl = new Date($('#checkout-time').val());
		let minDt = new Date(minDate);
		let maxDt = new Date(maxDate);
		var validate = true;
		var notifications = "";

		if (!(checkinTimeEl instanceof Date && isFinite(checkinTimeEl))) {
			notifications += "<li> Please make sure that your start time is filled corretly</li>";
			validate = false;
		}
		if (!(checkoutTimeEl instanceof Date && isFinite(checkoutTimeEl))) {
			notifications += "<li> Please make sure that your exit time is filled corretly</li>";
			validate = false;
		}
		
		if((checkinTimeEl instanceof Date && isFinite(checkinTimeEl))&&
				(checkoutTimeEl instanceof Date && isFinite(checkoutTimeEl))){
		if ((checkinTimeEl.getTime() < minDt.getTime()) ) {
			notifications += "<li> Check In time can't be earlier than " + minDate + "</li>";
			validate = false;
		}
		
		if ( (checkinTimeEl.getTime() > maxDt.getTime()) ) {
			notifications += "<li> Check In time can't be later than " + maxDate + "</li>";
			validate = false;
		}
		
		if (checkoutTimeEl.getTime() > maxDt.getTime()) {
			notifications += "<li> Check out time can't be later than " + maxDate + "</li>";
			validate = false;
		}
		
		if (checkoutTimeEl.getTime() < minDt.getTime()) {
			notifications += "<li> Check out time can't be earlier than " + minDate + "</li>";
			validate = false;
		}
		if (!(checkinTimeEl.getTime() < checkoutTimeEl.getTime())|| checkinTimeEl == checkoutTimeEl) {
			notifications += "<li> Check in time can't be later or equals to check out time</li>";
			validate = false;
		}
		}
		if (validate) {
			calculatePrice();
		} else {
			$('#ul').empty();
			$('#ul').append(notifications);
			var favDialog = document.getElementById('favDialog');
			favDialog.showModal();
			return validate;
		}
	}

	function closeDialog() {
		var favDialog = document.getElementById('favDialog');
		favDialog.close();
	}
function calculatePrice() {
	let startTimeEl = new Date ($('#checkin-time').val()).getTime();
	let exitTimeEl = new Date($('#checkout-time').val()).getTime();
	let minDt = new Date(minDate).getTime();
	let maxDt = new Date(maxDate).getTime();
	var times = {
		"checkintime":startTimeEl,
		"checkouttime":exitTimeEl,
		"mintime":minDt,
		"maxtime":maxDt
	}
	$('#totalSalary').show();
	$('#totalSalary').addClass('spinner');
			$.ajax({
		type : 'GET',
		contentType : 'application/json',
		url : "http://localhost:8080/pillarkatababysitter/rest/calculate/" + encodeURIComponent(JSON.stringify(times)),
		dataType : "json",
		success : function(data, textStatus, jqXHR) {

			for(var i=0;i<10000;i++){
				console.log(i);
			}
			$('#totalSalary').removeClass('spinner');
			$('#totalSalary').text("Your total Salary for the day is: $"+data.totalSalary)
			console.log(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$('#totalSalary').removeClass('spinner');
			alert('Oops, we couldn\'t process your work hours. Please try again');
			console.log(textStatus+" "+errorThrown);
		}
	});
}