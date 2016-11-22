<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/abstract.jsp">
	<c:param name="addHeadTitle">
		<%-- ページタイトル --%>
		ERROR
	</c:param>
	<c:param name="addHeadStyleSheet">
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/error.css"></c:url>'>
	</c:param>
	<c:param name="addBody">
		<div id="errorWrapper">
			<h1>Error</h1>
			<hr>
			<p>${message}</p>
			<br><br>
			<a href="javascript:history.back();">戻る</a>
		</div>
	</c:param>
</c:import>