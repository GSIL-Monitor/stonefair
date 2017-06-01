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
	</style>
</head>
<body>
<!-- 展位预向列表 -->
<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<div title="展位预向列表" style="padding:5px">
		<table id="boothIntetionList" data-options="url:'${base}/user/queryXicecMapIntetionByPage',
         						   loadMsg: '数据加载中......',
						           singleSelect:false,	//只能当行选择：关闭
						           fit:true,
						           fitColumns:true,
						           idField:'id',
						           remoteSort:true,
						           toolbar:boothIntetionbar,
						           rownumbers: 'true',
						           pagination:'true',
						           pageSize:'20'">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field: 'booth_num', width: $(this).width() / 7">
					展位<br/>
					<input id="booth_num_intetion" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'tag', formatter: formatTag, width: $(this).width() / 7">
					标签<br/>
					<select id="exhibitorsTag" style="width:100%;height:21px;" onchange="filter(this.options[this.options.selectedIndex].value);">
					</select>
				</th>
			</tr>
			</thead>
		</table>
	</div>
</div>

<!-- 添加展位预向表单 -->
<div id="addBoothIntetionDlg" data-options="iconCls:'icon-add',modal:true">
	<form id="addBoothIntetionForm">
		<table style="width: 320px;margin: 20px auto">
			<tr>
				<td style="width: 90px;text-align: center">展位：</td>
				<td><input class="easyui-validatebox" type="text" name="booth_num" data-options="required:true"></td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: center">所属人：</td>
				<td><select name="tag" id="addBoothIntetionTag" style="width:100%;height:21px;" data-options="required:true"></select></td>
			</tr>
		</table>
	</form>
</div>

<!-- 修改展位预向表单 -->
<div id="modifyBoothIntetionDlg" data-options="iconCls:'icon-add',modal:true">
	<form id="modifyBoothIntetionForm" name="modifyBoothIntetionForm">
		<table style="width: 320px;margin: 20px auto">
			<tr>
				<td style="width: 90px;text-align: center">展位：</td>
				<td><input class="easyui-validatebox" type="text" name="booth_num" data-options="required:true"></td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: center">所属人：</td>
				<td><select name="tag" id="modifyBoothIntetionTag" style="width:90%;height:21px;" data-options="required:true"></select></td>
			</tr>
		</table>
		<input type="hidden" value="" name="id" />
	</form>
</div>
<script>
	var checkedItems = [];
	var tags = {};
	function filter(){
		var filterParm = "?";
		if(document.getElementById("booth_num_intetion").value != ""){
			filterParm += '&booth_num=' + encodeURI(document.getElementById("booth_num_intetion").value);
		}
		if(document.getElementById("exhibitorsTag").value != ""){
			filterParm += '&tag=' + document.getElementById("exhibitorsTag").value;
		}
		$('#boothIntetionList').datagrid('options').url = '${base}/user/queryXicecMapIntetionByPage' + filterParm;
		$('#boothIntetionList').datagrid('reload');
	}

	//----------------------------------------------------自定义函数结束--------------------------------------------------------//
	var boothIntetionbar = [
		{
			text: '添加展位预向记录',
			iconCls: 'icon-add',
			handler: function () {
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"${base}/user/queryTags?rows=50",
					success : function(result) {
						if(result){
							$("#addBoothIntetionTag").html('');
							$("#addBoothIntetionTag").append('<option value="">全部</option>');
							for(var i=0,a;a=result.rows[i++];){
								$("#addBoothIntetionTag").append('<option value="'+a.id+'">'+a.name+'</option>');
							}
						}
					}
				});
				$("#addBoothIntetionDlg").dialog("open");
			}
		}
	];

	$(document).ready(function () {
		//加载所属人列表
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"${base}/user/queryTags?rows=50",
			success : function(result) {
				if(result){
					$("#exhibitorsTag").html('');
					$("#exhibitorsTag").append('<option value="">全部</option>');
					for(var i=0,a;a=result.rows[i++];){
						tags[a.id] = a.name;
						$("#exhibitorsTag").append('<option value="'+a.id+'">'+a.name+'</option>');
					}
				}
			}
		});

		// 国内客商列表渲染
		$('#boothIntetionList').datagrid({
			onSelect:function (rowIndex, rowData){
				var row = $('#boothIntetionList').datagrid('getSelections');
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
				$.ajax({
					type:"POST",
					dataType:"json",
					url:"${base}/user/queryTags?rows=50",
					success : function(result) {
						if(result){
							$("#modifyBoothIntetionTag").html('');
							$("#modifyBoothIntetionTag").append('<option value="">全部</option>');
							for(var i=0,a;a=result.rows[i++];){
								$("#modifyBoothIntetionTag").append('<option value="'+a.id+'">'+a.name+'</option>');
							}
						}
					}
				});
				document.modifyBoothIntetionForm.id.value = field.id;
				document.modifyBoothIntetionForm.booth_num.value = field.booth_num;
				$("#modifyBoothIntetionDlg").dialog("open");
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

	function formatTag(val, row) {
		if (val != null) {
			return tags[val];
		} else {
			return null;
		}
	}

	// 添加所属人标签
	$('#addBoothIntetionDlg').dialog({
		title: '添加展位预向记录',
		width: 350,
		height: 200,
		closed: true,
		cache: false,
		modal: true,
		buttons: [
			{
				text: '确认添加',
				iconCls: 'icon-ok',
				handler: function () {
					if ($("#addTagForm").form("validate")) {
						$.ajax({
							url: "${base}/user/addBoothIntetion",
							type: "post",
							dataType: "json",
							data: $("#addBoothIntetionForm").serializeJson(),
							success: function (data) {
								if (data.resultCode == 0) {
									$("#boothIntetionList").datagrid("reload");
									$("#addBoothIntetionDlg").dialog("close");
									$.messager.show({
										title: '成功',
										msg: '添加展位预向记录成功',
										timeout: 5000,
										showType: 'slide'
									});
									$("#addBoothIntetionForm").clearForm();
								} else if (data.resultCode == 2) {
									$.messager.alert('错误', '所属人冲突');
								} else {
									$.messager.alert('错误', '系统错误');
								}
							}
						});
					}
				}
			},
			{
				text: '取消',
				handler: function () {
					document.getElementById("addBoothIntetionForm").reset();
					$("#addBoothIntetionDlg").dialog("close");
				}
			}
		]
	});
	$('#modifyBoothIntetionDlg').dialog({
		title: '修改展位预向记录',
		width: 350,
		height: 150,
		closed: true,
		cache: false,
		modal: true,
		buttons: [
			{
				text: '确认修改',
				handler: function () {
					if ($("#modifyTagForm").form("validate")) {
						$.ajax({
							url: "${base}/user/modifyBoothIntetion",
							type: "post",
							dataType: "json",
							data: $("#modifyBoothIntetionForm").serializeJson(),
							success: function (data) {
								if (data.resultCode == 0) {
									$("#boothIntetionList").datagrid("reload");
									$("#modifyBoothIntetionDlg").dialog("close");
									$.messager.show({
										title: '成功',
										msg: '修改展位预向记录成功',
										timeout: 5000,
										showType: 'slide'
									});
								} else {
									$.messager.alert('错误', '系统错误');
								}
							}
						});
					}
				}
			},
			{
				text: '取消',
				handler: function () {
					document.getElementById("modifyBoothIntetionForm").reset();
					$("#modifyBoothIntetionDlg").dialog("close");
				}
			}
		]
	});
</script>
</body>
</html>