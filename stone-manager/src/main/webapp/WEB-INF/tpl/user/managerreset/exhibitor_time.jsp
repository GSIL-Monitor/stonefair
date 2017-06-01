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
<body  onload="queryExhibitorTimeData()">
<div style="height: auto;" class="easyui-panel" title="境内时间参数">
	<table align="center" style="margin-left: 10px;margin-top: 5px">
		<tr>
			<td style="width: 300px">公司基本信息截止时间（中文）：</td>
			<td>
				<input id="company_Info_Submit_Deadline_Zh" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">公司基本信息截止时间（英文）：</td>
			<td>
				<input id="company_Info_Submit_Deadline_En" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">参展人员证件办理截止时间（中文）：</td>
			<td>
				<input id="participant_List_Submit_Deadline_Zh" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">参展人员证件办理截止时间（英文）：</td>
			<td>
				<input id="participant_List_Submit_Deadline_En" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">发票信息办理截止时间（中文）：</td>
			<td>
				<input id="invoice_Information_Submit_Deadline_Zh" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">发票信息办理截止时间（英文）：</td>
			<td>
				<input id="invoice_Information_Submit_Deadline_En" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">广告预订办理截止时间（中文）：</td>
			<td>
				<input id="advertisement_Submit_Deadline_Zh" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">广告预订办理截止时间（英文）：</td>
			<td>
				<input id="advertisement_Submit_Deadline_En" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">公司基本信息创建截止时间（中文）：</td>
			<td>
				<input id="company_Info_Insert_Submit_Deadline_Zh" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">公司基本信息创建截止时间（英文）：</td>
			<td>
				<input id="company_Info_Insert_Submit_Deadline_En" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">Visa信息登记截止时间（中文）：</td>
			<td>
				<input id="visa_Info_Submit_Deadline_Zh" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">Visa信息登记截止时间（英文）：</td>
			<td>
				<input id="visa_Info_Submit_Deadline_En" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">展会登记截止时间：</td>
			<td>
				<input id="stone_fair_end_year" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">展会登记开始时间：</td>
			<td>
				<input id="stone_fair_begin_year" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">展会开展时间（中文）：</td>
			<td>
				<input id="stone_Fair_Show_Date_Zh" style="width:70%;" type="text"/>
			</td>

			<td style="width: 300px">展会开展时间（英文）：</td>
			<td>
				<input id="stone_Fair_Show_Date_En" style="width:70%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">展会结束时间（中文）：</td>
			<td>
				<input id="exhibitor_end_time" style="width:70%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">展会登记截止时间（另一种格式）：</td>
			<td>
				<input id="company_Info_Data_End_Html" style="width:60%;" type="text"/>
			</td>
		</tr>
	</table>
	<div class="email-footer" align="center" style="margin-left: 50%;margin-top: 30px">
		<button type="button" class="btn btn-primary" id="saveData">确认修改</button>
	</div>
</div>

<div style="height: auto;" class="easyui-panel" title="境内菜单移动">
	<table style="margin-left: 10px;margin-top: 5px">
		<tr>
			<td style="width: 300px">菜单移动开关：</td>
			<td>
				<input id="menu_move_switch" name="menu_move_switch" type="checkbox">
			</td>
		</tr>
	</table>
	<div class="email-footer" align="center" style="margin-left: 50%;margin-top: 30px">
		<button type="button" class="btn btn-primary" id="saveSwitchData">确认修改</button>
	</div>
</div>

