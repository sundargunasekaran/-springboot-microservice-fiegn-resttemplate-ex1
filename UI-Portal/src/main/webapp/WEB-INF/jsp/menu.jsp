<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div style="border: 1px solid #ccc; padding: 5px; margin-bottom: 20px;">
	<c:set var="appurl" value="${pageContext.request.contextPath}" />
	<a href="${pageContext.request.contextPath}/rt/">welcome-rt</a> |
	<a href="${pageContext.request.contextPath}/rt/all">Home-rt</a> |
	<a href="${pageContext.request.contextPath}/op/">welcome-op</a> |	
	<a href="${pageContext.request.contextPath}/op/all">Home-op</a> 

	<%-- a href="${pageContext.request.contextPath}/add">Add User</a> |   
            <a onclick="document.forms['logoutForm'].submit()">Logout</a> --%>
       

   <%--  <form id="logoutForm" method="POST" action="${contextPath}/logout">
    <sec:csrfInput /> 
    </form> --%>

</div>