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
<!-- 国外客商历史记录列表 -->
<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<div title="客商列表" style="padding:5px">
		<table id="customerHistory" data-options="url:'${base}/user/queryHistoryForeignCustomersByPage',
         						   loadMsg: '数据加载中......',
						           singleSelect:false,	//只能当行选择：关闭
						           fit:true,
						           fitColumns:true,
						           idField:'id',
						           view: emptyView,
						           emptyMsg: '没有记录',
						           remoteSort:true,
								   toolbar:'#customerbar',
						           rownumbers: 'true',
						           pagination:'true',
						           pageSize:'20'">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field: 'owner', formatter: formatTag, width: $(this).width() * 0.1">
					<span id="stag" class="sortable">所属人</span><br/>
					<select id="customerOwner" style="width:100%;height:21px;" onchange="filter(this.options[this.options.selectedIndex].value);">
					</select>
				</th>
				<th data-options="field: 'country', formatter: formatCountry, width: $(this).width() * 0.1">
					<span id="scountry" class="sortable">国家</span><br/>
					<select id="customerCountry" style="width:100%;height:21px;" onchange="filter(this.options[this.options.selectedIndex].value);">
					</select>
				</th>
				<th data-options="field: 'company', width: $(this).width() / 7">
					公司名称<br/>
					<input id="customerCompany" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'contact', width: $(this).width() / 7">
					联系人<br/>
					<input id="customerContact" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'position', width: $(this).width() / 7">
					职位<br/>
					<input id="customerPositon" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'address', width: $(this).width() / 7">
					地址<br/>
					<input id="customerAddress" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'email', width: $(this).width() / 7">
					邮箱<br/>
					<input id="customerEmail" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'website', width: $(this).width() / 7">
					网址<br/>
					<input id="customerWebsite" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'backupAddress', width: $(this).width() / 7">
					备用地址<br/>
					<input id="customerBackupAddress" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'remark', width: $(this).width() / 7">
					备注<br/>
					<input id="customerRemark" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'createtime', formatter:formatDatebox, width: $(this).width() / 7">
					导入时间<br/>
					<input id="customerCreatetime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
			</tr>
			</thead>
		</table>
	</div>
</div>
<!-- 导出历史国外客商到Excel -->
<form id="exportForeignCustomersToExcel" action="${base}/user/exportHistoryForeignCustomersToExcel" method="post">
	<div id="cidParm3"></div>
</form>
<!-- 导入国外历史客商资料 -->
<div id="importForeignCustomerDlg" data-options="iconCls:'icon-add',modal:true">
	<form id="importForeignCustomerForm" name="importForeignCustomerForm">
		<table style="width: 320px;margin: 20px auto">
			<tr>
				<td style="width: 70px;text-align: left">导入模版：</td>
				<td style="width: 90px;text-align: left"><input type="file" name="file" id="file" /></td>
			</tr>
			<tr>
				<td style="width: 80px;text-align: left">强制导入：</td>
				<td style="width: 20px;text-align: left">
					<input id="isForceIncludeFlag" name="isForceIncludeFlag" type="checkbox">
				</td>
			</tr>
			<input type="hidden" value="2" name="inlandOrForeign">
			<input type="hidden" id="isForceIncludeFlagValue" name="isForceIncludeFlagValue">
		</table>
	</form>
