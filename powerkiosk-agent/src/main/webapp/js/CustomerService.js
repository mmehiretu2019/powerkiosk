$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/customerService/1"
    }).then(function(data) {
       alert("Next customer is " + data.id);
    });
});