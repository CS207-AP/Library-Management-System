package servlet;

import java.io.IOException;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	//final User currentuser=LoginServlet.login;
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		// form id="login_form", email id="login_email", password id="login_password"
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("login"))
		{
			 String email = request.getParameter("login_email"); 
		     String pass = request.getParameter("login_password");
		     User obj = mydbConnect.checkCredentials(email, pass);
	        	if (obj==null)
	        	{
	           //out.println("Username or Password is incorrect");
	           request.setAttribute("loginResult", true);
	           RequestDispatcher rs = request.getRequestDispatcher("loginPage.jsp"); 
	           rs.include(request, response);
	           }
	        	else
	        	{   u=obj;
	        		String type=obj.getType();
	        		request.setAttribute("memberid", obj.getMemId());
	        		if(type.equalsIgnoreCase("admin"));
	        		RequestDispatcher rs = request.getRequestDispatcher("admin_login.jsp");
	        		rs.forward(request, response);
	        		rs = request.getRequestDispatcher("member_login.jsp");
	        		rs.forward(request, response);
	        	}
		}
		else if(action.equalsIgnoreCase("create_book"))
		{
			book.setTitle(request.getParameter("title"));
			String author=request.getParameter("author");
			book.setAuthor("author");
			String publisher = request.getParameter("publisher");
			book.setPublisher("publisher");
			String genre=request.getParameter("genre");
			book.setGenre("author");
			String ISBN=request.getParameter("ISBN");
			book.setISBN("ISBN");
			String sbookID=request.getParameter("sbookID");
			int bookID=Integer.parseInt(sbookID);
			book.setid(bookID);
			String squantity=request.getParameter("quantity");
			int quantity=Integer.parseInt(squantity);
			book.setQuantity(quantity);
			String savailable=request.getParameter("savailable");
			int available=Integer.parseInt(savailable);
			book.setAvailable(available);
			
			
			try {
			boolean save=mydbConnect.addBook(book);
			if(save==true)
			{
				out.println("Book Added Successfully");
			}
			request.getRequestDispatcher("Add Book WebPage").include(request, response);
			}
			catch (Exception ex) {
			Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		}
		else if(action.equalsIgnoreCase("create_user"))
		{
			User obj = new User();
			String user_type=request.getParameter("User Type");
			obj.setType(user_type);
			String Name=request.getParameter("Name");
			obj.setName(Name);
			String Email=request.getParameter("Email");
			obj.setEmail(Email);
			String Password=request.getParameter("Password");
			obj.setPassword(Password);
			
			try {
			boolean save;
			save=mydbConnect.addUser(obj);
			if(save==true)
			{
			out.println("Member added successfully");
			}
			request.getRequestDispatcher("add member html page").include(request, response); //form for member add.
			}
			catch (Exception ex) {
				Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		
		else if(action.equalsIgnoreCase("calling_edit_books")) {
			
//			List<Object[]> objectlist = new ArrayList<Object[]>();
//			int user_id=u.getMemId();
//			objectlist=mydbConnect.browseBooks(user_id+"");
			List<Book> booklist = new ArrayList<Book>();
			booklist = mydbConnect.getAllBooks();
//			for(int i=0;i<objectlist.size();i++) {		
//				Object []array=objectlist.get(i);
//				booklist.add((Book)array[0]);
//			}
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
				out.println("Edited Book Successfully");
			}
			request.getRequestDispatcher("edit_books.jsp").include(request, response); //wherever it has to get redirected.
			
		}
		else if(action.equalsIgnoreCase("calling_edit_accounts")) {
			
			List<User> memberlist= new ArrayList<User>();
			memberlist=mydbConnect.getAllUsers();
			request.setAttribute("users",memberlist);//set list as attribute
			
			request.getRequestDispatcher("edit_user.jsp").include(request, response);
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
				out.println("Edited User details Successfully");
			}
			request.getRequestDispatcher("edit_accounts.jsp").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_book")) {
			
			String bookid=request.getParameter("bookId");
			int book_id=Integer.parseInt(bookid); 
			
			boolean remove=mydbConnect.deleteBook(book_id);
			if(remove==true)
			{
				out.println("BOOK DELETED SUCCESSFULLY!");
			}
			
			request.getRequestDispatcher("edit_books.jsp").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_user")) {
			
			String userid =request.getParameter("memId");
			int user_id=Integer.parseInt(userid);
			double fine=0.0;
			fine=mydbConnect.deleteMember(user_id);
			if (fine>0)
			{
				out.println("Fine Due is :"+ fine);
			}
			else
			{
				out.println("Removed User from Database.");
			}
			request.setAttribute("user", user_id);
			request.setAttribute("fine", fine);
			request.getRequestDispatcher("edit_accounts.jsp").include(request, response); //wherever it has to get redirected.

			
		}
		else if(action.equalsIgnoreCase("calling_current_issues")) {
			
			List<Object[]> objectlist = new ArrayList<Object[]>();
			objectlist=mydbConnect.getAllBooksCurrentlyIssued();
//			List<Book> getIssues = new ArrayList<Book>();
//			
//			for(int i=0;i<objectlist.size();i++) {		
//				Object []array=objectlist.get(i);
//				getIssues.add((Book)array[0]);
//			}
			request.setAttribute("currentIssues",objectlist);//set list as attribute
			request.getRequestDispatcher("current_issues_page.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("calling_past_issues")) {
			
//			List<Object[]> objectlist = new ArrayList<Object[]>();
//			objectlist=mydbConnect.getAllBooksCurrentlyIssued();
//			
//			List<Object[]> allObjects=mydbConnect.browseBooks(1);
			List<Book> allBooks=new ArrayList<Book>();
			allBooks = mydbConnect.getAllBooks();
//			
//			for(int i=0;i<allObjects.size();i++) {
//				
//				Object [] object=allObjects.get(i);
//				Book book = new Book();
//				
//				book=(Book)object[0];
//				allBooks.add(book);
//			}
			
			
			request.setAttribute("books",allBooks);//set list as attribute
			request.getRequestDispatcher("view_history.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("calling_individual_view_history")) {
			
			String bookid=request.getParameter("bookid");
			int bookID=Integer.parseInt(bookid);
			String title = request.getParameter("booktitle");
			List<Object[]> issues = new ArrayList<Object[]>();
			issues=mydbConnect.getBookIssueHistory(bookID);
//			List<Book> getHistorey = new ArrayList<Book>();
//			
//			for(int i=0;i<objectlist.size();i++) {		
//				Object []array=objectlist.get(i);
//				getHistory.add((Book)array[0]);
//			}
			//request.setAttribute("bookd", bookid);
			request.setAttribute("booktitle", title);
			request.setAttribute("history",issues);//set list as attribute
			request.getRequestDispatcher("individual_book_history.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("calling_view_your_books")) {
			
			int memberID=u.getMemId(); 
			List<Object[]> objectlist = new ArrayList<Object[]>();
			objectlist=mydbConnect.getUserCurrentIssue(memberID);
//			List<Book> getCIssues = new ArrayList<Book>();
//			
//			for(int i=0;i<objectlist.size();i++) {		
//				Object []array=objectlist.get(i);
//				getCIssues.add((Book)array[0]);
//			}
			
			request.setAttribute("current_issues",objectlist);//set list as attribute
			List<Object[]> objectlist1 = new ArrayList<Object[]>();
			objectlist1=mydbConnect.getUserIssueHistory(memberID);
//			List<Book> getPIssues = new ArrayList<Book>();
//			
//			for(int i=0;i<objectlist1.size();i++) {		
//				Object []array=objectlist1.get(i);
//				getPIssues.add((Book)array[0]);
//			}
			request.setAttribute("past_issues",objectlist1);//set list as attribute
			request.getRequestDispatcher("view_your_books.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("edit_your_details")) { //if user wants to change something
			
//			int memID=Integer.parseInt(request.getParameter("Member ID"));
//			u.setMemId(memID);
			String Name=request.getParameter("name");
			u.setName(Name);
			String Email=request.getParameter("email");
			u.setEmail(Email);
			String Password=request.getParameter("password");
			u.setPassword(Password);
			
			boolean save=mydbConnect.editUserDetails(u);
			if(save==true)
			{
				out.println("Edited User details Successfully");
			}
			request.getRequestDispatcher("member_login.jsp").include(request, response); //wherever it has to get redirected.
		}
		
		else if(action.equalsIgnoreCase("calling_edit_your_details"))
		{
			//int user_id=currentuser.getMemId();
			//request.setAttribute("userid", user_id);
			String name=u.getName();
			request.setAttribute("name", name);
			String email=u.getEmail();
			request.setAttribute("email", email);
			//String password=currentuser.getPassword();
			//request.setAttribute("password", password);
			//String type=currentuser.getType();
			//request.setAttribute("type",type);
			request.getRequestDispatcher("edit_your_details.jsp").include(request, response);	
		}
		
		else if(action.equalsIgnoreCase("borrowing_book"))   //user Issue Books
		{
			int userID=u.getMemId();
			String bookid="";
			request.getAttribute(bookid);
			int bookID=Integer.parseInt(bookid);
			
			boolean issue=mydbConnect.borrowBook(userID,bookID);
			if(issue==true)
			{
				out.println("Book Issued Successfully");
			}
			else
			{
				out.println("Unable to Issue the Book. Try again later!");
			}
			request.getRequestDispatcher("issue-books.jsp").include(request, response); //to connect the next page, check name of jsp.
		}
		
		else if(action.equalsIgnoreCase("returning_book"))
		{
			int userID=u.getMemId();
			
			String bookid="";
			request.getAttribute(bookid);
			int bookID=Integer.parseInt(bookid);
			
			double fine=mydbConnect.returnBook(userID,bookID);
			if(fine>0)
			{
				out.println("You have to pay a fine of Rs. :" + fine);
			}
			else
			{
				out.println("Returned Book Successfully!");
			}
			
			request.getRequestDispatcher("return-book.jsp").include(request, response); //to connect the next page,check name of jsp.
		}
		else if(action.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();
			response.sendRedirect("loginPage.jsp");
		}
		else if(action.equalsIgnoreCase("calling_browse_books"))
		{
			List<Object[]> objectlist = new ArrayList<Object[]>();
			int user_id=u.getMemId();
			objectlist=mydbConnect.browseBooks(user_id);
            request.setAttribute("object_list",objectlist);//set list as attribute
			request.getRequestDispatcher("browse_books.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("add_to_waitlist"))
		{
			int bookid = Integer.parseInt(request.getParameter("book-id"));
			int userid = u.getMemId();
			mydbConnect.addtoWaitlist(bookid,userid);
			
			if(u.getType().equalsIgnoreCase("admin"))
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //to connect the next page, check name of jsp.
			else
				request.getRequestDispatcher("member_login.jsp").include(request, response);
			
			
		}
		else if(action.equalsIgnoreCase("remove_from_waitlist"))
		{
			int bookid = Integer.parseInt(request.getParameter("book-id"));
			int userid = u.getMemId();
			mydbConnect.removeFromWaitlist(bookid, userid);
			
			if(u.getType().equalsIgnoreCase("admin"))
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //to connect the next page, check name of jsp.
			else
				request.getRequestDispatcher("member_login.jsp").include(request, response);
			
		}
		
	}
		
}

