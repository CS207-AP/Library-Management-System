package dao;

import util.DButil;
import objects.Book;
import java.time.*;
import objects.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static javax.swing.text.html.HTML.Tag.SELECT;

public class DBConnector {
	
	DButil dbUtil = new DButil();
	
	public User checkCredentials(String email, String password)
	{
		User user = new User();
		try {
            Connection conn = dbUtil.getConnection();
            String query = "SELECT memberId, email, memberType, name, password FROM member_list WHERE email = " + email+";";
            
            Statement st = conn.createStatement();
           
            ResultSet rs = st.executeQuery(query);
            
            
            while (rs.next()) {
            	String em = rs.getString("email");
            	String pw = rs.getString("password");
                if (em.equals(email)
                        && pw.equals(password) )
                
                    user.setMemId(rs.getInt("memId"));
                    user.setEmail(em);
                    user.setPassword(pw);
                    user.setType(rs.getString("type"));
                    user.setName(rs.getString("name"));
                }
            }
            
         catch (SQLException e) {
            System.err.println("Got an exception in checkcredentials in dbconnector");
            System.err.println(e.getMessage());
        }
		return user;
	}
	
	Book[] browseBooks() {
		
		Connection conn;
		Book [] books=null;
		try {
			conn = dbUtil.getConnection();
			 String query = "SELECT * FROM book_list";
			 String getNoOfBooks= "SELECT COUNT(*) FROM book_list";
		        
		      Statement st = conn.createStatement();
		     
		      ResultSet bookNoSet = st.executeQuery(getNoOfBooks);
		      
		      int noOfBooks = bookNoSet.getInt(0);
		      
		      books = new Book [noOfBooks];
		      
		      
		      ResultSet bookSet = st.executeQuery(query);
		      
		      for(int i=0;i<noOfBooks;i++) {
		    	  
		    	  books[i]= new Book();
		    	  
		    	  books[i].setTitle(bookSet.getString("book_title"));
		    	  books[i].setAuthor(bookSet.getString("book_author"));
		    	  books[i].setAvailable(bookSet.getInt("book_available"));
		    	  books[i].setQuantity(bookSet.getInt("book_quantity"));
		    	  books[i].setGenre(bookSet.getString("book_genre"));
		    	  books[i].setid(bookSet.getInt("book_id"));
		    	  
		      }
		      
		   	      
		      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error while getting books");
			e.printStackTrace();
		}
		
		return books;
		
	}

	
	
	boolean borrowBook(int memId, int bookId)
	{
	
		
		
		Connection connection;
		int i = 0;
		try {
			connection = dbUtil.getConnection();
			PreparedStatement ps;
			String query="SELECT COUNT(memId) FROM lms_db.currentissues";			
			 Statement st = connection.createStatement();         
	         ResultSet rs = st.executeQuery(query);
	         int noOfBooks=rs.getInt(0);
	         
	         if(noOfBooks>=2) {
	        	 System.out.println("Only two books bro");
	         }
	         
			ps = connection.prepareStatement("UPDATE lms_db.book_list SET available_copies = (available_copies +1) WHERE bookId = ?");
			ps.setInt(1, bookId);
			ps.executeUpdate();
			ps = connection.prepareStatement("DELETE from lms_db.waiting_list WHERE memId=? AND bookId=?;");
			ps.setInt(1, memId);
			ps.setInt(2, bookId);
			ps.executeUpdate();
	        ps = connection.prepareStatement("INSERT INTO lms_db.current_issues (bookId, memId, issue_date, due_date) VALUES (?, ?, ?, ?);");
            ps.setInt(1, bookId);
	        ps.setInt(2, memId);
	        LocalDate idate = LocalDate.now();
	        LocalDate ddate = idate.plusDays(14);
	        ps.setDate(3, java.sql.Date.valueOf(idate));
	        ps.setDate(4, java.sql.Date.valueOf(ddate));
	        
	        i = ps.executeUpdate();
	        
	        
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in borrowbook in dbconnector");
		}
		if (i == 1) 
            return true;
         else 
            return false;
	}
	
	double returnBook(int memId, int bookId) 
	{
		Connection connection;
		int i = 0;
		try {
			connection = dbUtil.getConnection();
			PreparedStatement ps;
			ps = connection.prepareStatement("UPDATE lms_db.book_list SET available_copies = (available_copies + 1) WHERE bookId = ?");
			ps.setInt(1, bookId);
			ps.executeUpdate();
	        ps = connection.prepareStatement("DELETE FROM lms_db.current_issues WHERE memId=? AND bookId=?;");
            ps.setInt(1, memId);
	        ps.setInt(2, bookId);
	        
	        ps = connection.prepareStatement("INSERT INTO lms_db.past_issues (bookId, memId, num) VALUES (?, ?, 1) ON DUPLICATE KEY UPDATE num = num + 1;");
	        ps.setInt(1, bookId);
	        ps.setInt(2, memId);
	        i = ps.executeUpdate();
	        
	        
	        
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in returnbook in dbconnector");
		}
		return calcFine(memId, bookId);
	}
	
