package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBConnector;
import objects.Book;
/**
 * Servlet implementation class AddBook
 */
@WebServlet("/AddBook")
public class AddBook extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		
		String title=request.getParameter("title");
		String author=request.getParameter("author");
		String publisher = request.getParameter("publisher");
		String genre=request.getParameter("genre");
		String ISBN=request.getParameter("ISBN");
		String bookID=request.getParameter("sbookID");
		String squantity=request.getParameter("quantity");
		int quantity=Integer.parseInt(squantity);
		String savailable=request.getParameter("savailable");
		int available=Integer.parseInt(savailable);
		DBConnector db=new DBConnector();
		boolean save=db.addBook(bookID,title,genre,author,publisher,ISBN, quantity);
		if(save==true)
		{
			out.println("Book Added Successfully");
		}
		request.getRequestDispatcher("Add Book WebPage").include(request, response);
		
		out.close();
	}

}
