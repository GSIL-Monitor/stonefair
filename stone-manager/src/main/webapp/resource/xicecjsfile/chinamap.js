$(document).ready(function () {
    var reserverData = [];
    var selloutData = [];
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/manager/user/getReserveExhibitorInfoAndBoothNum",
        success: function (data) {
            reserverData = data;
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "/manager/user/getSelloutExhibitorInfoAndBoothNum",
                success: function (data) {
                    selloutData = data;
                    Map(reserverData, selloutData);
                    bindReserveArea(reserverData);
                    bindSelloutArea(data);
                },
                error: function () {
                    alert('错误');
                }
            });
        },
        error: function () {
            alert('错误');
        }
    });
});

/*
 * 配置Raphael生成svg的属性
 */
function Map(reserverData, selloutData) {
    //$("#map").html("");
    Raphael.getColor.reset();
    var R = Raphael("map", 600, 600); //大小与矢量图形文件图形对应

    var current = null;
    var textAttr = {
        "fill": "#000",
        "font-size": "14px",
        "cursor": "pointer"
    };

    //调用绘制地图方法
    paintMap(R);

    var ToolTip = $('#ToolTip');
    ToolTip.html('地图成功绘制！').delay(1500).fadeOut('slow');
    $('body').append("<div id='tiplayer' style='display:none'></div>");
    var tiplayer = $('#tiplayer');

    for (var i=0, reserverInfoAndBooth; reserverInfoAndBooth = reserverData[i++];) {
        for (var state in china) {
            var temp = china[state]['name'];
            if (reserverInfoAndBooth.boothNum == temp) {
                china[state]['tag'] = reserverInfoAndBooth.tagName;
            }
        }
    }

    for (var k=0, selloutInfoAndBooth; selloutInfoAndBooth = selloutData[k++];) {
        for (var state in china) {
            var temp = china[state]['name'];
            if (selloutInfoAndBooth.boothNum == temp) {
                china[state]['company'] = selloutInfoAndBooth.company;
            }
        }
    }

    for (var state in china) {
        china[state]['path'].color = Raphael.getColor(0.9);
        china[state]['path'].transform("t30,0");

        (function (st, state) {
            if(china[state]['name'] != ""){
                //***获取当前图形的中心坐标
                var xx = st.getBBox().x + (st.getBBox().width / 2);
                var yy = st.getBBox().y + (st.getBBox().height / 2);
                //***写入展位名称,并加点击事件,部分区域太小，增加对文字的点击事件
                china[state]['text'] = R.text(xx, yy, china[state]['name']).attr(textAttr).click(function () {
                    clickMap();
                }).hover(function () {
                    var $sl = $("#topList").find("[title='" + china[state]['name'] + "']:not([select])");
                    $sl.css("font-size", "100px");
                }, function () {
                    var $sl = $("#topList").find("[title='" + china[state]['name'] + "']:not([select])");
                    $sl.css("font-size", "");
                });

                //图形的点击事件
                $(st[0]).click(function (e) {
                    clickMap();
                });
                //鼠标样式
                $(st[0]).css('cursor', 'pointer');
                //移入事件,显示信息
                $(st[0]).hover(function (e) {
                    if (e.type == 'mouseenter') {
                        if (tiplayer.is(':animated')) tiplayer.stop();
                        if(china[state]['company'] != "") {
                            tiplayer.text("展位号：" + china[state]['name'] + "，公司名：" + china[state]['company']).css({
                                'opacity': '0.75',
                                'top': (e.pageY + 10) + 'px',
                                'left': (e.pageX + 10) + 'px'
                            }).fadeIn('normal');
                        }else if(china[state]['tag'] != "") {
                            tiplayer.text("展位号：" + china[state]['name'] + "，标签：" + china[state]['tag']).css({
                                'opacity': '0.75',
                                'top': (e.pageY + 10) + 'px',
                                'left': (e.pageX + 10) + 'px'
                            }).fadeIn('normal');
                        }
                    } else {
                        if (tiplayer.is(':animated')) tiplayer.stop();
                        tiplayer.hide();
                    }
                });

                function clickMap() {
                    if (current == state)
                        return;
                    if((china[state]['company'] != "") && (china[state]['tag'] == "")) {
                        tiplayer.text("展位号：" + china[state]['name'] + "，公司名：" + china[state]['company']);
                    }else if((china[state]['tag'] != "") && (china[state]['company'] == "")) {
                        tiplayer.text("展位号：" + china[state]['name'] + "，标签：" + china[state]['tag']);
                    }
                    //重置上次点击的图形
                    current && china[current]['path'].animate({ transform: "t30,0", stroke: "#ddd" }, 2000, "elastic");
                    current = state;    //将当前值赋给变量
                    //对本次点击
                    china[state]['path'].animate({ transform: "t30,0 s1.03 1.03", stroke: "#000" }, 1200, "elastic");
                    st.toFront();   //向上
                    R.safari();
                    china[current]['text'].toFront();   //文字向上
                    if (china[current] === undefined) return;
                    $("#topList").find("[title='" + china[current]['name'] + "']").click();
                }
            }
        })
        (china[state]['path'], state);
    }
}

//绑定预定的展商
function bindReserveArea(reserverData) {
    var args = $("#args *").serialize();
    var html = "";
    for (var i = 0, infoAndBooth; infoAndBooth = reserverData[i++];) {
        var colorset;
        if(infoAndBooth.tagName != ""){
            colorset = "blue";
        }else{
            colorset = "gray";
        }
        for (var state in china) {
            if(state == infoAndBooth.boothNum){
                var anim = Raphael.animation({
                    fill: china[infoAndBooth.boothNum]['path'].color = colorset,
                    stroke: "#eee"
                }, 5);
                china[infoAndBooth.boothNum]['path'].stop().animate(anim.delay(i * 10));
            }
        }
    }
}

//绑定已经激活的展商
function bindSelloutArea(infoData) {
    var args = $("#args *").serialize();
    var html = "";
    for (var i = 0, infoAndBooth; infoAndBooth = infoData[i++];) {
        var colorset;
        if(infoAndBooth.company != ""){
            colorset = "green";
        }else{
            colorset = "gray";
        }
        for (var state in china) {
            if(state == infoAndBooth.boothNum){
                var anim = Raphael.animation({
                    fill: china[infoAndBooth.boothNum]['path'].color = colorset,
                    stroke: "#eee"
                }, 5);
                china[infoAndBooth.boothNum]['path'].stop().animate(anim.delay(i * 10));
            }
        }
    }
}

function indexOf(arr, str) {
    // 如果可以的话，调用原生方法
    if (arr && arr.indexOf) {
        return arr.indexOf(str);
    }

    var len = arr.length;
    for (var i = 0; i < len; i++) {
        // 定位该元素位置
        if (arr[i] == str) {
            return i;
        }
    }
    // 数组中不存在该元素
    return -1;
}