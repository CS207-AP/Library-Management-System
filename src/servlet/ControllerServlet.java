package servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Servlet.Model_View_Controller_Pattern;
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
		doGet(request, response);
		
		// form id="login_form", email id="login_email", password id="login_password"
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("create_book"))
		{
			book.setTitle(request.getParameter("title"));
			//same for author etc.
			try {
				boolean result1;
				result1 = mydbConnect.addBook(book);
				
			} catch (Exception ex) {
				Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
			
		}
		else if(action.equalsIgnoreCase("create_user"))
		{
			user.setName(request.getParameter("name"));
			
		}
		else if(action.equalsIgnoreCase("edit_book")) {}
		else if(action.equalsIgnoreCase("edit_user")) {}
		else if(action.equalsIgnoreCase("delete_book")) {}
		else if(action.equalsIgnoreCase("delete_user")) {}
		
	}

}
