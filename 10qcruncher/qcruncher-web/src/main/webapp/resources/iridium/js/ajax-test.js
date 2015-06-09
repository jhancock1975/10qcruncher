/**
 * 
 */
function crunchifyAjax() {
	$.ajax({
		url : 'ajaxtest.html',
		success : function(data) {
			$('#result').html(data);
		}
	});
}

var intervalId = 0;
intervalId = setInterval(crunchifyAjax, 3000);