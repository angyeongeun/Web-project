$("#mailSendBtn").click(function (){

    if($("#inputEmail").val() ==""){
        $("#emailDiv").text("이메일을 입력해주세요.").css("color","gray").css("font-size","10pt");
    }else{
        $.ajax({
            url:'/mail',
            method: 'POST',
            data: 'email='+ $("#inputEmail").val(),
            dataType: 'text',
            //contentType: "application/json; charset=utf-8",
            success: function (data) {
                $("#emailDiv").text(data).css("color","gray").css("font-size","10pt");
            },
            error: function (response) {
                alert(response);
            }
        });

    }

    });