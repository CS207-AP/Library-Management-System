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

.logout-btn{
padding-right:10px;
}
.col-md-9{
padding-left: 350px;}
.button{
padding-top:380px;
padding-left:780px;}
.homepage{
padding-left:10px;}
.input[type="radio"]{
vertical-align:middle;}

</style>

</head>

<body >
<nav class="navbar navbar-dark bg-dark">
    <div class="container-d-flex-justify-content-center"> <a style="color:white;" class="navbar-brand" href="admin_login.jsp">
        <b>Library Management System</b>
      </a> </div>
  </nav>
  <table style="width:100%">
  <tbody>
  <tr>
  <td width="45%"><div><a class="btn btn-link" href="admin_login.jsp">Go To Homepage</a></div></td>
  <td width="50%"><div><form action="ControllerServlet" method="post"><input type="hidden" id="action" name="action" value="calling_edit_accounts"/><input class="btn btn-link" type="submit" value="View Member List"/></form></div></td>
  <td width="20%"><div class="logout-btn"><form action="ControllerServlet" method="post"><input type="hidden" id="action" name="action" value="logout"/><input class="btn btn-link" type="submit" value="Log Out"/></form></div></td>
  </tr></tbody></table>
    <br/>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-9">
          <form id="create_user" class="" action="ControllerServlet" method="post">
            <div class="form-group row"> <label for="inputmailh" class="col-2 col-form-label">Name</label>
              <div class="col-10">
                <input form="create_user" type="text" class="form-control" name="name" placeholder="Adam"> </div>
            </div>
            <div class="form-group row"> <label for="type" class="col-2 col-form-label">Type</label>
              <div class="col-10" style="white-space:nowrap;">
              <input form="create_user" type="radio" class="form-control" id="type" name="type" value="member" checked="yes"> <label class="radlabel">Regular Member</label>
                <input form="create_user" type="radio" class="form-control" name="type" value="admin"><label class="radlabel">Admin</label><br></div>
            </div>
            <div class="form-group row"> <label for="inputpasswordh" class="col-2 col-form-label">Email</label>
              <div class="col-10">
                <input form="create_user" type="text" class="form-control" name="email" placeholder="mail@example.com"> </div>
            </div>
            <div class="form-group row"> <label for="inputmailh" class="col-2 col-form-label">Password</label>
              <div class="col-10">
                <input form="create_user" type="password" class="form-control" name="password" placeholder="*********"> </div>
            </div>
            
            <input type="hidden" name="action" value="create_user"/>  </form> </div>
            <div class="button"><button form="create_user" type="submit" class="btn btn-primary">Create User</button>
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