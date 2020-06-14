<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>    
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <title>Customer List</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<script type="text/javascript">
function customerSearch(){
	var cId = $("#customerId").val();
	location.href = "${pageContext.request.contextPath}/op/details/search/" + cId;
}
</script>
<body>
<jsp:include page="menu.jsp" /> 
 <div class="container">
  <h2>Customer List</h2>
  <form method="GET" name="search" id="search"  cssClass="form">
   <div class="form-group">
    <lable for="CustomerId">Customer Id :</lable>
    <input type ="text" name=customerId id="customerId" value="${customerId}"/>
   </div>
   <button type="button" class="btn btn-primary" onClick="customerSearch();">Search</button>
  </form>
  <table class="table table-striped">
   <thead>
    <tr>
     <th scope="row">Customer Id</th>
     <th scope="row">Customer Name</th>
     <th scope="row">Email</th>
     <th scope="row">Role</th>
     <th scope="row">Policy</th>
     <th scope="row">Policy Company</th>
     <th scope="row">Add Policy</th>
     <th scope="row">Edit</th>
     <th scope="row">Delete</th>
    </tr>
   </thead>
   <tbody>
    <c:forEach items="${customers }" var="customer" >
    <c:set var="policyName" value="" />
    <c:set var="policycmp" value="" />
     <tr>
      <td>${customer.customerId }</td>
      <td>${customer.username }</td>
      <td>${customer.emailId }</td>
      <td>${customer.role }</td>
       <c:forEach items="${customer.policyModelList }" var="policy" >
       	<c:set var="policyName" value="${policyName}${empty policyName ? '' : ','}${policy.policyName}" />
       	<c:set var="policycmp" value="${policycmp}${empty policycmp ? '' : ','}${policy.policyCompany}" />
       </c:forEach>
      <td>${policyName}</td>
      <td>${policycmp}</td>
      <td>
       <spring:url value="/op/addUserPolicy/${customer.customerId }" var="policyURL" />
       <a class="btn btn-primary" href="${policyURL }" role="button">Add Policy</a>
      </td>
      <td>
       <spring:url value="/op/details/update/${customer.customerId }" var="updateURL" />
       <a class="btn btn-primary" href="${updateURL }" role="button">Update</a>
      </td>
      <td>
       <spring:url value="/op/delete/${customer.customerId }" var="deleteURL" />
       <a class="btn btn-primary" href="${deleteURL }" role="button">Delete</a>
      </td>
     </tr>
    </c:forEach>
   </tbody>
  </table>
  <spring:url value="/op/add" var="addURL" />
  <a class="btn btn-primary" href="${addURL }" role="button">Add New Customer</a>
 </div>
</body>
</html>