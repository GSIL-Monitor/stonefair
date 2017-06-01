<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    /**-====================================================================
     *                               基本常量的设定
     *=====================================================================**/
    //设定context path
    String base = "/manager";
    pageContext.setAttribute("base", base);
%>

<script type="text/javascript" src="${base}/resource/js/lib/jquery.js"></script>
<script type="text/javascript" src="${base}/resource/js/lib/raphael-min.js"></script>
<script type="text/javascript" src="${base}/resource/js/res/chinaMapConfigTemp.js"></script>
<script type="text/javascript" src="${base}/resource/js/res/worldMapConfig.js"></script>
<script type="text/javascript" src="${base}/resource/js/map.js"></script>

<link rel="stylesheet" type="text/css" href="${base}/resource/js/css/SyntaxHighlighter.css">
<script type="text/javascript" src="${base}/resource/js/lib/SyntaxHighlighter.js"></script>