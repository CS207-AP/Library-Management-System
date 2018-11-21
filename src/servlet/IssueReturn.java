package servlet;

import java.io.IOException;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.User;
import objects.Book;
import dao.DBConnector;
@WebServlet("/IssueReturn")
public class IssueReturn extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String action=request.getParameter("action"); //depends on jsp pages
		DBConnector db=new DBConnector();
		Book b=new Book();
		User id=new User();
		int bookID=0; // will get it from servlet.
		int userID=id.getMemId();// need to get user's id and book id,not sure if this is right
		if(action.equalsIgnoreCase("Issue"))   //again depends on jsp pages
		{
			boolean issue=db.borrowBook(userID,bookID);
			if(issue==true)
			{
				out.println("Book Issued Successfully");
			}
			else
			{
				out.println("Unable to Issue the Book. Try again later!");
			}
			request.getRequestDispatcher("website.html").include(request, response); //to connect the next page.
		}
		else if(action.equalsIgnoreCase("Return"))
		{
			double fine=db.returnBook(userID,bookID);
			if(fine>0)
			{
				out.println("You have to pay a fine of Rs. :" + fine);
			}
			else
			{
				out.println("Returned Book Successfully!");
			}
			
		}
		request.getRequestDispatcher("website.html").include(request, response); //to connect the next page.
		
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
