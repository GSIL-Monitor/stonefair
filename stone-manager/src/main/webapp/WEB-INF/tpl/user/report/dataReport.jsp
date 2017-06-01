<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tpl/user/managerrole/datareporthead.jsp" %>

<!DOCTYPE html>
<html>
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
            width: 150px;
            height: 26px;
        }

        /*.exhibitors:hover{
            background-color:#6caef5;
        }
        .exhibitors{
            padding:8px;
        }*/
    </style>
</head>
<body>
<table id="datareportshow" class="easyui-treegrid" fitColumns="true" >
    <table>
        <tr>
            <td width="100" align="center">数据对象：</td>
            <td width="30" align="center">
                <select id="dataSource" onchange="setOwnerEnableOrUnabled();" >
                    <option selected="true" value='0'>展商</option>
                    <option value='1'>客商</option>
                </select>
            </td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <td width="70" align="center">所属人：</td>
            <td field="tag" width="30" align="center">
                <select id="exhibitorsTag">
                </select>
            </td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <td width="100" align="center">起始日期：</td>
            <td width="60" align="center">
                <input id="dataOfBeginDate" type="text" value="请选择日期" style="border:1px solid #999;"
                       onclick="setday(this)" readonly="readonly"/>
            </td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <td width="120" align="center">
                <button type="button" class="btn btn-sm btn-success" id="showReport" onclick="showReport();">生&nbsp;&nbsp;成&nbsp;&nbsp;报&nbsp;&nbsp;表</button>
            </td>
        </tr>
        <tr>
            <td width="30" align="center">区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域：</td>
            <td width="30" align="center">
                <select id="dataRegion" onchange="changeArea();">
                    <option selected="true" value='0'>国内</option>
                    <option value='1'>国外</option>
                </select>
            </td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <td width="50" align="center">省&nbsp;&nbsp;&nbsp;&nbsp;份：</td>
            <td field="province" width="30" align="center">
                <select id="exhibitorsProvince">
                </select>
            </td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <td width="100" align="center">截止日期：</td>
            <td width="60" align="center">
                <input id="dataOfEndDate" type="text" value="请选择日期" style="border:1px solid #999;"
                       onclick="setday(this)" readonly="readonly"/>
            </td>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <td width="120" align="center">
                <button type="button" class="btn btn-sm btn-success" id="exportDataDetail">导出详细数据</button>
            </td>
        </tr>
    </table>
    <form id="exportInlandCustomersToExcel" action="${base}/user/exportDataReportInfo" method="post">
        <div id="cidParm3"></div>
    </form>
</table>
<div id="dataReportRegin" style="height: 88%; width: 98%;border:1px solid #ccc;" align="center"></div>

<%--<div style="border:1px solid #ccc;" align="center">
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
    </select>
