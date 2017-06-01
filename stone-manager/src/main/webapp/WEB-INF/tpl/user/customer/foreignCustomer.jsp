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
<!-- 境外客商列表 -->
<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<div title="境外客商列表" style="padding:5px">
		<table id="customers" data-options="url:'${base}/user/queryForeignCustomersByPage',
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
				<th data-options="field: 'firstName', width: $(this).width() / 8">
					姓名<br/>
					<input id="customersFirstName" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'company', width: $(this).width() / 8">
					公司名称<br/>
					<input id="customersCompany" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'country', formatter: formatCountry, width: $(this).width() * 0.07">
					国家<br/>
					<select id="customersCountry" style="width:100%;height:21px;" onchange="filter(this.options[this.options.selectedIndex].value);"></select>
				</th>
				<th data-options="field: 'address', width: $(this).width() / 8">
					地址<br/>
					<input id="customersAddress" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'mobilePhone', width: $(this).width() / 8">
					手机<br/>
					<input id="customersMobilePhone" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'email', width: $(this).width() / 8">
					邮箱<br/>
					<input id="customerEmail" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'createdTime', formatter:formatDatebox, width: $(this).width() / 8">
					登记时间<br/>
					<input id="createdTime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'updateTime', formatter:formatDatebox, width: $(this).width() / 8">
					<span id="supdateTime" class="sortable">修改时间</span><br/>
					<input id="updateTime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'isProfessional', formatter: formatStatus, width: $(this).width() / 7">
					<span id="sisProfessional" class="sortable">状态</span><br/>
					<select id="customerIsProfessional" style="width:104%;height:21px;" onchange="filter();">
						<option selected value="">全部</option>
						<option value="0">普通</option>
						<option value="1">专业</option>
					</select>
				</th>
				<th data-options="field: 'isActivated', formatter: formatActiviteStatus, width: $(this).width() / 7">
					<span id="sisActivated" class="sortable">状态</span><br/>
					<select id="customerIsActivated" style="width:104%;height:21px;" onchange="filter();">
						<option selected value="">全部</option>
						<option value="0">未激活</option>
						<option value="1">激活</option>
					</select>
				</th>
			</tr>
			</thead>
		</table>
	</div>
</div>
<!-- 导出客商到Excel -->
<form id="exportForeignCustomersToExcel" action="${base}/user/exportForeignCustomersToExcel" method="post">
	<div id="cidParm2"></div>
</form>
<!-- 导出客商到Excel -->
<form id="exportCustomersByYearOrTimeToExcel" action="${base}/user/exportCustomersByYearOrTimeToExcel" method="post">
	<div id="cidParm1"></div>
</form>
<!-- 工具栏 -->
<div id="customerbar">
	<div style="display:inline-block;">
		<div class="easyui-menubutton" menu="#email" iconCls="icon-redo">邮件</div>
	</div>
	<div id="email" style="width:180px;">
		<div id="emailAllCustomers" iconCls="icon-redo">群发所有已激活客商邮件</div>
		<div id="emailSelectedCustomers" iconCls="icon-redo">群发所选已激活客商邮件</div>
	</div>
	<div style="display:inline-block;">
		<div class="easyui-menubutton" menu="#export" iconCls="icon-redo">导出</div>
	</div>
	<div id="export" style="width:180px;">
		<div id="exportAllCustomers" iconCls="icon-redo">所有客商信息到Excel</div>
		<div id="exportSelectedCustomers" iconCls="icon-redo">所选客商信息到Excel</div>
	</div>
	<div class="menu-sep"></div>
	<td width="30" align="center">年份：</td>
	<td width="30" align="center">
		<select id="customerTime">
		</select>
	</td>
	<td width="30" align="center">字段：</td>
	<td width="30" align="center">
		<select id="customerField">
			<option selected="true" value='0'>初次预约登记时间</option>
			<option value='1'>激活预约登记时间</option>
		</select>
	</td>
	<td width="80" align="center">
		<button type="button" class="btn btn-primary" id="exportDataDetail">导出数据</button>
	</td>
</div>