</div>
<!-- 工具栏 -->
<div id="customerbar">
	<div style="display:inline-block;">
		<div class="easyui-menubutton" menu="#template" iconCls="icon-redo">模板</div>
	</div>
	<div id="template" style="width:180px;">
		<td colspan="2" style="width: 120px;text-align: center"><a href="${base}/resource/foreigncustomertemplate.xls">下载导入模版</a><br /></td>
	</div>
	<div style="display:inline-block;">
		<div class="easyui-menubutton" menu="#export" iconCls="icon-redo">导出</div>
	</div>
	<div id="export" style="width:180px;">
		<div id="exportAllCustomers" iconCls="icon-redo">所有客商信息到Excel</div>
		<div id="exportSelectedCountryCustomers" iconCls="icon-redo">所选国家的客商信息到Excel</div>
		<div id="exportSelectedCustomers" iconCls="icon-redo">所选客商信息到Excel</div>
		<div class="menu-sep"></div>
		<div id="exportAllTelphone" iconCls="icon-redo">所有客商手机到Excel</div>
		<div id="exportAllEmail" iconCls="icon-redo">所有客商邮箱到Excel</div>
	</div>
	<div style="display:inline-block;">
		<div class="easyui-menubutton" menu="#import" iconCls="icon-redo">导入</div>
	</div>
	<div id="import" style="width:100px;">
		<div id="importInlandCustomer" iconCls="icon-undo">导入国外历史客商资料</div>
	</div>
	<div id="addForeignCustomer" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增客商</div>
	<div id="ignoreForeignCustomer" class="easyui-linkbutton" iconCls="icon-edit" plain="true">删除客商</div>
</div>

<!-- 资料重复提示对话框 -->
<div id="repeatDiv" class="easyui-dialog" iconCls="icon-search" data-options="width: $(this).width() * 0.75, height: $(this).height() * 0.86" closed="true">
	<div style="height: 360px;">
		<table id="willImportTable" title="要导入的资料" class="easyui-datagrid" fitColumns="true" rownumbers="true" fit="true">
		</table>
	</div>
	<div style="height: 360px;">
		<table id="isExistTable" title="已存在的资料" class="easyui-datagrid" fitColumns="true" rownumbers="true" fit="true">
		</table>
	</div>
</div>

<!-- 修改资料对话框 -->
<div id="modifyCustomerInfoDlg" data-options="iconCls:'icon-edit',modal:true">
	<form id="modifyCustomerInfoForm"  name="modifyCustomerInfoForm">
		<table style="width: 380px;margin: 20px auto">
			<tr>
				<td style="width: 90px;text-align: right">国家：</td>
				<td style="width: 90px;text-align: left" data-options="field: 'country', formatter: formatCountry">
					<select id="historyCustomerCountry" style="width:70%" value="" name="country">
					</select>
				</td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: right">公司名：</td>
				<td><input class="easyui-validatebox" type="text" value="" name="company"></td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: right">联系人：</td>
				<td><textarea class="easyui-validatebox" style="width:200px;height:100px;" type="text" value="" name="contact"></textarea></td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: right">职位：</td>
				<td><input class="easyui-validatebox" type="text" value="" name="position"></td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: right">地址：</td>
				<td><input class="easyui-validatebox" style="width:200px;height:100px;" type="text" value="" name="address"></td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: right">邮箱：</td>
				<td><input class="easyui-validatebox" style="width:200px;height:100px;" type="text" value="" name="email"></td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: right">网址：</td>
				<td><input class="easyui-validatebox" type="text" value="" name="website"></td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: right">备用地址：</td>
				<td><input class="easyui-validatebox" type="text" value="" name="backupaddress"></td>
			</tr>
			<tr>
				<td style="width: 90px;text-align: right">备注：</td>
				<td><input class="easyui-validatebox" type="text" value="" name="remark"></td>
			</tr>
			<input type="hidden" value="${id}" name="id">
			<input type="hidden" value="" name="flag">
		</table>
	</form>
