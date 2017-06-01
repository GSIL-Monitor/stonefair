<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <script src="/manager/resource/xicecjsfile/jquery-1.8.2.min.js"></script>
    <script src="/manager/resource/xicecjsfile/chinamapPath.js"></script>
    <script src="/manager/resource/xicecjsfile/chinamap.js"></script>
    <script src="/manager/resource/xicecjsfile/raphael.js"></script>
    <script src="/manager/resource/xicecjsfile/snap.svg.js"></script>
    <script src="/manager/resource/xicecjsfile/snap.svg.zpd.js"></script>
    <style type="text/css">
        #ChinaMap {
            padding-right: 100px;
            padding-left: 10px;
            padding-bottom: 10px;
            margin: 0px auto;
            padding-top: 10px;
            position: relative;
            text-align: center;
        }
        #tiplayer {
            padding-right: 5px;
            padding-left: 5px;
            z-index: 1000;
            min-height: 1em;
            background: #000;
            max-width: 250px;
            padding-bottom: 5px;
            font: 12px 'Microsoft YaHei',Arial,宋体,Tahoma,Sans-Serif;
            color: #fff;
            padding-top: 5px;
            position: absolute;
            text-align: left;
            word-wrap: break-word;
            -moz-border-radius: 3px;
            -khtml-border-radius: 3px;
            -webkit-border-radius: 3px;
            border-radius: 3px;
        }

        .ToolTip {
            padding-right: 5px;
            padding-left: 5px;
            z-index: 1000;
            min-height: 1em;
            background: #000;
            max-width: 350px;
            padding-bottom: 5px;
            font: 12px 'Microsoft YaHei',Arial,宋体,Tahoma,Sans-Serif;
            color: #fff;
            padding-top: 5px;
            position: absolute;
            text-align: left;
            word-wrap: break-word;
            -moz-border-radius: 3px;
            -khtml-border-radius: 3px;
            -webkit-border-radius: 3px;
            border-radius: 3px;
        }

        .ToolTip {
            border-right: #c5b270 1px solid;
            padding-right: 15px;
            border-top: #c5b270 1px solid;
            padding-left: 15px;
            background: #fffbd6;
            padding-bottom: 0px;
            border-left: #c5b270 1px solid;
            color: #bb861c;
            line-height: 30px;
            padding-top: 0px;
            border-bottom: #c5b270 1px solid;
            top: 30px;
        }

        .tbtn {
            -moz-box-shadow:inset 0px 1px 0px 0px #ffffff;
            -webkit-box-shadow:inset 0px 1px 0px 0px #ffffff;
            box-shadow:inset 0px 1px 0px 0px #ffffff;
            background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #ededed), color-stop(1, #dfdfdf) );
            background:-moz-linear-gradient( center top, #ededed 5%, #dfdfdf 100% );
            background-color:#ededed;
            -moz-border-radius:6px;
            -webkit-border-radius:6px;
            border-radius:6px;
            border:1px solid #dcdcdc;
            display:inline-block;
            color:#444;
            font-family:arial;
            font-size:12px;
            font-weight:bold;
            padding:3px 10px;
            text-decoration:none;
            text-shadow:1px 1px 0px #ffffff;
            cursor:pointer;
        }
    </style>
</head>
<body>
<div id="toolbar" class="toolbar">
    <span style="float:right; margin-top:6px;padding-right:20px;"></span>
        <span>
            <input type="button" id="zoomout" value="放大" class="tbtn" />
            <input type="button" id="zoonin" value="缩小"  class="tbtn" />
        </span>
</div>
<div id="ChinaMap">
    <div id="map" style="width:100%; height:100%;"></div>
    <div id="ToolTip" class="ToolTip" style="left: 10px">
        <div id="areaInfo"></div>
        <div id="cityInfo"></div>
    </div>
    <div class="ToolTip" style="margin: 0px auto;">
        <div id="topList" style="width: 130px; float: left; display:none"></div>
        <div id="cityList" style="display: none; float: left">
        </div>
    </div>
</div>
</body>
</html>