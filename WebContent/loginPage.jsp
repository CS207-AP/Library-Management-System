
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" shrink-to-fit=no">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="theme.css" type="text/css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css">
<style>
.form-group-row{
padding-left: 400px;
padding-top: 20px;}
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
.button{
padding-top:20px;
padding-left:680px;

}

}</style>
<script type="text/javascript">
        function preventBack() { window.history.forward(); }
        setTimeout("preventBack()", 0);
        window.onunload = function () { null };
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
            history.pushState(null, null, document.URL);
        });
    </script>

</head>

<body >
<nav class="navbar navbar-dark bg-dark">
    <div class="container-d-flex-justify-content-center"> <a style="color:white;" class="navbar-brand" href="loginPage.jsp">
        <b>Library Management System</b>
      </a> </div>
  </nav>
  <div class="py-5">
    <div class="container">
      <div class="row">
        <div class="col-md-8">
    <c:if test="${not empty loginResult}">
    <div class="alert alert-error" style="color:red;">
    <strong>Error!</strong> Invalid Credentials.
    </div>
    </c:if>
      
      
          <form id="login_form" class="" action="ControllerServlet?" method="post">
            <div class="form-group-row"> <label for="inputmailh" class="col-2 col-form-label">E-mail</label>
              <div class="col-10">
                <input form="login_form" type="email" class="form-control" name="login_email" placeholder="mail@example.com" required> </div>
            </div>
            <div class="form-group-row"> <label for="inputpasswordh" class="col-2 col-form-label">Password</label>
              <div class="col-10">
                <input form="login_form" type="password" class="form-control" name="login_password" placeholder="**********" required> </div>
            </div>
            <input type="hidden" id="action" name="action" value="login"/>
           
            <div class="button"><button type="submit" class="btn btn-primary">Submit</button>
            </div>
            
          </form>
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