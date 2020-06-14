<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Customer</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function() {	

	$('select#roleId').val("${customers.roleId}");
});
</script>
<body>
	<jsp:include page="menu.jsp" />
	<div class="container" style="width: 25%; margin-left: 10%;">
		<spring:url value="/op/save" var="saveURL" />
		<h2>Customer</h2>
		<form method="post" action="${saveURL }" class="form">
			<input type="text" name="customerId" class="form-control"	id="customerId" value= "${customers.customerId }"/>
			<div class="form-group">
				<lable for="firstName">Name</lable>
				<input type="text" name="username" class="form-control"	id="username" value= "${customers.username }"/>
			</div>
			<div class="form-group">
				<lable for="password">Password</lable>
				<input type="password" name="password" class="form-control"	id="password" />
			</div>
			<div class="form-group">
				<lable for="email">Email</lable>
				<input type="text" name="emailId" class="form-control" id="emailId" value= "${customers.emailId }"/>
			</div>

			<div class="form-group">
				<lable for="role">Role</lable>
				<select id="roleId" name="roleId">
					<c:forEach items="${roles}" var="type" varStatus="status">
						<option value="${type.key}">${type.value}</option>
					</c:forEach>
				</select>
			</div>
			<%-- <div class="form-group">
				<lable for="role">Policy</lable>
				<select id="policyId" name="policyId">
					<option value="">Select</option>
					<c:forEach items="${policy}" var="policyTemp" varStatus="status">
						<option value="${policyTemp.key}">${policyTemp.value}</option>
					</c:forEach>
				</select>
			</div> --%>
			<button type="submit" class="btn btn-primary">Save</button>
		</form>

	</div>
</body>
</html>