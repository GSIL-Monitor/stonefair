<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <style>
        html, body { margin:0; padding:0; overflow:hidden }
        svg { position:fixed; top:0; left:0; height:100%; width:100%;
            background: -webkit-radial-gradient(#ffffff, #ffffff, #ffffff); /* Safari 5.1 to 6.0 */
            background: -o-radial-gradient(#ffffff, #ffffff, #ffffff); /* For Opera 11.6 to 12.0 */
            background: -moz-radial-gradient(#ffffff, #ffffff, #ffffff); /* For Firefox 3.6 to 15 */
            background: radial-gradient(#ffffff, #ffffff, #ffffff); /* Standard syntax */
        }
        .button {
            position: fixed;
            left: 10px;
            top: 10px;
            z-index: 100;
        }
    </style>

    <script src="/manager/resource/xicecjsfile/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="/manager/resource/easyui/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/manager/resource/easyui/themes/metro-blue/easyui.css">
    <link rel="stylesheet" type="text/css" href="/manager/resource/easyui/themes/icon.css">
    <script src="/manager/resource/xicecjsfile/snap.svg.js"></script>
    <script src="/manager/resource/xicecjsfile/snap.svg.zpd.js"></script>
</head>
<body>
<div class="button">
    <button id="up">向上</button><button id="down">向下</button><button id="left">向左</button><button id="right">向右</button>
    <button id="zoom2x">放大成两倍</button><button id="zoom1x">放大成1倍</button>
    <button id="location">移到顶部</button><button id="rotateL">逆时针旋转15度</button><button id="rotateR">顺时针旋转15度</button>
    <button id="destroypapaer">锁上地图</button><button id="reapply">激活地图</button>
</div>

<!-- 展位预定提示框 -->
<div id="reserveBoothInfoDlg" data-options="iconCls:'icon-edit',modal:true">
</div>

<!-- 展位取消提示框 -->
<div id="cancelBoothInfoDlg" data-options="iconCls:'icon-edit',modal:true">
</div>

<script type="text/javascript">
    var reserverData = [];
    var selloutData = [];
    var currentBooth;
    var target;
    var becomeBlack;
    var reverseBlack;
    var paper;
    var applyZpd;
    var tooltip;
    var showLabel;
    var hideLabel;

    $(document).ready(function () {
        //展位预定提示框
        $('#reserveBoothInfoDlg').dialog({
            title: '展位预定提示框',
            width: 300,
            height: 80,
            closed: true,
            cache: false,
            modal: true,
            buttons: [
                {
                    text: '确认预定',
                    iconCls: 'icon-ok',
                    handler: function () {
                        $("#reserveBoothInfoDlg").dialog("close");
                        $.ajax({
                            url:"/manager/user/addReserveExhibitorInfoAndBoothNum",
                            type:"POST",
                            data:{"boothNum":currentBooth},
                            dataType:"json",
                            async: false, //同步执行
                            success : function(result) {
                                if(result){
                                    target.setAttributeNS(null, "fill", "#0000FF");
                                    //重新加载相关数据
                                    $.ajax({
                                        type: "POST",
                                        dataType: "json",
                                        url: "/manager/user/getReserveExhibitorInfoAndBoothNum",
                                        success: function (reserverDataValue) {
                                            reserverData = reserverDataValue;
                                            target.setAttributeNS(null, "tag", result.tagName);
                                            document.getElementById(currentBooth).onmouseover = becomeBlack;
                                            document.getElementById(currentBooth).onmouseout = reverseBlack;
                                        }
                                    });

                                    $.messager.show({
                                        title: '成功',
                                        msg: '增加展位预定成功',
                                        timeout: 3000,
                                        showType: 'slide'
                                    });
                                }
                            }
                        });
                    }
                },
                {
                    text: '取消',
                    handler: function () {
                        $("#reserveBoothInfoDlg").dialog("close");
                    }
                }
            ]
        });

        //展位取消提示框
        $('#cancelBoothInfoDlg').dialog({
            title: '展位取消提示框',
            width: 300,
            height: 80,
            closed: true,
            cache: false,
            modal: true,
            buttons: [
                {
                    text: '确认取消',
                    iconCls: 'icon-ok',
                    handler: function () {
                        $("#cancelBoothInfoDlg").dialog("close");
                        $.ajax({
                            url:"/manager/user/cancelReserveExhibitorInfoAndBoothNum",
                            type:"POST",
                            data:{"boothNum":currentBooth},
                            dataType:"json",
                            async: false, //同步执行
                            success : function(result) {
                                if(result.resultCode == 0){
                                    target.setAttributeNS(null, "fill", "#00FFFF");
                                    //重新加载相关数据
                                    $.ajax({
                                        type: "POST",
                                        dataType: "json",
                                        url: "/manager/user/getReserveExhibitorInfoAndBoothNum",
                                        success: function (reserverDataValue) {
                                            reserverData = reserverDataValue;
                                            target.setAttributeNS(null, "tag", "");
                                        }
                                    });

                                    $.messager.show({
                                        title: '成功',
                                        msg: '取消展位预定成功',
                                        timeout: 3000,
                                        showType: 'slide'
                                    });
                                }
                            }
                        });
                    }
                },
                {
                    text: '取消',
                    handler: function () {
                        $("#cancelBoothInfoDlg").dialog("close");
                    }
                }
            ]
        });

        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/manager/user/getReserveExhibitorInfoAndBoothNum",
            success: function (reserverDataValue) {
                reserverData = reserverDataValue;
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "/manager/user/getSelloutExhibitorInfoAndBoothNum",
                    success: function (selloutDataValue) {
                        selloutData = selloutDataValue;

                        paper = Snap();
                        applyZpd = function() {
                            paper.zpd();
                        };

                        becomeBlack = function (e) {
                            this.style.stroke = '#000000';
                            this.style.cursor = 'pointer';
                            var text1 = this.getAttribute('title');
                            var company = this.getAttribute('company');
                            var tagName = this.getAttribute('tag');
                            if(company != ""){
                                showLabel(e, "展位号：" + text1 + "，公司名：" + company);
                            }else if(tagName != ""){
                                showLabel(e, "展位号：" + text1 + "，标签："+ tagName);
                            }
                        };

                        reverseBlack = function (e) {
                            this.style.stroke = '#000000';
                            hideLabel(e);
                        }

                        showLabel = function (evt, mouseovertext) {
                            tooltip.setAttributeNS(null,"x",evt.clientX+8);
                            tooltip.setAttributeNS(null,"y",evt.clientY+16);
                            tooltip.setAttributeNS(null,"visibility","visible");
                            tooltip.textContent = mouseovertext;
                        };

                        hideLabel = function (evt){
                            tooltip.setAttributeNS(null,"visibility","hidden");
                        };

                        Snap.load('/manager/resource/xicecjsfile/xicecmap.svg', function (f) {
                            paper.append(f);
                            applyZpd();
                            /*paper.zoomTo(1.5, 100, null, function () {
                                paper.panTo('+200', '+200');
                            });*/

                            loadReserverData(reserverData);
                            loadSelloutData(selloutData);

                            var label = paper.text({
                                fill: 'black'
                            });
                            label.node.id = 'label';
                            tooltip = document.getElementById('label');
                        });

                        var intervalF;
                        var clearIntervalF = function () {
                            clearInterval(intervalF);
                        };
                        document.getElementById('location').onmousedown = function () {
                            paper.panTo(0, 0);
                        };
                        document.getElementById('left').onmousedown = function () {
                            paper.panTo('-10');
                            intervalF = setInterval(function () {
                                paper.panTo('-10');
                            }, 100);
                        };
                        document.getElementById('left').onmouseup = clearIntervalF;
                        document.getElementById('left').onmouseleave = clearIntervalF;
                        document.getElementById('right').onmousedown = function () {
                            paper.panTo('+10');
                            intervalF = setInterval(function () {
                                paper.panTo('+10');
                            }, 100);
                        };
                        document.getElementById('right').onmouseup = clearIntervalF;
                        document.getElementById('right').onmouseleave = clearIntervalF;
                        document.getElementById('up').onmousedown = function () {
                            paper.panTo('+0', '-10');
                            intervalF = setInterval(function () {
                                paper.panTo('+0', '-10');
                            }, 100);
                        };
                        document.getElementById('up').onmouseup = clearIntervalF;
                        document.getElementById('up').onmouseleave = clearIntervalF;
                        document.getElementById('down').onmousedown = function () {
                            paper.panTo('+0', '+10');
                            intervalF = setInterval(function () {
                                paper.panTo('+0', '+10');
                            }, 100);
                        };
                        document.getElementById('down').onmouseup = clearIntervalF;
                        document.getElementById('down').onmouseleave = clearIntervalF;
                        document.getElementById('rotateL').onmousedown = function () {
                            paper.rotate(-15);
                            intervalF = setInterval(function () {
                                paper.rotate(-15);
                            }, 100);
                        };
                        document.getElementById('rotateL').onmouseup = clearIntervalF;
                        document.getElementById('rotateL').onmouseleave = clearIntervalF;
                        document.getElementById('rotateR').onmousedown = function () {
                            paper.rotate(15);
                            intervalF = setInterval(function () {
                                paper.rotate(15);
                            }, 100);
                        };
                        document.getElementById('rotateR').onmouseup = clearIntervalF;
                        document.getElementById('rotateR').onmouseleave = clearIntervalF;
                        document.getElementById('zoom2x').onmousedown = function () {
                            paper.zoomTo(2, 400);
                        };
                        document.getElementById('zoom1x').onmousedown = function () {
                            paper.zoomTo(1, 400);
                        };
                        /*document.getElementById('save').onmousedown = function () {
                            paper.zpd('save', function (err, data) {
                                var output = JSON.stringify(data);
                                alert('Save Data:' + output);
                            });
                        };
                        document.getElementById('threshold').onmousedown = function () {
                            paper.zoomTo(1, 400);
                            paper.zpd({ drag: true, zoomThreshold: [0.5, 3] });
                        };*/
                        document.onkeydown = function (e) {
                            switch(e.keyCode) {
                                case 37: // left
                                    paper.panTo('-10');
                                    break;
                                case 38: // up
                                    paper.panTo('+0', '-10');
                                    break;
                                case 39: // right
                                    paper.panTo('+10');
                                    break;
                                case 40: // down
                                    paper.panTo('+0', '+10');
                                    break;
                            }
                        };

                        document.getElementById('destroypapaer').onmousedown = function () {
                            paper.zpd('destroy');
                        };

                        document.getElementById('reapply').onmousedown = function () {
                            applyZpd();
                        };
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

    function changeColor(evt) {
        target = evt.target;
        currentBooth = target.id;
        var flag = false;
        for (var i=0, selloutInfoAndBooth; selloutInfoAndBooth = selloutData[i++];) {
            if(selloutInfoAndBooth.boothNum == currentBooth){
                alert("已经卖出去的展位，不能再修改其属性。");
                return;
            }
        }
        for (var i=0, reserverInfoAndBooth; reserverInfoAndBooth = reserverData[i++];) {
            if(reserverInfoAndBooth.boothNum == currentBooth){
                flag = true;
                $("#cancelBoothInfoDlg").dialog("open");
                break;
            }
        }
        if(!flag){
            $("#reserveBoothInfoDlg").dialog("open");
        }
    }

    function loadReserverData(reserverData) {
        for (var i=0, reserverInfoAndBooth; reserverInfoAndBooth = reserverData[i++];) {
            if(document.getElementById(reserverInfoAndBooth.boothNum) != null){
                document.getElementById(reserverInfoAndBooth.boothNum).setAttribute('fill', "#0000FF");
                document.getElementById(reserverInfoAndBooth.boothNum).setAttribute('tag', reserverInfoAndBooth.tagName);
                document.getElementById(reserverInfoAndBooth.boothNum).setAttribute('name', "boothArea");
                document.getElementById(reserverInfoAndBooth.boothNum).onmouseover = becomeBlack;
                document.getElementById(reserverInfoAndBooth.boothNum).onmouseout = reverseBlack;
            }
        }
    }

    function loadSelloutData(selloutData) {
        for (var i=0, selloutInfoAndBooth; selloutInfoAndBooth = selloutData[i++];) {
            if(document.getElementById(selloutInfoAndBooth.boothNum) != null){
                document.getElementById(selloutInfoAndBooth.boothNum).setAttribute('fill', "#00FF00");
                document.getElementById(selloutInfoAndBooth.boothNum).setAttribute('company', selloutInfoAndBooth.company);
                document.getElementById(selloutInfoAndBooth.boothNum).setAttribute('name', "boothArea");
                document.getElementById(selloutInfoAndBooth.boothNum).onmouseover = becomeBlack;
                document.getElementById(selloutInfoAndBooth.boothNum).onmouseout = reverseBlack;
            }
        }
    }
</script>
</body>
</html>
