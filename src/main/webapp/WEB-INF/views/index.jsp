<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/WEB-INF/views/abstract.jsp">
	<c:param name="addHeadTitle">
		<%-- ページタイトル --%>
		BLUE NOTE
	</c:param>
	<c:param name="addHeadKeyword">
		<meta name="keywords" content="プログラム,メモ,ソフトウェア,備忘録">
	</c:param>
	<c:param name="addHeadMeta">
		<%-- ページ説明 --%>
		<meta name="description" content="しがないエンジニアの備忘録">
	</c:param>
	<c:param name="addHeadStyleSheet">
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/mCustomScrollbar/jquery.mCustomScrollbar.min.css"></c:url>'>
		<link rel="stylesheet" type="text/css" href='<c:url value="/css/index.css"></c:url>'>
		<c:if test="${(select == 'login') || (select == 'manage_home') || (select == 'manage_create')}">
			<link rel="stylesheet" type="text/css" href='<c:url value="/css/noteManager.css"></c:url>'>
		</c:if>
		<c:if test="${not empty releaseCssFile}">
			<link rel="stylesheet" type="text/css" href='<c:url value="/css/archives/${releaseCssFile}"></c:url>'>
		</c:if>
	</c:param>
	<c:param name="addHeadScriptFile">
		<script type="text/javascript" src='<c:url value="/js/mCustomScrollbar/jquery.mCustomScrollbar.min.js"></c:url>'></script>
		<script type="text/javascript" src='<c:url value="/js/index.js"></c:url>'></script>
		<c:if test="${(select == 'login') || (select == 'manage_home') || (select == 'manage_create')}">
			<script type="text/javascript" src='<c:url value="/js/noteManager.js"></c:url>'></script>
			<script type="text/javascript" src='<c:url value="/js/clipboard/clipboard.min.js"></c:url>'></script>
			<script type="text/javascript" src='<c:url value="/js/contextmenu/jquery.contextmenu.r2.packed.js"></c:url>'></script>
		</c:if>
	</c:param>
	<c:param name="addHeadStyle">
		${headerStyle}
	</c:param>
	<c:param name="addBody">
		<div id="sideMenu">
			<div id="sideMenuTitle">
				<div id="sideMenuTitleWord">BLUE NOTE</div>
				<div id="sideMenuTitleUpdate">
					<c:if test="${not empty update}">
						最終更新日：${update}
					</c:if>
				</div>
			</div>
			<dl id="sideMenuContent">
				<c:if test="${not empty menu}">
					<c:forEach var="list" items="${menu}">
						<dt>
							<c:choose>
								<c:when test="${empty list.sub}">
									${list.main.name}
									<a href="${list.main.url}"></a>
								</c:when>
								<c:otherwise>
									<i class="fa fa-plus-square" aria-hidden="true"></i>&nbsp;
									${list.main.name}
								</c:otherwise>
							</c:choose>
						</dt>
						<c:if test="${not empty list.sub}">
							<c:forEach var="subList" items="${list.sub}">
								<dd>
									${subList.name}
									<a href="${subList.url}"></a>
								</dd>
							</c:forEach>
						</c:if>
					</c:forEach>
				</c:if>
			</dl>
		</div>
		<div id="mainContent">
			<c:choose>
				<c:when test="${select == 'list'}">
					<c:import url="/WEB-INF/views/common/list.jsp"></c:import>
				</c:when>
				<c:when test="${select == 'view'}">
					<c:import url="/WEB-INF/views/common/view.jsp"></c:import>
				</c:when>
				<c:when test="${select == 'login'}">
					<c:import url="/WEB-INF/views/management/login.jsp"></c:import>
				</c:when>
				<c:when test="${select == 'manage_home'}">
					<c:import url="/WEB-INF/views/management/home.jsp"></c:import>
				</c:when>
				<c:when test="${select == 'manage_create'}">
					<c:import url="/WEB-INF/views/management/create.jsp"></c:import>
				</c:when>
				<c:otherwise>
					<c:import url="/WEB-INF/views/common/home.jsp"></c:import>
				</c:otherwise>
			</c:choose>
		</div>
		<script type="text/javascript">
			// 処理呼び出し
			var NoteScript = new Note("#mainContent");

			// LOAD後処理
			$(window).load(function() {
				NoteScript.main();
			});
		</script>

		<c:if test="${(select == 'login') || (select == 'manage_home') || (select == 'manage_create')}">
			<script type="text/javascript">
				// 処理呼び出し
				var NoteManagerScript = new NoteManager();
				var clipboard = new Clipboard('.uploadImage');
				clipboard.on('success', function(e) {
					alert("クリップボードへコピーしました");
				});

				// LOAD後処理
				$(window).load(function() {
					NoteManagerScript.main();
				});
			</script>
		</c:if>
	</c:param>
</c:import>