	boolean currentlyIssued(int bookId, int memId)
	{
		Connection conn;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT book_Id, mem_Id, issue_date, due_date FROM lms_db.current_issues WHERE bookId = ? AND memId = ?;");
	        ps.setInt(1, bookId);
	        ps.setInt(2, memId);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next())
	        {
	        	return true;
	        }
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in currentlyissued in dbconnector");
		}
		return false;
        
	}
	boolean deleteBook(int bookId)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("DELETE from lms_db.book_list WHERE bookId = ?;");
	        ps.setInt(1, bookId);
	        x = ps.executeUpdate();
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in deletebook in dbconnector");
		}
		if (x == 1) 
            return true;
         else 
            return false;
	}
	
	double deleteMember(int memId)
	{
		Connection conn; int x=0;
		double fine=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("DELETE from lms_db.member_list WHERE memId = ?;");
	        ps.setInt(1, memId);
	        x = ps.executeUpdate();
	        ps = conn.prepareStatement("SELECT book_Id, mem_Id FROM lms_db.current_issues WHERE memId = ?;");
	        ResultSet rs = ps.executeQuery();
	        //int bookId = rs.getInt(1);
	        if(rs.next()) {
	        	PreparedStatement ps2 = conn.prepareStatement("UPDATE lms_db.book_list SET available_copies = (available_copies + 1) WHERE bookId = ?;");
	        	ps2.setInt(1, rs.getInt(1));
	        	ps2.executeUpdate();
	            fine = calcFine(memId, rs.getInt(1));
	        }
	        ps = conn.prepareStatement("DELETE from lms_db.current_issues WHERE memId = ?;");
	        ps.setInt(1, memId);
	        x = ps.executeUpdate();
	       
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in deletemember in dbconnector");
		}
		 return fine;
	}
	
	public boolean addBook(int bookId, String title, String genre, String author, String ISBN, int total_copies)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("INSERT into book_list (bookId, title, genre, author, ISBN, publisher total_copies, available_copies, waitlist) values (NULL, ?, ?, ?, ?, ?, ?, 0);");
	        ps.setString(2, title);
	        ps.setString(3, genre);
	        ps.setString(4, author);
	        ps.setString(5, ISBN);
	        ps.setInt(6, total_copies);
	        //ps.setInt(7, available_copies);
	        x = ps.executeUpdate();
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in addbook in dbconnector");
		}
		if (x == 1) 
            return true;
         else 
            return false;
	}
	
	public boolean addMember(String type, String email, String name, String password)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("INSERT into member_list (memId, type, email, name, password) values (0, ?, ?, ?, ?);");
	        ps.setString(2, type);
	        ps.setString(3, email);
	        ps.setString(4, name);
	        ps.setString(5, password);
	       
	        x = ps.executeUpdate();
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in addmember in dbconnector");
		}
		if (x == 1) 
            return true;
         else 
            return false;
	}
	
	boolean editBook(int memId, String type, String email, String name, String password)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("UPDATE book_list SET type=?, email=?, name=?, password=? WHERE memId=?;");
	        ps.setString(1, type);
	        ps.setString(2, email);
	        ps.setString(3, name);
	        ps.setString(4, password);
	        ps.setInt(5, memId);
	        
	        x = ps.executeUpdate();
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in editbook in dbconnector");
		}
		if (x == 1) 
            return true;
         else 
            return false;
	}
	
	boolean editMember(int bookId, String title, String genre, String author, String ISBN, String publisher, int total_copies, int available_copies)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("UPDATE book_list SET title=?, genre=?, author=?, ISBN=?, publisher=?, total_copies=?, available_copies=? WHERE bookId=?;");
	        ps.setString(1, title);
	        ps.setString(2, genre);
	        ps.setString(3, author);
	        ps.setString(4, ISBN);
	        ps.setInt(5, total_copies);
	        ps.setInt(6, available_copies);
	        ps.setInt(7,  bookId);
	        x = ps.executeUpdate();
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in editmemebr in dbconnector");
		}
		if (x == 1) 
            return true;
         else 
            return false;
	}
	
	double calcFine(int memId, int bookId) {
		Connection conn; int x=0;
		double fine=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT issue_date, due_date FROM lms_db.current_issues WHERE memId =? AND bookId=?;");
	        ps.setInt(1, memId);
	        ps.setInt(2, bookId);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next()) {
	        	LocalDate idate = rs.getDate(3).toLocalDate();
	        	LocalDate ddate = rs.getDate(4).toLocalDate();
	        	if(ddate.isAfter(idate)) {
	        	Period period = Period.between(ddate, idate);
	        	int daysElapsed = period.getDays();
	        	if(Math.abs(daysElapsed)>0)
	        	   fine = daysElapsed*20;
	        	}
	        }
	       
	        x = ps.executeUpdate();
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in calcfine in dbconnector");
		}
		return fine;
		
	}
	
	

}
        
	


