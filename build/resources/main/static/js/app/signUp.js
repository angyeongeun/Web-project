
var markingErrorField = function (response) {
    const errorFields = response.responseJSON.errors;
    if(!errorFields){
        alert(response.response.message);
        return;
    }
    var $field, error;
    for(var i=0, length = errorFields.length; i<length;i++){
        error = errorFields[i];
        $field = $('#'+error['field']);

        if($field && $field.length > 0){
            $field.siblings('.error-message').remove();
            $field.after('<span class="error-message text-muted taxt-small text-danger">'+error.defaultMessage+'</span>');
        }
    }
};


$("#signUpBtn").click(function (){

        if($("#jb-checkbox").is(":checked")){
            $("#role").val("SHOPPER");
        }else{
            $("#role").val("USER");
        }
    var member = {
        name: $('#name').val(),
        password: $('#password').val(),
        email: $('#email').val(),
        address: $("#address").val(),
        role: $("#role").val()
    };

    $.ajax({
        url:$("#signUp").attr('action'),
        method: 'POST',
        data: JSON.stringify(member),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            window.location.href="/user/loginPage"
        },
        error: function (response) {
            markingErrorField(response);
        }
    });
});



