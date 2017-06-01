<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%
    /**-====================================================================
     *                               基本常量的设定
     *=====================================================================**/
    //设定context path
    String base = "/manager";
    pageContext.setAttribute("base", base);
%>

<script type="text/javascript" src="${base}/resource/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${base}/resource/js/jquery-ui.js"></script>
<link type="text/css" href="${base}/resource/css/style.css" rel="stylesheet" />

<link rel="stylesheet" type="text/css" href="${base}/resource/ckeditor/samples/sample.css">
<link rel="stylesheet" type="text/css" href="${base}/resource/easyui/themes/metro-blue/easyui.css">
<link rel="stylesheet" type="text/css" href="${base}/resource/easyui/themes/icon.css">
<script type="text/javascript" src="${base}/resource/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${base}/resource/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resource/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/resource/easyui/empty-view.js"></script>
<script type="text/javascript" src="${base}/resource/ajaxfileupload.js"></script>
<script type="text/javascript" src="${base}/resource/common.js"></script>
<script type="text/javascript" src="${base}/resource/easyui/easyui-validate.js"></script>