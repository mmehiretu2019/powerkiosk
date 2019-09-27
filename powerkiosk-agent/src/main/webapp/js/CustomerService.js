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




var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});