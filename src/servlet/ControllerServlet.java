package servlet;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

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
		
		if(action.equalsIgnoreCase("create_book"))
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
		else if(action.equalsIgnoreCase("edit_book")) {
			
			int book_ID=Integer.parseInt(request.getParameter("BOOK ID"));
			String Title=request.getParameter("Title");
			String Author=request.getParameter("Author");
			String ISBN=request.getParameter("ISBN");
			String Publisher=request.getParameter("Publisher");
			int quantity=Integer.parseInt(request.getParameter("Quantity"));
			DBConnector db=new DBConnector();
			boolean save=true;//=db.editBook(memID,user_type,Name,Email); will change accordingly to editbook method
			if(save==true)
			{
				out.println("Edited Book Successfully");
			}
			request.getRequestDispatcher("next page").include(request, response); //wherever it has to get redirected.
			
		}
		else if(action.equalsIgnoreCase("edit_user")) {
			
			String user_type=request.getParameter("User Type");
			int memID=Integer.parseInt(request.getParameter("Member ID"));
			String Name=request.getParameter("Name");
			String Email=request.getParameter("Email");
			DBConnector db=new DBConnector();
			boolean save=db.editUser(memID,user_type,Name,Email);
			if(save==true)
			{
				out.println("Edited User details Successfully");
			}
			request.getRequestDispatcher("next page").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_book")) {
			
			int book_id=Integer.parseInt(request.getParameter("BOOK ID")); //will get from jsp either this way or by attribute way like issue book
			DBConnector db= new DBConnector();
			boolean remove=db.deleteBook(book_id);
			if(remove==true)
			{
				out.println("BOOK DELETED SUCCESSFULLY!");
			}
			
			request.getRequestDispatcher("next page").include(request, response); //wherever it has to get redirected.
		}
		else if(action.equalsIgnoreCase("delete_user")) {
			
			int user_id=Integer.parseInt(request.getParameter("Member ID")); //will get from jsp either this way or by attribute way like issue book
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
		
	}

}
