<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="archive_wrraper">
	<div id="archive_header" class="clearfix">
		<div id="archive_title">
			${title}
		</div>
		<div id="archive_uploadDate">
			${uploadDate}
		</div>
		<div id="archive_class">
			${largeClass}
			<c:if test="${not empty middleClass}">
				&nbsp;>&nbsp;${middleClass}
			</c:if>
			<c:if test="${not empty smallClass}">
				&nbsp;>&nbsp;${smallClass}
			</c:if>
		</div>
	</div>

	<%-- ********** FREE EREA START ********** --%>

	<div class="archive_section">
		<div class="archive_section_title">
			見出し
		</div>
		<div class="archive_section_content">
			本文
		</div>
	</div>

	<%-- ********** FREE EREA END ********** --%>

</div>