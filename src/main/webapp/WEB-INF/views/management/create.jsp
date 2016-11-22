<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="managerBody">
	<div id="managerBodyHeader">
		<div id="managerBodyHeaderTitle">記事投稿</div>
		<div id="managerBodyHeaderUserId">
			<i class="fa fa-reply-all" aria-hidden="true" id="back" title="HOMEへ戻る"></i>
			<i class="fa fa-upload" aria-hidden="true" id="create" title="投稿"></i>
			<i class="fa fa-picture-o modal-open" aria-hidden="true" id="modal-image" title="画像選択"></i>
			<i class="fa fa-clone" aria-hidden="true" id="preView" title="プレビュー"></i>
			<i class="fa fa-archive" aria-hidden="true" id="save" title="保存"></i>
			<i class="fa fa-user" aria-hidden="true"></i>
			&nbsp;:&nbsp;
			${sessionScope.sessionManager.getLoginForm().getId()}
		</div>
	</div>
	<div id="managerBodyHeaderDummy"></div>
	<div id="managerBodyContent">
		<form:form id="createContentsForm" modelAttribute="contentsForm" action="/bluenote/manager/create/contents">
			<table>
				<c:if test="${not empty message}">
					<p style="color:red;line-height:150%;">${message}</p>
				</c:if>
				<tr>
					<th>番号</th><td><form:input path="seqNo" readonly="true"/></td>
					<th>登録日</th><td><form:input path="uploadDate" readonly="true"/></td>
				</tr>
				<tr>
					<th>タイトル</th><td colspan="6"><form:input path="title"/></td>
				</tr>
				<tr>
					<th>大分類</th><td><form:input path="largeClass" readonly="true"/></td>
					<th>中分類</th><td><form:input path="middleClass" readonly="true"/></td>
					<th>小分類</th><td><form:input path="smallClass" readonly="true"/></td>
					<td><button type="button" class="modal-open" id="modal-classEdit">変更</button>
				</tr>
				<tr>
					<th>概要文</th><td colspan="6"><form:textarea path="overView"/></td>
				</tr>
				<tr>
					<th>JSPファイル名</th><td colspan="6"><form:input path="jspFileName" readonly="true"/></td>
				</tr>
				<tr>
					<th>CSSファイル名</th><td colspan="6"><form:input path="cssFileName" readonly="true"/></td>
				</tr>
				<tr>
					<td colspan="7">
						<ul class="tabMenu">
							<li class="tabSelect">JSP</li>
							<li>CSS</li>
						</ul>
						<ul class="tabContents">
							<li><form:textarea path="jsp"/></li>
							<li class="tabHide"><form:textarea path="css"/></li>
						</ul>
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>

<!-- For Image Modal -->
<div id="modal-image-content" class="modal-content">
	<div class="modal-wrapper">
		<div id="uploadImageTitle">画像ファイル</div>
		<div id="uploadImageList">
			<p>アップロード済み</p>
			<c:forEach var="file" items="${imageFiles}">
				<img class="uploadImage" alt="uploadImage" src='<c:url value="/img/upload/${file}"></c:url>' data-clipboard-text='<c:url value="/img/upload/${file}"></c:url>'>
			</c:forEach>
		</div>
		<div class="contextMenu" id="MyRightMenu">
			<ul>
				<li id="scale‐up">拡大</li>
				<li id="imageDelete">削除</li>
			</ul>
		</div>
		<br>
		<form action="/bluenote/uploadImage" method="post" enctype="multipart/form-data">
			<input type="file" name="file">
			<button type="submit"><i class="fa fa-cloud-upload" aria-hidden="true"></i></button>
		</form>
	</div>
</div>

<!-- For ClassEdit Modal -->
<div id="modal-classEdit-content" class="modal-content">
	<div class="modal-wrapper">
		<h3>分類変更</h3>
		<br>
		<table>
			<tr>
				<th>大分類</th><td><select id="classEditLarge"></select></td>
			</tr>
			<tr>
				<th>中分類</th><td><select id="classEditMiddle"></select></td>
			</tr>
			<tr>
				<th>小分類</th><td><select id="classEditSmall"></select></td>
			</tr>
		</table>
		<br>
		<button type="button" id="classEditButton">更新</button>
	</div>
</div>
