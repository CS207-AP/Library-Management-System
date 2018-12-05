package servlet;

import java.io.IOException;



import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import dao.DBConnector;
import objects.Book;
import objects.User;


/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DBConnector mydbConnect = new DBConnector();
	Book book=new Book();
	User u = new User();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        chain.doFilter(req, res);
    }

	/**
	 * 
	 * The dopost method of the controller servlet in this project is an HTTP servlet which handles request from the jsp pages and provides response to it. The requests that the controller servlet accepts is:
	 *	<p>
	 *	1.Login: Accepts user details from the JSP page and calls method check Credentials in DBConnector.If the input is valid, then the user gets redirected to the admin_login or member_login page depending on the user type.
	 *	<p>
	 *  2.Logout: Terminates the current session.
	 *  <p>
	 *	3.Create Book: Accepts book details from the JSP page and stores those details in the Book object book. It then calls method addBook in DBConnector, which adds the book in the database. If the add is a success, we set attribute <code>success</code> to <code>addBook</code> and redirect to the admin_login page (only admin can add books to the library).
	 *	<p>
	 *  4.Create User: Accepts user details from the JSP page and stores those details in the User object obj. It then calls method add User in DBConnector, which adds the user in the database. If the add is a success, we set attribute <code>success</code> to <code>addBook</code> and redirect to the admin_login page (only admin can add users to the library).
	 *	<p>
	 *	5.Borrowing_ Book: Obtains the user id of the currently logged in user and accepts the book id of the book that needs to be issued from the JSP page. It then calls the method borrowBook in the DBConnector. If the issue book is a success, we set attribute <code>yes</code> to <code>borrowSuccess</code> , else we set attribute <code>yes</code> to <code>borrowFail</code>  and redirect to the admin_login page or member_login page whoever is logged in and is borrowing the book.
	 *	<p>
	 *	6.Returning_Book: Obtains the user id of the currently logged in user and accepts the book id of the book that needs to be returned from the JSP page. It then calls the method returnBook in the DBConnector. If there is a delay in returning the book(past the due date), we set attribute fine (variable containing the fine)  to <code>fine</code> .Else if the return book is a success, we set attribute <code>yes</code> to <code>returnBook</code> and redirect to the admin_login page or member_login page whoever is logged in and is borrowing the book.
     *	<p>
	 *	7.Edit Book: Accepts the book details of the book that needs to be edited, from the JSP page and stores it in Book object book. It then calls method editBook in DBConnector, which edits that particular book in the database. If the edit is a success, we set attribute <code>success</code> to <code>editBook</code> and redirect to the admin_login page (only admin can edit book details).
	 *  <p>
	 *  8.Delete Book: Accepts the book id of the book that needs to be deleted,from the JSP page. It then calls method deleteBook in DBConnector, which deletes that particular book in the database. If the delete is a success, we set attribute <code>success</code> to <code>deleteBook</code> and redirect to the admin_login page (only admin can delete a book).
	 *	<p>
	 *	9.Delete user:  Accepts the user id of the user that needs to be deleted, from the JSP page. It then calls method deleteMember in DBConnector, which deletes that particular member in the database only if the member has no book issued currently or is not on waitlist for any particular book. If the delete is a success, the message <code>Removed User from Database</code> is printed and is redirected to edit_accounts.jsp (only admin can delete a user).
	 *	<p>
	 *	10.Edit_user by Admin : Accepts the user details of the user that needs to be edited, from the JSP page and stores it in User object userToEdit. It then calls method editUserDetails in DBConnector, which edits that particular user details in the database. If the edit is a success, we set attribute <code>success</code> to <code>editUser</code> and redirect to the admin_login page (only admin can edit user details).
	 *	<p>
	 *	11.Edit_details by Member: Accepts the three parameters from the JSP page that the member can edit for himself i.e. name,email and password and stores it in User object u. It then calls method editUserDetails in DBConnector, which edits that particular members details in the database. If the edit is a success, the message <code>Edited Details Successfully</code> is printed and the page is redirected to member_login.
	 *	<p>
	 *	12.Calling_browse_books: Obtains the user id and the user type of the currently logged in user and a list of objects from the method browseBooks in the DBConnector and stores it in a list called objectlist. If the user type is admin, it sets attribute <code>yes</code> to <code>adminBrowsing</code>, else if the user type is member, it sets attribute <code>yes</code> to <code>memberBrowsing</code>. Then it sets attribute objectlist to <code>object_list</code> and the page is redirected to browse_books.jsp 
	 *	<p>
	 *	13.Add_to_Waitlist: Obtains the user id of the currently logged in user and accepts the book id of the book that he needs to issue but is not available in the library, from the JSP page. It then calls method addtoWaitlist which adds the user to the waitlist. If adding to waitlist is a success, it sets attribute <code>yes</code> to <code>waitlistSuccess</code>, else it sets attribute <code>yes</code> to <code>waitlistFailure</code> and the page is redirected to admin_login.jsp or member_login.jsp depending on who is currently logged in and wants to borrow this particular book.
	 *	<p>
	 *	14.Remove_from_Waitlist: Obtains the user id of the currently logged in member and accepts the book id of the book that he doesn't want to issue anymore (and is on the waitlist for this book), from the JSP page. It then calls method removeFromWaitlist which removes the users from the waitlist. If removing to waitlist is a success, it sets attribute <code>yes</code> to <code>remWaitlistS</code>, else it sets attribute <code>yes</code> to <code>remWaitlistF</code> and the page is redirected to admin_login.jsp or member_login.jsp depending on who is currently logged in and wants to remove himself from the waitlist for that book.
	 * <p>
	 *	15.Calling_view_your_books: It obtains the user id of the currently logged in member, an object list from the method getUserCurrentIssue and stores it a list called objectlist, and an object list from the method getUserIssueHistory and stores it in a list called objectlist1. It then sets attribute objectlist to <code>current_issues</code> and objectlist1 to <code>past_issues</code>. Then the page is redirected to view_your_books.jsp
	 *	<p>
	 *	16.Calling_individual_view_history:Accepts the book id and the title of the book from the JSP page.It then obtains a list of objects from the method getBooksIssueHistory in the DBConnector and stores it in a list called issues.Then it sets attribute issues to <code>history</code> and title to <code>booktitle</code> (for the particular book we obtained from the JSP page) and the page is redirected to individual_book_history.jsp 
	 *	<p>
	 *	17.Calling_current_issues: Obtains a list of objects from the method getAllBooksCurrentlyIssued in the DBConnector and stores it in a list called objectlist. Then it sets attribute objectlist to <code>currentIssues</code> and object[5] to <code>book</code> (for a particular book) and the page is redirected to current_issues_page.jsp. 
	 * <p>
     *  18.Calling_edit_books:Obtains a list of Books from getAllBooks method in the DBConnector and stores it in booklist. Then it sets attribute booklist to <code>book_list</code> and the page is redirected to edit_books.jsp
	 *  <p>
	 *  19.Calling_edit_accounts: Obtains a list of users from getAllUsers method in the DBConnector and stores it in memberlist.Then it sets attribute memberlist to <code>users</code> and the page is redirected to edit_accounts.jsp
	 *  <p>
	 *  20.Calling_past_issues: Obtains a list of books from the method getAllBooks in the DBConnector and stores it in allBooks. Then it sets attribute allBooks to <code>books</code> and the page is redirected to view_history.jsp.
	 *  <p>	
	 *  21.Calling edit_your_details: Obtains the name and email of the user from the JSP page and set attributes name to <code>name</code> and email to <code>email</code>. Then the page is redirected to edit_your_details.jsp
	 *  <p>
	 *  22.Search:It accepts the title, genre, publisher, author, ISBN of the book the user is searching for and stores it in a Book object toSearch. It then obtains a list of objects from the method searchBooks in the DBConnector and stores it in a list called objectlist. It also obtains the user id of the logged in member. Then it sets attribute objectlist to <code>object_list</code> and the page is redirected to browse_books.jsp
	 *  <p>
	 * @param email Contains the email address entered by the user
	 * @param pass Contains the password entered by the user.
	 * @param password Contains the password entered by the user.
	 * @param action Contains the action passed by the jsp pages to the Servlet.
	 * @param obj is an object of class User
	 * @param userToEdit is an object of class User
	 * @param u is an object of class User for currently logged in user.
	 * @param user_ToEdit is an object of class User
	 * @param book is an object of class Book
	 * @param toSearch is an object of class Book
	 * @param Title Contains the title of the Book.
	 * @param title Contains the title of the Book.
	 * @param Author Contains the author of the Book.
	 * @param author Contains the author of the Book.
	 * @param book_id Contains the id of the Book.
	 * @param bookid Contains the id of the Book.
	 * @param ISBN Contains the ISBN of the Book.
	 * @param Publisher Contains the Publisher of the Book.
	 * @param Quantity Contains the quantity of the Book available.
	 * @param Genre Contains the genre of the Book.
	 * @param quantity Contains the quantity of the Book available.
	 * @param genre Contains the genre of the Book.
	 * @param memberlist Contains the list of members.
	 * @param booklist Contains the list of books.
	 * @param allBooks Contains the list of books.
	 * @param Name Contains the name of a user.
	 * @param name Contains the name of a user.
	 * @param user_id Contains the id of a user.
	 * @param userid Contains the id of a user.
	 * @param issues Contains the list of objects of books.
	 * @param objectlist Contains the list of objects of books.
	 * @param objectlist1 Contains the list of objects of books.
	 * @param fine Contains the fine a user is supposed to pay while returning the book.
	 * @param out is an object of class PrintWriter
	 * @param rs is an object of class Request Dispatcher.
	 * @param save is a boolean variable which checks if the action is performed successully or not.
	 * @param delete is a boolean variable which checks if the action is performed successully or not.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String action = "";
		action=request.getParameter("action");
		
		
		if(action.equalsIgnoreCase("login"))
		{
			
			 String email = request.getParameter("login_email"); 
		     String pass = request.getParameter("login_password");
		     User obj = mydbConnect.checkCredentials(email, pass);
	        	if (obj.getMemId()==0)
	        	{
	          // out.println("Invalid Credentials");
	           request.setAttribute("loginResult", "Error");
	           RequestDispatcher rs = request.getRequestDispatcher("loginPage.jsp"); 
	           rs.include(request, response);
	           }
	        	else
	        	{   
	        		u=obj;
	        		String type=obj.getType();
	        		request.setAttribute("memberid", obj.getMemId());
	        		RequestDispatcher rs;
	        		if(type.equalsIgnoreCase("admin"))
	        		{
	        		rs = request.getRequestDispatcher("admin_login.jsp");
	        		}else {
	        		rs = request.getRequestDispatcher("member_login.jsp");
	        		}
	        		rs.forward(request, response);

	        	}
		}
		else if(action.equalsIgnoreCase("create_book"))
		{
			book.setTitle(request.getParameter("title"));
			book.setAuthor(request.getParameter("author"));
			book.setPublisher(request.getParameter("publisher"));
			book.setGenre(request.getParameter("genre"));
			book.setISBN(request.getParameter("isbn"));
			int quantity=Integer.parseInt(request.getParameter("number_of_copies"));
			book.setQuantity(quantity);
			book.setAvailable(quantity);
			System.out.println("INSIDE CREATE_BOOK OF SERVLET: "+book.getTitle());
			try {
			boolean save=mydbConnect.addBook(book);
			if(save==true)
			{
				request.setAttribute("addBook", "success");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response);
			}
			catch (Exception ex) {
			Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		}
		else if(action.equalsIgnoreCase("create_user"))
		{
			User obj = new User();
			String user_type=request.getParameter("type");
			obj.setType(user_type);
			String Name=request.getParameter("name");
			obj.setName(Name);
			String Email=request.getParameter("email");
			obj.setEmail(Email);
			String Password=request.getParameter("password");
			obj.setPassword(Password);
			
			try {
			boolean save;
			save=mydbConnect.addUser(obj);
			if(save==true)
			{
			request.setAttribute("addUser", "success");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //form for member add.
			}
			catch (Exception ex) {
				Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		
		else if(action.equalsIgnoreCase("calling_edit_books")) {
			
			List<Book> booklist = new ArrayList<Book>();
			booklist = mydbConnect.getAllBooks();
			request.setAttribute("book_list",booklist);//set list as attribute
			
			request.getRequestDispatcher("edit_books.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("edit_book")) {
			
			int book_ID=Integer.parseInt(request.getParameter("id"));
			book.setid(book_ID);
			String Title=request.getParameter("title");
			book.setTitle(Title);
			
			String Author=request.getParameter("author");
			book.setAuthor(Author);
			String ISBN=request.getParameter("isbn");
			book.setISBN(ISBN);
			String Publisher=request.getParameter("publisher");
			book.setPublisher(Publisher);
			String Genre=request.getParameter("genre");
			book.setGenre(Genre);
			int quantity=Integer.parseInt(request.getParameter("copies"));
			book.setQuantity(quantity);
			
			boolean save=mydbConnect.editBook(book);
			if(save==true)
			{
				request.setAttribute("editBook", "success");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //wherever it has to get redirected.
			
		}
		else if(action.equalsIgnoreCase("calling_edit_accounts")) {
			System.out.println("Inside edit_user in servlet");
			List<User> memberlist= new ArrayList<User>();
			memberlist=mydbConnect.getAllUsers();
			request.setAttribute("users",memberlist);//set list as attribute
			
			request.getRequestDispatcher("edit_accounts.jsp").include(request, response);
		}
		
		else if(action.equalsIgnoreCase("edit_user")) {
			
			User userToEdit = mydbConnect.getUserDetails(Integer.parseInt(request.getParameter("member-id")));
			
			String user_type=request.getParameter("member-type");
			userToEdit.setType(user_type);
			String Name=request.getParameter("member-name");
			userToEdit.setName(Name);
			
			String Email=request.getParameter("member-email");
			userToEdit.setEmail(Email);
		
			boolean save=mydbConnect.editUserDetails(userToEdit);
			
			if(save==true)
			{
				request.setAttribute("editUser", "success");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_book")) {
			
			String bookid=request.getParameter("bookId");
			int book_id=Integer.parseInt(bookid); 
			
			boolean remove=mydbConnect.deleteBook(book_id);
			if(remove==true)
			{
				request.setAttribute("deleteBook", "success");
			}
			
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_user")) {
			
			String userid =request.getParameter("memId");
			int user_id=Integer.parseInt(userid);
			
			boolean deleted;
			deleted=mydbConnect.deleteMember(user_id);
			if (!deleted)
			{
				out.println("User is has borrowed a book or is in a waitlist, cannot delete");
			}
			else
			{
				out.println("Removed User from Database.");
			}
			request.setAttribute("user", user_id);
			request.getRequestDispatcher("edit_accounts.jsp").include(request, response); //wherever it has to get redirected.

			
		}
		else if(action.equalsIgnoreCase("calling_current_issues")) {
			
			List<Object[]> objectlist = new ArrayList<Object[]>();
			objectlist=mydbConnect.getAllBooksCurrentlyIssued();
			request.setAttribute("currentIssues",objectlist);//set list as attribute
			request.setAttribute("book", new Object[5]);
			request.getRequestDispatcher("current_issues_page.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("calling_past_issues")) {
			
			List<Book> allBooks=new ArrayList<Book>();
			allBooks = mydbConnect.getAllBooks();
			
			request.setAttribute("books",allBooks);//set list as attribute
			request.getRequestDispatcher("view_history.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("calling_individual_view_history")) {
			
			String bookid=request.getParameter("bookid");
			int book_id=Integer.parseInt(bookid);
			String title = request.getParameter("booktitle");
			List<Object[]> issues = new ArrayList<Object[]>();
			issues=mydbConnect.getBookIssueHistory(book_id);
			request.setAttribute("booktitle", title);
			request.setAttribute("history",issues);
			request.getRequestDispatcher("individual_book_history.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("calling_view_your_books")) {
			
			int memberID=u.getMemId(); 
			List<Object[]> objectlist = new ArrayList<Object[]>();
			objectlist=mydbConnect.getUserCurrentIssue(memberID);
			request.setAttribute("current_issues",objectlist);
			List<Object[]> objectlist1 = new ArrayList<Object[]>();
			objectlist1=mydbConnect.getUserIssueHistory(memberID);
			request.setAttribute("past_issues",objectlist1);
			request.getRequestDispatcher("view_your_books.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("edit_your_details")) { 
			

			String Name=request.getParameter("name");
			u.setName(Name);
			String Email=request.getParameter("email");
			u.setEmail(Email);
			String Password=request.getParameter("password");
			u.setPassword(Password);
			boolean save=mydbConnect.editUserDetails(u);
			if(save==true)
			{
				out.println("Edited details successfully.");
			}
			request.getRequestDispatcher("member_login.jsp").include(request, response); 
		}
		
		else if(action.equalsIgnoreCase("calling_edit_your_details"))
		{
			String name=u.getName();
			request.setAttribute("name", name);
			String email=u.getEmail();
			request.setAttribute("email", email);
			request.getRequestDispatcher("edit_your_details.jsp").include(request, response);	
		}
		  
		else if(action.equalsIgnoreCase("borrowing_book"))   //user Issue Books
		{
			int user_id=u.getMemId();
			
			int book_id=Integer.parseInt(request.getParameter("book-id"));
			
			int issue=mydbConnect.borrowBook(user_id,book_id);
			if(issue==2)
			   request.setAttribute("excess", "yes");
			else if(issue==1)
				request.setAttribute("borrowSuccess", "yes");
			else if(issue==0)
				request.setAttribute("borrowFail", "yes");
			
			
			RequestDispatcher rs;
			if(u.getType().equalsIgnoreCase("admin"))
			 rs=request.getRequestDispatcher("admin_login.jsp");
			else
				rs=request.getRequestDispatcher("member_login.jsp");
			rs.include(request, response);
		}
		
		else if(action.equalsIgnoreCase("returning_book"))
		{
			System.out.println("Entered returning_book in servlet");
			int userID=u.getMemId();
			
			int bookID = Integer.parseInt(request.getParameter("book-id"));
			
			double fine=mydbConnect.returnBook(userID,bookID);
			if(fine>0)
			{
				request.setAttribute("fine", (double)fine);
			}
			else
			{
				request.setAttribute("returnBook", "success");
			}
			RequestDispatcher rs;
			if(u.getType().equalsIgnoreCase("admin"))
			 rs=request.getRequestDispatcher("admin_login.jsp");
			else
				rs=request.getRequestDispatcher("member_login.jsp");
			rs.include(request, response);
		}
		else if(action.equalsIgnoreCase("logout")) {
			
			HttpSession session=request.getSession();  
			session.invalidate();
			request.setAttribute("loginResult", null);
			RequestDispatcher rd = request.getRequestDispatcher("loginPage.jsp");
            rd.forward(request, response);
			out.close();
			
		}
		else if(action.equalsIgnoreCase("calling_browse_books"))
		{
		
			List<Object[]> objectlist = new ArrayList<Object[]>();
			int user_id=u.getMemId();
			objectlist=mydbConnect.browseBooks(user_id);
			if(u.getType().equalsIgnoreCase("admin"))
				request.setAttribute("adminBrowsing", "yes");
			else
				request.setAttribute("memberBrowsing", "yes");
            request.setAttribute("object_list",objectlist);
			request.getRequestDispatcher("browse_books.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("add_to_waitlist"))
		{
			int bookid = Integer.parseInt(request.getParameter("book-id"));
			int userid = u.getMemId();
			boolean x = mydbConnect.addtoWaitlist(bookid,userid);
			if(x)
			{
				request.setAttribute("waitlistSuccess", "yes");
			}
			else
			{
				request.setAttribute("waitlistFailure", "yes");
			}
			if(u.getType().equalsIgnoreCase("admin"))
			request.getRequestDispatcher("admin_login.jsp").include(request, response); 
			else
				request.getRequestDispatcher("member_login.jsp").include(request, response);
			
			
		}
		else if(action.equalsIgnoreCase("remove_from_waitlist"))
		{
			int bookid = Integer.parseInt(request.getParameter("book-id"));
			int userid = u.getMemId();
			boolean x = mydbConnect.removeFromWaitlist(bookid, userid);
			if(x) {
				request.setAttribute("remWaitlistS", "yes");
			}
			else
				request.setAttribute("remWaitlistF", "yes");
			if(u.getType().equalsIgnoreCase("admin"))
			request.getRequestDispatcher("admin_login.jsp").include(request, response); 
			else
				request.getRequestDispatcher("member_login.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("search")) {
			String search_title=request.getParameter("search_title");
			String search_genre=request.getParameter("search_genre");
			String search_publisher=request.getParameter("search_publisher");
			String search_author=request.getParameter("search_author");
			String search_isbn=request.getParameter("search_isbn");
			Book toSearch=new Book();
			System.out.println(search_title);
			toSearch.setid(0);
			toSearch.setAuthor(search_author);
			toSearch.setAvailable(0);
			toSearch.setGenre(search_genre);
			toSearch.setISBN(search_isbn);
			toSearch.setPublisher(search_publisher);
			toSearch.setQuantity(0);
			toSearch.setTitle(search_title);
			List<Object[]> objectlist = new ArrayList<Object[]>();
			int user_id=u.getMemId();
			objectlist=mydbConnect.searchBooks(toSearch,user_id);
			if(u.getType().equalsIgnoreCase("admin"))
			 request.setAttribute("adminBrowsing", "yes");
			else
				request.setAttribute("memberBrowsing", "yes");
		    request.setAttribute("object_list",objectlist);
			request.getRequestDispatcher("browse_books.jsp").include(request, response);
			
		}
		
	}
		
}

