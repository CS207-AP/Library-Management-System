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
.search{
display:inline-block;
padding-left:20px;
margin-left:10px;}
.homepage{
padding-left: 10px;}
</style>
<script>
$(document).ready(function(){
	var type=request.getAttribute("type");
    if(type=="admin")
    {	$("a").click(function(){
        $("#header").attr("href", "admin_login.jsp");
        $("#home").attr("href", "admin_login.jsp");
    });}
    else{
    	$("a").click(function(){
            $("#header").attr("href", "member_login.jsp");
            $("#home").attr("href", "member_login.jsp");
        });
    	
    }
});
</script>
</head>
<body >
<nav class="navbar navbar-dark bg-dark">
    <div class="container-d-flex-justify-content-center"> 
    <c:if test="${not empty adminBrowsing}">
    <a class="navbar-brand" style="color:white;" href="admin_login.jsp" id="header">
        <b> Library Management System</b>
      </a> </c:if>
      <c:if test="${not empty memberBrowsing}">
    <a class="navbar-brand" style="color:white;" href="member_login.jsp" id="header">
        <b> Library Management System</b>
      </a> </c:if>
      </div>
  </nav>
  <div class="homepage"><c:if test="${not empty adminBrowsing}"><a href="admin_login.jsp" style="float: left;" id="home">Go To Homepage</a></c:if><c:if test="${not empty memberBrowsing}"><a href="member_login.jsp" style="float: left;" id="home">Go To Homepage</a></c:if></div>
  <div class="logout-btn"><form style="float: right;" action="ControllerServlet" method="post"><input style="float: right;" type="hidden" id="action" name="action" value="logout"/><input class="btn btn-link" type="submit" value="Log Out"/></form>
  </div>
    <br/>
     <br/>
    <div class="search"><form action="ControllerServlet" method="post">
    <input type="text" name="search_title" placeholder="Macbeth"/>
    <input type="text" name="search_genre" placeholder="Thriller"/>
    <input type="text" name="search_author" placeholder="Shakespeare"/>
    <input type="text" name="search_publisher" placeholder="Penguin"/>
    <input type="text" name="search_isbn" placeholder="978-3-16-148410-0"/>
    <input type="hidden" name="action" value="search"/>
    <input type="submit" class="btn btn-primary" value="Search"/>
    </form>
    </div>
     <br/> 
     <br/>
  <table class="table table-bordered table-striped table-hover">
    <thead>
    
    </thead>
    <tbody>
    <tr>
 
    <!-- You can adjust the width of table columns as well -->
    <th class="col-md-4">Title</th>
    <th class="col-md-2">Genre</th>
    <th class="col-md-2">Author</th>
    <th class="col-md-2">Publisher</th>
    <th class="col-md-1"></th>
    <th class="col-md-1"></th>
    
    
    </tr>
    <c:forEach items="${object_list}" var="array" varStatus="status"> 
    
                <tr>
                    <td><c:out value="${array[0].getTitle()}" /></td>
                    <td><c:out value="${array[0].getGenre()}" /></td>
                    <td><c:out value="${array[0].getAuthor()}" /></td>
                    <td><c:out value="${array[0].getPublisher()}" /></td>
                    <td><c:if test="${array[1][1]==true}">
                         <form action="ControllerServlet" method="post">
                         <input type="hidden" name="book-id" value="<c:out value="${array[0].getid()}"/>"/>
                         <input type="hidden" name="action" value="returning_book"/>
                         <input type="submit" class="btn btn-primary" value="Return Book"/>
                         </form></c:if>
                         <c:if test="${array[1][0]==true}">
                         <form action="ControllerServlet" method="post">
                         <input type="hidden" name="book-id" value="<c:out value="${array[0].getid()}"/>" />
                         <input type="hidden" name="action" value="borrowing_book"/>
                         <input type="submit" class="btn btn-primary" value="Borrow Book"/>
                         </form></c:if>
                         <c:if test="${array[1][2]==true}">
                         <form action="ControllerServlet" method="post">
                         <input type="hidden" name="book-id" value="<c:out value="${array[0].getid()}"/>"/>
                         <input type="hidden" name="action" value="add_to_waitlist"/>
                         <input type="submit" class="btn btn-primary" value="Waitlist"/>
                         </form></c:if></td>
                         <td>
                         <c:if test="${array[1][3]==true}">
                         <form action="ControllerServlet" method="post">
                         <input type="hidden" name="book-id" value="<c:out value="${array[0].getid()}"/>"/>
                         <input type="hidden" name="action" value="remove_from_waitlist"/>
                         <input type="submit" class="btn btn-link" value="Remove from Waitlist"/>
                         </form></c:if></td>
                </tr>  
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