var stompClient = null;


function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        //Subscribe to topic for receiving serving info
        stompClient.subscribe('/topic/servingInfo', function (message) {

            var messageObj = JSON.parse(message.body);
            showServingInfo(messageObj.body);
        });

         //Subscribe to topic for general serving info (serving summary)
        stompClient.subscribe('/topic/servingSummary', function (message) {

            var messageObj = JSON.parse(message.body);
            showServingSummary(messageObj.body);
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

function getNextCustomer(){
    stompClient.send("/app/info", {}, JSON.stringify({'serverId': '1'}));
}

function getNextNumberInLine(){
    stompClient.send("/app/summary", {}, {});
}

function showServingInfo(data) {

    $("#currentServingTable tr").remove();
    $("#currentServingTable").append(
    '<tr>'+
           '<th/>'+
                '</tr>'+
                    '<tr>'+
                        '<td>Serving</td>'+
                    '</tr>'+
                '<tr>'+
           '<td>Average Waiting Time: 30 min</td>'+
    '</tr>'
    );
    $.each( data.customerServers, function( key, value ) {

        $('#currentServingTable').find('th').eq(value.id - 1).after('<th>Window ' + value.id + '</th>');
        $('#currentServingTable').find('tr').eq(1).find('td').eq(value.id - 1).after('<td>' + value.currentCustomer.lineNumber + '</td>');
        $('#currentServingTable').find('tr').eq(2).find('td').eq(value.id -1).after('<td>20 min</td>');

        });
}

function showServingSummary(data){

    //update second row. first col = 'total', second col = 'served', third col = 'waiting'
    var total = data.servedCount + data.waitingCount;
    $('#servingSummaryTable').find('tr').eq(1).find('td').eq(0).html(total);
    $('#servingSummaryTable').find('tr').eq(1).find('td').eq(1).html(data.servedCount);
    $('#servingSummaryTable').find('tr').eq(1).find('td').eq(2).html(data.waitingCount);
}

$(document).ready(function() {
    connect();
});