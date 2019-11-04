
function signUp(){

    var providerObj = new Object();
    providerObj.firstName = $('#first-name').val();
    providerObj.lastName = $('#last-name').val();
    providerObj.email = $('#email').val();
    providerObj.phone = $('#phone').val();
    providerObj.address1 = $('#address1').val();
    providerObj.address2 = $('#address2').val();
    providerObj.city = $('#city').val();
    providerObj.zipCode = $('#zip-code').val();
    providerObj.country = $('#country').val();
    providerObj.name = $('#provider-name').val();
    providerObj.logoFilePath = $('#logo-file').val();

    $.ajax({type: 'POST',
        url: 'http://localhost:8080/providers',
        data: JSON.stringify(providerObj),
        contentType: 'application/json',
        success: function (data, status){
            console.log("Data: " + data + ", Status: " + status);
        }});
}