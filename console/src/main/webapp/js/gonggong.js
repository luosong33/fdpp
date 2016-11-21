$(function () {
    $('.zgs_span_right').click(function () {
        $(this).addClass('yangsi').css({
            background: 'url(img/mnav_right_bg.png)'
        }).siblings().removeClass('yangsi').css({
            background: 'url()'
        })
    })

})

/*$(function () {
    function flush(){
        $.ajax({
            url: 'query/json/findJrbfInfo',
            type: 'post',
            asnyc: false,
            data: {
                // params: "{\"minute\":\"" + 60 * (new Date().getHours() * 60 + new Date().getMinutes()) + "\"}",
                params:"{}",
                dicts: "{}"
            },
            dataType: 'json',
            success: function (data) {

                var dsignpremium = 0;
                var dsignqty = 0;
                var statdate = "";
                for (var i = 0; i < data.length; i++) {
                    // console.log(data[i]);
                    dsignpremium = data[i].dsignpremium;
                    dsignqty = data[i].dsignqty;
                    statdate = data[i].statdate;
                }

                setCurrent(dsignpremium);
                setCurrent1(dsignqty);
                $("#time").html(statdate);
                /!*clearInterval(TimeShou);*!/
            }
        })
    }
   flush();
    setInterval(flush,60000);
})*/

var feeTime = 0, currNum = 0, targetNum = 0, step = 1;

function setCurrent(num) {
    feeTime && clearInterval(feeTime);
    targetNum = num;
    computeOrder();
}

var feeTime1, currNum1 = 0, targetNum1 = 0, step1 = 1;
/* 今日签单最新获取保费值 */
function setCurrent1(num) {
    feeTime1 && clearInterval(feeTime1);
    targetNum1 = num;
    computeOrder1();
}

