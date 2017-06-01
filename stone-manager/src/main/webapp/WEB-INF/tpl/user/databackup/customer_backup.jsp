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

		.customerBackup:hover{
			background-color:#6caef5;
		}
		.customerBackup{
			padding:8px;
		}

		#bg{ display: none; position: absolute; top: 0%; left: 0%; width: 50%; height: 50%; background-color: black; z-index:1001; -moz-opacity: 0.2; opacity:.2; filter: alpha(opacity=50);}
		.loading{display: none; position: absolute; top: 50%; left: 50%; z-index:1002; }
	</style>
</head>

<body>
<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<div title="客商备份数据列表" style="padding:5px">
		<table id="customerBackup" data-options="url:'${base}/user/queryAllCustomerBackupInfosByPage',
            								 loadMsg: '数据加载中......',
									         singleSelect:false,	//只能当行选择：关闭
									         fit:true,
									         fitColumns:true,
									         idField:'id',
									         remoteSort:true,
									         view: emptyView,
											 emptyMsg: '没有记录',
									         rownumbers: true,
									         pagination:'true',
									         pageSize:'20'">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field: 'firstName', width: $(this).width() / 7">
					<span id="scustomerFirstName" class="sortable">姓名</span><br/>
					<input id="customerFirstName" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'company', width: $(this).width() / 7">
					<span id="scustomerCompany" class="sortable">公司名称</span><br/>
					<input id="customerCompany" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'city', width: $(this).width() / 7">
					<span id="scustomerCity" class="sortable">城市</span><br/>
					<input id="customerCity" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'address', width: $(this).width() / 7">
					<span id="scustomerAddress" class="sortable">地址</span><br/>
					<input id="customerAddress" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'mobilePhone', width: $(this).width() / 7">
					<span id="scustomerMobilePhone" class="sortable">手机</span><br/>
					<input id="customerMobilePhone" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<%--<th data-options="field: 'telephone', width: $(this).width() / 7">
					电话<br/>
					<input id="customerTelephone" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>--%>
				<th data-options="field: 'email', width: $(this).width() / 7">
					<span id="scustomerEmail" class="sortable">邮箱</span><br/>
					<input id="customerEmail" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'createdTime', formatter:formatDatebox, width: $(this).width() / 7">
					<span id="screatedTime" class="sortable">登记时间</span><br/>
					<input id="createdTime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'updateTime', formatter:formatDatebox, width: $(this).width() / 8">
					<span id="supdateTime" class="sortable">修改时间</span><br/>
					<input id="updateTime" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
				</th>
				<th data-options="field: 'country', width: $(this).width() / 9">
					<span id="scountry" class="sortable">国家</span><br/>
					<select id="customersCountry" style="width:100%;height:21px;" onchange="filter();">
					</select>
				</th>
				<th data-options="field: 'professional', formatter: formatStatus, width: $(this).width() / 7">
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

