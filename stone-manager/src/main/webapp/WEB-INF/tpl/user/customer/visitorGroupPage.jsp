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
<!-- 客商列表 -->
<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<div title="参观团列表" style="padding:5px">
		<table id="visitorGroup" data-options="url:'${base}/user/queryVisitorGroupByPage',
         						   loadMsg: '数据加载中......',
						           singleSelect:false,	//只能当行选择：关闭
						           fit:true,
						           fitColumns:true,
								   toolbar:'#customerbar',
						           idField:'leaderID',
						           remoteSort:true,
						           rownumbers: 'true',
						           pagination:'true',
						           pageSize:'20'">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field: 'groupName', width: $(this).width() / 7">
					参观团名称<br/>
					<input id="groupName" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'memberName', width: $(this).width() / 7">
					团长姓名<br/>
					<input id="groupHeaderName" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'mobile', width: $(this).width() / 7">
					团长手机<br/>
					<input id="groupHeaderTelphone" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'position', width: $(this).width() / 7">
					团长职位<br/>
					<input id="groupHeaderPosition" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'email', width: $(this).width() / 7">
					团长邮箱<br/>
					<input id="groupHeaderEmail" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'address', width: $(this).width() / 7">
					团长地址<br/>
					<input id="groupHeaderAddress" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'createTime', formatter:formatDatebox, width: $(this).width() / 7">
					注册时间<br/>
					<input id="groupHeaderCreateTime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'updateTime', formatter:formatDatebox, width: $(this).width() / 8">
					修改时间<br/>
					<input id="groupHeaderUpdateTime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'isDisabled', formatter: formatStatus, width: $(this).width() / 7">
					<span id="sisProfessional" class="sortable">状态</span><br/>
					<select id="groupIsDisabled" style="width:104%;height:21px;" onchange="filter();">
						<option selected value="">全部</option>
						<option value="0">激活</option>
						<option value="1">注销</option>
					</select>
				</th>
			</tr>
			</thead>
		</table>
	</div>
</div>

<!-- 导出展商到Excel -->
<form id="exportCustomerGroupToExcel" action="${base}/user/exportCustomerGroupToExcel" method="post">
	<div id="cidParm3"></div>
</form>

<div id="customerbar">
	<div style="display:inline-block;">
		<div class="easyui-menubutton" menu="#export" iconCls="icon-redo">导出</div>
	</div>
	<div id="export" style="width:180px;">
		<div id="exportAllGroupInfo" iconCls="icon-redo">所有参观团信息到Excel</div>
		<div id="exportSelectedGroupInfo" iconCls="icon-redo">所选参观团信息到Excel</div>
	</div>
</div>

<div class="loading"><img src="${base}/resource/load.gif"></div>
<script>
	var checkedItems = [];
	//----------------------------------------------------自定义函数开始--------------------------------------------------------//
	function filter(){
		var filterParm = "?";
		if(document.getElementById("groupName").value != ""){
			filterParm += '&group_name=' + encodeURI(document.getElementById("groupName").value);
		}
		if(document.getElementById("groupHeaderName").value != ""){
			filterParm += '&group_header_name=' + encodeURI(document.getElementById("groupHeaderName").value);
		}
		if(document.getElementById("groupHeaderTelphone").value != ""){
			filterParm += '&group_header_telphone=' + encodeURI(document.getElementById("groupHeaderTelphone").value);
		}
		if(document.getElementById("groupHeaderPosition").value != ""){
			filterParm += '&group_header_position=' + encodeURI(document.getElementById("groupHeaderPosition").value);
		}
		if(document.getElementById("groupHeaderEmail").value != ""){
			filterParm += '&group_header_email=' + encodeURI(document.getElementById("groupHeaderEmail").value);
		}
		if(document.getElementById("groupHeaderAddress").value != ""){
			filterParm += '&group_header_address=' + encodeURI(document.getElementById("groupHeaderAddress").value);
		}
		if(document.getElementById("groupHeaderCreateTime").value != ""){
			filterParm += '&group_header_create_time=' + encodeURI(document.getElementById("groupHeaderCreateTime").value);
		}
		if(document.getElementById("groupHeaderUpdateTime").value != ""){
			filterParm += '&group_header_update_time=' + document.getElementById("groupHeaderUpdateTime").value;
		}
		if(document.getElementById("groupIsDisabled").value != ""){
			filterParm += '&group_is_disabled=' + document.getElementById("groupIsDisabled").value;
		}
		$('#visitorGroup').datagrid('options').url = '${base}/user/queryVisitorGroupByPage' + filterParm;
		$('#visitorGroup').datagrid('reload');
	}

	function formatStatus(val, row) {
		if (val == 0) {
			return '激活';
		} else {
			return '注销';
		}
	}

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

	//----------------------------------------------------自定义函数结束--------------------------------------------------------//
	$(document).ready(function () {
		$('#tabs').tabs({
			onClose: function(title,index){
				filter();
				return false;
			}
		});

		$('#visitorGroup').datagrid({
			onSelect:function (rowIndex, rowData){
				var row = $('#visitorGroup').datagrid('getSelections');
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
					if (!$("#tabs").tabs("exists", field.groupName)) {
						$('#tabs').tabs('add', {
							title: field.groupName,
							content:'<iframe frameborder="0" src="'+ "${base}/user/directToVisitorGroupInfo?leaderID=" + field.id+'" style="width:100%;height:99%;"></iframe>',
							closable: true
						});
					} else {
						$("#tabs").tabs("select", field.groupName);
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

        //导出所有参观团信息到Excel
        $('#exportAllGroupInfo').click(function(){
            cidParm3.innerHTML = "";
            var node = "<input type='hidden' name='cids' value='-1'/>";
            cidParm3.innerHTML += node;
            document.getElementById("exportCustomerGroupToExcel").submit();
            $.messager.alert('提示', '导出所有参观团信息成功');
        });
        //导出所选参观团信息到Excel
        $('#exportSelectedGroupInfo').click(function(){
            cidParm3.innerHTML = "";
            if(checkedItems.length > 0){
                for (var i = 0; i < checkedItems.length; i++) {
                    var node = "<input type='hidden' name='cids' value='"+checkedItems[i]+"'/>";
                    cidParm3.innerHTML += node;
                }
                document.getElementById("exportCustomerGroupToExcel").submit();
                $.messager.alert('提示', '导出所选参观团信息成功');
            }else{
                $.messager.alert('提示', '请至少选择一项参观团再导出');
            }
        });
	});
</script>
</body>
</html>