function format(num, suffix) {
    if (num != num) {
        return '--';
    }
    return ((Math.round(num * 100) / 100).toFixed(2) + '').replace(
            /\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')
        + (suffix || '');
}
function format1(num) {
    return (Math.round(num) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g,
        '$&,');
}

/* 今日签单保费变化动画 */
function computeOrder1() {
    feeTime1 = setInterval(function () {
        if (targetNum1 == currNum1) {
            clearInterval(feeTime1);
            feeTime1 = false;
        } else {
            step1 = Math.ceil((targetNum1 - currNum1) / 10);
            currNum1 += step1;
            $("#numberRun1").html(format1(currNum1));
        }
    }, 2);
}

Window.prototype.clearInterval = function(intervalId) {};

/* 今日保费变化动画 */
function computeOrder() {
    feeTime = setInterval(function () {
        if (targetNum == currNum) {
            clearInterval(feeTime);
            feeTime = false;
        } else {
            step = Math.ceil((targetNum - currNum) / 10);
            currNum += step;
            $("#numberRun").html(format1(currNum));
        }
    }, 2);
}

$(function () {
    $.ajax({
        type: 'get',
        url: 'json/baobiao.json',
        dataType: "json",
        success: function (data) {
            var bfnum = 0;
            var qdnum = 0;
            $.each(data, function (index, item) {
                $('.tab').append("<tr><td style='border-left: 1px #e3e4e8 solid'>" + item.name + "</td><td>" + item.bf + "</td><td>" + item.qd + "</td></tr>")

                bfnum += parseInt(item.bf);
                qdnum += parseInt(item.qd);

            })
            $('.tab').append(" <tr class='fast_tr'><td style='border-left: 1px #e3e4e8 solid'>总计</td><td class='num'></td><td class='num1'></td></tr>")
            $('.num').html(bfnum)
            $('.num1').html(qdnum);


        }


    })


})

$(function () {
    $('#header .her_ul li a ').click(function () {
        $(this).addClass('henggang').css({
            background: 'url(img/nav_bg.png)',
            color: '#ffffff'
        }).parent('li').siblings('li').children('a').removeClass('henggang').css({
            background: 'url(img)',
            color: '#333333'
        });
    })

    //鼠标划过改变楼层的高亮
    $("#floor_Nav ul li").not(".last").hover(function () {
        $(this).addClass("hover");
    }, function () {
        $(this).removeClass("hover")
    });

    //鼠标点击
    var mark = 1;

    //浏览器窗口口滚动事件，scroll 事件适用于所有可滚动的元素和 window 对象（浏览器窗口）。
    $('#center').scroll(function () {
        //alert(1);
        if (mark == 1) {
            var $t = $(this).scrollTop(); //获取距离顶端的滚动距离
            if ($t > 700) { //通过滚动条来判断
                $("#floor_Nav").fadeIn(); //淡入 导航慢慢显示出来
            } else {
                $("#floor_Nav").fadeOut(); //淡出 导航慢慢消失
            }
        }
    });
    //点击 Top按钮 跳转到浏览器顶部
    $("#floor_Nav ul .last").click(function () {
        $("#center").animate({
            scrollTop: 0
        }, 0, function () {
            mark = 1;
        });
    });
})

$(document).ready(function () {
    $('.tubiao span').click(function () {
        $(this).addClass('ys_span').css({
            background: '#00aade',
            color: '#fff',
            height: '30px',
            border: '1px solid #f1f3f4'

        }).siblings('span').removeClass().css({
            background: '#fff',
            color: '#333333',
            height: '28px',
            border: '1px solid #f1f3f4'
        });
    });
});


function siMenu(id) {
    if (id == 'li1') {
        $('#top_main').css({'display': 'block'});
        $('#top_main1').css({'display': 'none'});

    } else if (id == 'li2') {
        $('#top_main').css({'display': 'none'});
        $('#top_main1').css({'display': 'block'});

    } else if (id == 'li3') {
        $('#zgs_bottom').css({'display': 'block'});
        $('#zgs_bottom1').css({'display': 'none'});
    } else if (id == 'li4') {
        $('#zgs_bottom').css({'display': 'none'});
        $('#zgs_bottom1').css({'display': 'block'});
    } else if (id == 'li5') {
        $('#zgs_bottom_yue').css({'display': 'block'});
        $('#zgs_bottom1_yue').css({'display': 'none'});
    } else if (id == 'li6') {
        $('#zgs_bottom_yue').css({'display': 'none'});
        $('#zgs_bottom1_yue').css({'display': 'block'});
    }

}
function siMenu1(id) {
    if (id == 'li7') {
        $('#zgs_bottom_nian').css({'display': 'block'});
        $('#zgs_bottom1_nian').css({'display': 'none'});
    } else if (id == 'li8') {
        $('#zgs_bottom_nian').css({'display': 'none'});
        $('#zgs_bottom1_nian').css({'display': 'block'});
    }
}

function delect() {
    $('.tubiao>span').removeClass('ys_span').css({
        background: '#fff',
        color: '#333333',
        height: '28px',
        border: '1px solid #f1f3f4'
    })
}
function tab(id) {
    if (id == 'ryn1') {
        // $('.ryn').css({'display': 'block'});
        // $('.ryn_yue').css({'display': 'none'});
        // $('.ryn_nian').css({'display': 'none'});
        $('#zgs_bottom').css({'display': 'block'});
        $('#zgs_bottom1').css({'display': 'none'});
        $('#ryn1').click(function () {
            delect();
        })

    } else if (id == 'ryn2') {
        // $('.ryn').css({'display': 'none'});
        // $('.ryn_yue').css({'display': 'block'});
        // $('.ryn_nian').css({'display': 'none'});
        $('#zgs_bottom_yue').css({'display': 'block'});
        $('#zgs_bottom1_yue').css({'display': 'none'});
        $('#ryn2').click(function () {
            delect();
        })

    } else if (id == 'ryn3') {
        // $('.ryn').css({'display': 'none'});
        // $('.ryn_yue').css({'display': 'none'});
        // $('.ryn_nian').css({'display': 'block'});
        $('#zgs_bottom_nian').css({'display': 'block'});
        $('#zgs_bottom1_nian').css({'display': 'none'});
        $('#ryn3').click(function () {
            delect();
        })
    }

}
