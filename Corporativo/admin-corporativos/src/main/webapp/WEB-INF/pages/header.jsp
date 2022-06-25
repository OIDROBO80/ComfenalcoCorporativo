<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<spring:url value="/resources/img/logoFooter.png" var="logoHeader" />
<spring:url value="/resources/css/header.css" var="headerCss" />
<script type="text/javascript">
	var configGeneral = {
		csrfParameter : '${_csrf.parameterName}',
		csrfToken : '${_csrf.token}'
	};
</script>
<head>
<link href="${headerCss}" rel="stylesheet" />
<meta name="_csrf" content="${_csrf.token}" />
<meta name="session_token" content="${sessionToken}" />
</head>
<header>
	<div class="col-md-12 imgHeaderBar">
		<div class="menuHeader">
			<div class="width1150 height100">
				<div class="col-md-12 col-sm-3 height100 containerCenter">
					<a href="/Admin/home"><img
						class="width100 logoFooter center-block" src="${logoHeader}"></a>
				</div>
			</div>
		</div>
		<div class="row col-md-12 container_options_header">
			<c:url value="/logout" var="logoutUrl" />
			<form action="${logoutUrl}" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>

			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<h5>
					Usuario : <span id="username">${pageContext.request.userPrincipal.name}</span>
					| <a class="pointer" data-ng-click="adminCtrl.logout()"> Cerrar
						sesion</a>
				</h5>
			</c:if>
		</div>
	</div>
	<input type="hidden" name="empresaUsername" id="empresaUsername"
		value="${empresaUsername}">
</header>