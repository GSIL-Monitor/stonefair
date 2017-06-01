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
<body onload="queryMsgManagerTemplateByPage()">
<!-- 公告列表 -->
<table id="emailTemplate" style="width: 100%;height: 100%;">
	<tr>
		<td>
			<table style="width: 100%;height: 100%;">
				<tr>
					<td style="width: 130px;text-align: left">已预登记(已激活)短信标题_中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="1" name="msg_register_subject_cn" id="msg_register_subject_cn"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">已预登记(已激活)短信内容_中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="4" name="msg_register_content_cn" id="msg_register_content_cn"></textarea>
					</td>
				</tr>

				<tr>
					<td style="width: 130px;text-align: left">未预登记短信标题_中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="1" name="msg_unregister_subject_cn" id="msg_unregister_subject_cn"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">未预登记短信内容_中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="6" name="msg_unregister_content_cn" id="msg_unregister_content_cn"></textarea>
					</td>
				</tr>

				<tr>
					<td style="width: 130px;text-align: left">已预登记(未激活)短信标题_中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="1" name="msg_unactive_subject_cn" id="msg_unactive_subject_cn"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">已预登记(未激活)短信内容_中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="5" name="msg_unactive_content_cn" id="msg_unactive_content_cn"></textarea>
					</td>
				</tr>

				<tr>
					<td style="width: 130px;text-align: left">短信邀请标题_中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="1" name="msg_invite_subject_cn" id="msg_invite_subject_cn"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">短信邀请内容_中文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="msg_invite_content_cn" id="msg_invite_content_cn"></textarea>
					</td>
				</tr>
			</table>
		</td>
		<td>
			<table style="width: 100%;height: 100%;">
				<tr>
					<td style="width: 130px;text-align: left">已预登记(已激活)短信_英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="1" name="msg_register_subject_en" id="msg_register_subject_en"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">已预登记(已激活)短信内容_英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="4" name="msg_register_content_en" id="msg_register_content_en"></textarea>
					</td>
				</tr>

				<tr>
					<td style="width: 130px;text-align: left">未预登记短信标题_英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="1" name="msg_unregister_subject_en" id="msg_unregister_subject_en"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">未预登记短信内容_英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="6" name="msg_unregister_content_en" id="msg_unregister_content_en"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">已预登记(未激活)短信标题_英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="1" name="msg_unactive_subject_en" id="msg_unactive_subject_en"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">已预登记(未激活)短信内容_英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="5" name="msg_unactive_content_en" id="msg_unactive_content_en"></textarea>
					</td>
				</tr>

				<tr>
					<td style="width: 130px;text-align: left">短信邀请标题_英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="1" name="msg_invite_subject_en" id="msg_invite_subject_en"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width: 130px;text-align: left">短信邀请内容_英文：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="msg_invite_content_en" id="msg_invite_content_en"></textarea>
					</td>
				</tr>
				<tr>
				<tr>
					<td style="width: 130px;text-align: left">国内历史客商新年祝福短信内容：</td>
					<td></td>
				</tr>
				<tr>
					<td style="text-align: right">
						<textarea cols="70" rows="2" name="msg_new_year_content" id="msg_new_year_content"></textarea>
					</td>
				</tr>
				</tr>
			</table>
		</td>
	</tr>
</table>
<div class="email-footer" align="center">
	<button type="button" class="btn btn-primary" id="savemsg">确认修改</button>
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

	function queryMsgManagerTemplateByPage() {
		$.ajax({
			url: "${base}/user/queryMsgManagerTemplateByPage",
			type: "post",
			dataType: "json",
			traditional: true,
			success: function (data) {
				if(data.rows.length>0) {
					for(var i = 0; i < data.rows.length; i++){
						var map = data.rows[i];
						for (var key in map) {
							if(equalsIgnoreCase(map[key],"msg_register_subject_cn")){
								document.getElementById("msg_register_subject_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_register_subject_en")){
								document.getElementById("msg_register_subject_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_register_content_cn")){
								document.getElementById("msg_register_content_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_register_content_en")){
								document.getElementById("msg_register_content_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_invite_subject_cn")){
								document.getElementById("msg_invite_subject_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_invite_subject_en")){
								document.getElementById("msg_invite_subject_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_invite_content_cn")){
								document.getElementById("msg_invite_content_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_invite_content_en")){
								document.getElementById("msg_invite_content_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_register_policyDeclare_cn")){
								document.getElementById("msg_register_policyDeclare_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_register_policyDeclare_en")){
								document.getElementById("msg_register_policyDeclare_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_unregister_subject_cn")){
								document.getElementById("msg_unregister_subject_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_unregister_content_cn")){
								document.getElementById("msg_unregister_content_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_unregister_subject_en")){
								document.getElementById("msg_unregister_subject_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_unregister_content_en")){
								document.getElementById("msg_unregister_content_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_unactive_subject_cn")){
								document.getElementById("msg_unactive_subject_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_unactive_content_cn")){
								document.getElementById("msg_unactive_content_cn").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_unactive_subject_en")){
								document.getElementById("msg_unactive_subject_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_unactive_content_en")){
								document.getElementById("msg_unactive_content_en").value = map["tpl_value"];
							}else if(equalsIgnoreCase(map[key],"msg_new_year_content")){
								document.getElementById("msg_new_year_content").value = map["tpl_value"];
							}
						}
					}
				}
			}
		});
	}

	$(document).ready(function () {
		$("#savemsg").click(function () {
			$.messager.confirm('确认修改','你确定要修改短信模板?',function(r){
				if (r){
					$.ajax({
						url: "${base}/user/updateMsgManagerTemplateByPage",
						type: "post",
						dataType: "json",
						traditional: true,
						data: {"msg_register_subject_cn": $("#msg_register_subject_cn").val(),
							"msg_register_subject_en": $("#msg_register_subject_en").val(),
							"msg_register_content_cn": $("#msg_register_content_cn").val(),
							"msg_register_content_en": $("#msg_register_content_en").val(),
							"msg_invite_subject_cn": $("#msg_invite_subject_cn").val(),
							"msg_invite_subject_en": $("#msg_invite_subject_en").val(),
							"msg_invite_content_cn": $("#msg_invite_content_cn").val(),
							"msg_invite_content_en": $("#msg_invite_content_en").val(),
							"msg_unregister_subject_cn": $("#msg_unregister_subject_cn").val(),
							"msg_unregister_content_cn": $("#msg_unregister_content_cn").val(),
							"msg_unregister_subject_en": $("#msg_unregister_subject_en").val(),
							"msg_unregister_content_en": $("#msg_unregister_content_en").val(),
							"msg_unactive_subject_cn": $("#msg_unactive_subject_cn").val(),
							"msg_unactive_content_cn": $("#msg_unactive_content_cn").val(),
							"msg_unactive_subject_en": $("#msg_unactive_subject_en").val(),
							"msg_unactive_content_en": $("#msg_unactive_content_en").val(),
							"msg_new_year_content": $("#msg_new_year_content").val()},
						success: function (data) {
							if (data.resultCode == 1) {
								$.messager.alert('错误', '更新短信模板失败错误');
							} else if (data.resultCode > 1) {
								$.messager.alert('错误', '服务器错误');
							} else {
								queryMsgManagerTemplateByPage();
								$.messager.show({
									title: '成功',
									msg: '修改短信模板成功',
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