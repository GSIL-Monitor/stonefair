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
<body onload="queryEmailManagerTemplateByPage()">
<!-- 公告列表 -->
<table id="emailTemplate" style="width: 100%;height: 100%;">
	<tr>
		<td>
			<table style="width: 100%;height: 100%;">
				<tr>
					<td style="width: 130px;text-align: left">邮箱注册标题中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="mail_register_subject_cn" id="mail_register_subject_cn"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">邮箱注册中文内容：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="15" name="mail_register_content_cn" id="mail_register_content_cn"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">邮箱邀请标题中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="mail_invite_subject_cn" id="mail_invite_subject_cn"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">邮箱邀请中文内容：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="15" name="mail_invite_content_cn" id="mail_invite_content_cn"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">普通客商信息未完整标题中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="mail_register_subject_cn_unpro" id="mail_register_subject_cn_unpro"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">普通客商信息未完整内容中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="15" name="mail_register_content_cn_unpro" id="mail_register_content_cn_unpro"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">邮箱注册声明中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="mail_register_policyDeclare_cn" id="mail_register_policyDeclare_cn"></textarea>
					</td>
				</tr>
			</table>
		</td>
		<td>
			<table style="width: 100%;height: 100%;">
				<tr>
					<td style="width: 130px;text-align: left">邮箱注册标题英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="mail_register_subject_en" id="mail_register_subject_en"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">邮箱注册英文内容：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="15" name="mail_register_content_en" id="mail_register_content_en"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">邮箱邀请标题英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="mail_invite_subject_en" id="mail_invite_subject_en"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">邮箱邀请英文内容：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="15" name="mail_invite_content_en" id="mail_invite_content_en"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">普通客商信息未完整标题英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="mail_register_subject_en_unpro" id="mail_register_subject_en_unpro"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">普通客商信息未完整内容英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="15" name="mail_register_content_en_unpro" id="mail_register_content_en_unpro"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">邮箱注册声明英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="mail_register_policyDeclare_en" id="mail_register_policyDeclare_en"></textarea>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<div class="email-footer" align="center">
	<button type="button" class="btn btn-primary" onclick="preMailChineseTemplate()">中文版注册预览</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="btn btn-primary" onclick="preMailEnglishTemplate()">英文版注册预览</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="btn btn-primary" onclick="preMailChineseInviteTemplate()">中文版邀请预览</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="btn btn-primary" onclick="preMailEnglishInviteTemplate()">英文版邀请预览</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="btn btn-primary" onclick="preMailChineseNormalCustomerTemplate()">中文版普通客商预览</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="btn btn-primary" onclick="preMailEnglishNormalCustomerTemplate()">英文版普通客商预览</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="btn btn-primary" id="savemail">确认修改</button>
</div>