<div class="loading"><img src="${base}/resource/load.gif"></div>
<script>
    var country = [];
	var checkedItems = [];
	//----------------------------------------------------------自定义函数开始------------------------------------------------------------//
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

	Date.prototype.format = function (format){
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

	// 列表过滤功能
	function filter(){
		var filterParm = "?";
		if(document.getElementById("customerFirstName").value != ""){
			filterParm += '&firstName=' + encodeURI(document.getElementById("customerFirstName").value);
		}
		if(document.getElementById("customerCompany").value != ""){
			filterParm += '&company=' + encodeURI(document.getElementById("customerCompany").value);
		}
		if(document.getElementById("customerCity").value != ""){
			filterParm += '&city=' + encodeURI(document.getElementById("customerCity").value);
		}
		if(document.getElementById("customerAddress").value != ""){
			filterParm += '&address=' + encodeURI(document.getElementById("customerAddress").value);
		}
		if(document.getElementById("customerMobilePhone").value != ""){
			filterParm += '&mobilePhone=' + encodeURI(document.getElementById("customerMobilePhone").value);
		}
		/*if(document.getElementById("customerTelephone").value != ""){
		 filterParm += '&telephone=' + encodeURI(document.getElementById("customerTelephone").value);
		 }*/
		if(document.getElementById("customerEmail").value != ""){
			filterParm += '&email=' + encodeURI(document.getElementById("customerEmail").value);
		}
		if(document.getElementById("createdTime").value != ""){
			filterParm += '&createTime=' + encodeURI(document.getElementById("createdTime").value);
		}
        if(document.getElementById("customersCountry").value != ""){
            filterParm += '&country=' + encodeURI(document.getElementById("customersCountry").value);
        }
		if(document.getElementById("customerIsProfessional").value != ""){
			filterParm += '&isProfessional=' + document.getElementById("customerIsProfessional").value;
		}
		if(document.getElementById("customerIsActivated").value != ""){
			filterParm += '&isActivated=' + document.getElementById("customerIsActivated").value;
		}
		$('#customerBackup').datagrid('options').url = '${base}/user/queryAllCustomerBackupInfosByPage' + filterParm;
		$('#customerBackup').datagrid('reload');
	}
	//----------------------------------------------------------自定义函数结束------------------------------------------------------------//
	$(document).ready(function () {
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
                        $("#customersCountry").append('<option value="'+a.chineseName+'">'+a.countryValue+a.chineseName+'</option>');
                    }
                }
            }
        });
		// 客商数据备份列表渲染
		$('#tabs').tabs({
			onClose: function(title,index){
				filter();
				return false;
			}
		});

		// 展商列表渲染
		$('#customerBackup').datagrid({
			onSelect:function (rowIndex, rowData){
				var row = $('#customerBackup').datagrid('getSelections');
				for (var i = 0; i < row.length; i++) {
					if (findCheckedItem(row[i].eid) == -1) {
						checkedItems.push(row[i].eid);
					}
				}
			},
			onUnselect:function (rowIndex, rowData){
				var k = findCheckedItem(rowData.eid);
				if (k != -1) {
					checkedItems.splice(k, 1);
				}
			},
			onSelectAll:function (rows){
				for (var i = 0; i < rows.length; i++) {
					var k = findCheckedItem(rows[i].eid);
					if (k == -1) {
						checkedItems.push(rows[i].eid);
					}
				}
			},
			onUnselectAll:function (rows){
				for (var i = 0; i < rows.length; i++) {
					var k = findCheckedItem(rows[i].eid);
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
							content:'<iframe frameborder="0" src="'+ "${base}/user/directToCustomerBackupInfo?id=" + field.id+'" style="width:100%;height:99%;"></iframe>',
							closable: true
						});
					} else {
						$("#tabs").tabs("select", field.company);
					}
				}else if(field.companye != ""){
					if (!$("#tabs").tabs("exists", field.companye)) {
						$('#tabs').tabs('add', {
							title: field.companye,
							content:'<iframe frameborder="0" src="'+ "${base}/user/directToCustomerBackupInfo?id=" + field.id+'" style="width:100%;height:99%;"></iframe>',
							closable: true
						});
					} else {
						$("#tabs").tabs("select", field.companye);
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

		function findCheckedItem(eid) {
			for (var i = 0; i < checkedItems.length; i++) {
				if (checkedItems[i] == eid) return i;
			}
			return -1;
		}
		// 全局排序功能
		$("#customerBackup").data().datagrid.dc.view.find("div.datagrid-header td .sortable").click(function(){
			if(order == "asc"){
				$('#customerBackup').datagrid('reload', {
					sort : $(this).prop("id").substr(1, $(this).prop("id").length),
					order : order
				});
				$(this).html($(this).html().split(" ▲")[0].split(" ▼")[0] + " ▲");
				order = "desc";
			}else if(order == "desc"){
				$('#customerBackup').datagrid('reload', {
					sort : $(this).prop("id").substr(1, $(this).prop("id").length),
					order : order
				});
				$(this).html($(this).html().split(" ▲")[0].split(" ▼")[0] + " ▼");
				order = "null";
			}else{
				$('#customerBackup').datagrid('reload', {
					sort : null,
					order : null
				});
				$(this).html($(this).html().split(" ▲")[0].split(" ▼")[0]);
				order = "asc";
			}
		});
	});
</script>
</body>
</html>