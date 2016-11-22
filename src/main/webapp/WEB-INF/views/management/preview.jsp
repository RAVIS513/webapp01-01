<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/abstract.jsp">
	<c:param name="addHeadTitle">
		<%-- ページタイトル --%>
		PREVIEW
	</c:param>
	<c:param name="addHeadStyleSheet">
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/index.css"></c:url>'>
		<c:if test="${not empty cssFile}">
			<c:choose>
				<c:when test="${status == '0'}">
					<link rel="stylesheet" type="text/css" href='<c:url value="/css/archives/${cssFile}"></c:url>'>
				</c:when>
				<c:when test="${status == '1'}">
					<link rel="stylesheet" type="text/css" href='<c:url value="/css/provisional/${cssFile}"></c:url>'>
				</c:when>
				<c:otherwise>
					<link rel="stylesheet" type="text/css" href='<c:url value="/css/temp/${cssFile}"></c:url>'>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:param>
	<c:param name="addBody">
		<div id="mainContent">
			<c:if test="${not empty message}">
				<h2>${message}</h2>
			</c:if>
			<c:if test="${not empty jspFile}">
				<span>JSP_FIlE&nbsp;=&nbsp;</span><span id="jspFile">${jspFile}</span>
			</c:if>
			<c:if test="${not empty cssFile}">
				<span>&nbsp;/&nbsp;CSS_FILE&nbsp;=&nbsp;</span><span id="cssFile">${cssFile}</span><hr>
			</c:if>
			<c:if test="${not empty jspFile}">
				<c:choose>
					<c:when test="${status == '0'}">
						<c:import url="/WEB-INF/views/archives/${jspFile}"></c:import>
					</c:when>
					<c:when test="${status == '1'}">
						<c:import url="/WEB-INF/views/provisional/${jspFile}"></c:import>
					</c:when>
					<c:otherwise>
						<c:import url="/WEB-INF/views/temp/${jspFile}"></c:import>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
	</c:param>
</c:import>
