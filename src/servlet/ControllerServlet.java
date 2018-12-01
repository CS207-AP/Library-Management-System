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
	final User currentuser=LoginServlet.login;
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
			
			DBConnector db=new DBConnector();
			try {
			boolean save=db.addBook(book);
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
			String user_type=request.getParameter("User Type");
			u.setType(user_type);
			String Name=request.getParameter("Name");
			u.setName(Name);
			String Email=request.getParameter("Email");
			u.setEmail(Email);
			String Password=request.getParameter("Password");
			u.setPassword(Password);
			DBConnector db=new DBConnector();
			try {
			boolean save;
			save=db.addUser(u);
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
			DBConnector db=new DBConnector();
			List<Object[]> objectlist = new ArrayList<Object[]>();
			int user_id=currentuser.getMemId();
			objectlist=db.browseBooks(user_id+"");
			List<Book> booklist = new ArrayList<Book>();
			
			for(int i=0;i<objectlist.size();i++) {		
				Object []array=objectlist.get(i);
				booklist.add((Book)array[0]);
			}
			request.setAttribute("book_list",booklist);//set list as attribute
			
			request.getRequestDispatcher("edit-books.jsp").include(request, response);
			
		}
		else if(action.equalsIgnoreCase("edit_book")) {
			
			int book_ID=Integer.parseInt(request.getParameter("BOOK ID"));
			book.setid(book_ID);
			String Title=request.getParameter("Title");
			book.setTitle(Title);
			String Author=request.getParameter("Author");
			book.setAuthor(Author);
			String ISBN=request.getParameter("ISBN");
			book.setISBN(ISBN);
			String Publisher=request.getParameter("Publisher");
			book.setPublisher(Publisher);
			String Genre=request.getParameter("Genre");
			book.setGenre(Genre);
			int quantity=Integer.parseInt(request.getParameter("Quantity"));
			book.setQuantity(quantity);
			DBConnector db=new DBConnector();
			boolean save=db.editBook(book);
			//if(save==true)
			//{
			//	out.println("Edited Book Successfully");
			//}
			request.getRequestDispatcher("next page").include(request, response); //wherever it has to get redirected.
			
		}
		else if(action.equalsIgnoreCase("calling edit_accounts")) {
			DBConnector db=new DBConnector();
			List<User> memberlist= new ArrayList<User>();
			memberlist=db.getAllUsers();
			request.setAttribute("Member_list",memberlist);//set list as attribute
			
			request.getRequestDispatcher("edit-user.jsp").include(request, response);
		}
		
		else if(action.equalsIgnoreCase("edit_user")) {
			
			String user_type=request.getParameter("User Type");
			u.setType(user_type);
			int memID=Integer.parseInt(request.getParameter("Member ID"));
			u.setMemId(memID);
			String Name=request.getParameter("Name");
			u.setName(Name);
			String Email=request.getParameter("Email");
			u.setEmail(Email);
			DBConnector db=new DBConnector();
			boolean save=db.editUserDetails(currentuser,u);
			if(save==true)
			{
				out.println("Edited User details Successfully");
			}
			request.getRequestDispatcher("next page").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_book")) {
			
			String bookid="";
			request.getAttribute(bookid);
			int book_id=Integer.parseInt(bookid); 
			DBConnector db= new DBConnector();
			boolean remove=db.deleteBook(book_id);
			if(remove==true)
			{
				out.println("BOOK DELETED SUCCESSFULLY!");
			}
			
			request.getRequestDispatcher("next page").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_user")) {
			
			String userid ="";
			request.getAttribute(userid);
			int user_id=Integer.parseInt(userid);
			DBConnector db=new DBConnector();
			double delete=db.deleteMember(user_id);
			if (delete>0)
			{
				out.println("Fine Due is :"+ delete);
			}
			else
			{
				out.println("Removed User from Database.");
			}
			request.getRequestDispatcher("next page").include(request, response); //wherever it has to get redirected.

			
		}
		else if(action.equalsIgnoreCase("calling_current_issues")) {
			DBConnector db=new DBConnector();
			List<Object[]> objectlist = new ArrayList<Object[]>();
			objectlist=db.getAllBooksCurrentlyIssued();
			List<Book> getIssues = new ArrayList<Book>();
			
			for(int i=0;i<objectlist.size();i++) {		
				Object []array=objectlist.get(i);
				getIssues.add((Book)array[0]);
			}
			request.setAttribute("getIssues",getIssues);//set list as attribute
			request.getRequestDispatcher("current_issues_page.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("calling_individual_book_history")) {
			DBConnector db=new DBConnector();
			String bookid="";
			request.getAttribute(bookid);
			int bookID=Integer.parseInt(bookid);
			List<Object[]> objectlist = new ArrayList<Object[]>();
			objectlist=db.getBookIssueHistory(bookID);
			List<Book> getHistory = new ArrayList<Book>();
			
			for(int i=0;i<objectlist.size();i++) {		
				Object []array=objectlist.get(i);
				getHistory.add((Book)array[0]);
			}
			request.setAttribute("getHistory",getHistory);//set list as attribute
			request.getRequestDispatcher("individual_book_history.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("calling_view_your_books")) {
			DBConnector db=new DBConnector();
			int memberID=currentuser.getMemId(); 
			List<Object[]> objectlist = new ArrayList<Object[]>();
			objectlist=db.getUserCurrentIssue(memberID);
			List<Book> getCIssues = new ArrayList<Book>();
			
			for(int i=0;i<objectlist.size();i++) {		
				Object []array=objectlist.get(i);
				getCIssues.add((Book)array[0]);
			}
			
			request.setAttribute("getCIssues",getCIssues);//set list as attribute
			List<Object[]> objectlist1 = new ArrayList<Object[]>();
			objectlist1=db.getUserIssueHistory(memberID);
			List<Book> getPIssues = new ArrayList<Book>();
			
			for(int i=0;i<objectlist1.size();i++) {		
				Object []array=objectlist1.get(i);
				getPIssues.add((Book)array[0]);
			}
			request.setAttribute("getPIssues",getPIssues);//set list as attribute
			request.getRequestDispatcher("view_your_books.jsp").include(request, response);
			
		}
		
		else if(action.equalsIgnoreCase("edit_details")) { //if user wants to change something
			
			int memID=Integer.parseInt(request.getParameter("Member ID"));
			u.setMemId(memID);
			String Name=request.getParameter("Name");
			u.setName(Name);
			String Email=request.getParameter("Email");
			u.setEmail(Email);
			String Password=request.getParameter("password");
			u.setPassword(Password);
			DBConnector db=new DBConnector();
			boolean save=db.editUserDetails(currentuser,u);
			if(save==true)
			{
				out.println("Edited User details Successfully");
			}
			request.getRequestDispatcher("next page").include(request, response); //wherever it has to get redirected.
		}
		
		else if(action.equalsIgnoreCase("calling_edit_your_details"))
		{
			int user_id=currentuser.getMemId();
			request.setAttribute("userid", user_id);
			String name=currentuser.getName();
			request.setAttribute("name", name);
			String email=currentuser.getEmail();
			request.setAttribute("email", email);
			String password=currentuser.getPassword();
			request.setAttribute("password", password);
			String type=currentuser.getType();
			request.setAttribute("type",type);
			request.getRequestDispatcher("edit_your_details.jsp").include(request, response);	
		}
		
		else if(action.equalsIgnoreCase("Issue Book"))   //user Issue Books
		{
			int userID=currentuser.getMemId();
			String bookid="";
			request.getAttribute(bookid);
			int bookID=Integer.parseInt(bookid);
			DBConnector db=new DBConnector();
			boolean issue=db.borrowBook(userID,bookID);
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
		
		else if(action.equalsIgnoreCase("Return"))
		{
			int userID=currentuser.getMemId();
			
			String bookid="";
			request.getAttribute(bookid);
			int bookID=Integer.parseInt(bookid);
			DBConnector db=new DBConnector();
			double fine=db.returnBook(userID,bookID);
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
		
		
	}
		
}

