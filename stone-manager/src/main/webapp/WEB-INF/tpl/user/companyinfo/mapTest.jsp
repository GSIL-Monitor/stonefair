<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tpl/user/managerrole/head.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
	<title>金泓信展商管理后台</title>
	<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
	<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
	<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=f177900411f002a428efab4b588228b3"></script>
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

		div.info {position: relative;z-index: 100;border: 1px solid #BCBCBC;box-shadow: 0 0 10px;#B7B6B6;border-radius: 8px;background-color: rgba(255,255,255,0.9);transition-duration: 0.25s;}
		div.info:hover {box-shadow: 0px 0px 15px #0CF;}
		div.info-top {position: relative;background: none repeat scroll 0 0 #F9F9F9;border-bottom: 1px solid #CCC;border-radius:5px 5px 0 0;}
		div.info-top div {display: inline-block;color: #333333;font-size:14px;font-weight:bold;    line-height:31px;padding:0 10px;}
		div.info-top img {position: absolute;top: 10px;right: 10px;transition-duration: 0.25s;}
		div.info-top img:hover{box-shadow: 0px 0px 5px #000;}
		div.info-middle {font-size:12px;padding:10px;line-height:21px;}
		div.info-bottom {height:0px;width:100%;clear:both;text-align:center;}
		div.info-bottom img{position: relative;z-index:104;}
	</style>
</head>
<body>
<!-- 地图显示标签 -->
<div id="map" class="easyui-tabs" data-options="width: $(this).width(), height: $(this).height()"></div>
<div id="tip"></div>
<script>
	$(document).ready(function () {
		var map, geolocation, marker;
		//加载地图，调用浏览器定位服务
		map = new AMap.Map('map', {
			resizeEnable: true
		});
		map.plugin('AMap.Geolocation', function() {
			geolocation = new AMap.Geolocation({
				enableHighAccuracy: true,//是否使用高精度定位，默认:true
				timeout: 10000,          //超过10秒后停止定位，默认：无穷大
				maximumAge: 0,           //定位结果缓存0毫秒，默认：0
				convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
				showButton: true,        //显示定位按钮，默认：true
				buttonPosition: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
				buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
				showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
				showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
				panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
				zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
			});
			map.addControl(geolocation);
			geolocation.getCurrentPosition();
			AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
			AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息

			//在地图中添加ToolBar插件
			map.plugin(["AMap.ToolBar"],function(){
				toolBar = new AMap.ToolBar();
				toolBar.show();
				toolBar.showRuler();
				toolBar.showDirection();
				map.addControl(toolBar);
			});

			//在地图中添加鹰眼插件
			map.plugin(["AMap.OverView"],function(){
				//加载鹰眼
				overView = new AMap.OverView({
					visible:true //初始化显示鹰眼
				});
				map.addControl(overView);
				overView.open(); //展开鹰眼
			});

			//加载比例尺插件
			map.plugin(["AMap.Scale"],function(){
				scale = new AMap.Scale();
				map.addControl(scale);
				scale.show();
			});

			//创建测距插件
			map.plugin(["AMap.RangingTool"],function(){
				ruler = new AMap.RangingTool(map);
				AMap.event.addListener(ruler,"end",function(e){
					ruler.turnOff();
				});
				ruler.turnOn();
			});
		});
		//解析定位结果
		function onComplete(data) {
			var str=['定位成功'];
			str.push('经度：' + data.position.getLng());
			str.push('纬度：' + data.position.getLat());
			str.push('精度：' + data.accuracy + ' 米');
			str.push('是否经过偏移：' + (data.isConverted ? '是' : '否'));
			document.getElementById('tip').innerHTML = str.join('<br>');
			marker = new AMap.Marker({
				position:(new AMap.LngLat(data.position.getLng(),data.position.getLat())),
				draggable:false, //点标记可拖拽
				cursor:'move'   //鼠标悬停点标记时的鼠标样式
			});
			marker.setAnimation('AMAP_ANIMATION_BOUNCE');
			marker.setMap(map);

			myWindow(data.position);
			/*var info = [];
			info.push("<div><div><img style=\"float:left;\" src=\" http://webapi.amap.com/images/autonavi.png \"/></div> ");
			info.push("<div style=\"padding:0px 0px 0px 4px;\"><b>高德软件</b>");
			info.push("经度：" + data.position.getLng() + "   纬度 : " + data.position.getLat());
			info.push("地址 : 厦门会展中心会展路198号</div></div>");
			inforWindow = new AMap.InfoWindow({
				content:info.join("<br/>")  //使用默认信息窗体框样式，显示信息内容
			});
			inforWindow.open(map,new AMap.LngLat(data.position.getLng(),data.position.getLat()));*/
		}
		//解析定位错误信息
		function onError(data) {
			document.getElementById('tip').innerHTML = '定位失败';
		}

		var infoWindow = new AMap.InfoWindow({
			isCustom:true,  //使用自定义窗体
			content:createInfoWindow('会展中心&nbsp;&nbsp;<span style="font-size:11px;color:#F00;">欢迎您！</span>',"<img src='${base}/resource/images/mainlogo.png' style='float:left;margin:0 5px 5px 0;'>地址：厦门市思明区会展路198号<br/>电话：0592 5959616<br/><a href='www.stonefair.org.cn'>详细信息</a>"),
			size:new AMap.Size(300, 0),
			offset:new AMap.Pixel(0, -50)//-113, -140
		});

		//关闭信息窗体
		function closeInfoWindow(){
			map.clearInfoWindow();
		}
		//构建自定义信息窗体
		function createInfoWindow(title,content){
			var info = document.createElement("div");
			info.className = "info";
			// 定义顶部标题
			var top = document.createElement("div");
			top.className = "info-top";
			var titleD = document.createElement("div");
			titleD.innerHTML = title;
			var closeX = document.createElement("img");
			closeX.src = "close2.gif";
			closeX.onclick = closeInfoWindow;

			top.appendChild(titleD);
			top.appendChild(closeX);
			info.appendChild(top);
			// 定义中部内容
			var middle = document.createElement("div");
			middle.className = "info-middle";
			middle.innerHTML = content;
			info.appendChild(middle);

			// 定义底部内容
			var bottom = document.createElement("div");
			bottom.className = "info-bottom";
			var sharp = document.createElement("img");
			sharp.src = "sharp.png";
			bottom.appendChild(sharp);
			info.appendChild(bottom);
			return info;
		}
		function myWindow(location){
			infoWindow.open(map,new AMap.LngLat(location.getLng(),location.getLat()));
		}
	});
</script>
</body>
</html>