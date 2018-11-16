package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DBConnector;

@WebServlet("/AddMember")
public class AddMember extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String memID=request.getParameter("Member ID");
		String Name=request.getParameter("Name");
		String Email=request.getParameter("Email");
		String Password=request.getParameter("Password");
		DBConnector db=new DBConnector();
		boolean save;
		save=db.addMember(memID,Name,Email,Password);
		if(save==true)
		{
		out.println("Member added successfully");
		}
		request.getRequestDispatcher("add member html page").include(request, response); //form for member add.
		out.close();
		}
}


