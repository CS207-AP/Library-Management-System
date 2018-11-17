<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> --%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" type="text/css">
  <link rel="stylesheet" href="theme.css" type="text/css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" type="text/css">
<style>
input[type="submit"]{
    /* change these properties to whatever you want */
    background-color: #555;
    color: #fff;
    border-radius: 10px;
}
</style>
</head>
<body >
<nav class="navbar navbar-dark bg-dark">
    <div class="container d-flex justify-content-center"> <a class="navbar-brand" href="#">
        <b>Library Management System</b>
      </a> </div>
  </nav>
  <table class="table table-bordered table-striped table-hover">
    <thead>
    
    </thead>
    <tbody>
    <tr>
 
    <!-- You can adjust the width of table columns as well -->
    <th class="col-md-10">Book Title</th>
 
    <!-- Use text alignment like text-center or text-right -->
    <th class="text-center">Edit</th>
    <th class="text-center">Delete</th>
    </tr>
    <%-- <c:forEach items="${users}" var="user"> --%>
                <tr>
                    <td><%-- <c:out value="${book.getTitle()}" /> --%>User 1</td>
                    <td><button type="button" class="btn btn-link" data-toggle="modal" data-target="#editAccount" data-mem-id="ID 2019" data-mem-name="Aastha" data-mem-email="ok@gmail.com" data-mem-type="regular">Edit</button></td>
                    <td><button type="button" class="btn btn-link" data-toggle="modal" data-target="#deleteAccount" data-mem-id="123" data-mem-name="Aastha">Delete</button></td>
                </tr>
            <%-- </c:forEach> --%>
   
    <tr>
    <td><a href="#"></a></td>
    <td><a href="#" class="btn btn-link">Edit</a></td>
    <td>Delete</td>
    </tr>
    </tbody>
    </table>
    <div class="modal fade" id="editAccount" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="editModalLabel">Edit Details for: </h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        </div>
        <div class="modal-body">
         <form id="edit-account-form" action="" method="post">
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Member ID:</label>
            <input type="text" class="form-control" id="member-id" value="" readonly>
          </div>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Name:</label>
            <input type="text" class="form-control" id="member-name" value="">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Email ID:</label>
            <input type="text" class="form-control" id="member-email" value="">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Member Type:</label>
            <input type="text" class="form-control" id="member-type" value="">
          </div>
          
        </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
           <input type="submit" form="edit-account-form" value="Save Changes"/>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="deleteAccount" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Are you sure?</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        </div>
        <div class="modal-body">
         <h5 class="message" id="message">Are you sure you want to delete member </h5>
        </div>
        <div class="modal-footer">
          <form id="delete-account-form" action="" method="post">
          <input type="hidden" id="deleteAcc" value=""/>
          <input type="submit" value="Yes"/>
          
          </form >
           <button type="button" class="btn btn-danger" data-dismiss="modal">No</button>
        </div>
      </div>
    </div>
  </div>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  <script>
  $('#editAccount').on('show.bs.modal', function (event) {
	  
	  var button = $(event.relatedTarget)
	  var memId = button.data('mem-id')
	  var name = button.data('mem-name')
	  var email = button.data('mem-email')
	  var type = button.data('mem-type')
	  var modal = $(this)
	  modal.find('.modal-title').text('Edit Details for: ' + name)
	  modal.find('input[id="member-id"]').val(memId)
	  modal.find('input[id="member-name"]').val(name)
	  modal.find('input[id="member-email"]').val(email)
	  modal.find('input[id="member-type"]').val(type)
	  modal.find('input[id="account-to-change"]').val(memId)
	  modal.find('input[id="change-name"]').val(name)
	  modal.find('input[id="change-email"]').val(email)
	  modal.find('input[id="change-type"]').val(type)
	  
	})
</script>
<script>
  $('#deleteAccount').on('show.bs.modal', function (event) {
	  
	  var button = $(event.relatedTarget)
	  var memId = button.data('mem-id')
	  var name = button.data('mem-name')
	  var modal = $(this)
	  
	  modal.find('.modal-body').text('Are you sure you want to delete member '+name+'?')
	  modal.find('input[id="deleteAcc"]').val(memId)
	  
	  
	})
</script>

</body>

</html>