
var  main = {
    init : function(){
        var _this = this;

/*        $('btn-success').on('click', function () {
            alert('register 클릭시')
            _this.save();
        });*/

        $('#btn-success').on('click', function () {
            alert('register 클릭시')
            _this.save();
        });


    },
    save : function () {
        var data={
            title : $('#title').val(),
            author : $('#author').val(),
            content : $('#content').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/post/shopper/register',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },


};

main.init();

