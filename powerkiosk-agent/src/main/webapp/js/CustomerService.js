$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/customerService/currentServingInfo"
    }).then(function(data) {
    	$.each( data.customerServers, function( key, value ) {

    	$('#currentServingTable').find('th').eq(value.id - 1).after('<th>Window ' + value.id + '</th>');
    	$('#currentServingTable').find('tr').eq(1).find('td').eq(value.id - 1).after('<td>' + value.currentCustomer.lineNumber + '</td>');
    	$('#currentServingTable').find('tr').eq(2).find('td').eq(value.id -1).after('<td>20 min</td>');
    		
		});
    });
});

function getNextCustomer(serverId){
    $.ajax({
            url: "http://localhost:8080/customerService/" + serverId
        }).then(function(data) {

        });
}