</div>
<div class="loading"><img src="${base}/resource/load.gif"></div>
<script type="text/javascript">
	var checkedItems = [];
	var isExistCheckedItems = [];
	var willImportCheckedItems = [];
	var country = [];
	var tags = {};
	var exhibitorsCountryOld = -1;

	//----------------------------------------------------工具栏函数开始--------------------------------------------------------//
	//导出所有客商信息到Excel
	$('#exportAllCustomers').click(function(){
		cidParm3.innerHTML = "";
		var node = "<input type='hidden' name='cids' value='-1'/>";
		var type = "<input type='hidden' name='type' value='0'/>";
		var country = "<input type='hidden' name='country' value='-1'/>";
		cidParm3.innerHTML += node;
		cidParm3.innerHTML += type;
		cidParm3.innerHTML += country;
		document.getElementById("exportForeignCustomersToExcel").submit();
		$.messager.alert('提示', '导出所有客商成功');
	});
	//导出所选国家的客商信息到Excel
	$('#exportSelectedCountryCustomers').click(function(){
		cidParm3.innerHTML = "";
		var node = "<input type='hidden' name='cids' value='-1'/>";
		var type = "<input type='hidden' name='type' value='0'/>";
		var country = "<input type='hidden' name='country' value='"+exhibitorsCountryOld+"'/>";
		cidParm3.innerHTML += node;
		cidParm3.innerHTML += type;
		cidParm3.innerHTML += country;
		document.getElementById("exportForeignCustomersToExcel").submit();
		$.messager.alert('提示', '导出所有客商成功');
	});
	//导出所选客商信息到Excel
	$('#exportSelectedCustomers').click(function(){
//     	alert(checkedItems);
		cidParm3.innerHTML = "";
		if(checkedItems.length > 0){
			for (var i = 0; i < checkedItems.length; i++) {
				var node = "<input type='hidden' name='cids' value='"+checkedItems[i]+"'/>";
				cidParm3.innerHTML += node;
			}
			var type = "<input type='hidden' name='type' value='0'/>";
			cidParm3.innerHTML += type;
			document.getElementById("exportForeignCustomersToExcel").submit();
			$.messager.alert('提示', '导出所选客商成功');
		}else{
			$.messager.alert('提示', '请至少选择一项客商再导出');
		}
	});
	//导出所有客商手机到Excel
	$('#exportAllTelphone').click(function(){
		cidParm3.innerHTML = "";
		var node = "<input type='hidden' name='cids' value='-1'/>";
		var type = "<input type='hidden' name='type' value='1'/>";
		cidParm3.innerHTML += node;
		cidParm3.innerHTML += type;
		document.getElementById("exportForeignCustomersToExcel").submit();
		$.messager.alert('提示', '导出所有客商成功');
	});
	//导出所有客商邮箱到Excel
	$('#exportAllEmail').click(function(){
		cidParm3.innerHTML = "";
		var node = "<input type='hidden' name='cids' value='-1'/>";
		var type = "<input type='hidden' name='type' value='2'/>";
		cidParm3.innerHTML += node;
		cidParm3.innerHTML += type;
		document.getElementById("exportForeignCustomersToExcel").submit();
		$.messager.alert('提示', '导出所有客商成功');
	});
	//导入国外历史客商资料
	$('#importInlandCustomer').click(function(){
		$('#isForceIncludeFlag').attr("checked",false);
		$("#importForeignCustomerDlg").dialog("open");
	});
	// 导入国外历史客商资料弹出框
	$('#importForeignCustomerDlg').dialog({
		title: '导入国外历史客商资料',
		width: 350,
		height: 340,
		closed: true,
		cache: false,
		modal: true,
		buttons: [
			{
				text: '确认添加',
				iconCls: 'icon-ok',
				handler: function () {
					var value;
					if($(":checkbox[name='isForceIncludeFlag']").is(':checked')){
						document.getElementById("isForceIncludeFlagValue").value = 1;
					} else{
						document.getElementById("isForceIncludeFlagValue").value = 0;
					}

					var file = document.getElementById("file").value;
					if(file == null || file == ""){
						$.messager.alert('提示', '请选择所要上传的文件！');
					} else {
						var index = file.lastIndexOf(".");
						if(index < 0) {
							$.messager.alert('提示', '上传的文件格式不正确，请选择97-2003Excel文件(*.xls)！');
						} else {
							var ext = file.substring(index + 1, file.length);
							if(ext != "xls") {
								$.messager.alert('提示', '上传的文件格式不正确，请选择97-2003Excel文件(*.xls)！');
							} else {
								$("#importForeignCustomerDlg").dialog("close");
								$("#bg,.loading").show();
								$.ajaxFileUpload({
									url:'companyinfo/importCustomer',
									dataType:'text/html',
									data:$("#importForeignCustomerForm").serializeJson(),
									fileElementId:'file',
									success: function (data){
										$("#bg,.loading").hide();
										filter();
										var json = $.parseJSON(data);
										var count = JSON.parse(json.result);
										$.messager.confirm("导入成功", count, function (data) {
											if (data) {
												if(json.resultCode == 1 ){
													$("#repeatDiv").dialog("open").dialog({title: '资料重复',
														buttons: [{text: '不做处理',
															handler: function () {
																$("#repeatDiv").dialog("close");
																$("#customerHistory").datagrid("reload");
															}}]});
													$("#repeatDiv").dialog("open");
													$("#isExistTable").datagrid("reload");
													$("#willImportTable").datagrid("reload");
												}else if(json.resultCode == 0){
													filter();
													$.messager.show({
														title: '成功',
														msg: '导入国内历史客商资料成功',
														timeout: 5000,
														showType: 'slide'
													});
												}else{
													$.messager.alert('错误', "导入国内历史客商资料失败");
												}
											}
										});
									},error: function (data){
										$("#bg,.loading").hide();
										$.messager.alert('错误', "导入国内历史客商资料失败");
									}
								});
							}
						}
					}
				}
			},
			{
				text: '取消',
				handler: function () {
					$("#importForeignCustomerDlg").dialog("close");
					$("#customerHistory").datagrid("reload");
				}
			}
		]
	});

	//新增客商数据
	$('#addForeignCustomer').click(function(){
		document.modifyCustomerInfoForm.flag.value = 3;
		document.modifyCustomerInfoForm.id.value = "";
		document.modifyCustomerInfoForm.country.value = "";
		document.modifyCustomerInfoForm.company.value = "";
		document.modifyCustomerInfoForm.contact.value = "";
		document.modifyCustomerInfoForm.position.value = "";
		document.modifyCustomerInfoForm.address.value = "";
		document.modifyCustomerInfoForm.email.value = "";
		document.modifyCustomerInfoForm.website.value = "";
		document.modifyCustomerInfoForm.backupaddress.value = "";
		document.modifyCustomerInfoForm.remark.value = "";
		$("#modifyCustomerInfoDlg").dialog("open");
	});

	//忽略所选的客商资料
	$('#ignoreForeignCustomer').click(function(){
		if(checkedItems.length > 0){
			$.ajax({
				url:"${base}/user/ignoreHistoryForeignCustomersToExcel",
				type:"POST",
				data:{"cids":checkedItems, "type":2},
				dataType:"json",
				traditional: true,
				success : function(result) {
					if(result.resultCode == 0){
						$.messager.show({
							title: '成功',
							msg: '删除客商资料成功',
							timeout: 5000,
							showType: 'slide'
						});
						$("#customerHistory").datagrid("reload");
					}
				}
			});
		}else{
			$.messager.alert('提示', '请至少选择一项客商再操作');
		}
	});

	// 修改资料弹出框
	$('#modifyCustomerInfoDlg').dialog({
		title: '修改资料',
		width: 400,
		height: 400,
		maximized:false,
		closed: true,
		cache: false,
		modal: true,
		buttons: [
			{
				text: '确认修改',
				handler: function () {
					$.ajax({
						url: "${base}/user/modifyHistoryRepeatCustomerInfo",
						type: "post",
						dataType: "json",
						data: $("#modifyCustomerInfoForm").serializeJson(),
						success: function (data) {
							if (data.resultCode == 0) {
								$("#modifyCustomerInfoDlg").dialog("close");
								$("#isExistTable").datagrid("reload");
								$("#willImportTable").datagrid("reload");
								$("#customerHistory").datagrid("reload");
							} else {
								$.messager.alert('错误', '系统错误');
							}
						}
					});
				}
			},
			{
				text: '取消',
				handler: function () {
					document.getElementById("modifyCustomerInfoForm").reset();
					$("#modifyCustomerInfoDlg").dialog("close");
				}
			}
		]
	});
	//----------------------------------------------------工具栏函数结束--------------------------------------------------------//
	var willImportTagBar = [
		{
			text: '导入',
			iconCls: 'icon-add',
			handler: function () {
				if(willImportCheckedItems.length > 0){
					$.messager.confirm('确认导入','你确定要导入选中的资料?',function(r){
						if (r){
							$.ajax({
								url: "${base}/user/insertHistoryCustomerInfo",
								type: "post",
								dataType: "json",
								data: {"tids": willImportCheckedItems},
								traditional: true,
								success: function (data) {
									if (data.resultCode == 0) {
										$.messager.show({
											title: '成功',
											msg: '导入成功',
											timeout: 5000,
											showType: 'slide'
										});
										$("#willImportTable").datagrid("reload");
										$("#customerHistory").datagrid("reload");
									} else {
										$.messager.alert('错误', '系统错误');
									}
								}
							});
							$.messager.alert('提示', '资料导入成功');
						}
					});
				}else{
					$.messager.alert('提示', '请至少选择一项资料再导入');
				}
			}
		},
		{
			text: '忽略',
			iconCls: 'icon-remove',
			handler: function () {
				if(willImportCheckedItems.length > 0){
					$.messager.confirm('确认忽略','你确定要忽略选中的资料?',function(r){
						if (r){
							$.ajax({
								url: "${base}/user/ignoreHistoryCustomerInfo",
								type: "post",
								dataType: "json",
								data: {"tids": willImportCheckedItems},
								traditional: true,
								success: function (data) {
									if (data.resultCode == 0) {
										$("#willImportTable").datagrid("reload");
										$("#isExistTable").datagrid("reload");
										$("#customerHistory").datagrid("reload");
									} else {
										$.messager.alert('错误', '系统错误');
									}
								}
							});
							$.messager.alert('提示', '资料操作成功');
						}
					});
				}else{
					$.messager.alert('提示', '请至少选择一项资料再导入');
				}
			}
		}
	];

	var isExistTagBar = [
		{
			text: '删除',
			iconCls: 'icon-remove',
			handler: function () {
				if(isExistCheckedItems.length > 0){
					$.messager.confirm('确认删除','你确定要删除选中的资料?',function(r){
						if (r){
							$.ajax({
								url: "${base}/user/deleteHistoryCustomerInfo",
								type: "post",
								dataType: "json",
								data: {"tids": isExistCheckedItems},
								traditional: true,
								success: function (data) {
									if (data.resultCode == 0) {
										$.messager.show({
											title: '成功',
											msg: '删除成功',
											timeout: 5000,
											showType: 'slide'
										});
										$("#isExistTable").datagrid("reload");
										$("#customerHistory").datagrid("reload");
									} else {
										$("#isExistTable").datagrid("reload");
										$("#customerHistory").datagrid("reload");
										$.messager.alert('错误', '您可能没有权限删除选择中的数据');
									}
								}
							});
						}
					});
				}else{
					$.messager.alert('提示', '请至少选择一项资料再删除');
				}
			}
		}
	];

	$("#willImportTable").datagrid({
		url: '${base}/user/showWillImportCustomerInfo',
		singleSelect:false,	//只能当行选择：关闭
		fit:true,
		fitColumns:true,
		toolbar:willImportTagBar,
		rownumbers: true,
		columns: [
			[
				{field: 'ck', checkbox:true },
				{field: 'country', formatter: formatCountry, title: '国家', width: 100},
				{field: 'company', title: '公司', width: 100},
				{field: 'contact', title: '联系人', width: 100},
				{field: 'position', title: '职位', width: 100},
				{field: 'address', title: '地址', width: 100},
				{field: 'email', title: '邮箱', width: 100},
				{field: 'website', title: '网址', width: 100},
				{field: 'backupaddress', title: '备用地址', width: 100},
				{field: 'remark', title: '备注', width: 100},
				{field: 'owner', formatter: formatTag, title: '所属者', width: 100}
			]
		],
		onSelect:function (rowIndex, rowData){
			var row = $('#willImportTable').datagrid('getSelections');
			for (var i = 0; i < row.length; i++) {
				if (findWillImportCheckedItem(row[i].id) == -1) {
					willImportCheckedItems.push(row[i].id);
				}
			}
			refreshIsExistTableData(willImportCheckedItems);
		},
		onUnselect:function (rowIndex, rowData){
			var k = findWillImportCheckedItem(rowData.id);
			if (k != -1) {
				willImportCheckedItems.splice(k, 1);
			}
			refreshIsExistTableData(willImportCheckedItems);
		},
		onSelectAll:function (rows){
			for (var i = 0; i < rows.length; i++) {
				var k = findWillImportCheckedItem(rows[i].id);
				if (k == -1) {
					willImportCheckedItems.push(rows[i].id);
				}
			}
		},
		onUnselectAll:function (rows){
			for (var i = 0; i < rows.length; i++) {
				var k = findWillImportCheckedItem(rows[i].id);
				if (k != -1) {
					willImportCheckedItems.splice(k, 1);
				}
			}
		},
		onDblClickRow: function (index, field, value) {
			//flag=1表示是将要导入的资料
			document.modifyCustomerInfoForm.flag.value = 1;
			document.modifyCustomerInfoForm.id.value = field.id;
			document.modifyCustomerInfoForm.country.value = formatCountry(field.country);
			document.modifyCustomerInfoForm.company.value = field.company;
			document.modifyCustomerInfoForm.contact.value = field.contact;
			document.modifyCustomerInfoForm.position.value = field.position;
			document.modifyCustomerInfoForm.address.value = field.address;
			document.modifyCustomerInfoForm.email.value = field.email;
			document.modifyCustomerInfoForm.website.value = field.website;
			document.modifyCustomerInfoForm.backupaddress.value = field.backupaddress;
			document.modifyCustomerInfoForm.remark.value = field.remark;
			$("#modifyCustomerInfoDlg").dialog("open");
		}
	});
	function findWillImportCheckedItem(id) {
		for (var i = 0; i < willImportCheckedItems.length; i++) {
			if (willImportCheckedItems[i] == id) return i;
		}
		return -1;
	}

	$("#isExistTable").datagrid({
		url: '${base}/user/showIsExistCustomerInfo',
		singleSelect:false,	//只能当行选择：关闭
		fit:true,
		fitColumns:true,
		toolbar:isExistTagBar,
		queryParams: {'willImportCheckedItems': willImportCheckedItems},
		rownumbers: true,
		columns: [
			[
				{field: 'ck', checkbox:true },
				{field: 'country', formatter: formatCountry, title: '国家', width: 100},
				{field: 'company', title: '公司', width: 100},
				{field: 'contact', title: '联系人', width: 100},
				{field: 'position', title: '职位', width: 100},
				{field: 'address', title: '地址', width: 100},
				{field: 'email', title: '邮箱', width: 100},
				{field: 'website', title: '网址', width: 100},
				{field: 'backupaddress', title: '备用地址', width: 100},
				{field: 'remark', title: '备注', width: 100},
				{field: 'owner', formatter: formatTag, title: '所属者', width: 100}
			]
		],
		onSelect:function (rowIndex, rowData){
			var row = $('#isExistTable').datagrid('getSelections');
			for (var i = 0; i < row.length; i++) {
				if (findIsExistCheckedItem(row[i].id) == -1) {
					isExistCheckedItems.push(row[i].id);
				}
			}
		},
		onUnselect:function (rowIndex, rowData){
			var k = findIsExistCheckedItem(rowData.id);
			if (k != -1) {
				isExistCheckedItems.splice(k, 1);
			}
		},
		onSelectAll:function (rows){
			for (var i = 0; i < rows.length; i++) {
				var k = findIsExistCheckedItem(rows[i].id);
				if (k == -1) {
					isExistCheckedItems.push(rows[i].id);
				}
			}
		},
		onUnselectAll:function (rows){
			for (var i = 0; i < rows.length; i++) {
				var k = findIsExistCheckedItem(rows[i].id);
				if (k != -1) {
					isExistCheckedItems.splice(k, 1);
				}
			}
		},
		onDblClickRow: function (index, field, value) {
			//flag=2表示是要导入已经存在资料
			document.modifyCustomerInfoForm.flag.value = 2;
			document.modifyCustomerInfoForm.id.value = field.id;
			document.modifyCustomerInfoForm.country.value = formatCountry(field.country);
			document.modifyCustomerInfoForm.company.value = field.company;
			document.modifyCustomerInfoForm.contact.value = field.contact;
			document.modifyCustomerInfoForm.position.value = field.position;
			document.modifyCustomerInfoForm.address.value = field.address;
			document.modifyCustomerInfoForm.email.value = field.email;
			document.modifyCustomerInfoForm.website.value = field.website;
			document.modifyCustomerInfoForm.backupaddress.value = field.backupaddress;
			document.modifyCustomerInfoForm.remark.value = field.remark;
			$("#modifyCustomerInfoDlg").dialog("open");
		}
	});
	function findIsExistCheckedItem(id) {
		for (var i = 0; i < isExistCheckedItems.length; i++) {
			if (isExistCheckedItems[i] == id) return i;
		}
		return -1;
	}

	function refreshIsExistTableData(checkedItems) {
		//加载可能存在重复的资料
		$.ajax({
			url:"${base}/user/showIsExistCustomerInfo",
			type: "post",
			dataType: "json",
			data: {"willImportCheckedItems": checkedItems},
			traditional: true,
			success : function(result) {
				if(result){
					$('#isExistTable').datagrid('loadData',result);
					//$("#isExistTable").datagrid("reload");
				}
			}
		});
	}
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

	function formatCountry(val, row) {
		if (val != null) {
			if(val == 44) return country[0].chineseName;
			if(val > 0 && val <= 43){
				return country[val].chineseName;
			}else if(val > 43 && val <= 240){
				return country[val - 1].chineseName;
			}else{
				return null;
			}
		} else {
			return null;
		}
	}

	function formatTag(val, row) {
		if (val != null) {
			return tags[val];
		} else {
			return null;
		}
	}

	function filter(){
		var filterParm = "?";
		if(document.getElementById("customerCountry").value != ""){
			filterParm += '&country=' + encodeURI(document.getElementById("customerCountry").value);
		}
		if(document.getElementById("customerCompany").value != ""){
			filterParm += '&company=' + encodeURI(document.getElementById("customerCompany").value);
		}
		if(document.getElementById("customerContact").value != ""){
			filterParm += '&contact=' + encodeURI(document.getElementById("customerContact").value);
		}
		if(document.getElementById("customerPositon").value != ""){
			filterParm += '&position=' + encodeURI(document.getElementById("customerPositon").value);
		}
		if(document.getElementById("customerAddress").value != ""){
			filterParm += '&address=' + encodeURI(document.getElementById("customerAddress").value);
		}
		if(document.getElementById("customerEmail").value != ""){
			filterParm += '&email=' + encodeURI(document.getElementById("customerEmail").value);
		}
		if(document.getElementById("customerWebsite").value != ""){
			filterParm += '&website=' + encodeURI(document.getElementById("customerWebsite").value);
		}
		if(document.getElementById("customerBackupAddress").value != ""){
			filterParm += '&backupAddress=' + encodeURI(document.getElementById("customerBackupAddress").value);
		}
		if(document.getElementById("customerRemark").value != ""){
			filterParm += '&remark=' + encodeURI(document.getElementById("customerRemark").value);
		}
		if(document.getElementById("customerCreatetime").value != ""){
			filterParm += '&createtime=' + encodeURI(document.getElementById("customerCreatetime").value);
		}
		filterParm += '&inlandOrForeign=2';
		$('#customerHistory').datagrid('options').url = '${base}/user/queryHistoryForeignCustomersByPage' + filterParm;
		$('#customerHistory').datagrid('reload');
	}

	function filter(countryId){
		var filterParm = "?";
		if(document.getElementById("customerOwner").value != ""){
			filterParm += '&owner=' + document.getElementById("customerOwner").value;
		}
		if(document.getElementById("customerCountry").value != ""){
			if(document.getElementById("customerCountry").value != exhibitorsCountryOld){
				filterParm += '&country=' + encodeURI(document.getElementById("customerCountry").value);
				exhibitorsCountryOld = document.getElementById("customerCountry").value;
			}
		}
		if(document.getElementById("customerCompany").value != ""){
			filterParm += '&company=' + encodeURI(document.getElementById("customerCompany").value);
		}
		if(document.getElementById("customerContact").value != ""){
			filterParm += '&contact=' + encodeURI(document.getElementById("customerContact").value);
		}
		if(document.getElementById("customerPositon").value != ""){
			filterParm += '&position=' + encodeURI(document.getElementById("customerPositon").value);
		}
		if(document.getElementById("customerAddress").value != ""){
			filterParm += '&address=' + encodeURI(document.getElementById("customerAddress").value);
		}
		if(document.getElementById("customerEmail").value != ""){
			filterParm += '&email=' + encodeURI(document.getElementById("customerEmail").value);
		}
		if(document.getElementById("customerWebsite").value != ""){
			filterParm += '&website=' + encodeURI(document.getElementById("customerWebsite").value);
		}
		if(document.getElementById("customerBackupAddress").value != ""){
			filterParm += '&backupAddress=' + encodeURI(document.getElementById("customerBackupAddress").value);
		}
		if(document.getElementById("customerRemark").value != ""){
			filterParm += '&remark=' + encodeURI(document.getElementById("customerRemark").value);
		}
		if(document.getElementById("customerCreatetime").value != ""){
			filterParm += '&createtime=' + encodeURI(document.getElementById("customerCreatetime").value);
		}
		filterParm += '&inlandOrForeign=2';
		$('#customerHistory').datagrid('options').url = '${base}/user/queryHistoryForeignCustomersByPage' + filterParm;
		$('#customerHistory').datagrid('reload');
	}
	//----------------------------------------------------自定义函数结束--------------------------------------------------------//

	$(document).ready(function () {
		//加载国家列表
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"${base}/user/queryAllCountry",
			success : function(result) {
				if(result){
					country = result;
					$("#historyCustomerCountry").html('');
					$("#historyCustomerCountry").append('<option value="">全部</option>');
					for(var i=0,a;a=country[i++];){
						$("#historyCustomerCountry").append('<option value="'+a.id+'">'+a.countryValue+a.chineseName+'</option>');
					}
				}
			}
		});

		//加载全部所属人列表
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

		//加载资料所属人列表
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"${base}/user/loadInfoOwnerList",
			success : function(result) {
				if(result){
					$("#customerOwner").html('');
					$("#customerOwner").append('<option value="">全部</option>');
					for(var i=0,a;a=result.rows[i++];){
						tags[a.id] = a.name;
						$("#customerOwner").append('<option value="'+a.id+'">'+a.name+'</option>');
					}
				}
			}
		});

		// 国内客商列表渲染
		$('#customerHistory').datagrid({
			onSelect:function (rowIndex, rowData){
				var row = $('#customerHistory').datagrid('getSelections');
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
				if(field.company != ""){
					if (!$("#tabs").tabs("exists", field.company)) {
						$('#tabs').tabs('add', {
							title: field.company,
							content:'<iframe frameborder="0" src="'+ "${base}/user/directToHistoryCustomerInfo?id=" + field.id + "&inOrOut=2" + '" style="width:100%;height:99%;"></iframe>',
							closable: true
						});
					} else {
						$("#tabs").tabs("select", field.company);
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