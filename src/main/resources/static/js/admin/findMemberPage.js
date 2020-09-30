$("#searchBtn").click(function () {
    if($('#searchMember').val() == ''){

    }else{

    }
    $('#userInfo').empty();
    $('.pagination').empty();

    var data ={"user" :$('#searchMember').val(),
                "searchOption" :$('#findUser option:selected').val()};

    $.ajax({
        url:'/admin/findMember/1',
        type: 'POST',
        data: data,
        dataType: 'json'
    }).done(function (data) {
/*
        alert(JSON.stringify(data.list));
*/
            $.each(data.list, function (index, items) {
                var useremail = items.email;
                var html ='';
                html +='<tr>';
                html +='<td>'+items.email+'</td>';
                html +='<td>'+items.name+'</td>';
                html +='<td>'+items.address+'</td>';
                html +='<td>'+items.role+'</td>';
                html +='<td>'+items.enable+'</td>';
                if(!items.enable){
                    html +='<td><input type="button"  class="cancleBtn btn btn-primary" value="해지"></td>';
                }else{
                    html+='<td></td>';
                }
                html +='</tr>';
                $('#userInfo').append(html);


        });
            var paginationCode='';
/*
            paginationCode += '<li class="page-item"><a class="page-link">Previous</a></li>';
*/
            for(var i=1; i<=data.pageNum; i++){
                paginationCode += '<li class="page-item"><a class="page-link" onclick="pageClick('+i+')">'+i;
                paginationCode += '</a></li>'
            }
/*
            paginationCode +='<li class="page-item"><a class="page-link">Next</a></li>';
*/
            $('.pagination').append(paginationCode);

    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
});

$("#userInfo").on("click",".cancleBtn",function () {
    $(this).parents("tr").children().first().text();
/*
    alert($(this).parents("tr").children().first().text());
*/
    var $selectedBtn = $(this);

    $.ajax({
        method: 'POST',
        url: '/admin/deblock',
        data: "user="+ $(this).parents("tr").children().first().text()
    }).done(function () {

        $selectedBtn.hide();

    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
});

function pageClick(i) {
    $('#userInfo').empty();
    $('.pagination').empty();

    var data ={"user" :$('#searchMember').val(),
        "searchOption" :$('#findUser option:selected').val()};

    $.ajax({
        url:'/admin/findMember/'+i,
        type: 'POST',
        data: data,
        dataType: 'json'
    }).done(function (data) {
        /*
                alert(JSON.stringify(data.list));
        */
        $.each(data.list, function (index, items) {
            var useremail = items.email;
            var html ='';
            html +='<tr>';
            html +='<td class="email">'+items.email+'</td>';
            html +='<td>'+items.name+'</td>';
            html +='<td>'+items.address+'</td>';
            html +='<td>'+items.role+'</td>';
            html +='<td>'+items.enable+'</td>';
            if(!items.enable){
                html +='<td><input type="button"  class="cancleBtn btn btn-primary" value="해지"></td>';
            }else{
                html+='<td></td>';
            }
            html +='</tr>';
            $('#userInfo').append(html);


        });
        var paginationCode='';
/*
        paginationCode += '<li class="page-item"><a class="page-link">Previous</a></li>';
*/
        for(var i=1; i<=data.pageNum; i++){
            paginationCode += '<li class="page-item"><a class="page-link" onclick="pageClick('+i+')">'+i;
            paginationCode += '</a></li>'
        }
/*
        paginationCode +='<li class="page-item"><a class="page-link">Next</a></li>';
*/
        $('.pagination').append(paginationCode);

    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}