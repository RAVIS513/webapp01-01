<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="managerBody">
	<div id="managerBodyHeader">
		<span id="managerBodyHeaderTitle">管理画面</span>
	</div>
	<div id="managerBodyContent">
		<div id="managerLogin">

			<form:form id="loginForm" modelAttribute="loginForm">
				<div>
					<form:label path="id"><i class="fa fa-user" aria-hidden="true"></i></form:label>
					<form:input path="id" pattern="^[0-9A-Za-z]+$" />
				</div>
				<div>
					<form:label path="pass"><i class="fa fa-key" aria-hidden="true"></i></form:label>
					<form:input path="pass" type="password" pattern="^[a-zA-Z0-9 -/:-@\[-\`\{-\~]+$" />
				</div>
				<div>
					<form:button>LOGIN</form:button>
				</div>
				<c:if test="${not empty message}">
					<div style="color:red;">
						${message}
					</div>
				</c:if>
			</form:form>

		</div>
	</div>
</div>