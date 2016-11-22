<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="contentsListWrapper">
	<div id="contentsListHeader">
		<div id="contentsListHeaderTitle">
			[&nbsp;${listLarge}&nbsp;>&nbsp;${listMiddle}&nbsp;]&nbsp;記事一覧
		</div>
		<div id="contentsListHeaderSearch">
			<form method="get">
				<input name="search">&nbsp;
				<i class="fa fa-search" aria-hidden="true" title="検索"></i>
			</form>
		</div>
	</div>
	<div id="contentsListHeaderDummy"></div>
	<dl id="contentsListBody">
		<c:choose>
			<c:when test="${not empty list}">
				<c:forEach var="list" items="${list}">
					<dd class="contentsListBodyBox">
						<a href="/bluenote/view/${list.seqNo}"></a>
						<div class="contentsListBodyTitle">${list.title}</div>
						<div class="contentsListBodyUploadDate">${list.uploadDate}</div>
						<div class="contentsListBodyOverview">${list.overView}</div>
					</dd>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<dd>記事がありません</dd>
			</c:otherwise>
		</c:choose>
	</dl>
</div>