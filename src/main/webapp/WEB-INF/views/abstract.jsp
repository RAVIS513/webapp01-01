<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- 検索エンジンキーワード --%>
<c:if test="${not empty param.addHeadKeyword}">
	${param.addHeadKeyword}
</c:if>
<%-- 作者 --%>
<meta name="author" content="TeamRavi">
<meta name="copyright" content="Copyright &copy; 2016 TeamRavi. All Rights Reserved.">
<%-- その他METAタグ --%>
<c:if test="${not empty param.addHeadMeta}">
	${param.addHeadMeta}
</c:if>
<%-- 外部スタイルシート --%>
<link rel="stylesheet" type="text/css" href='<c:url value="/css/common.css"></c:url>'>
<link rel="stylesheet" type="text/css" href='<c:url value="/css/font-awesome-4.6.3/css/font-awesome.min.css"></c:url>'>
<c:if test="${not empty param.addHeadStyleSheet}">
	${param.addHeadStyleSheet}
</c:if>
<%-- 外部スクリプト --%>
<script type="text/javascript" src='<c:url value="/js/jquery/jquery-1.12.3.min.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/js/common.js"></c:url>'></script>
<c:if test="${not empty param.addHeadScriptFile}">
	${param.addHeadScriptFile}
</c:if>
<%-- タイトル --%>
<title>${param.addHeadTitle}</title>
<%-- スタイル定義 --%>
<c:if test="${not empty param.addHeadStyle}">
	${param.addHeadStyle}
</c:if>
<%-- スクリプト --%>
<c:if test="${not empty param.addHeadScript}">
	${param.addHeadScript}
</c:if>
</head>
<body>
	${param.addBody}
</body>
</html>