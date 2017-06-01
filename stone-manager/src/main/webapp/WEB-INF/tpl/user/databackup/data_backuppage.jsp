<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tpl/user/managerrole/head.jsp" %>

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
			width: 200px;
			height: 20px;
		}

		.btn { display: block; position: relative; background: #aaa; padding: 5px; float: left; color: #fff; text-decoration: none; cursor: pointer; }
		.btn * { font-style: normal; background-image: url(btn2.png); background-repeat: no-repeat; display: block; position: relative; }
		.btn i { background-position: top left; position: absolute; margin-bottom: -5px;  top: 0; left: 0; width: 5px; height: 5px; }
		.btn span { background-position: bottom left; left: -5px; padding: 0 0 5px 10px; margin-bottom: -5px; }
		.btn span i { background-position: bottom right; margin-bottom: 0; position: absolute; left: 100%; width: 10px; height: 100%; top: 0; }
		.btn span span { background-position: top right; position: absolute; right: -10px; margin-left: 10px; top: -5px; height: 0; }
		* html .btn span,
		* html .btn i { float: left; width: auto; background-image: none; cursor: pointer; }
		.btn.blue { background: #2ae; }
		.btn.green { background: #9d4; }
		.btn.pink { background: #e1a; }
		.btn:hover { background-color: #a00; }
		.btn:active { background-color: #444; }
		.btn[class] {  background-image: url(shade.png); background-position: bottom; }
		* html .btn { border: 3px double #aaa; }
		* html .btn.blue { border-color: #2ae; }
		* html .btn.green { border-color: #9d4; }
		* html .btn.pink { border-color: #e1a; }
		* html .btn:hover { border-color: #a00; }
		p { clear: both; padding-bottom: 2em; }
		form { margin-top: 2em; }
		form p .btn { margin-right: 1em; }
		textarea { margin: 1em 0;}

		#bg{ display: none; position: absolute; top: 0%; left: 0%; width: 50%; height: 50%; background-color: black; z-index:1001; -moz-opacity: 0.2; opacity:.2; filter: alpha(opacity=50);}
		.loading{display: none; position: absolute; top: 50%; left: 50%; z-index:1002; }
	</style>
</head>
<body class="body">
<!-- 重置列表管理 -->
<div region="center">
	<div title="数据备份" data-options="iconCls:'icon-home'">
		<table align="center" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<a href="#" id="exhibitor_Backup" class="btn blue" style="width: 180px">展商数据备份</a>
				</td>
				<td>
					<a href="#" id="customer_Backup" class="btn blue" style="width: 180px">客商数据备份</a>
				</td>
			</tr>
		</table>
	</div>
</div>

<div class="loading"><img src="${base}/resource/load.gif"></div>
<script>
	$('#exhibitor_Backup').click(function(){
		$.messager.confirm('确认备份','你确定要备份展商数据吗?',function(r){
			if(r){
				$("#bg,.loading").show();
				$.ajax({
					url: "${base}/user/backupExhibitorData",
					type: "post",
					dataType: "json",
					traditional: true,
					success: function (data) {
						$("#bg,.loading").hide();
						if (data.resultCode == 0) {
							alert("展商数据备份成功");
						} else {
							$.messager.alert('错误', '展商数据备份出错');
						}
					}
				});
			}
		});
	});

	$('#customer_Backup').click(function(){
		$.messager.confirm('确认备份','你确定要备份客商数据吗?',function(r){
			if(r){
				$("#bg,.loading").show();
				$.ajax({
					url: "${base}/user/backupCustomerData",
					type: "post",
					dataType: "json",
					traditional: true,
					success: function (data) {
						$("#bg,.loading").hide();
						if (data.resultCode == 0) {
							alert("客商数据备份成功");
						} else {
							$.messager.alert('错误', '客商数据备份出错');
						}
					}
				});
			}
		});
	});
</script>
</body>
</html>