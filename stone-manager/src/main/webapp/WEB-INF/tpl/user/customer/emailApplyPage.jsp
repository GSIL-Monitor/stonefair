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
<!-- 邮件申请列表 -->
<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<div title="邮件申请列表" style="padding:5px">
		<table id="emailApply" data-options="url:'${base}/user/loadEmailApplyByPage',
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
				<th data-options="field: 'customerID', width: $(this).width() / 7">
					客商ID<br/>
					<input id="CustomerID" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'createTime', formatter:formatDatebox, width: $(this).width() / 7">
					申请时间<br/>
					<input id="CreateTime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'createIP', width: $(this).width() / 7">
					所在IP<br/>
					<input id="CreateIP" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'confirmTime', formatter:formatDatebox, width: $(this).width() / 7">
					确认时间<br/>
					<input id="ConfirmTime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'confirmIP', width: $(this).width() / 7">
					确认IP<br/>
					<input id="ConfirmIP" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'status', width: $(this).width() / 7">
					状态<br/>
					<input id="Status" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'admin', width: $(this).width() / 7">
					管理员<br/>
					<input id="Admin" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
			</tr>
			</thead>
		</table>
	</div>
</div>
<!-- 工具栏 -->
<div id="customerbar">
	<div style="display:inline-block;">
		<div class="easyui-menubutton" menu="#email" iconCls="icon-redo">邮件</div>
	</div>
	<div id="email" style="width:180px;">
		<div id="emailAllCustomers" iconCls="icon-redo">群发所有邮件</div>
		<div id="emailSelectedCustomers" iconCls="icon-redo">群发所选邮件</div>
	</div>
</div>

<script>
	var checkedItems = [];

	//----------------------------------------------------工具栏函数开始--------------------------------------------------------//
	//群发所有客商邮件
	$('#emailAllCustomers').click(function(){
		$.messager.confirm('确认删除','你确定要群发所有客商邮件吗?',function(r){
			if (r){
				$.ajax({
					url: "${base}/user/emailAllInlandStoneCustomers",
					type: "post",
					data: {"cids": "-2"},
					dataType: "json",
					beforeSend:function(XMLHttpRequest){
						$.messager.show({
							title: '处理中...',
							msg: '正在群发邮件，请稍等...',
							timeout: 10000,
							showType: 'slide'
						});
					},
					success: function (data) {
						if (data.resultCode == 0) {
							$.messager.show({
								title: '成功',
								msg: '群发邮件成功',
								timeout: 5000,
								showType: 'slide'
							});
							$("#emailApply").datagrid("reload");
						} else {
							$.messager.alert('错误', '系统错误');
						}
					}
				});
			}
		});
	});
	//群发所选客商邮件
	$('#emailSelectedCustomers').click(function(){
		if(checkedItems.length > 0){
			$.messager.confirm('确认删除','你确定要群发所选客商邮件吗?',function(r){
				if (r){
					$.ajax({
						url: "${base}/user/emailAllInlandStoneCustomers",
						type: "post",
						data: {"cids": checkedItems},
						dataType: "json",
						traditional: true,
						beforeSend:function(XMLHttpRequest){
							$.messager.show({
								title: '处理中...',
								msg: '正在群发邮件，请稍等...',
								timeout: 10000,
								showType: 'slide'
							});
						},
						success: function (data) {
							if (data.resultCode == 0) {
								$.messager.show({
									title: '成功',
									msg: '群发所选客商邮件成功',
									timeout: 5000,
									showType: 'slide'
								});
								$("#emailApply").datagrid("reload");
							} else {
								$.messager.alert('错误', '系统错误');
							}
						}
					});
				}
			});
		}else{
			$.messager.alert('提示', '请至少选择一项客商再操作');
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

	Date.prototype.format = function (format)
	{
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
	}

	function filter(){
		var filterParm = "?";
		if(document.getElementById("CustomerID").value != ""){
			filterParm += '&CustomerID=' + encodeURI(document.getElementById("CustomerID").value);
		}
		if(document.getElementById("CreateTime").value != ""){
			filterParm += '&CreateTime=' + encodeURI(document.getElementById("CreateTime").value);
		}
		if(document.getElementById("CreateIP").value != ""){
			filterParm += '&CreateIP=' + encodeURI(document.getElementById("CreateIP").value);
		}
		if(document.getElementById("ConfirmTime").value != ""){
			filterParm += '&ConfirmTime=' + encodeURI(document.getElementById("ConfirmTime").value);
		}
		if(document.getElementById("ConfirmIP").value != ""){
			filterParm += '&ConfirmIP=' + encodeURI(document.getElementById("ConfirmIP").value);
		}
		if(document.getElementById("Status").value != ""){
			filterParm += '&Status=' + encodeURI(document.getElementById("Status").value);
		}
		if(document.getElementById("Admin").value != ""){
			filterParm += '&Admin=' + encodeURI(document.getElementById("Admin").value);
		}
		$('#emailApply').datagrid('options').url = '${base}/user/queryEmailApplyByPage' + filterParm;
		$('#emailApply').datagrid('reload');
	}
	//----------------------------------------------------自定义函数结束--------------------------------------------------------//

	$(document).ready(function () {
		// 邮件申请列表渲染
		$('#emailApply').datagrid({
			onSelect:function (rowIndex, rowData){
				var row = $('#emailApply').datagrid('getSelections');
				for (var i = 0; i < row.length; i++) {
					if (findCheckedItem(row[i]['customerID']) == -1) {
						checkedItems.push(row[i]['customerID']);
					}
				}
			},
			onUnselect:function (rowIndex, rowData){
				var k = findCheckedItem(rowData['customerID']);
				if (k != -1) {
					checkedItems.splice(k, 1);
				}
			},
			onSelectAll:function (rows){
				for (var i = 0; i < rows.length; i++) {
					var k = findCheckedItem(rows[i]['customerID']);
					if (k == -1) {
						checkedItems.push(rows[i]['customerID']);
					}
				}
			},
			onUnselectAll:function (rows){
				for (var i = 0; i < rows.length; i++) {
					var k = findCheckedItem(rows[i]['customerID']);
					if (k != -1) {
						checkedItems.splice(k, 1);
					}
				}
			},
			onDblClickRow: function (index, field, value) {
				$.ajax({
					url: "${base}/user/getCompanyByCustomerId",
					type: "post",
					data: {"id": field['customerID']},
					dataType: "json",
					traditional: true,
					success: function (data) {
						if (data.resultCode == 0) {
							var titleValue = data.description;
							if(titleValue != ""){
								if (!$("#tabs").tabs("exists", titleValue)) {
									$('#tabs').tabs('add', {
										title: titleValue,
										content:'<iframe frameborder="0" src="'+ "${base}/user/directToCustomerInfo?id=" + field['customerID']+'" style="width:100%;height:99%;"></iframe>',
										closable: true
									});
								} else {
									$("#tabs").tabs("select", field.company);
								}
							}
						}
					}
				});
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