<div style="height: auto;" class="easyui-panel" title="境外时间参数">
	<table align="center" style="margin-left: 10px;margin-top: 5px">
		<tr>
			<td style="width: 300px">公司基本信息截止时间（中文）：</td>
			<td>
				<input id="company_Info_Submit_Deadline_Zh_Foreign" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">公司基本信息截止时间（英文）：</td>
			<td>
				<input id="company_Info_Submit_Deadline_En_Foreign" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">参展人员证件办理截止时间（中文）：</td>
			<td>
				<input id="participant_List_Submit_Deadline_Zh_Foreign" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">参展人员证件办理截止时间（英文）：</td>
			<td>
				<input id="participant_List_Submit_Deadline_En_Foreign" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">发票信息办理截止时间（中文）：</td>
			<td>
				<input id="invoice_Information_Submit_Deadline_Zh_Foreign" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">发票信息办理截止时间（英文）：</td>
			<td>
				<input id="invoice_Information_Submit_Deadline_En_Foreign" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">广告预订办理截止时间（中文）：</td>
			<td>
				<input id="advertisement_Submit_Deadline_Zh_Foreign" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">广告预订办理截止时间（英文）：</td>
			<td>
				<input id="advertisement_Submit_Deadline_En_Foreign" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">公司基本信息创建截止时间（中文）：</td>
			<td>
				<input id="company_Info_Insert_Submit_Deadline_Zh_Foreign" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">公司基本信息创建截止时间（英文）：</td>
			<td>
				<input id="company_Info_Insert_Submit_Deadline_En_Foreign" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">Visa信息登记截止时间（中文）：</td>
			<td>
				<input id="visa_Info_Submit_Deadline_Zh_Foreign" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">Visa信息登记截止时间（英文）：</td>
			<td>
				<input id="visa_Info_Submit_Deadline_En_Foreign" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">展会登记截止时间：</td>
			<td>
				<input id="stone_fair_end_year_Foreign" style="width:60%;" type="text"/>
			</td>

			<td style="width: 300px">展会登记开始时间：</td>
			<td>
				<input id="stone_fair_begin_year_Foreign" style="width:60%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">展会开展时间（中文）：</td>
			<td>
				<input id="stone_Fair_Show_Date_Zh_Foreign" style="width:70%;" type="text"/>
			</td>

			<td style="width: 300px">展会开展时间（英文）：</td>
			<td>
				<input id="stone_Fair_Show_Date_En_Foreign" style="width:70%;" type="text"/>
			</td>
		</tr>
		<tr>
			<td style="width: 300px">展会登记截止时间（另一种格式）：</td>
			<td>
				<input id="company_Info_Data_End_Html_Foreign" style="width:60%;" type="text"/>
			</td>
		</tr>
	</table>
	<div class="email-footer" align="center" style="margin-left: 50%;margin-top: 30px">
		<button type="button" class="btn btn-primary" id="saveDataForeign">确认修改</button>
	</div>
</div>

<div style="height: auto;" class="easyui-panel" title="境外菜单移动">
	<table style="margin-left: 10px;margin-top: 5px">
		<tr>
			<td style="width: 300px">菜单移动开关：</td>
			<td>
				<input id="menu_move_switch_foreign" name="menu_move_switch_foreign" type="checkbox">
			</td>
		</tr>
	</table>
	<div class="email-footer" align="center" style="margin-left: 50%;margin-top: 30px">
		<button type="button" class="btn btn-primary" id="saveSwitchDataForeign">确认修改</button>
	</div>
