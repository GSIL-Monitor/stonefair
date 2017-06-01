<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/tpl/user/managerrole/head.jsp" %>


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
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

		.exhibitors:hover{
			background-color:#6caef5;
		}
		.exhibitors{
			padding:8px;
		}

		#bg{ display: none; position: absolute; top: 0%; left: 0%; width: 50%; height: 50%; background-color: black; z-index:1001; -moz-opacity: 0.2; opacity:.2; filter: alpha(opacity=50);}
		.loading{display: none; position: absolute; top: 50%; left: 50%; z-index:1002; }
	</style>
</head>

<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
	<div title="展商列表" style="padding:5px">
		<table id="exhibitors" data-options="url:'${base}/user/queryAllExhibitorBackupInfosByPage',
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
                    <th data-options="field: 'tag', formatter: formatTag, width: $(this).width() * 0.07">
                        <span id="stag" class="sortable">所属人</span><br/>
                        <select id="exhibitorsTag" style="width:100%;height:21px;" onchange="filter(this.options[this.options.selectedIndex].value);">
                        </select>
                    </th>
                    <th data-options="field: 'group', formatter: formatGroup, width: $(this).width() * 0.07">
                        <span id="sgroup" class="sortable">展团</span><br/>
                        <select id="exhibitorsGroup" style="width:100%;height:21px;" onchange="filter(this.options[this.options.selectedIndex].value);">
                        </select>
                    </th>
                    <th data-options="field: 'boothNumber', width: $(this).width() * 0.07">
                        <span id="sboothNumber" class="sortable">展位号</span><br/>
                        <input id="exhibitorsBoothNumber" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
                    </th>
                    <th data-options="field: 'company', width: $(this).width() * 0.25">
                        <span id="scompany" class="sortable">公司中文名</span><br/>
                        <input id="exhibitorsCompany" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
                    </th>
                    <th data-options="field: 'companye', width: $(this).width() * 0.26">
                        <span id="scompanye" class="sortable">公司英文名</span><br/>
                        <input id="exhibitorsCompanye" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
                    </th>
					<th data-options="field: 'contractId', width: $(this).width() * 0.07">
						<span id="scontractId" class="sortable">合同编号</span><br/>
						<input id="exhibitorsContractId" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
					</th>
                    <th data-options="field: 'area', formatter: formatArea, width: $(this).width() * 0.03">
                        <span id="sarea" class="sortable">展区</span><br/>
                        <select id="exhibitorsArea" style="width:100%;height:21px;" onchange="filter();">
                            <option selected value="">全部</option>
                            <option value="1">国内</option>
                            <option value="2">国外</option>
                        </select>
                    </th>
                    <th data-options="field: 'country', formatter: formatCountry, width: $(this).width() * 0.03">
                        <span id="scountry" class="sortable">国家</span><br/>
						<select id="exhibitorsCountry" style="width:100%;height:21px;" onchange="filter(this.options[this.options.selectedIndex].value);">
						</select>
                    </th>
                    <th data-options="field: 'province', formatter: formatProvince, width: $(this).width() * 0.07">
                        <span id="sprovince" class="sortable">省份</span><br/>
                        <select id="exhibitorsProvince" style="width:100%;height:21px;" onchange="filter(this.options[this.options.selectedIndex].value);">
                        </select>
                    </th>
                    <th data-options="field: 'isLogout', formatter: formatStatus, width: $(this).width() * 0.07">
                        <span id="sisLogout" class="sortable">状态</span><br/>
                        <select id="exhibitorsIsLogout" style="width:104%;height:21px;" onchange="filter();">
                            <option selected value="">全部</option>
                            <option value="0">正常</option>
                            <option value="1">注销</option>
                        </select>
                    </th>
                </tr>
            </thead>
        </table>
	</div>
</div>

<div class="loading"><img src="${base}/resource/load.gif"></div>
<script>
	var checkedItems = [];
	var country = [];
	var province = [];
	var tags = {};
	var groups = {};
	var exhibitorsProvinceOld = -1;
	var exhibitorsCountryOld = -1;
	var order = "asc";
	var previewExhibitorList = [];
	var previewExhibitorIndex = 0;
