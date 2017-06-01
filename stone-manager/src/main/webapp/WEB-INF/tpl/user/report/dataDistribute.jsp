<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tpl/user/managerrole/datareporthead.jsp" %>

<!DOCTYPE html>
<head>
    <title>金泓信展商管理后台</title>
    <style>
        body {
            margin: 0px;
            padding: 0px;
            width: 100%;
            height: 100%;
        }

        input {
            width: 200px;
            height: 20px;
        }
    </style>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div>
    <div id="mapRegin" style="height: 95%; width: 98%;border:1px solid #ccc;"></div>
    <div align="center">
        <button type="button" class="btn btn-sm btn-success" onclick="refresh(true)">刷 新</button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span class="text-primary">切换主题</span>
        <select id="theme-select" onchange="modifyTheme();">
            <option selected="true" value='default'>default</option>
            <option value='infographic'>infographic</option>
            <option value='macarons'>macarons</option>
            <option value='shine'>shine</option>
            <option value='dark'>dark</option>
            <option value='blue'>blue</option>
            <option value='green'>green</option>
            <option value='red'>red</option>
            <option value='gray'>gray</option>
            <option value="helianthus">helianthus</option>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span class="text-primary">区域</span>
        <select id="data-select" onchange="selectDataArea();">
            <option selected="true" value='0'>国内</option>
            <option value='1'>全球</option>
        </select>
    </div>
