<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    /**-====================================================================
     *                               基本常量的设定
     *=====================================================================**/
    //设定context path
    String base = "/manager";
    pageContext.setAttribute("base", base);
%>

<link rel="stylesheet" type="text/css" href="${base}/resource/ckeditor/samples/sample.css">
<link rel="stylesheet" type="text/css" href="${base}/resource/easyui/themes/metro-blue/easyui.css">
<link rel="stylesheet" type="text/css" href="${base}/resource/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/resource/echarts/css/font-awesome.min.css">
<%--<link rel="stylesheet" type="text/css" href="${base}/resource/echarts/css/bootstrap.css">--%>
<link rel="stylesheet" type="text/css" href="${base}/resource/echarts/css/carousel.css">
<%--<link rel="stylesheet" type="text/css" href="${base}/resource/echarts/css/echartsHome.css">--%>

<script type="text/javascript" src="${base}/resource/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${base}/resource/jquery.min.js"></script>
<script type="text/javascript" src="${base}/resource/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/resource/common.js"></script>
<script type="text/javascript" src="${base}/resource/date.js"></script>
<script type="text/javascript" src="${base}/resource/echarts/esl.js"></script>