//----------------------------------------------------------工具栏按钮结束------------------------------------------------------------//
//----------------------------------------------------------自定义函数开始------------------------------------------------------------//
	function formatTag(val, row) {
        if (val != null) {
			return tags[val];
        } else {
            return null;
        }
    }
	function formatGroup(val, row) {
        if (val != null) {
			return groups[val];
        } else {
            return null;
        }
    }
	function formatArea(val, row) {
        if (val == 1) {
			return "国内";
        } else if (val == 2) {
        	return "国外";
        } else return "";
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
	function formatProvince(val, row) {
        if (val != null) {
        	if(val > 0 && val <= province.length){
	            return province[val - 1].chineseName;
        	}
        	return null;
        } else {
            return null;
        }
    }
    function formatStatus(val, row) {
        if (val == 0) {
            return '正常';
        } else {
            return '注销';
        }
    }

    function country_change(countryId){
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:"${base}/user/queryProvinceByCountryId",
    		data:{ 'countryId': countryId },
    		success : function(result) {
    			if(result){
    				$("#province").html('');
    				document.getElementById('province').disabled=false;
	   		 		$("#province").append('<option value="">请选择</option>');
	   		 		for(var i=0,a;a=province[i++];){
// 	   		 		    console.log(a);
	   		 			$("#province").append('<option value="'+a.id+'">'+a.chineseName+'</option>');
	   		 		}
    			}
    		},
    		error : function(result) {
   				$("#province").html('');
   				document.getElementById('province').disabled=true;
    		}
    	});
    }
    function country_change_import(countryId){
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:"${base}/user/queryProvinceByCountryId",
    		data:{ 'countryId': countryId },
    		success : function(result) {
    			if(result){
    				$("#provinceImport").html('');
    				document.getElementById('provinceImport').disabled=false;
	   		 		$("#provinceImport").append('<option value="">请选择</option>');
	   		 		for(var i=0,a;a=province[i++];){
// 	   		 		    console.log(a);
	   		 			$("#provinceImport").append('<option value="'+a.id+'">'+a.chineseName+'</option>');
	   		 		}
    			}
    		},
    		error : function(result) {
   				$("#provinceImport").html('');
   				document.getElementById('provinceImport').disabled=true;
    		}
    	});
    }

    // 列表过滤功能
    function filter(countryId){
    	var filterParm = "?";
    	if(document.getElementById("exhibitorsTag").value != ""){
    		filterParm += '&tag=' + document.getElementById("exhibitorsTag").value;
    	}
    	if(document.getElementById("exhibitorsGroup").value != ""){
    		filterParm += '&group=' + document.getElementById("exhibitorsGroup").value;
    	}
    	if(document.getElementById("exhibitorsBoothNumber").value != ""){
    		filterParm += '&boothNumber=' + document.getElementById("exhibitorsBoothNumber").value;
    	}
    	if(document.getElementById("exhibitorsCompany").value != ""){
    		filterParm += '&company=' + encodeURI(document.getElementById("exhibitorsCompany").value);
    	}
    	if(document.getElementById("exhibitorsCompanye").value != ""){
    		filterParm += '&companye=' + encodeURI(document.getElementById("exhibitorsCompanye").value);
    	}
		if(document.getElementById("exhibitorsContractId").value != ""){
			filterParm += '&contractId=' + encodeURI(document.getElementById("exhibitorsContractId").value);
		}
    	if(document.getElementById("exhibitorsArea").value != ""){
    		filterParm += '&area=' + document.getElementById("exhibitorsArea").value;
    	}
    	if(document.getElementById("exhibitorsCountry").value != ""){
    		if(document.getElementById("exhibitorsCountry").value != exhibitorsCountryOld){
    			filterParm += '&country=' + document.getElementById("exhibitorsCountry").value;
    			exhibitorsCountryOld = document.getElementById("exhibitorsCountry").value;
        		$.ajax({
            		type:"POST",
            		dataType:"json",
            		url:"${base}/user/queryProvinceByCountryId",
            		data:{ 'countryId': countryId },
            		success : function(result) {
            			if(result){
            				$("#exhibitorsProvince").html('');
            				document.getElementById('exhibitorsProvince').disabled=false;
        	   		 		$("#exhibitorsProvince").append('<option value="">请选择</option>');
        	   		 		for(var i=0,a;a=province[i++];){
//         	   		 		    console.log(a);
        	   		 			$("#exhibitorsProvince").append('<option value="'+a.id+'">'+a.chineseName+'</option>');
        	   		 		}
            			}
            		},
            		error : function(result) {
           				$("#exhibitorsProvince").html('');
           				document.getElementById('exhibitorsProvince').disabled=true;
            		}
            	});
    		}
    	}else{
    		filterParm += '&country=' + document.getElementById("exhibitorsCountry").value;
			exhibitorsCountryOld = document.getElementById("exhibitorsCountry").value;
    		$("#exhibitorsProvince").html('');
			document.getElementById('exhibitorsProvince').disabled=true;
    	}
    	if(document.getElementById("exhibitorsProvince").value != ""){
    		if(document.getElementById("exhibitorsProvince").value != exhibitorsProvinceOld){
	    		filterParm += '&province=' + document.getElementById("exhibitorsProvince").value;
	    		exhibitorsProvinceOld = document.getElementById("exhibitorsProvince").value;
    		}
    	}
    	if(document.getElementById("exhibitorsIsLogout").value != ""){
    		filterParm += '&isLogout=' + document.getElementById("exhibitorsIsLogout").value;
    	}
    	$('#exhibitors').datagrid('options').url = '${base}/user/queryAllExhibitorBackupInfosByPage' + filterParm;
        $('#exhibitors').datagrid('reload');
    }