<script>
	var checkedItems = [];
	var country = [];
	//----------------------------------------------------工具栏函数开始--------------------------------------------------------//
	//群发所有客商邮件
	$('#emailAllCustomers').click(function(){
		$.messager.confirm('确认删除','你确定要群发所有客商邮件吗?',function(r){
			if (r){
				$.ajax({
					url: "${base}/user/emailAllForeignStoneCustomers",
					type: "post",
					data: {"cids": "-1"},
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
							$("#customers").datagrid("reload");
                            $('#customers').datagrid('clearSelections');
						} else {
							$.messager.alert('错误', '系统错误');
						}
					}
				});
			}
		});
	});
	$('#emailSelectedCustomers').click(function(){
		if(checkedItems.length > 0){
			$.messager.confirm('确认删除','你确定要群发所选客商邮件吗?',function(r){
				if (r){
					$.ajax({
						url: "${base}/user/emailAllForeignStoneCustomers",
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
								$("#customers").datagrid("reload");
                                $('#customers').datagrid('clearSelections');
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
	//导出所有客商信息到Excel
	$('#exportAllCustomers').click(function(){
		cidParm2.innerHTML = "";
		var node = "<input type='hidden' name='cids' value='-1'/>";
		cidParm2.innerHTML += node;
		document.getElementById("exportForeignCustomersToExcel").submit();
		$.messager.alert('提示', '导出所有客商成功');
	});
	//导出所选客商信息到Excel
	$('#exportSelectedCustomers').click(function(){
		cidParm2.innerHTML = "";
		if(checkedItems.length > 0){
			for (var i = 0; i < checkedItems.length; i++) {
				var node = "<input type='hidden' name='cids' value='"+checkedItems[i]+"'/>";
				cidParm2.innerHTML += node;
			}
			document.getElementById("exportForeignCustomersToExcel").submit();
			$.messager.alert('提示', '导出所选客商成功');
		}else{
			$.messager.alert('提示', '请至少选择一项客商再导出');
		}
	});
	//----------------------------------------------------工具栏函数结束--------------------------------------------------------//
	//----------------------------------------------------自定义函数开始--------------------------------------------------------//
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
		if (val == 0) {
			return '普通';
		} else {
			return '专业';
		}
	}

	function formatActiviteStatus(val, row) {
		if (val == 0) {
			return '未激活';
		} else {
			return '激活';
		}
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
		};
		if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
				(this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o) if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1,
							RegExp.$1.length == 1 ? o[k] :
							("00" + o[k]).substr(("" + o[k]).length));
		return format;
	}

	function filter() {
		var filterParm = "?";
		if(document.getElementById("customersFirstName").value != ""){
			filterParm += '&firstName=' + encodeURI(document.getElementById("customersFirstName").value);
		}
		if(document.getElementById("customersCompany").value != ""){
			filterParm += '&company=' + encodeURI(document.getElementById("customersCompany").value);
		}
		if(document.getElementById("customersCountry").value != ""){
			filterParm += '&country=' + encodeURI(document.getElementById("customersCountry").value);
		}
		if(document.getElementById("customersAddress").value != ""){
			filterParm += '&address=' + encodeURI(document.getElementById("customersAddress").value);
		}
		if(document.getElementById("customersMobilePhone").value != ""){
			filterParm += '&mobilePhone=' + encodeURI(document.getElementById("customersMobilePhone").value);
		}
		if(document.getElementById("customerEmail").value != ""){
			filterParm += '&email=' + encodeURI(document.getElementById("customerEmail").value);
		}
		if(document.getElementById("createdTime").value != ""){
			filterParm += '&createTime=' + encodeURI(document.getElementById("createdTime").value);
		}
		if(document.getElementById("updateTime").value != ""){
			filterParm += '&updateTime=' + encodeURI(document.getElementById("updateTime").value);
		}
		if(document.getElementById("customerIsProfessional").value != ""){
			filterParm += '&isProfessional=' + document.getElementById("customerIsProfessional").value;
		}
		if(document.getElementById("customerIsActivated").value != ""){
			filterParm += '&isActivated=' + document.getElementById("customerIsActivated").value;
		}
		filterParm += '&inlandOrForeign=2';
		$('#customers').datagrid('options').url = '${base}/user/queryStoneCustomersByPage' + filterParm;
		$('#customers').datagrid('reload');
	}

	//根据年份和时间导出客商到Excel
	$('#exportDataDetail').click(function () {
		var cidParm1 = document.getElementById("cidParm1");
		var fieldYear = document.getElementById("customerTime").value;
		var fieldTime = document.getElementById("customerField").value;
		cidParm1.innerHTML = "";
		var node1 = "<input type='hidden' name='fieldYear' value='"+fieldYear+"' />";
		cidParm1.innerHTML += node1;
		var node2 = "<input type='hidden' name='fieldTime' value='"+fieldTime+"' />";
		cidParm1.innerHTML += node2;
		var node3 = "<input type='hidden' name='inlandOrForeign' value='2'/>";
		cidParm1.innerHTML += node3;
		document.getElementById("exportCustomersByYearOrTimeToExcel").submit();
		$.messager.alert('提示', '导出客商资料成功');
	});
//----------------------------------------------------自定义函数结束--------------------------------------------------------//
    $(document).ready(function () {
		//加载导出的年份
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"${base}/user/loadYearListForCustomer",
			success : function(result) {
				if(result.resultCode == 0){
					var yeardata = JSON.parse(result.yearData);
					if (yeardata != null && yeardata.length > 0) {
						$("#customerTime").html('');
						$("#customerTime").append('<option value="">全部</option>');
						for (var i = 0, a; a = yeardata[i++];) {
							$("#customerTime").append('<option value="'+a.year+'">'+a.year+'</option>');
						}
					}
				}
			}
		});

		//加载国家列表
		$.ajax({
			type:"POST",
			dataType:"json",
			url:"${base}/user/queryAllCountry",
			success : function(result) {
				if(result){
					country = result;
					$("#customersCountry").html('');
					$("#customersCountry").append('<option value="">全部</option>');
					for(var i=0,a;a=country[i++];){
						if(a.id != 44) $("#customersCountry").append('<option value="'+a.id+'">'+a.countryValue+a.chineseName+'</option>');
					}
				}
			}
		});

    	// 国外客商列表渲染
		$('#tabs').tabs({
			onClose: function(title,index){
				filter();
				return false;
			}
		});

        $('#customers').datagrid({
       		onSelect:function (rowIndex, rowData){
	        	var row = $('#customers').datagrid('getSelections');
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
							content:'<iframe frameborder="0" src="'+ "${base}/user/directToCustomerInfo?id=" + field.id+'" style="width:100%;height:99%;"></iframe>',
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