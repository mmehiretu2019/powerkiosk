//GLOBAL VARIABLES
var isServing = false;
var stompClient = null;
//use browser name as providerId for now. Later, it will be actual provider id
var providerId = navigator.productSub;

function connect() {
    var socket = new SockJS('http://localhost:8080/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);


        //Subscribe to topic for receiving serving info
        stompClient.subscribe('/topic/servingInfo/' + providerId, function (message) {

            var messageObj = JSON.parse(message.body);
            showServingInfo(messageObj.body);
        });

         //Subscribe to topic for general serving info (serving summary)
         stompClient.subscribe('/topic/servingSummary/' + providerId, function (message) {

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
    stompClient.send("/app/info/" + providerId, {}, JSON.stringify({'serverId': '1'}));
}

function getNextNumberInLine(){
    stompClient.send("/app/summary/" + providerId, {}, {});
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
    showPage(window.location.hash);
    showServingPage(isServing);
    connect();
});

function setUpView(){
    //depending on login status and other states, displays and disables the correct views
    $('#login-container').removeClass('.hidden');
    $('#admin-view-container').addClass('.hidden');
}

function logIn(){
    var userObj = new Object();
    userObj.username = $('#logInUsername');
    userObj.password = $('#logInPassword');

    $.ajax({type: 'POST',
        url: 'http://localhost:8080/session',
        data: JSON.stringify(userObj),
        contentType: 'application/json',
        success: function (data, status){
            console.log("Data: " + data + ", Status: " + status);
            showPage('#main');
        }});
}

function createAccount(){
    showPage('#sign-up-container');
}
function signUp(){
    var userObj = new Object();
    userObj.email = $('#signUpEmail');
    userObj.password = $('#signUpPassword');

    $.ajax({type: 'POST',
        url: 'http://localhost:8080/users',
        data: JSON.stringify(userObj),
        contentType: 'application/json',
        success: function (data, status){
            console.log("Data: " + data + ", Status: " + status);
            showPage('#logIn');
        }});
}

function startServing(){
    //send serving info
    var customerServer = new Object();
    customerServer.stationId = $('#servingStationId');
    stompClient.send("/customerService/" + providerId + "/serve", {}, JSON.stringify(customerServer));
    isServing = true;
    showServingPage(isServing);
}

function showPage(pageId){
    //check if pageId is valid. If not, show login page by default for now
    //late on, we might want to show 404
    pageId = !$(pageId) || pageId.length < 2 ? '#logIn' : pageId;
    $('.row').hide();
    $(pageId).removeClass('d-none');
    window.location.hash=pageId;
    $(pageId).show();
}

function showServingPage(serving){
    if(serving){
        $('#startServingModal').hide();
        $('#serving').show();
    }
}

//=====Register Event Listeners
$(function(){
    $('#createAccountBtn').click(function(e){
        e.preventDefault();
        showPage('#signUp');
    });

    $('#logInBtn').click(function(e){
        e.preventDefault();
        logIn();
    });

    $('#signUpBtn').click(function(e){
        e.preventDefault();
        signUp();
    });

    $('#startServingStartBtn').click(function(e){
        e.preventDefault();
        startServing();
    });

});

