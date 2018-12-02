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
.table-bordered{
padding-left:10px;
padding-right:10px;}
</style>
</head>
<body >
<nav class="navbar navbar-dark bg-dark">
    <div class="container-d-flex-justify-content-center"> <a style="color:white;" class="navbar-brand" href="admin_login.jsp">
        <b>Library Management System</b>
      </a> </div>
  </nav>
  <div class="col-md-12"><a class="btn btn-link" href="create_book.jsp" style="float: left;">Order Books</a></div>
  <div class="logout-btn"><form style="float: right;" action="ControllerServlet" method="post"><input style="float: right;" type="hidden" id="action" name="action" value="logout"/><input class="btn btn-link" type="submit" value="Log Out"/></form>
  </div>
  <table class="table table-bordered table-striped table-hover">
    <thead>
    
    </thead>
    <tbody>
    <tr>
 
    <!-- You can adjust the width of table columns as well -->
    <th class="col-md-8 text-center">Book Title</th>
 
    <!-- Use text alignment like text-center or text-right -->
    <th class="text-center">Edit</th>
    <th class="text-center">Delete</th>
    </tr>
    <c:forEach items="${book_list}" var="book">
                <tr>
                    <td><c:out value="${book.getTitle()}" /></td>
                    <td><button type="button" class="btn btn-link" data-toggle="modal" data-target="#editBook" data-book-id="${book.getid()}" data-book-title="${book.getTitle()}" data-book-isbn="${book.getISBN()}" data-book-author="${book.getAuthor()}" data-book-genre="${book.getGenre()}" data-book-publisher="${book.getPublisher()}" data-book-copies="${book.getQuantity()}" >Edit</button></td>
                    <td><button type="button" class="btn btn-link" data-toggle="modal" data-target="#deleteBook" data-book-title="${book.getTitle()}" data-book-id="${book.getid()}">Delete</button></td>
                </tr>
             </c:forEach> 
   
    </tbody>
    </table>
    <div class="modal fade" id="editBook" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="editModalLabel">Edit Details for: </h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        </div>
        <div class="modal-body">
         <form id="edit-book-form" action="ControllerServlet" method="post">
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Book ID:</label>
            <input type="text" class="form-control" id="id" value="" readonly>
          </div>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">ISBN:</label>
            <input type="text" class="form-control" id="isbn" value="" readonly>
          </div>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Title:</label>
            <input type="text" class="form-control" id="title" value="">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Author:</label>
            <input type="text" class="form-control" id="author" value="">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Genre:</label>
            <input type="text" class="form-control" id="genre" value="">
          </div>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Publisher:</label>
            <input type="text" class="form-control" id="publisher" value="">
          </div>
          
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Total Copies:</label>
            <input type="number" class="form-control" id="copies" value="" min="1" max="25"/>
          </div>
          <input type="hidden" id="action" value="edit_book"/>
        </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
           <input type="submit" form="edit-book-form" value="Save Changes"/>
        </div>
      </div>
    </div>
  </div>
  <div class="modal fade" id="deleteBook" role="dialog">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Are you sure?</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        </div>
        <div class="modal-body">
         <h5 class="message" id="message">Are you sure you want to delete book </h5>
        </div>
        <div class="modal-footer">
          <form id="delete-book-form" action="ControllerServlet" method="post">
          <input type="hidden" id="action" value="delete_book"/>
          <input type="hidden" id="bookId" value=""/>
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
  $('#editBook').on('show.bs.modal', function (event) {
	  
	  var button = $(event.relatedTarget)
	  var bookId = button.data('book-id')
	  var title = button.data('book-title')
	  var genre = button.data('book-genre')
	  var publisher = button.data('book-publisher')
	  var isbn = button.data('book-isbn')
	  var author = button.data('book-author')
	  var copies = button.data('book-copies')
	  
	  var modal = $(this)
	  modal.find('.modal-title').text('Edit Details for: ' + title)
	  modal.find('input[id="id"]').val(bookId)
	  modal.find('input[id="isbn"]').val(isbn)
	  modal.find('input[id="title"]').val(title)
	  modal.find('input[id="author"]').val(author)
	  modal.find('input[id="genre"]').val(genre)
	  modal.find('input[id="publisher"]').val(publisher)
	  modal.find('input[id="copies"]').val(copies)
	  modal.find('input[id="book-to-change"]').val(bookId)
	  //modal.find('input[id="isbn"]').val(isbn)
	  modal.find('input[id="change-title"]').val(title)
	  modal.find('input[id="change-author"]').val(author)
	  modal.find('input[id="change-genre"]').val(genre)
	  modal.find('input[id="change-publisher"]').val(publisher)
	  modal.find('input[id="change-copies"]').val(copies)
	})
</script>
<script>
  $('#deleteBook').on('show.bs.modal', function (event) {
	  
	  var button = $(event.relatedTarget)
	  
	  var title = button.data('book-title')
	  var bookId = button.data('book-id')
	  var modal = $(this)
	  
	  modal.find('.modal-body').text('Are you sure you want to delete '+title+'?')
	  modal.find('input[id="bookId"]').val(bookId)
	  
	  
	})
</script>

</body>

</html>