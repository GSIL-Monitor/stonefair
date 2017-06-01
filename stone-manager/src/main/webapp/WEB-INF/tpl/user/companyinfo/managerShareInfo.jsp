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
<!-- 所属人列表 -->
<table id="tags" pagination="true" pageSize="20"></table>
<!-- 修改所属人表单 -->
<div id="modifyTagDlg" data-options="iconCls:'icon-add',modal:true">
    <form id="modifyTagForm" name="modifyTagForm">
        <table style="width: 410px; margin: 20px auto">
            <tr>
                <td style="width: 90px;text-align: center">所属人：</td>
                <td><input class="easyui-validatebox" type="text" name="name" data-options="required:true"></td>
            </tr>
            <tr>
                <td style="width: 90px;text-align: center">分享对象：</td>
                <td>
                    <select id="tagList" class="easyui-combobox" name="tagList" style="width:200px;"
                            data-options="url:'/manager/user/queryTags',valueField:'id',textField:'text',multiple:true,panelHeight:'auto'"/>
                </td>
            </tr>
        </table>
        <input type="hidden" value="" name="id" />
        <input type="hidden" value="" name="sharers" />
        <input type="hidden" value="" name="sharerIds" />
    </form>
</div>
<script>
	var checkedItems = [];
    var ownerList = {};
    var tagData = [];
    
    $(document).ready(function () {
        // 资料库拥有者列表渲染
        $("#tags").datagrid({
            url: '/manager/user/queryTagsAndShared',
            singleSelect:false,	//只能当行选择：关闭
            fit:true,
            fitColumns:true,
            rownumbers: true,
            columns: [
                [
    				{field: 'ck', checkbox:true }, 
                    {field: 'name', title: '所属人', width: 100},
                    {field: 'sharers', title: '分享对象', width: 100}
                ]
            ],
            onSelect:function (rowIndex, rowData){
            	var row = $('#tags').datagrid('getSelections');
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
            	document.modifyTagForm.id.value = field.id;
            	document.modifyTagForm.name.value = field.name;
                document.modifyTagForm.sharers.value = field.sharers;
                $('#tagList').combobox('clear');
                $("#tagList").combobox("loadData", tagData);
                if(field.sharerIds.length >0) {
                    var shareArray = field.sharerIds.split(",");
                    for (var i=0 ; i< shareArray.length ; i++)
                    {
                        $("#tagList").combobox('select', parseInt(shareArray[i]));
                    }
                }else {
                    $('#tagList').combobox('setText',"请选择");
                }
            	$("#modifyTagDlg").dialog("open");
            }
        });
        function findCheckedItem(id) {
			for (var i = 0; i < checkedItems.length; i++) {
				if (checkedItems[i] == id) return i;
			}
			return -1;
		}

        //加载所属人列表
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"/manager/user/queryTags",
            success : function(result) {
                if(result){
                    for(var i=0,a;a=result.rows[i++];){
                        ownerList[a.id] = a.name;
                        tagData.push({ "text": a.name, "id": a.id });
                    }
                }
            }
        });

        $('#modifyTagDlg').dialog({
            title: '设置分享对象',
            width: 430,
            height: 220,
            closed: true,
            cache: false,
            modal: true,
            buttons: [
                {
                    text: '确认修改',
                    handler: function () {
                        if ($("#modifyTagForm").form("validate")) {
                            var sharerId = document.modifyTagForm.id.value;
                            var oldSharers = document.modifyTagForm.name.value;
                            var newSharers = $('#tagList').combobox('getText');
                            //alert("id: " + document.modifyTagForm.id.value + ",  old: " + document.modifyTagForm.name.value + ",  new: " + newSharers);
                            $.ajax({
                                url: "/manager/user/modifyTagShare",
                                type: "post",
                                dataType: "json",
                                data: {'sharerId': sharerId, 'oldSharers': oldSharers, 'newSharers': newSharers},
                                success: function (data) {
                                    if (data.resultCode == 0) {
                                        $("#tags").datagrid("reload");
                                        $("#modifyTagDlg").dialog("close");
                                        $.messager.show({
                                            title: '成功',
                                            msg: '修改所属人成功',
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
                    	document.getElementById("modifyTagForm").reset();
                    	$("#modifyTagDlg").dialog("close");
                    }
                }
            ]
        });
    });
</script>
</body>
</html>