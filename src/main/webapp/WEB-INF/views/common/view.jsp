<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty releaseJspFile}">
	<c:import url="/WEB-INF/views/archives/${releaseJspFile}"></c:import>
</c:if>