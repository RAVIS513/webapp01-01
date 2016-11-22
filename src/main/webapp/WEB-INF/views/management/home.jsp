<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="managerBody">
	<div id="managerBodyHeader">
		<span id="managerBodyHeaderTitle">管理画面</span>
		<span id="managerBodyHeaderUserId">
			<i class="fa fa-user" aria-hidden="true"></i>&nbsp;:&nbsp;
			${sessionScope.sessionManager.getLoginForm().getId()}
		</span>
	</div>
	<div id="managerBodyHeaderDummy"></div>
	<div id="managerBodyContent">
		<c:if test="${not empty message}">
			<p style="color:red;line-height:150%;">${message}</p>
		</c:if>
		<div class="managerHomeTitle">
			<p>分類編集</p>
		</div>
		<hr>
		<div class="managerHomeContents">
			<p>[追加]</p>
			<form:form id="addClassForm" modelAttribute="classForm"
				action="/bluenote/manager/addclass">
				<form:label path="largeClass">大分類</form:label>
				<form:input path="largeClass" />
				<form:label path="middleClass">中分類</form:label>
				<form:input path="middleClass" />
				<form:label path="smallClass">小分類</form:label>
				<form:input path="smallClass" />
				<button type="button" id="addClassBtn">
					<i class="fa fa-plus" aria-hidden="true" title="分類追加"></i>
				</button>
			</form:form>

			<br>
			<br>

			<p>[削除：クラス一覧]</p>
			<form:form id="delClassForm" modelAttribute="classForm"
				action="/bluenote/manager/delclass">
				<c:choose>
					<c:when test="${not empty classes}">
						<table id="delClassTable">
							<tr>
								<th>大分類</th>
								<th>中分類</th>
								<th>小分類</th>
								<th></th>
							</tr>
							<c:forEach var="list1" items="${classes}">
								<c:forEach var="list2" items="${list1.subClass}">
									<c:forEach var="list3" items="${list2.subClass}">
										<tr>
											<td>${list1.mainClass}</td>
											<td>${list2.mainClass }</td>
											<td>${list3}</td>
											<td>
												<button type="button">
													<i class="fa fa-times" aria-hidden="true" title="分類削除"></i>
												</button>
											</td>
										</tr>
									</c:forEach>
								</c:forEach>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<p>分類が0件、または取得エラーです</p>
					</c:otherwise>
				</c:choose>
			</form:form>
		</div>

		<br>
		<br>

		<div class="managerHomeTitle">
			<p>記事投稿</p>
		</div>
		<hr>
		<div class="managerHomeContents">
			<form:form id="createContentsForm" modelAttribute="contentsForm"
				action="/bluenote/manager/contents">
				<table>
					<tr>
						<th>タイトル</th>
						<td colspan="3"><form:input path="title" style="width:99%;" /></td>
					</tr>
					<tr>
						<th>分類</th>
						<td>大：<form:select path="largeClass"></form:select></td>
						<td>中：<form:select path="middleClass"></form:select></td>
						<td>小：<form:select path="smallClass"></form:select></td>
					</tr>
				</table>
				<button type="button" id="addContentsBtn">
					<i class="fa fa-plus" aria-hidden="true" title="記事投稿画面へ"></i>
				</button>
			</form:form>
		</div>

		<br>
		<br>

		<div class="managerHomeTitle">
			<p>記事編集／削除</p>
		</div>
		<hr>
		<div class="managerHomeContents">
			<p>[分類絞り込み]</p>
			<form:form id="serchClassForm" modelAttribute="classForm"
				action="/bluenote/manager/searchContents">
				<form:label path="largeClass">大分類</form:label>
				<form:input path="largeClass" />
				<form:label path="middleClass">中分類</form:label>
				<form:input path="middleClass" />
				<form:label path="smallClass">小分類</form:label>
				<form:input path="smallClass" />
				<button type="button" id="searchContentsBtn">
					<i class="fa fa-search" aria-hidden="true" title="検索"></i>
				</button>
			</form:form>

			<br>

			<p>[記事一覧]</p>
			<c:choose>
				<c:when test="${not empty contents}">
					<table id="editContentsTable">
						<tr>
							<th>番号</th>
							<th>投稿日</th>
							<th>タイトル</th>
							<th>大分類</th>
							<th>中分類</th>
							<th>小分類</th>
							<th>ステータス</th>
							<th></th>
						</tr>
						<c:forEach var="list" items="${contents}">
							<tr>
								<td>${list.seqNo}</td>
								<td>${list.uploadDate}</td>
								<td>${list.title}</td>
								<td>${list.largeClass}</td>
								<td>${list.middleClass}</td>
								<td>${list.smallClass}</td>
								<td>${list.status}</td>
								<td>
									<form:form id="editContents" modelAttribute="contentsForm"
										action="/bluenote/manager/editContents">
										<button type="button" name="editContent">
											<i class="fa fa-pencil-square-o" aria-hidden="true"
												title="編集"></i>
										</button>
										&nbsp;
									</form:form>
									<form:form id="deleteContents" modelAttribute="contentsForm"
										action="/bluenote/manager/deleteContents">
										<button type="button" name="deleteContent">
											<i class="fa fa-times" aria-hidden="true" title="削除"></i>
										</button>
										&nbsp;
									</form:form>
									<form:form id="viewContents" modelAttribute="contentsForm"
										action="/bluenote/manager/viewContents">
										<button type="button" name="viewContent">
											<i class="fa fa-clone" aria-hidden="true" title="参照"></i>
										</button>
									</form:form>
								</td>
							</tr>
						</c:forEach>
					</table>
					<c:if test="${not empty contentsNum}">
						${contentsNum}件中${currentPageStart}件から${currentPageEnd}件を表示
					</c:if>
					<c:if test="${not empty prevPage}">
						&nbsp;
						<span><a href="/bluenote/manager?page=${prevPage}">前の20件を表示</a></span>
					</c:if>
					<c:if test="${not empty nextPage}">
						&nbsp;
						<span><a href="/bluenote/manager?page=${nextPage}">次の20件を表示</a></span>
					</c:if>
				</c:when>
				<c:otherwise>
					<p>投稿記事が0件または取得エラーです</p>
				</c:otherwise>
			</c:choose>
		</div>

		<br>
		<br>

	</div>
</div>