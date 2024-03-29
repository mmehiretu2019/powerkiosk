//GLOBAL VARIABLES
var isServing = false;
var stompClient = null;
var AUTH_URL = "http://localhost:8080/oauth/token";
var AUTH_CLIENT_ID = "clientapp";
var AUTH_CLIENT_SECRET = "123456";
var WEB_SOCKET_BASE_URL = "http://localhost:8080/gs-guide-websocket";
var providerId = null;

var accessToken = null;

function connect() {
    var endpoint = WEB_SOCKET_BASE_URL + '?access_token=' + accessToken;
    var socket = new SockJS(endpoint);
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
    //connect();
});

function setUpView(){
    //depending on login status and other states, displays and disables the correct views
    $('#login-container').removeClass('.hidden');
    $('#admin-view-container').addClass('.hidden');
}

function logIn(){
    var userObj = new Object();
    userObj.username = $('#logInUsername').val();
    userObj.password = $('#logInPassword').val();
    userObj.client_id = AUTH_CLIENT_ID;
    userObj.client_secret = AUTH_CLIENT_SECRET;
    userObj.grant_type = 'password';


    $.ajax({
        type: 'POST',
        url: AUTH_URL,
        data: $.param(userObj),
        success: function(data, textStatus, jQxhr ){
            console.log("Data: "+ data.access_token + ", textStatus: " + textStatus + ", jQxhr: " + jQxhr.status);
            if(jQxhr.status == 200){
                accessToken = data.access_token;
                //connect to websocket
                providerId = $('#providerId').val();
                connect();
                showPage('#main');
            }
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log("Invlid credentials");
            $('#invalidCredentialText').removeClass('d-none');
        }
    });
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
        statusCode: {
            200: function (){
                console.log("Data: " + data + ", Status: " + status);
                showPage('#logIn');
            }}});
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