</div>
</body>
<script type="text/javascript">
    var mapChart;
    var mapData = [];
    var maxValue = 0;
    var curTheme = 'default';
    $(document).ready(function () {
        showInlandMapChartData();
    });

    function initMapChartInfo(mapValue, maxValue, themeInfo, flag) {
        var mapType = 'china';
        var legend = '';
        var titleText = '';
        if(flag == 0){
            titleText = '全国展商数据分布图';
            legend = '国内展商';
            mapType = 'china';
        }else{
            titleText = '全球展商数据分布图';
            legend = '全球展商';
            mapType = 'world';
        }
        require.config({
            paths: {
                echarts: '${base}/resource/echarts/echarts1',
                'echarts/chart/map': '${base}/resource/echarts/echarts-map'
            }
        });

        var def,infographic,macarons,shine,dark, blue,green,red,gray,helianthus;
        require(['${base}/resource/echarts/theme/default'], function(theme) {
            def = theme;
        });
        require(['${base}/resource/echarts/theme/infographic'], function(theme) {
            infographic = theme;
        });
        require(['${base}/resource/echarts/theme/macarons'], function(theme) {
            macarons = theme;
        });
        require(['${base}/resource/echarts/theme/shine'], function(theme) {
            shine = theme;
        });
        require(['${base}/resource/echarts/theme/dark'], function(theme) {
            dark = theme;
        });
        require(['${base}/resource/echarts/theme/blue'], function(theme) {
            blue = theme;
        });
        require(['${base}/resource/echarts/theme/green'], function(theme) {
            green = theme;
        });
        require(['${base}/resource/echarts/theme/red'], function(theme) {
            red = theme;
        });
        require(['${base}/resource/echarts/theme/gray'], function(theme) {
            gray = theme;
        });
        require(['${base}/resource/echarts/theme/helianthus'], function(theme) {
            helianthus = theme;
        });

        require(['echarts','echarts/chart/map'],DrawEChart);
        function DrawEChart(ec) {
            if(themeInfo.toLowerCase() == 'default'){
                mapChart = ec.init(document.getElementById('mapRegin'), def);
            } else if(themeInfo.toLowerCase() == 'infographic'){
                mapChart = ec.init(document.getElementById('mapRegin'), infographic);
            } else if(themeInfo.toLowerCase() == 'macarons'){
                mapChart = ec.init(document.getElementById('mapRegin'), macarons);
            } else if(themeInfo.toLowerCase() == 'shine'){
                mapChart = ec.init(document.getElementById('mapRegin'), shine);
            } else if(themeInfo.toLowerCase() == 'dark'){
                mapChart = ec.init(document.getElementById('mapRegin'), dark);
            } else if(themeInfo.toLowerCase() == 'blue'){
                mapChart = ec.init(document.getElementById('mapRegin'), blue);
            } else if(curTheme.toLowerCase() == 'green'){
                mapChart = ec.init(document.getElementById('mapRegin'), green);
            } else if(themeInfo.toLowerCase() == 'red'){
                mapChart = ec.init(document.getElementById('mapRegin'), red);
            } else if(themeInfo.toLowerCase() == 'gray'){
                mapChart = ec.init(document.getElementById('mapRegin'), gray);
            } else if(themeInfo.toLowerCase() == 'helianthus'){
                mapChart = ec.init(document.getElementById('mapRegin'), helianthus);
            } else {
                mapChart = ec.init(document.getElementById('mapRegin'), def);
            }
            mapChart.showLoading({text : "图表数据正在努力加载..."});
            //定义图表options
            var option = {
                title: {
                    text: titleText,
                    x: 'center'
                },
                // 提示框
                tooltip: {
                    trigger: 'item',           // 触发类型，默认数据触发，见下图，可选为：'item' ¦ 'axis'
                    showDelay: 20,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                    hideDelay: 100,            // 隐藏延迟，单位ms
                    transitionDuration : 0.4,  // 动画变换时间，单位s
                    backgroundColor: 'rgba(0,0,0,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
                    borderColor: '#333',       // 提示边框颜色
                    borderRadius: 4,           // 提示边框圆角，单位px，默认为4
                    borderWidth: 0,            // 提示边框线宽，单位px，默认为0（无边框）
                    padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
                                               // 接受数组分别设定上右下左边距，同css
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'line',         // 默认为直线，可选为：'line' | 'shadow'
                        lineStyle : {          // 直线指示器样式设置
                            color: '#48b',
                            width: 2,
                            type: 'solid'
                        },
                        shadowStyle : {                       // 阴影指示器样式设置
                            width: 'auto',                   // 阴影大小
                            color: 'rgba(150,150,150,0.3)'  // 阴影颜色
                        }
                    },
                    textStyle: {
                        color: '#fff'
                    }
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: [legend]
                },
                dataRange: {
                    x:'left',
                    y:'center',
                    min: 0,
                    max: maxValue,
                    text: ['高', '低'],
                    calculable: true,
                    textStyle: {
                        color: 'orange'
                    }
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    x: 'right',
                    y: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                roamController: {
                    show: true,
                    x: 'right',
                    mapTypeControl: {
                        'china': true
                    }
                },
                series: [
                    {
                        name: legend,
                        type: 'map',
                        mapType: mapType,
                        roam: true,
                        selectedMode: 'single',
                        itemStyle: {
                            normal: {label: {show: true}, color: '#ffd700'},
                            emphasis: {borderColor:'#B22222',borderWidth:2, label: {show: true}}
                        },
                        mapLocation : {
                            x : 'center',
                            y : 'center',
                            width : '100%'
                        },
                        data:mapValue
                    }
                ]
            };
            mapChart.setOption(option);
            mapChart.hideLoading();
        }
    }

    function showInlandMapChartData() {
        $.ajax({
            url: "${base}/user/queryExhibitorForReport",
            type: "post",
            async : false, //同步执行
            data: {"cids": "0"},
            dataType: "json",
            success: function (mapValue) {
                if (mapValue.resultCode == 0) {
                    maxValue = 0;
                    mapData = [];
                    var str = JSON.parse(mapValue.mapData);
                    for(var i = 0;i < str.length;i ++){
                        var nameValue = str[i].name;
                        var countValue = parseInt(str[i].value);
                        if(countValue > maxValue) {
                            maxValue = countValue;
                        }
                        var oneData = {name:nameValue,value:countValue};
                        mapData.push(oneData);
                    }
                    maxValue = parseInt(maxValue);
                    initMapChartInfo(mapData, parseInt(maxValue), 'default', 0);
                } else {
                    $.messager.alert('错误', '数据报表获取失败');
                }
            }
        });
    }

    function showWordMapChartData() {
        $.ajax({
            url: "${base}/user/queryExhibitorForReport",
            type: "post",
            async : false, //同步执行
            data: {"cids": "1"},
            dataType: "json",
            success: function (mapValue) {
                if (mapValue.resultCode == 0) {
                    maxValue = 0;
                    mapData = [];
                    var str = JSON.parse(mapValue.mapData);
                    for(var i = 0;i < str.length;i ++){
                        var nameValue = str[i].name;
                        var countValue = parseInt(str[i].value);
                        if(countValue > maxValue) {
                            maxValue = countValue;
                        }
                        var oneData = {name:nameValue,value:countValue};
                        mapData.push(oneData);
                    }
                    maxValue = parseInt(maxValue);
                    initMapChartInfo(mapData, parseInt(maxValue), 'default', 1);
                } else {
                    $.messager.alert('错误', '数据报表获取失败');
                }
            }
        });
    }

    function refresh() {
        var value = document.getElementById("data-select").value;
        if(value != "" && value == 0){
            if(mapChart != null){
                initMapChartInfo(mapData, parseInt(maxValue), curTheme, 0);
            }
        } else {
            if(mapChart != null){
                initMapChartInfo(mapData, parseInt(maxValue), curTheme, 1);
            }
        }
    }

    function modifyTheme(){
        var value = document.getElementById("data-select").value;
        var themeValue = document.getElementById("theme-select").value;
        if(value != "" && value == 0){
            if(mapChart != null){
                initMapChartInfo(mapData, parseInt(maxValue), themeValue, 0);
            }
        } else {
            if(mapChart != null){
                initMapChartInfo(mapData, parseInt(maxValue), themeValue, 1);
            }
        }
    }

    function selectDataArea() {
        var value = document.getElementById("data-select").value;
        if(value != "" && value == 0){
            showInlandMapChartData();
        } else {
            showWordMapChartData();
        }
    }
</script>

<script type="text/javascript" src="${base}/resource/echarts/theme/blue.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/dark.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/default.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/gray.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/green.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/helianthus.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/infographic.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/macarons.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/red.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/shine.js"></script>