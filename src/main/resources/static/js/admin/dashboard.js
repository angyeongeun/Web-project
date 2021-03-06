
$(document).ready(function () {

    //달별 주문량 가져오기
    $.ajax({
        method: 'POST',
        url: '/admin/chartData',
        dataType: 'json',
    }).done(function (data) {
        var monthsSales = new Array();
        $.each(data.list, function(index,items){
                monthsSales.push(items.salesMonth);

        });
        //line
        var ctxL = document.getElementById("lineChart").getContext('2d');
        var myLineChart = new Chart(ctxL, {
            type: 'line',
            data: {
                labels: ["1월","2월","3월","4월","5월","6월", "7월", "8월", "9월", "10월", "11월", "12월"],
                datasets: [{
                    label: "월별 주문건",
                    data: monthsSales,
                    backgroundColor: [
                        'rgba(105, 0, 132, .2)',
                    ],
                    borderColor: [
                        'rgba(200, 99, 132, .7)',
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true
            }
        });


    }).fail(function (error) {
        alert(JSON.stringify(error));
    });

    var shopper = $("#docutChart").data('shopper');
    data = {
        datasets: [{
            backgroundColor: ['#E880A6','#B5A4AA'],
            data: [ shopper, 100-shopper]
        }],
        // 라벨의 이름이 툴팁처럼 마우스가 근처에 오면 나타남
        labels: ['shopper','client']
    };
    var ctx2 = document.getElementById("docutChart");
    var myDoughnutChart = new Chart(ctx2,{
        type: 'doughnut',
        data: data,
        options:{}
    });




    //스토어별 판매량

        $.ajax({
            url: '/admin/storeRank',
            type: 'POST',
            dataType: 'json'
        }).done(function (data) {
            $.each(data.list, function (index, items) {
                var html ='';
                if(index ==0){
                    html+='<li><span style="font-size: 28pt; font-weight: bold;">'+items.store+'</span><span style="font-size: 11pt; color:gray;">&emsp;'+items.amount+'</span></li>'

                }else{
                    html+='<li><span style="font-size: 20pt; font-weight: bold;">'+items.store+'</span><span style="font-size: 11pt; color:gray;">&emsp;'+items.amount+'</span></li>'

                }

/*
                html+=(<span>'+items.store+'</span>&emsp;<span>'+items.amount+'</span><br>';
*/

                $('#storeRank').append(html);

            });
            $('#storeRank').children().first().css('font-size',28);


        });


});

