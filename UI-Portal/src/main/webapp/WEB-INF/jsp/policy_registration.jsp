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
function showNewPolicy(obj){
	var selVal = obj.value;
	if(selVal == "9999"){
		$("#div_newpolicy").show();
		$("#policyContactNo").val("");
		$("#policyYrs").val("");
	}else{
		$("#div_newpolicy").hide();
		$("#policyContactNo").val("-1");
		$("#policyYrs").val("-1");
	}
}
</script>
<body>
	<jsp:include page="menu.jsp" />
	<div class="container" style="width: 25%; margin-left: 10%;">
		<spring:url value="/op/savepolicy" var="saveURL" />
		<h2>Policy</h2>
		<form method="post" action="${saveURL }" class="form">
			<input type="hidden" name="customerId" class="form-control"	id="customerId" value= "${customers.customerId }"/>
			<div class="form-group">
				<lable for="firstName">Customer Name</lable>
				<input type="text" disabled="disabled" name="username" class="form-control"	id="username" value= "${customers.username }"/>
			</div>
			<div class="form-group">
				<lable for="role">Policy</lable>
				<select id="policyId" name="policyId" onchange="showNewPolicy(this);">
					<c:forEach items="${policy}" var="policyTemp" varStatus="status">
						<option value="${policyTemp.key}">${policyTemp.value}</option>
					</c:forEach>
				</select>
			</div>
			<div id="div_newpolicy" style="display: none;">
			<div class="form-group">
				<lable for="firstName">Policy Name</lable>
				<input type="text" name="policyName" class="form-control"	id="policyName" value= ""/>
			</div>
			<div class="form-group">
				<lable for="policyCompany">Policy Company Name</lable>
				<div class="form-group">
				<select id="policyCompany" name="policyCompany" >
					<c:forEach items="${policy}" var="policyTemp" varStatus="status">
					<c:if test="${policyTemp.key != 9999}">
						<option value="${policyTemp.value}">${policyTemp.value}</option>
					</c:if>
						
					</c:forEach>
				</select>
			</div>
			</div>
			<div class="form-group">
				<lable for="policyContactNo">Policy Contact No</lable>
				<input type="text" name="policyContactNo" class="form-control"	id="policyContactNo" value= "-1"/>
			</div>
			<div class="form-group">
				<lable for="policyEmail">Policy Email</lable>
				<input type="text"  name="policyEmail" class="form-control"	id="policyEmail" value= ""/>
			</div>
			<div class="form-group">
				<lable for="policyYrs">Policy Years</lable>
				<input type="text"  name="policyYrs" class="form-control"	id="policyYrs" value= "-1"/>
			</div>
			</div>
			<button type="submit" class="btn btn-primary">Save</button>
		</form>

	</div>
</body>
</html>