<script>
	function equalsIgnoreCase(str1, str2)
	{
		if(str1 != null && (str1.toString() == str2.toString()))
		{
			return true;
		}
		return false;
	}

	function preMailChineseTemplate() {
		var code = document.getElementById("mail_register_content_cn").value;//即要运行的代码。
		var newwin = window.open('','','');  //打开一个窗口并赋给变量newwin。
		newwin.opener = null // 防止代码对论谈页面修改
		newwin.document.write(code);  //向这个打开的窗口中写入代码code，这样就实现了运行代码功能。
		newwin.document.close();
	}

	function preMailEnglishTemplate() {
		var code = document.getElementById("mail_register_content_en").value;//即要运行的代码。
		var newwin = window.open('','','');  //打开一个窗口并赋给变量newwin。
		newwin.opener = null // 防止代码对论谈页面修改
		newwin.document.write(code);  //向这个打开的窗口中写入代码code，这样就实现了运行代码功能。
		newwin.document.close();
	}

	function preMailChineseInviteTemplate() {
		var code = document.getElementById("mail_invite_content_cn").value;//即要运行的代码。
		var newwin = window.open('','','');  //打开一个窗口并赋给变量newwin。
		newwin.opener = null // 防止代码对论谈页面修改
		newwin.document.write(code);  //向这个打开的窗口中写入代码code，这样就实现了运行代码功能。
		newwin.document.close();
	}

	function preMailEnglishInviteTemplate() {
		var code = document.getElementById("mail_invite_content_en").value;//即要运行的代码。
		var newwin = window.open('','','');  //打开一个窗口并赋给变量newwin。
		newwin.opener = null // 防止代码对论谈页面修改
		newwin.document.write(code);  //向这个打开的窗口中写入代码code，这样就实现了运行代码功能。
		newwin.document.close();
	}

	function preMailChineseNormalCustomerTemplate() {
		var code = document.getElementById("mail_register_content_cn_unpro").value;//即要运行的代码。
		var newwin = window.open('','','');  //打开一个窗口并赋给变量newwin。
		newwin.opener = null // 防止代码对论谈页面修改
		newwin.document.write(code);  //向这个打开的窗口中写入代码code，这样就实现了运行代码功能。
		newwin.document.close();
	}

	function preMailEnglishNormalCustomerTemplate() {
		var code = document.getElementById("mail_register_content_en_unpro").value;//即要运行的代码。
		var newwin = window.open('','','');  //打开一个窗口并赋给变量newwin。
		newwin.opener = null // 防止代码对论谈页面修改
		newwin.document.write(code);  //向这个打开的窗口中写入代码code，这样就实现了运行代码功能。
		newwin.document.close();
	}

	function queryEmailManagerTemplateByPage() {
		$.ajax({
			url: "${base}/user/queryEmailManagerTemplateByPage",
			type: "post",
			dataType: "json",
			traditional: true,
			success: function (data) {
				if(data.rows.length>0) {
					for(var i = 0; i < data.rows.length; i++){
						var map = data.rows[i];
						for (var key in map) {
							if(equalsIgnoreCase(map[key],"mail_register_subject_cn")){
								document.getElementById("mail_register_subject_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_register_subject_en")){
								document.getElementById("mail_register_subject_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_register_content_cn")){
								document.getElementById("mail_register_content_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_register_content_en")){
								document.getElementById("mail_register_content_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_invite_subject_cn")){
								document.getElementById("mail_invite_subject_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_invite_subject_en")){
								document.getElementById("mail_invite_subject_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_invite_content_cn")){
								document.getElementById("mail_invite_content_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_invite_content_en")){
								document.getElementById("mail_invite_content_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_register_subject_cn_unpro")){
								document.getElementById("mail_register_subject_cn_unpro").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_register_content_cn_unpro")){
								document.getElementById("mail_register_content_cn_unpro").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_register_subject_en_unpro")){
								document.getElementById("mail_register_subject_en_unpro").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_register_content_en_unpro")){
								document.getElementById("mail_register_content_en_unpro").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_register_policyDeclare_cn")){
								document.getElementById("mail_register_policyDeclare_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"mail_register_policyDeclare_en")){
								document.getElementById("mail_register_policyDeclare_en").value = map["tpl_value"];
							}
						}
					}
				}
			}
		});
	}

	$(document).ready(function () {
		$("#savemail").click(function () {
			$.messager.confirm('确认修改','你确定要修改邮件模板?',function(r){
				if (r){
					$.ajax({
						url: "${base}/user/updateEmailManagerTemplateByPage",
						type: "post",
						dataType: "json",
						traditional: true,
						data: {"mail_register_subject_cn": $("#mail_register_subject_cn").val(),
							"mail_register_subject_en": $("#mail_register_subject_en").val(),
							"mail_register_content_cn": $("#mail_register_content_cn").val(),
							"mail_register_content_en": $("#mail_register_content_en").val(),
							"mail_invite_subject_cn": $("#mail_invite_subject_cn").val(),
							"mail_invite_subject_en": $("#mail_invite_subject_en").val(),
							"mail_invite_content_cn": $("#mail_invite_content_cn").val(),
							"mail_invite_content_en": $("#mail_invite_content_en").val(),
							"mail_register_subject_cn_unpro": $("#mail_register_subject_cn_unpro").val(),
							"mail_register_content_cn_unpro": $("#mail_register_content_cn_unpro").val(),
							"mail_register_subject_en_unpro": $("#mail_register_subject_en_unpro").val(),
							"mail_register_content_en_unpro": $("#mail_register_content_en_unpro").val(),
							"mail_register_policyDeclare_cn": $("#mail_register_policyDeclare_cn").val(),
							"mail_register_policyDeclare_en": $("#mail_register_policyDeclare_en").val()},
						success: function (data) {
							if (data.resultCode == 1) {
								$.messager.alert('错误', '更新邮件模板失败错误');
							} else if (data.resultCode > 1) {
								$.messager.alert('错误', '服务器错误');
							} else {
								queryEmailManagerTemplateByPage();
								$.messager.show({
									title: '成功',
									msg: '修改邮件模板成功',
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