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
	
	/**
	 * This method has all the functions of the Servlet. It checks if the action is: login,logout,issue book,return book, edit user,
	 * edit book, delete user, delete book, create user, add book, add on Waitlist, remove from Waitlist, browse books,
	 * individual book history and view your current and past books.
	 * 
	 * 
	 * @param email Contains the email address entered by the user
	 * @param pass Contains the password entered by the user
	 * @param action Contains the action passed by the jsp pages to the Servlet.
	 * @param obj is an object of class User
	 * @param u is an object of class User
	 * @param user_ToEdit is an object of class User
	 * @param book is an object of class Book
	 * @param Title Contains the title of the Book.
	 * @param Author Contains the author of the Book.
	 * @param book_id Contains the id of the Book.
	 * @param ISBN Contains the ISBN of the Book.
	 * @param Publisher Contains the Publisher of the Book.
	 * @param Quantity Contains the quantity of the Book available.
	 * @param Genre Contains the genre of the Book.
	 * @param memberlist Contains the list of members.
	 * @param booklist Contains the list of books.
	 * @param Name Contains the name of a user.
	 * @param user_id Contains the id of a user.
	 * @param issues Contains the list of objects of books.
	 * @param objectlist Contains the list of objects of books.
	 * @param objectlist1 Contains the list of objects of books.
	 * @param fine Contains the fine a user is supposed to pay while returning the book.
	 * @return returns a <code>User</code> object with its appropriate details of the user.
	 */


    	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    	{
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0); // Proxies.

            chain.doFilter(req, res);
        }
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String action = "";
		action=request.getParameter("action");
		
		
		if(action.equalsIgnoreCase("login"))
		{
			
			 String email = request.getParameter("login_email"); 
//			 System.out.println(email+"(in servlet)");
		     String pass = request.getParameter("login_password");
		     User obj = mydbConnect.checkCredentials(email, pass);
		     //System.out.println(obj.getMemId());
	        	if (obj.getMemId()==0)
	        	{
	           out.println("Invalid Credentials");
	           //request.setAttribute("loginResult", "Error");
	           RequestDispatcher rs = request.getRequestDispatcher("loginPage.jsp"); 
	           rs.include(request, response);
	           }
	        	else
	        	{   
	        		u=obj;
//	        	    System.out.println(obj.getMemId());
//	        	    System.out.println(obj.getEmail());
//	        	    System.out.println(obj.getPassword());
//	        	
	        		String type=obj.getType();
//	        		System.out.println(obj.getType());
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
				out.println("Book added successfully.");
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
			//System.out.println("INSIDE CREATE_USER IN SERVLET" +obj.getEmail());
			
			try {
			boolean save;
			save=mydbConnect.addUser(obj);
			if(save==true)
			{
			out.println("Member added successfully.");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //form for member add.
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
				out.println("Edited book details successfully.");
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
				out.println("Edited iser details successfully.");
			}
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_book")) {
			
			String bookid=request.getParameter("bookId");
			int book_id=Integer.parseInt(bookid); 
			
			boolean remove=mydbConnect.deleteBook(book_id);
			if(remove==true)
			{
				out.println("Book deleted successfully.");
			}
			
			request.getRequestDispatcher("admin_login.jsp").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_user")) {
			
			String userid =request.getParameter("memId");
			int user_id=Integer.parseInt(userid);
//			System.out.println("MEM ID IN DELETE USER SERVLET: "+user_id);
			
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
			request.setAttribute("book", new Object[5]);
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
//			System.out.println("INSIDE EDIT_YOUR_DETAILS IN SERVLET" +u.getName());
//			System.out.println(u.getEmail());
//			System.out.println(u.getPassword());
			boolean save=mydbConnect.editUserDetails(u);
			if(save==true)
			{
				out.println("Edited details successfully.");
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
			
			int bookID=Integer.parseInt(request.getParameter("book-id"));
			
			boolean issue=mydbConnect.borrowBook(userID,bookID);
			if(issue==true)
			{
				out.println("Book issued successfully.");
			}
			else
			{
				out.println("Unable to issue the book. Try again later!");
			}
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
				out.println("You have to pay a fine of Rs. :" + fine);
			}
			else
			{
				out.println("Returned book successfully.");
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
			RequestDispatcher rd = request.getRequestDispatcher("loginPage.jsp");
            rd.forward(request, response);
			out.close();
			
		}
		else if(action.equalsIgnoreCase("calling_browse_books"))
		{
		
			List<Object[]> objectlist = new ArrayList<Object[]>();
			int user_id=u.getMemId();
			objectlist=mydbConnect.browseBooks(user_id);
			request.setAttribute("type", u.getType());
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
		else if(action.equalsIgnoreCase("search")) {
			String search_title=request.getParameter("search-title");
			String search_genre=request.getParameter("search-genre");
			String search_publisher=request.getParameter("search-publisher");
			String search_author=request.getParameter("search-author");
			String search_isbn=request.getParameter("search-isbn");
			Book toSearch=new Book();
			toSearch.setid(0);
			toSearch.setAuthor(search_author);
			toSearch.setAvailable(0);
			toSearch.setGenre(search_genre);
			toSearch.setISBN(search_isbn);
			toSearch.setPublisher(search_publisher);
			toSearch.setQuantity(0);
			toSearch.setTitle(search_title);
//			List <Book> object_list
			
		}
		
	}
		
}

