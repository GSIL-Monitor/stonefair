<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib uri="/priveliege" prefix="privilege" %>

<%
    /**-====================================================================
     *                               基本常量的设定
     *=====================================================================**/
    //设定context path
    String path = request.getContextPath();
    if("/".equals(path.trim())) path = "";
    pageContext.setAttribute("path",path);
%>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${path}/resource/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${path}/resource/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${path}/resource/style/main.css">

<!-- JAVASCRIPT -->
<script type="text/javascript" src="${path}/resource/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${path}/resource/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/resource/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>