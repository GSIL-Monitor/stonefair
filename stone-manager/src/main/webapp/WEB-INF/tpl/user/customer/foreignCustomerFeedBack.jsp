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
		#bg{ display: none; position: absolute; top: 0%; left: 0%; width: 50%; height: 50%; background-color: black; z-index:1001; -moz-opacity: 0.2; opacity:.2; filter: alpha(opacity=50);}
		.loading{display: none; position: absolute; top: 50%; left: 50%; z-index:1002; }
	</style>
</head>
<body>
<!-- 境外客商问卷调查 -->
<div id="foreignCustomerFeedBackTab" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<div title="境外客商问卷调查" style="padding:5px">
		<table id="foreignCustomerFeedBackTabel" data-options="url:'${base}/user/queryForeignCustomerFeedBackByPage',
         						   loadMsg: '数据加载中......',
						           singleSelect:false,	//只能当行选择：关闭
						           fit:true,
						           fitColumns:true,
						           idField:'id',
						           remoteSort:true,
								   toolbar:'#customerbar',
						           rownumbers: 'true',
						           pagination:'true',
						           pageSize:'20'">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field: 'userName', width: $(this).width() / 7">
					姓名<br/>
					<input id="customerUserName" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'q1', width: $(this).width() / 7">
					问题一<br/>
					<input id="customerQ1" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'a1', width: $(this).width() / 7">
					答案一<br/>
					<input id="customerA1" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'q2', width: $(this).width() / 7">
					问题二<br/>
					<input id="customerQ2" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'a2', width: $(this).width() / 7">
					答案二<br/>
					<input id="customerA2" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'q3', width: $(this).width() / 7">
					问题三<br/>
					<input id="customerQ3" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'a3', formatter:formatDatebox, width: $(this).width() / 7">
					答案三<br/>
					<input id="customerA3" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'createTime', formatter:formatDatebox, width: $(this).width() / 7">
					创建时间<br/>
					<input id="customerCreateTime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'readed', formatter: formatStatus, width: $(this).width() / 7">
					<span id="customerIsRead">状态</span><br/>
					<select id="customerIsReadStatus" style="width:104%;height:21px;" onchange="filter();">
						<option selected value="">全部</option>
						<option value="0">未读</option>
						<option value="1">已读</option>
					</select>
				</th>
			</tr>
			</thead>
		</table>
	</div>
</div>
<!-- 导出境外客商问卷调查到Excel -->
<form id="exportForeignCustomerFeedBackToExcel" action="${base}/user/exportForeignCustomerFeedBackToExcel" method="post">
	<div id="cidParm1"></div>
</form>
<!-- 工具栏 -->
<div id="customerbar">
	<div style="display:inline-block;">
		<div class="easyui-menubutton" menu="#export" iconCls="icon-redo">导出</div>
	</div>
	<div id="export" style="width:180px;">
		<div id="exportAllForeignCustomerFeedBackData" iconCls="icon-redo">所有境外客商问卷调查到Excel</div>
		<div id="exportSelectedForeignCustomerFeedBackData" iconCls="icon-redo">所选境外客商问卷调查到Excel</div>
	</div>
</div>