//----------------------------------------------------------自定义函数结束------------------------------------------------------------//
    $(document).ready(function () {
    	//加载国家列表
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:"${base}/user/queryAllCountry",
    		success : function(result) {
    			if(result){
    				country = result;
    				$("#exhibitorsCountry").html('');
    				$("#exhibitorsCountry").append('<option value="">全部</option>');
    				for(var i=0,a;a=country[i++];){
    					$("#exhibitorsCountry").append('<option value="'+a.id+'">'+a.countryValue+a.chineseName+'</option>');
    				}
    				document.getElementById('exhibitorsProvince').disabled=true;
    			}
    		}
    	});
    	//加载省份列表
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:"${base}/user/queryAllProvince",
    		success : function(result) {
    			if(result){
    				province = result;
    			}
    		}
    	});
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
    	//加载展团列表
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:"${base}/user/queryExhibitorGroupByPage",
    		success : function(result) {
    			if(result){
    				$("#exhibitorsGroup").html('');
    				$("#exhibitorsGroup").append('<option value="">全部</option>');
    				for(var i=0,a;a=result.rows[i++];){
    					groups[a.id] = a.groupName;
    					$("#exhibitorsGroup").append('<option value="'+a.id+'">'+a.groupName+'</option>');
    				}
    			}
    		}
    	});

    	$('#menu').accordion({
			onSelect: function(title){
				if(title == "公告管理"){
					addTab("公告列表","${base}/user/article");
				}else if(title == "标签管理"){
					addTab("标签列表","${base}/user/tag");
				}else if(title == "VISA管理"){
					addTab("展商VISA列表","${base}/user/tVisa");
				}else if(title == "客商管理"){
					addTab("国内客商","${base}/user/inlandCustomer");
				}else if(title == "展商管理"){
					addTab("展商列表","${base}/user/exhibitor");
				}else if(title == "模板管理") {
					addTab("邮件模板","${base}/user/tEmail");
				} else if(title == "数据报表") {
					addTab("数据报表","${base}/user/tReport");
				}
			}
		});

        // 展商列表渲染
		$('#exhibitors').datagrid({
            onSelect:function (rowIndex, rowData){
            	var row = $('#exhibitors').datagrid('getSelections');
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
            rowStyler:function(index,row){
				if (row.infoFlag == 4){
					return 'color:green;font-weight:bold;';	//今年有更新并且完整-绿色
				}else if (row.infoFlag == 3){
					return 'color:orange;font-weight:bold;';//非必填项缺失-橙色
				}else if (row.infoFlag == 2){
					return 'color:red;font-weight:bold;';	//必填项缺失-红色
				}else if (row.infoFlag == 1){
					return 'color:green;font-weight:bold;';	//账号信息完整-绿色
				} else if(row.infoFlag == 5) {
					return 'color:black;font-weight:bold;';	//账号刚激活，但未登录的
				}
			},
            onDblClickRow: function (index, field, value) {
            	if(field.company != ""){
	                if (!$("#tabs").tabs("exists", field.company)) {
	                    $('#tabs').tabs('add', {
	                        title: field.company,
	                        content:'<iframe frameborder="0" src="'+ "${base}/user/directorExhibitorBackupInfo?id=" + field.id+'" style="width:100%;height:99%;"></iframe>',
	                        closable: true
	                    });
	                } else {
	                    $("#tabs").tabs("select", field.company);
	                }
            	}else if(field.companye != ""){
            		if (!$("#tabs").tabs("exists", field.companye)) {
	                    $('#tabs').tabs('add', {
	                        title: field.companye,
	                        content:'<iframe frameborder="0" src="'+ "${base}/user/directorExhibitorBackupInfo?id=" + field.id+'" style="width:100%;height:99%;"></iframe>',
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
    });
</script>