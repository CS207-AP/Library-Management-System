<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="theme.css" type="text/css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css">

<style>
.container-d-flex-justify-content-center{
padding-bottom: 140px;
padding-left: 475px;
padding-top: 10px;
background-color: black;
}
.navbar-brand{
padding-top: 50px;
padding-left:40px;
}
.homepage{
padding-left:10px;}
.title{
text-align: center;}

</style>
</head>
<body >
<nav class="navbar navbar-dark bg-dark">
    <div class="container-d-flex-justify-content-center"> <a class="navbar-brand" style="color:white;" href="admin_login.jsp">
        <b> Library Management System</b>
      </a> </div>
  </nav>
  <div class="homepage"><a href="member_login.jsp" style="float: left;">Go To Homepage</a></div>
  <div class="logout-btn"><form style="float: right;" action="ControllerServlet" method="post"><input style="float: right;" type="hidden" id="action" name="action" value="logout"/><input class="btn btn-link" type="submit" value="Log Out"/></form>
  </div>
  <br/>
   <h5 class="title">Current Issues:</h5>
  <table class="table table-bordered table-striped table-hover">
   
    <thead>
    
    </thead>
    <tbody>
    <tr>
 
    <!-- You can adjust the width of table columns as well -->
    <th class="col-md-2 text-center">Book ID</th>
   
    <th class="col-md-5 text-center">Book Title</th>
    <!-- Use text alignment like text-center or text-right -->
    <th class="text-center">Issue Date</th>
    <th class="text-center">Due Date</th>
    </tr>
    <c:forEach items="${objectlist}" var="books"> 
    <c:forEach items="${books}" var="book"> 
                <tr>
                    <td><c:out value="${book[0]}" /></td>
                   
                    <td><c:out value="${book[2]}" /></td>
                    <td class="idate"><c:out value="${book[3]}" /></td>
                    <td class="ddate"><c:out value="${book[4]}" /></td>
                </tr>  
            </c:forEach>
   </c:forEach>
   
        </tbody>
    </table>
     <h5 class="title">Past Issues:</h5>
    <table class="table table-bordered table-striped table-hover">
    <thead>
   
    </thead>
    <tbody>
    <tr>
 
    <!-- You can adjust the width of table columns as well -->
    <th class="col-md-2 text-center">Book ID</th>
    
    <th class="col-md-5 text-center">Book Title</th>
    <!-- Use text alignment like text-center or text-right -->
    <th class="text-center">Issue Date</th>
    <th class="text-center">Due Date</th>
    </tr>
    <c:forEach items="${objectlist1}" var="books"> 
    <c:forEach items="${books}" var="book"> 
                <tr>
                    <td><c:out value="${book[0]}" /></td>
                    
                    <td><c:out value="${book[2]}" /></td>
                    <td class="idate"><c:out value="${book[3]}" /></td>
                    <td class="ddate"><c:out value="${book[4]}" /></td>
                </tr>  
            </c:forEach>
   </c:forEach>
   
        </tbody>
    </table>
    
    
       
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  
</body>

</html>