</div>
<script>
	function queryExhibitorTimeData() {
		$.ajax({
			url: "${base}/user/queryAllExhibitorTime",
			type: "post",
			dataType: "json",
			traditional: true,
			success: function (data) {
				if(data.rows.length>0) {
					for(var i = 0; i < data.rows.length; i++){
						var map = data.rows[i];
						if(2 == map.area_time) {
							document.getElementById("company_Info_Submit_Deadline_Zh_Foreign").value = map.company_Info_Submit_Deadline_Zh;
							document.getElementById("company_Info_Submit_Deadline_En_Foreign").value = map.company_Info_Submit_Deadline_En;
							document.getElementById("participant_List_Submit_Deadline_Zh_Foreign").value = map.participant_List_Submit_Deadline_Zh;
							document.getElementById("participant_List_Submit_Deadline_En_Foreign").value = map.participant_List_Submit_Deadline_En;
							document.getElementById("invoice_Information_Submit_Deadline_Zh_Foreign").value = map.invoice_Information_Submit_Deadline_Zh;
							document.getElementById("invoice_Information_Submit_Deadline_En_Foreign").value = map.invoice_Information_Submit_Deadline_En;
							document.getElementById("advertisement_Submit_Deadline_Zh_Foreign").value = map.advertisement_Submit_Deadline_Zh;
							document.getElementById("advertisement_Submit_Deadline_En_Foreign").value = map.advertisement_Submit_Deadline_En;
							document.getElementById("company_Info_Insert_Submit_Deadline_Zh_Foreign").value = map.company_Info_Insert_Submit_Deadline_Zh;
							document.getElementById("company_Info_Insert_Submit_Deadline_En_Foreign").value = map.company_Info_Insert_Submit_Deadline_En;
							document.getElementById("stone_fair_end_year_Foreign").value = map.stone_fair_end_year;
							document.getElementById("stone_fair_begin_year_Foreign").value = map.stone_fair_begin_year;
							document.getElementById("company_Info_Data_End_Html_Foreign").value = map.company_Info_Data_End_Html;
							document.getElementById("visa_Info_Submit_Deadline_Zh_Foreign").value = map.visa_Info_Submit_Deadline_Zh;
							document.getElementById("visa_Info_Submit_Deadline_En_Foreign").value = map.visa_Info_Submit_Deadline_En;
							document.getElementById("stone_Fair_Show_Date_Zh_Foreign").value = map.stone_Fair_Show_Date_Zh;
							document.getElementById("stone_Fair_Show_Date_En_Foreign").value = map.stone_Fair_Show_Date_En;
							if(map.menu_move_switch == 1){
								$(":checkbox[name='menu_move_switch_foreign']").prop("checked", true);
							}else{
								$(":checkbox[name='menu_move_switch_foreign']").prop("checked", false);
							}
						}else if(1 == map.area_time){
							document.getElementById("company_Info_Submit_Deadline_Zh").value = map.company_Info_Submit_Deadline_Zh;
							document.getElementById("company_Info_Submit_Deadline_En").value = map.company_Info_Submit_Deadline_En;
							document.getElementById("participant_List_Submit_Deadline_Zh").value = map.participant_List_Submit_Deadline_Zh;
							document.getElementById("participant_List_Submit_Deadline_En").value = map.participant_List_Submit_Deadline_En;
							document.getElementById("invoice_Information_Submit_Deadline_Zh").value = map.invoice_Information_Submit_Deadline_Zh;
							document.getElementById("invoice_Information_Submit_Deadline_En").value = map.invoice_Information_Submit_Deadline_En;
							document.getElementById("advertisement_Submit_Deadline_Zh").value = map.advertisement_Submit_Deadline_Zh;
							document.getElementById("advertisement_Submit_Deadline_En").value = map.advertisement_Submit_Deadline_En;
							document.getElementById("company_Info_Insert_Submit_Deadline_Zh").value = map.company_Info_Insert_Submit_Deadline_Zh;
							document.getElementById("company_Info_Insert_Submit_Deadline_En").value = map.company_Info_Insert_Submit_Deadline_En;
							document.getElementById("stone_fair_end_year").value = map.stone_fair_end_year;
							document.getElementById("stone_fair_begin_year").value = map.stone_fair_begin_year;
							document.getElementById("company_Info_Data_End_Html").value = map.company_Info_Data_End_Html;
							document.getElementById("visa_Info_Submit_Deadline_Zh").value = map.visa_Info_Submit_Deadline_Zh;
							document.getElementById("visa_Info_Submit_Deadline_En").value = map.visa_Info_Submit_Deadline_En;
							document.getElementById("stone_Fair_Show_Date_Zh").value = map.stone_Fair_Show_Date_Zh;
							document.getElementById("stone_Fair_Show_Date_En").value = map.stone_Fair_Show_Date_En;
							document.getElementById("exhibitor_end_time").value = map.exhibitor_end_time;
							if(map.menu_move_switch == 1){
								$(":checkbox[name='menu_move_switch']").prop("checked", true);
							}else{
								$(":checkbox[name='menu_move_switch']").prop("checked", false);
							}
						}
					}
				}
			}
		});
	}

	$(document).ready(function () {
		$("#saveData").click(function () {
			$.messager.confirm('确认修改','你确定要修改时间参数?',function(r){
				if (r){
					$.ajax({
						url: "${base}/user/modifyExhibitorTime",
						type: "post",
						dataType: "json",
						traditional: true,
						data: {"company_Info_Submit_Deadline_Zh": $("#company_Info_Submit_Deadline_Zh").val(),
							"company_Info_Submit_Deadline_En": $("#company_Info_Submit_Deadline_En").val(),
							"participant_List_Submit_Deadline_Zh": $("#participant_List_Submit_Deadline_Zh").val(),
							"participant_List_Submit_Deadline_En": $("#participant_List_Submit_Deadline_En").val(),
							"invoice_Information_Submit_Deadline_Zh": $("#invoice_Information_Submit_Deadline_Zh").val(),
							"invoice_Information_Submit_Deadline_En": $("#invoice_Information_Submit_Deadline_En").val(),
							"advertisement_Submit_Deadline_Zh": $("#advertisement_Submit_Deadline_Zh").val(),
							"advertisement_Submit_Deadline_En": $("#advertisement_Submit_Deadline_En").val(),
							"company_Info_Insert_Submit_Deadline_Zh": $("#company_Info_Insert_Submit_Deadline_Zh").val(),
							"company_Info_Insert_Submit_Deadline_En": $("#company_Info_Insert_Submit_Deadline_En").val(),
							"stone_fair_end_year": $("#stone_fair_end_year").val(),
							"stone_fair_begin_year": $("#stone_fair_begin_year").val(),
							"company_Info_Data_End_Html": $("#company_Info_Data_End_Html").val(),
							"visa_Info_Submit_Deadline_Zh": $("#visa_Info_Submit_Deadline_Zh").val(),
							"visa_Info_Submit_Deadline_En": $("#visa_Info_Submit_Deadline_En").val(),
							"stone_Fair_Show_Date_Zh": $("#stone_Fair_Show_Date_Zh").val(),
							"stone_Fair_Show_Date_En": $("#stone_Fair_Show_Date_En").val(),
							"exhibitor_end_time":$("#exhibitor_end_time").val(),
							"exhibitor_area": 1},
						success: function (data) {
							if (data.resultCode == 1) {
								$.messager.alert('错误', '更新时间参数失败错误');
							} else if (data.resultCode > 1) {
								$.messager.alert('错误', '服务器错误');
							} else {
								queryExhibitorTimeData();
								$.messager.show({
									title: '成功',
									msg: '修改时间参数成功',
									timeout: 2000,
									showType: 'slide'
								});
							}
						}
					});
				}
			});
		});
		$("#saveSwitchData").click(function () {
			var value;
			if($(":checkbox[name='menu_move_switch']").is(':checked')){
				value = 1;
			} else{
				value = 0;
			}
			$.messager.confirm('确认修改','你确定要设置菜单移动属性?',function(r){
				if (r){
					$.ajax({
						url: "${base}/user/modifyExhibitorMenuMove",
						type: "post",
						dataType: "json",
						traditional: true,
						data: {"menu_move_switch": value, "exhibitor_area": 1},
						success: function (data) {
							if (data.resultCode == 1) {
								$.messager.alert('错误', '更新菜单移动属性失败错误');
							} else if (data.resultCode > 1) {
								$.messager.alert('错误', '服务器错误');
							} else {
								queryExhibitorTimeData();
								$.messager.show({
									title: '成功',
									msg: '修改菜单移动属性成功',
									timeout: 2000,
									showType: 'slide'
								});
							}
						}
					});
				}
			});
		});
		$("#saveDataForeign").click(function () {
			$.messager.confirm('确认修改','你确定要修改时间参数?',function(r){
				if (r){
					$.ajax({
						url: "${base}/user/modifyExhibitorTime",
						type: "post",
						dataType: "json",
						traditional: true,
						data: {"company_Info_Submit_Deadline_Zh": $("#company_Info_Submit_Deadline_Zh_Foreign").val(),
							"company_Info_Submit_Deadline_En": $("#company_Info_Submit_Deadline_En_Foreign").val(),
							"participant_List_Submit_Deadline_Zh": $("#participant_List_Submit_Deadline_Zh_Foreign").val(),
							"participant_List_Submit_Deadline_En": $("#participant_List_Submit_Deadline_En_Foreign").val(),
							"invoice_Information_Submit_Deadline_Zh": $("#invoice_Information_Submit_Deadline_Zh_Foreign").val(),
							"invoice_Information_Submit_Deadline_En": $("#invoice_Information_Submit_Deadline_En_Foreign").val(),
							"advertisement_Submit_Deadline_Zh": $("#advertisement_Submit_Deadline_Zh_Foreign").val(),
							"advertisement_Submit_Deadline_En": $("#advertisement_Submit_Deadline_En_Foreign").val(),
							"company_Info_Insert_Submit_Deadline_Zh": $("#company_Info_Insert_Submit_Deadline_Zh_Foreign").val(),
							"company_Info_Insert_Submit_Deadline_En": $("#company_Info_Insert_Submit_Deadline_En_Foreign").val(),
							"stone_fair_end_year": $("#stone_fair_end_year_Foreign").val(),
							"stone_fair_begin_year": $("#stone_fair_begin_year_Foreign").val(),
							"company_Info_Data_End_Html": $("#company_Info_Data_End_Html_Foreign").val(),
							"visa_Info_Submit_Deadline_Zh": $("#visa_Info_Submit_Deadline_Zh_Foreign").val(),
							"visa_Info_Submit_Deadline_En": $("#visa_Info_Submit_Deadline_En_Foreign").val(),
							"stone_Fair_Show_Date_Zh": $("#stone_Fair_Show_Date_Zh_Foreign").val(),
							"stone_Fair_Show_Date_En": $("#stone_Fair_Show_Date_En_Foreign").val(),
							"exhibitor_end_time":$("#exhibitor_end_time").val(),
							"exhibitor_area": 2},
						success: function (data) {
							if (data.resultCode == 1) {
								$.messager.alert('错误', '更新时间参数失败错误');
							} else if (data.resultCode > 1) {
								$.messager.alert('错误', '服务器错误');
							} else {
								queryExhibitorTimeData();
								$.messager.show({
									title: '成功',
									msg: '修改时间参数成功',
									timeout: 2000,
									showType: 'slide'
								});
							}
						}
					});
				}
			});
		});
		$("#saveSwitchDataForeign").click(function () {
			var value;
			if($(":checkbox[name='menu_move_switch_foreign']").is(':checked')){
				value = 1;
			} else{
				value = 0;
			}
			$.messager.confirm('确认修改','你确定要设置菜单移动属性?',function(r){
				if (r){
					$.ajax({
						url: "${base}/user/modifyExhibitorMenuMove",
						type: "post",
						dataType: "json",
						traditional: true,
						data: {"menu_move_switch_foreign": value, "exhibitor_area": 2},
						success: function (data) {
							if (data.resultCode == 1) {
								$.messager.alert('错误', '更新菜单移动属性失败错误');
							} else if (data.resultCode > 1) {
								$.messager.alert('错误', '服务器错误');
							} else {
								queryExhibitorTimeData();
								$.messager.show({
									title: '成功',
									msg: '修改菜单移动属性成功',
									timeout: 2000,
									showType: 'slide'
								});
							}
						}
					});
				}
			});
		});
	});
</script>
</body>
</html>