<div class="loading"><img src="${base}/resource/load.gif"></div>
<script>
	var checkedItems = [];
	//----------------------------------------------------工具栏函数开始--------------------------------------------------------//
	//导出所有境外客商问卷调查信息到Excel
	$('#exportAllForeignCustomerFeedBackData').click(function(){
		cidParm1.innerHTML = "";
		var node = "<input type='hidden' name='cids' value='-1'/>";
		cidParm1.innerHTML += node;
		document.getElementById("exportForeignCustomerFeedBackToExcel").submit();
		$.messager.alert('提示', '导出所有境外客商问卷调查成功');
	});
	//导出所选境外客商问卷调查信息到Excel
	$('#exportSelectedForeignCustomerFeedBackData').click(function(){
		cidParm1.innerHTML = "";
		if(checkedItems.length > 0){
			for (var i = 0; i < checkedItems.length; i++) {
				var node = "<input type='hidden' name='cids' value='"+checkedItems[i]+"'/>";
				cidParm1.innerHTML += node;
			}
			document.getElementById("exportForeignCustomerFeedBackToExcel").submit();
			$.messager.alert('提示', '导出所选境外客商问卷调查成功');
		}else{
			$.messager.alert('提示', '请至少选择一项境外客商问卷再导出');
		}
	});
	//----------------------------------------------------工具栏函数结束--------------------------------------------------------//
	//----------------------------------------------------自定义函数开始--------------------------------------------------------//
	//日期时间格式转换
	function formatDatebox(value) {
		if (value == null || value == '') {
			return '';
		}
		var dt;
		if (value instanceof Date) {
			dt = value;
		}
		else {
			dt = new Date(value);
			if (isNaN(dt)) {
				value = value.replace(/\/Date\((-?\d+)\)\//, '$1'); //标红的这段是关键代码，将那个长字符串的日期值转换成正常的JS日期格式
				dt = new Date();
				dt.setTime(value);
			}
		}
		return dt.format("yyyy-MM-dd h:m");   //这里用到一个javascript的Date类型的拓展方法，这个是自己添加的拓展方法，在后面的步骤3定义
	}

	function formatStatus(val, row) {
		if (val) {
			return '已读';
		} else {
			return '未读';
		}
	}

	Date.prototype.format = function (format) {
		var o = {
			"M+": this.getMonth() + 1, //month
			"d+": this.getDate(),    //day
			"h+": this.getHours(),   //hour
			"m+": this.getMinutes(), //minute
			"s+": this.getSeconds(), //second
			"q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
			"S": this.getMilliseconds() //millisecond
		}
		if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
				(this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o) if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1,
							RegExp.$1.length == 1 ? o[k] :
							("00" + o[k]).substr(("" + o[k]).length));
		return format;
	};

	function filter(){
		var filterParm = "?";
		if(document.getElementById("customerUserName").value != ""){
			filterParm += '&userName=' + encodeURI(document.getElementById("customerUserName").value);
		}
		if(document.getElementById("customerQ1").value != ""){
			filterParm += '&q1=' + encodeURI(document.getElementById("customerQ1").value);
		}
		if(document.getElementById("customerA1").value != ""){
			filterParm += '&a1=' + encodeURI(document.getElementById("customerA1").value);
		}
		if(document.getElementById("customerQ2").value != ""){
			filterParm += '&q2=' + encodeURI(document.getElementById("customerQ2").value);
		}
		if(document.getElementById("customerA2").value != ""){
			filterParm += '&a2=' + encodeURI(document.getElementById("customerA2").value);
		}
		if(document.getElementById("customerQ3").value != ""){
			filterParm += '&q3=' + encodeURI(document.getElementById("customerQ3").value);
		}
		if(document.getElementById("customerA3").value != ""){
			filterParm += '&a3=' + encodeURI(document.getElementById("customerA3").value);
		}
		if(document.getElementById("customerCreateTime").value != ""){
			filterParm += '&createTime=' + document.getElementById("customerCreateTime").value;
		}
		if(document.getElementById("customerIsReadStatus").value != ""){
			filterParm += '&readed=' + document.getElementById("customerIsReadStatus").value;
		}
		$('#foreignCustomerFeedBackTabel').datagrid('options').url = '${base}/user/queryForeignCustomerFeedBackByPage' + filterParm;
		$('#foreignCustomerFeedBackTabel').datagrid('reload');
	}
	//----------------------------------------------------自定义函数结束--------------------------------------------------------//
	$(document).ready(function () {
		// 境外客商问卷调查列表渲染
		$('#foreignCustomerFeedBackTab').tabs({
			onClose: function(title,index){
				filter();
				return false;
			}
		});

		$('#foreignCustomerFeedBackTabel').datagrid({
			onSelect:function (rowIndex, rowData){
				var row = $('#foreignCustomerFeedBackTabel').datagrid('getSelections');
				for (var i = 0; i < row.length; i++) {
					if (findCheckedItem(row[i].id) == -1) {
						checkedItems.push(row[i].id);
					}
				}
			},
			onUnselect:function (rowIndex, rowData){
				var k = findCheckedItem(rowData.id);
				if (k != -1) {
					checkedItems.splice(k, 1);
				}
			},
			onSelectAll:function (rows){
				for (var i = 0; i < rows.length; i++) {
					var k = findCheckedItem(rows[i].id);
					if (k == -1) {
						checkedItems.push(rows[i].id);
					}
				}
			},
			onUnselectAll:function (rows){
				for (var i = 0; i < rows.length; i++) {
					var k = findCheckedItem(rows[i].id);
					if (k != -1) {
						checkedItems.splice(k, 1);
					}
				}
			},
			onDblClickRow: function (index, field, value) {
				if(field.userName != ""){
					if (!$("#foreignCustomerFeedBackTab").tabs("exists", field.userName)) {
						$('#foreignCustomerFeedBackTab').tabs('add', {
							title: field.userName,
							content:'<iframe frameborder="0" src="'+ "${base}/user/directToForeignCustomerFeedBackInfo?id=" + field.id+'" style="width:100%;height:99%;"></iframe>',
							closable: true
						});
					} else {
						$("#foreignCustomerFeedBackTab").tabs("select", field.userName);
					}
				}
			}
		}).datagrid('getPager').pagination({
			pageSize: 20,//每页显示的记录条数，默认为10
			pageList: [10,20,30,40,50],//可以设置每页记录条数的列表
			beforePageText: '第',//页数文本框前显示的汉字
			afterPageText: '页    共 {pages} 页',
			displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
		function findCheckedItem(id) {
			for (var i = 0; i < checkedItems.length; i++) {
				if (checkedItems[i] == id) return i;
			}
			return -1;
		}
	});
</script>
</body>
</html>