<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" shrink-to-fit=no">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/theme.css" type="text/css">
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
.nav-item{
padding-bottom: 10px;
}

.btn-primary{
width: 50%;
font-size: 16px;
transition-duration: 0.4s;}

.btn-primary:hover{
background-color: white;
color: #3E76A7;}

.logout-btn{
padding-right:10px;
}
.homepage{
padding-left:10px;}
</style>
</head>
<body>
  <nav class="navbar navbar-dark bg-dark">
    <div class="container-d-flex-justify-content-center"> <a class="navbar-brand" style="color:white;" href="admin_login.jsp">
        <b> Library Management System</b>
      </a> </div>
  </nav>
  <div class="logout-btn"><form style="float: right;" action="ControllerServlet" method="post"><input style="float: right;" type="hidden" name="action" name="action" value="logout"/><input class="btn btn-link" type="submit" value="Log Out"/></form>
  </div>
  
    <br/>
    <br/>
    <c:if test="${not empty deleteBook}">
    <div class="alert alert-success" style="color:green;">
    <strong>Success!</strong> Book deleted successfully.
    </div>
    </c:if>
    <c:if test="${not empty addBook}">
    <div class="alert alert-success" style="color:green;">
    <strong>Success!</strong> Book added successfully.
    </div>
    </c:if>
    <c:if test="${not empty editUser}">
    <div class="alert alert-success" style="color:green;">
    <strong>Success!</strong> Edited user details successfully.
    </div>
    </c:if>
     <c:if test="${not empty editBook}">
    <div class="alert alert-success" style="color:green;">
    <strong>Success!</strong> Edited book details successfully.
    </div>
    </c:if>
     <c:if test="${not empty addUser}">
    <div class="alert alert-success" style="color:green;">
    <strong>Success!</strong> Member added successfully.
    </div>
    </c:if>
    <c:if test="${not empty borrowSuccess}">
    <div class="alert alert-success" style="color:green;">
    <strong>Success!</strong> Book issued successfully.
    </div>
    </c:if>
    <c:if test="${not empty borrowFail}">
    <div class="alert alert-error" style="color:red;">
    <strong>Oops!</strong> Book could not be issued.
    </div>
    </c:if>
    <c:if test="${not empty returnBook}">
    <div class="alert alert-success" style="color:green;">
    <strong>Success!</strong> Book returned successfully.
    </div>
    </c:if>
    <c:if test="${not empty fine}">
    <div class="alert alert-error" style="color:red;">
    <strong>Oops!</strong> You must pay a fine of Rs. ${fine}.
    </div>
    </c:if>
    <c:if test="${not empty waitlistFailure}">
    <div class="alert alert-error" style="color:red;">
    <strong>Oops!</strong> Could not add to waitlist.
    </div>
    </c:if> 
    <c:if test="${not empty waitlistSuccess}">
    <div class="alert alert-success" style="color:green;">
    <strong>Success!</strong> You have been added to the waitlist.
    </div>
    </c:if>
    <c:if test="${not empty remWaitlistS}">
    <div class="alert alert-success" style="color:green;">
    <strong>Success!</strong> You have removed yourself from the waitlist.
    </div>
    </c:if>
    <c:if test="${not empty remWaitlistF}">
    <div class="alert alert-error" style="color:red;">
    <strong>Oops!</strong> You could not remove yourself from the waitlist.
    </div>
    </c:if>
  <div class="py-3">
    <div class="container">
      <div class="row">
      
        <div class="col-md-12 text-center d-md-flex justify-content-between align-items-center">
          <ul class="nav d-flex justify-content-center">
            <li class="nav-item"> <form action="ControllerServlet" method="post">
            							<input type="hidden" name="action" value="calling_browse_books"/>
                                        <input class="btn btn-primary" type="submit" value="Browse Books"/> </form> </li> 
            <li class="nav-item"> <form action="ControllerServlet" method="post">
            							<input type="hidden" name="action" value="calling_edit_books"/>
                                        <input class="btn btn-primary" type="submit" value="Edit Books"/> </form> </li>
            <li class="nav-item"> <form action="ControllerServlet" method="post">
            							<input type="hidden" name="action" value="calling_edit_accounts"/>
                                        <input class="btn btn-primary" type="submit" value="Edit Account Details"/> </form> </li>
            <li class="nav-item"> <form action="ControllerServlet" method="post">
            							<input type="hidden" name="action" value="calling_current_issues"/>
                                        <input class="btn btn-primary" type="submit" value="View Current Issues"/> </form> </li>
            <li class="nav-item"> <form action="ControllerServlet" method="post">
            							<input type="hidden" name="action" value="calling_past_issues"/>
                                        <input class="btn btn-primary" type="submit" value="View Past Issues"/> </form> </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

</body>

</html>