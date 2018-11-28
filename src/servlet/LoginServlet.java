package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnector;
import objects.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class LoginServlet extends HttpServlet {
	User login=new User();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("login_email");  //email and pass depend on what keyword is present on the login html page code
        String pass = request.getParameter("login_password");
        
        	DBConnector db= new DBConnector();
        	User obj = db.checkCredentials(email, pass);
        	if (obj==null)
        	{
           //out.println("Username or Password is incorrect");
           request.setAttribute("loginResult", true);
           RequestDispatcher rs = request.getRequestDispatcher("loginPage.jsp"); //instead of index whatever is our login page html name
           rs.include(request, response);
           }
        	else
        	{   login=obj;
        		String type=obj.getType();
        		if(type.equalsIgnoreCase("admin"));
        		RequestDispatcher rs = request.getRequestDispatcher("admin_login.jsp"); //webpage where its supposed to direct to
        		rs.forward(request, response);
        		rs = request.getRequestDispatcher("member_login.jsp"); //webpage where its supposed to direct to
        		rs.forward(request, response);
        	}
	}

}