</div>--%>
</body>
</html>
<script type="text/javascript">
    var mapChart;
    var owner;
    var provinceValue;
    var dataSource;
    var xAxisValue;
    var yAxisValue;
    var yAxisValueEx;
    var dataRegion;
    var tags = {};
    var province = {};
    var ownerList = [];
    var provinceList = [];
    var data_Info = new Array();

    $(document).ready(function () {
        var myDate = new Date();
        var year = myDate.getFullYear();
        var month = myDate.getMonth() + 1;
        var day = myDate.getDay() + 1;
        var nowStr = myDate.format("yyyy-MM-dd");
        var beginStr = (year - 1) + "-" + month + "-" + day;

        document.getElementById("dataOfBeginDate").value = beginStr;
        document.getElementById("dataOfEndDate").value = nowStr;

        //加载所属人列表
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${base}/user/queryTags?rows=50",
            success: function (result) {
                if (result) {
                    $("#exhibitorsTag").html('');
                    $("#exhibitorsTag").append('<option value="-1">全部</option>');
                    for (var i = 0, a; a = result.rows[i++];) {
                        tags[a.id] = a.name;
                        ownerList.push(a.name);
                        $("#exhibitorsTag").append('<option value="' + a.id + '">' + a.name + '</option>');
                    }
                }
            },
            error: function (result) {
                $("#exhibitorsTag").html('');
                document.getElementById('exhibitorsTag').disabled = true;
            }
        });

        //加载省份列表
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${base}/user/queryProvinceByCountryId",
            data: {'countryId': 44},
            success: function (result) {
                if (result) {
                    $("#exhibitorsProvince").html('');
                    document.getElementById('exhibitorsProvince').disabled = false;
                    $("#exhibitorsProvince").append('<option value="-1">全部</option>');
                    for (var i = 0, a; a = result[i++];) {
                        province[a.id] = a.chineseName;
                        provinceList.push(a.chineseName);
                        $("#exhibitorsProvince").append('<option value="' + a.id + '">' + a.chineseName + '</option>');
                    }
                }
            },
            error: function (result) {
                $("#exhibitorsProvince").html('');
                document.getElementById('exhibitorsProvince').disabled = true;
            }
        });
    });

    function formatTag(val, row) {
        if (val != null) {
            return tags[val];
        } else {
            return null;
        }
    }

    function formatProvince(val, row) {
        if (val != null) {
            if (val > 0 && val <= province.length) {
                return province[val - 1].chineseName;
            }
            return null;
        } else {
            return null;
        }
    }

    function setOwnerEnableOrUnabled() {
        var source = document.getElementById("dataSource").value;
        if (source == 0) {
            $("#exhibitorsTag").removeAttr("disabled");
            $("#dataRegion").removeAttr("disabled");
        } else {
            $("#dataRegion").attr("disabled", "disabled");
            $("#exhibitorsTag").attr("disabled", "disabled");
        }
    }

    function filter(countryId) {
        var filterParm = "?";
        if (document.getElementById("exhibitorsTag").value != "") {
            filterParm += '&tag=' + document.getElementById("exhibitorsTag").value;
        }
        $('#exhibitors').datagrid('options').url = '${base}/user/queryExhibitorsByPage' + filterParm;
        $('#exhibitors').datagrid('reload');
    }

    function modifyTheme() {
        var themeValue = document.getElementById("theme-select").value;
        if (dataSource == 1) {
            initChartInfo(dataSource, xAxisValue, yAxisValue, yAxisValueEx, themeValue, dataRegion);
        } else {
            initTreeChartInfo(data_Info);
        }
    }

    $('#exportDataDetail').click(function () {
        var source = document.getElementById("dataSource").value;
        var beginDate = document.getElementById("dataOfBeginDate").value;
        var endDate = document.getElementById("dataOfEndDate").value;
        var owner = document.getElementById("exhibitorsTag").value;
        var proValue = document.getElementById("exhibitorsProvince").value;
        if (beginDate != '' && endDate != '') {
            var a1 = beginDate.split("-");
            var b1 = endDate.split("-");
            if (parseInt(a1[0]) > parseInt(b1[0])) {
                alert("开始时间不能大于结束时间!");
                return;
            } else if (parseInt(a1[0]) == parseInt(b1[0])) {
                if (parseInt(a1[1]) > parseInt(b1[1])) {
                    alert("开始时间不能大于结束时间");
                    return;
                } else if (parseInt(a1[1]) == parseInt(b1[1])) {
                    alert("开始时间不能等于结束时间");
                    return;
                } else {
                    cidParm3.innerHTML = "";
                    var node1 = "<input type='hidden' name='source' value='" + source + "'/>";
                    cidParm3.innerHTML += node1;
                    var node2 = "<input type='hidden' name='startdate' value='" + beginDate + "'/>";
                    cidParm3.innerHTML += node2;
                    var node3 = "<input type='hidden' name='enddate' value='" + endDate + "'/>";
                    cidParm3.innerHTML += node3;
                    var node4 = "<input type='hidden' name='owner' value='" + owner + "'/>";
                    cidParm3.innerHTML += node4;
                    var node5 = "<input type='hidden' name='province' value='" + proValue + "'/>";
                    cidParm3.innerHTML += node5;
                    document.getElementById("exportInlandCustomersToExcel").submit();
                    $.messager.alert('提示', '导出所有客商成功');
                }
            } else {
                cidParm3.innerHTML = "";
                var node1 = "<input type='hidden' name='source' value='" + source + "'/>";
                cidParm3.innerHTML += node1;
                var node2 = "<input type='hidden' name='startdate' value='" + beginDate + "'/>";
                cidParm3.innerHTML += node2;
                var node3 = "<input type='hidden' name='enddate' value='" + endDate + "'/>";
                cidParm3.innerHTML += node3;
                var node4 = "<input type='hidden' name='owner' value='" + owner + "'/>";
                cidParm3.innerHTML += node4;
                var node5 = "<input type='hidden' name='province' value='" + proValue + "'/>";
                cidParm3.innerHTML += node5;
                document.getElementById("exportInlandCustomersToExcel").submit();
                $.messager.alert('提示', '导出所有客商成功');
            }
        }
    });

    function showReport() {
        dataSource = document.getElementById("dataSource").value;
        dataRegion = document.getElementById("dataRegion").value;
        var dimenType = 0;
        var beginDate = document.getElementById("dataOfBeginDate").value;
        var endDate = document.getElementById("dataOfEndDate").value;
        owner = document.getElementById("exhibitorsTag").value;
        provinceValue = document.getElementById("exhibitorsProvince").value;

        if (beginDate != '' && endDate != '') {
            var a1 = beginDate.split("-");
            var b1 = endDate.split("-");
            if (parseInt(a1[0]) > parseInt(b1[0])) {
                alert("开始时间不能大于结束时间!");
                return;
            } else if (parseInt(a1[0]) == parseInt(b1[0])) {
                if (parseInt(a1[1]) > parseInt(b1[1])) {
                    alert("开始时间不能大于结束时间");
                    return;
                } else if (parseInt(a1[1]) == parseInt(b1[1])) {
                    alert("开始时间不能等于结束时间");
                    return;
                } else {
                    showDataReport(dataSource, owner, provinceValue, dataRegion, dimenType, beginDate, endDate);
                }
            } else {
                showDataReport(dataSource, owner, provinceValue, dataRegion, dimenType, beginDate, endDate);
            }
        }
    }

    function changeArea() {
        dataRegion = document.getElementById("dataRegion").value;
        if (dataRegion == 0) {
            $("#exhibitorsProvince").removeAttr("disabled");
        } else {
            $("#exhibitorsProvince").attr("disabled", "disabled");
        }
    }

    function changeDimen() {
        var dimenType = document.getElementById("dimenType").value;
        if (dimenType != "" && dimenType == 0) {
            document.getElementById('exhibitorsProvince').onclick = function () {
                setmonth(this);
            }
            document.getElementById('dataOfEndDate').onclick = function () {
                setmonth(this);
            }
        } else {
            document.getElementById('dataOfBeginDate').onclick = function () {
                setday(this);
            }
            document.getElementById('dataOfEndDate').onclick = function () {
                setday(this);
            }
        }
    }

    function showDataReport(dataSource, owner, provinceValue, dataRegion, dimenType, beginDate, endDate) {
        $.ajax({
            url: "${base}/user/queryDataReportEx",
            type: "post",
            async: false, //同步执行
            data: {
                "source": dataSource,
                "owner": owner,
                "province": provinceValue,
                "region": dataRegion,
                'dimen': dimenType,
                "startdate": beginDate,
                "enddate": endDate
            },
            dataType: "json",
            success: function (mapValue) {
                if (mapValue.resultCode == 0) {
                    if (dataSource == 1) {
                        xAxisValue = [];
                        yAxisValue = [];
                        yAxisValueEx = [];
                        var str = JSON.parse(mapValue.mapData);
                        var strEx = JSON.parse(mapValue.mapDataEx);
                        if (str != null && str.length > 0) {
                            for (var i = 0; i < str.length; i++) {
                                xAxisValue.push(str[i].yearMonth);
                                yAxisValue.push(parseInt(str[i].total));
                            }
                            for (var i = 0; i < strEx.length; i++) {
                                yAxisValueEx.push(parseInt(strEx[i].total));
                            }
                            initChartInfo(dataSource, xAxisValue, yAxisValue, yAxisValueEx, 'default', dataRegion);
                        } else {
                            $.messager.alert('错误', '没有找到合适的数据，请重新设定查询条件');
                        }
                    } else {
                        data_Info = new Array();
                        if (dataRegion == 0) {
                            var inlandData = JSON.parse(mapValue.mapData);
                            var inland_4 = new Array();
                            var inland_3 = new Array();
                            var inland_2 = new Array();
                            var inland_Info = new Array();
                            if (inlandData != null && inlandData.length > 0) {
                                for (var i = 0; i < inlandData.length; i++) {
                                    var ownerName = inlandData[i].owner;
                                    var provinceInfoList = inlandData[i].provinceInfo;
                                    if (provinceInfoList != null && provinceInfoList.length > 0) {
                                        for (var k = 0; k < provinceInfoList.length; k++) {
                                            var provinceName = provinceInfoList[k].province;
                                            var provinceCount = provinceInfoList[k].count;
                                            inland_4.push({
                                                name: provinceCount,
                                                value: provinceCount,
                                                level: 5,
                                                itemStyle: {normal: {color: '#cc5555', label: {position: 'right'}}}
                                            });
                                            inland_3.push({
                                                name: provinceName,
                                                children: inland_4,
                                                level: 4,
                                                itemStyle: {normal: {color: '#cc9999'}}
                                            });
                                            inland_4 = new Array();
                                        }
                                        inland_2.push({
                                            name: ownerName,
                                            children: inland_3,
                                            level: 3,
                                            itemStyle: {normal: {color: '#cc9999'}}
                                        });
                                        inland_3 = new Array();
                                    } else {
                                        inland_4.push({
                                            name: "",
                                            value: 0,
                                            level: 5,
                                            itemStyle: {normal: {color: '#cc5555', label: {position: 'right'}}}
                                        });
                                        inland_3.push({
                                            name: "",
                                            children: inland_4,
                                            level: 4,
                                            itemStyle: {normal: {color: '#cc9999'}}
                                        });
                                        inland_4 = new Array();
                                        inland_2.push({
                                            name: ownerName,
                                            children: inland_3,
                                            level: 3,
                                            itemStyle: {normal: {color: '#cc9999'}}
                                        });
                                        inland_3 = new Array();
                                    }
                                    inland_Info.push({
                                        name: "国内展商",
                                        children: inland_2,
                                        level: 2,
                                        itemStyle: {normal: {color: '#cc9999'}}
                                    });
                                    data_Info.push({
                                        name: "展商数据",
                                        children: inland_Info,
                                        level: 1,
                                        itemStyle: {normal: {color: '#cc9999'}}
                                    });
                                    inland_Info = new Array();
                                }
                            }
                        } else {
                            var outlnadData = JSON.parse(mapValue.mapDataEx);
                            var outland_4 = new Array();
                            var outland_3 = new Array();
                            var outland_2 = new Array();
                            var outland_Info = new Array();
                            if (outlnadData != null && outlnadData.length > 0) {
                                for (var i = 0; i < outlnadData.length; i++) {
                                    var ownerName = outlnadData[i].owner;
                                    var provinceInfoList = outlnadData[i].provinceInfo;
                                    if (provinceInfoList != null && provinceInfoList.length > 0) {
                                        for (var k = 0; k < provinceInfoList.length; k++) {
                                            var provinceName = provinceInfoList[k].province;
                                            var provinceCount = provinceInfoList[k].count;
                                            outland_4.push({
                                                name: provinceCount,
                                                value: provinceCount,
                                                level: 5,
                                                itemStyle: {normal: {color: '#cc5555', label: {position: 'right'}}}
                                            });
                                            outland_3.push({
                                                name: provinceName,
                                                children: outland_4,
                                                level: 4,
                                                itemStyle: {normal: {color: '#cc9999'}}
                                            });
                                            outland_4 = new Array();
                                        }
                                        outland_2.push({
                                            name: ownerName,
                                            children: outland_3,
                                            level: 3,
                                            itemStyle: {normal: {color: '#cc9999'}}
                                        });
                                        outland_3 = new Array();
                                    } else {
                                        outland_4.push({
                                            name: "",
                                            value: 0,
                                            level: 5,
                                            itemStyle: {normal: {color: '#cc5555', label: {position: 'right'}}}
                                        });
                                        outland_3.push({
                                            name: "",
                                            children: outland_4,
                                            level: 4,
                                            itemStyle: {normal: {color: '#cc9999'}}
                                        });
                                        outland_4 = new Array();
                                        outland_2.push({
                                            name: ownerName,
                                            children: outland_3,
                                            level: 3,
                                            itemStyle: {normal: {color: '#cc9999'}}
                                        });
                                        outland_3 = new Array();
                                    }
                                    outland_Info.push({
                                        name: "国外展商",
                                        children: outland_2,
                                        level: 2,
                                        itemStyle: {normal: {color: '#cc9999'}}
                                    });
                                    data_Info.push({
                                        name: "展商数据",
                                        children: outland_Info,
                                        level: 1,
                                        itemStyle: {normal: {color: '#cc9999'}}
                                    });
                                    outland_Info = new Array();
                                }
                            }
                        }
                        if (data_Info != null && data_Info.length > 0) {
                            initTreeChartInfo(data_Info);
                        } else {
                            $.messager.alert('错误', '没有找到合适的数据，请重新设定查询条件');
                        }
                    }
                } else {
                    $.messager.alert('错误', '数据报表获取失败');
                }
            }
        });
    }

    function initChartInfo(dataSource, xValue, yValue, yValueEx, themeInfo, flag) {
        var mapType = 'bar';
        var titleText = '';
        var label1 = '国内展商';
        var label2 = '国外展商';
        if (dataSource == 0) {
            titleText = '展商数据报表';
            label1 = '国内展商';
            label2 = '国外展商';
        } else {
            titleText = '客商数据报表';
            label1 = '国内客商';
            label2 = '国外客商';
        }

        require.config({
            paths: {
                echarts: '${base}/resource/echarts/echarts',
                'echarts/chart/bar': '${base}/resource/echarts/bar',
                'echarts/chart/line': '${base}/resource/echarts/line'
            }
        });

        var def, infographic, macarons, shine, dark, blue, green, red, gray, helianthus;
        require(['${base}/resource/echarts/theme/default'], function (theme) {
            def = theme;
        });
        require(['${base}/resource/echarts/theme/infographic'], function (theme) {
            infographic = theme;
        });
        require(['${base}/resource/echarts/theme/macarons'], function (theme) {
            macarons = theme;
        });
        require(['${base}/resource/echarts/theme/shine'], function (theme) {
            shine = theme;
        });
        require(['${base}/resource/echarts/theme/dark'], function (theme) {
            dark = theme;
        });
        require(['${base}/resource/echarts/theme/blue'], function (theme) {
            blue = theme;
        });
        require(['${base}/resource/echarts/theme/green'], function (theme) {
            green = theme;
        });
        require(['${base}/resource/echarts/theme/red'], function (theme) {
            red = theme;
        });
        require(['${base}/resource/echarts/theme/gray'], function (theme) {
            gray = theme;
        });
        require(['${base}/resource/echarts/theme/helianthus'], function (theme) {
            helianthus = theme;
        });

        require(['echarts', 'echarts/chart/bar', 'echarts/chart/line'], DrawEChart);
        function DrawEChart(ec) {
            if (themeInfo.toLowerCase() == 'default') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), def);
            } else if (themeInfo.toLowerCase() == 'infographic') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), infographic);
            } else if (themeInfo.toLowerCase() == 'macarons') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), macarons);
            } else if (themeInfo.toLowerCase() == 'shine') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), shine);
            } else if (themeInfo.toLowerCase() == 'dark') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), dark);
            } else if (themeInfo.toLowerCase() == 'blue') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), blue);
            } else if (curTheme.toLowerCase() == 'green') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), green);
            } else if (themeInfo.toLowerCase() == 'red') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), red);
            } else if (themeInfo.toLowerCase() == 'gray') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), gray);
            } else if (themeInfo.toLowerCase() == 'helianthus') {
                mapChart = ec.init(document.getElementById('dataReportRegin'), helianthus);
            } else {
                mapChart = ec.init(document.getElementById('dataReportRegin'), def);
            }
            mapChart.showLoading({text: "图表数据正在努力加载..."});
            //定义图表options
            var option = {
                title: {
                    text: titleText,
                    x: 'center'
                },
                // 提示框
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: [label1, label2],
                    x: 'left'
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        data: xValue
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value}位'
                        }
                    }
                ],
                series: [
                    {
                        name: label1,
                        type: mapType,
                        data: yValue
                    },
                    {
                        name: label2,
                        type: mapType,
                        data: yValueEx
                    }
                ]
            };
            mapChart.setOption(option);
            mapChart.hideLoading();
        }
    }

    function initTreeChartInfo(data_Info) {
        var mapType = 'tree';
        var titleText = '展商数据报表';

        //console.log("data_Info: " + data_Info);
        require.config({
            paths: {
                echarts: '${base}/resource/echarts/echarts',
                'echarts/chart/tree': '${base}/resource/echarts/tree'
            }
        });
        var def;
        require(['${base}/resource/echarts/theme/default'], function (theme) {
            def = theme;
        });
        require(['echarts', 'echarts/chart/tree'], DrawEChart);
        function DrawEChart(ec) {
            mapChart = ec.init(document.getElementById('dataReportRegin'), def);
            var option = {
                title: {
                    text: titleText
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: titleText,
                        type: mapType,
                        orient: 'horizontal',  // vertical horizontal
                        rootLocation: {x: 100, y: 230}, // 根节点位置  {x: 100, y: 'center'}
                        nodePadding: 8,
                        layerPadding: 200,
                        hoverable: false,
                        roam: true,
                        symbolSize: 8,
                        itemStyle: {
                            normal: {
                                color: '#4883b4',
                                label: {
                                    show: true,
                                    position: 'right',
                                    formatter: "{b}",
                                    textStyle: {
                                        color: '#000',
                                        fontSize: 8
                                    }
                                },
                                lineStyle: {
                                    color: '#ccc',
                                    type: 'broken' // 'curve'|'broken'|'solid'|'dotted'|'dashed'

                                }
                            },
                            emphasis: {
                                color: '#4883b4',
                                label: {
                                    show: false
                                },
                                borderWidth: 0
                            }
                        },
                        data: data_Info
                    }
                ]
            };
            mapChart.setOption(option);
            mapChart.hideLoading();
        }
    }
</script>

<script type="text/javascript" src="${base}/resource/echarts/theme/blue.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/dark.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/default.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/infographic.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/macarons.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/theme/shine.js"></script>
</script>