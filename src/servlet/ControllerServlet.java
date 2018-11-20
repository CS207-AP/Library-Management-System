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
	User user = new User();
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
			String title=request.getParameter("title");
			book.setTitle("title");
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
			String memID=request.getParameter("Member ID");
			String Name=request.getParameter("Name");
			String Email=request.getParameter("Email");
			String Password=request.getParameter("Password");
			DBConnector db=new DBConnector();
			try {
			boolean save;
			save=db.addMember(memID,Name,Email,Password);
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
		else if(action.equalsIgnoreCase("edit_book")) {}
		else if(action.equalsIgnoreCase("edit_user")) {}
		else if(action.equalsIgnoreCase("delete_book")) {}
		else if(action.equalsIgnoreCase("delete_user")) {}
		
	}

}
