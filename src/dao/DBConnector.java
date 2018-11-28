package dao;

import util.DButil;
import objects.Book;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import objects.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnector {
	
	DButil dbUtil = new DButil();
	
	/**
	 * This method returns a User object with its member variables after checking if login details are valid.
	 * 
	 * @param email Contains the email address entered by the user
	 * @param password Contains the password entered by the user
	 * @return returns a <code>User</code> object with its appropriate details of the user.
	 */
	public User checkCredentials(String email, String password)
	{
		User user = new User();
		try {
            Connection conn = dbUtil.getConnection();
            String query = "SELECT user_id, user_type, user_email, user_name, user_password FROM users WHERE email = "+email+";";
            
            Statement st = conn.createStatement();
           
            ResultSet rs = st.executeQuery(query);
            
            
            while (rs.next()) {
            	String em = rs.getString("user_email");
            	String pw = rs.getString("user_password");
            	
                if (em.equals(email)&& pw.equals(password) ) {
                
                    user.setMemId(rs.getInt("user_id"));
                    user.setEmail(em);
                    user.setPassword(pw);
                    user.setType(rs.getString("user_type"));
                    user.setName(rs.getString("user_name"));
                    
                	}
                }
            conn.close();
            
	}catch (SQLException e) {
            System.err.println("Got an exception in checkcredentials in dbconnector");
            System.err.println(e.getMessage());
        }
		return user;
	}
	
	public List<Object[]> browseBooks(String user_id) {
		
		Connection conn;
		List<Object[]> combinedList= new ArrayList<Object[]>();
		try {
			conn = dbUtil.getConnection();
			
			  String query = "SELECT * FROM books";		        
		      Statement st = conn.createStatement();	     	      
		      ResultSet bookSet = st.executeQuery(query);		      
		      
		      while(bookSet.next()) {
		    	  
		    	  Object[] array= new Object[2];
		    	  boolean[] buttons= new boolean[4];
		    	  
		    	  Book book = new Book();
		    	  book.setTitle(bookSet.getString("book_title"));
		    	  book.setAuthor(bookSet.getString("book_author"));
		    	  book.setAvailable(bookSet.getInt("book_available"));
		    	  book.setQuantity(bookSet.getInt("book_quantity"));
		    	  book.setGenre(bookSet.getString("book_genre"));
		    	  book.setid(bookSet.getInt("book_id"));
		    	  book.setISBN(bookSet.getString("book_ISBN"));
		    	  book.setPublisher(bookSet.getString("book_publisher"));
		    	  
		    	  if(hasIssuedThisBook(book.getid(),user_id)==true) {
		    		  
		    		  buttons[1]=true;// set return button to true
		    	  }else {
		    		  if(book.getAvailable()>0) {
		    			  
		    			  buttons[0]=true;// set borrow button to true
		    		  }
		    		  
		    		  if(book.getAvailable()==0 && posInWaitlist(book.getid(),user_id)==-1) {
		    			  
		    			  buttons[2]=true;//set add to WL true
		    		  }
		    		  
		    		  if(posInWaitlist(book.getid(),user_id)!=-1) {
		    			  
		    			  buttons[3]=true;// set rem from WL to true
		    		  }
		    	  }
		    	  
		    	  array[0]=book;
		    	  array[1]=buttons;
		    	  combinedList.add(array);
		    	  
		      }
		      
		      conn.close();
		      
		   	      
		      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error while getting books");
			e.printStackTrace();
		}
		
		return combinedList;
		
	}
	
	 public List<Object[]> getAllBooksCurrentlyIssued(){
		
		List <Object[]> allBooks = new ArrayList<Object[]>();
		Connection conn;
		
		try {
			conn = dbUtil.getConnection();
			String query = "SELECT * FROM currentlyIssued";			
		    Statement st = conn.createStatement();	     	      
		    ResultSet bookSet = st.executeQuery(query);
		    
		    while(bookSet.next()) {
		    	
		    	Object [] book = new Object[4];
		    	book[0]=bookSet.getString("book_id");
		    	book[1]=bookSet.getString("user_id");
		    	book[2]=bookSet.getDate("issue_date");
		    	book[3]=bookSet.getDate("return_date");
		    	allBooks.add(book);

		    }
		    
			conn.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return allBooks;
		
		
	}
	
	
	public List<Object[]> getBookIssueHistory(int book_id){
		
		List<Object[]> issues = new ArrayList<Object[]>();
		
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = dbUtil.getConnection();
			String query = "SELECT FROM issueHistory WHERE book_id ="+book_id;			
		    Statement st = conn.createStatement();	     	      
		    ResultSet userSet = st.executeQuery(query);
		    
		    while(userSet.next()) {
		    	
		    	Object [] issueData = new Object[3];
		    	issueData[0]=userSet.getString("user_id");
		    	issueData[1]=userSet.getDate("issue_date");
		    	issueData[2]=userSet.getDate("return_date");
		    	issues.add(issueData);
		    	

		    }
			conn.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return issues;
		
	}
	
	List<Object[]> getUserIssueHistory(String user_id){
		
		List<Object[]> issues = new ArrayList<Object[]>();
		
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = dbUtil.getConnection();
			String query = "SELECT FROM issueHistory WHERE user_id ="+user_id;			
		    Statement st = conn.createStatement();	     	      
		    ResultSet userSet = st.executeQuery(query);
		    
		    while(userSet.next()) {
		    	
		    	Object [] issueData = new Object[3];
		    	issueData[0]=userSet.getString("book_id");
		    	issueData[1]=userSet.getDate("issue_date");
		    	issueData[2]=userSet.getDate("return_date");
		    	issues.add(issueData);

		    }
		    conn.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return issues;
		
	}
	
	public List<Object[]> getBookCurrentIssue(int book_id){
		
		List<Object[]> issues = new ArrayList<Object[]>();
		
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = dbUtil.getConnection();
			String query = "SELECT FROM currentlyIssued WHERE book_id ="+book_id;			
		    Statement st = conn.createStatement();	     	      
		    ResultSet userSet = st.executeQuery(query);
		    
		    while(userSet.next()) {
		    	
		    	Object [] issueData = new Object[3];
		    	issueData[0]=userSet.getString("user_id");
		    	issueData[1]=userSet.getDate("issue_date");
		    	issueData[2]=userSet.getDate("due_date");
		    	issues.add(issueData);
		    	

		    }
			conn.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return issues;
		
	}
	
	
	public List<Object[]> getUserCurrentIssue(int user_id){
		
		List<Object[]> issues = new ArrayList<Object[]>();
		
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = dbUtil.getConnection();
			String query = "SELECT FROM currentlyIssued WHERE user_id ="+user_id;			
		    Statement st = conn.createStatement();	     	      
		    ResultSet userSet = st.executeQuery(query);
		    
		    while(userSet.next()) {
		    	
		    	Object [] issueData = new Object[3];
		    	issueData[0]=userSet.getString("book_id");
		    	issueData[1]=userSet.getDate("issue_date");
		    	issueData[2]=userSet.getDate("due_date");
		    	issues.add(issueData);
		    	

		    }
			conn.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return issues;
		
	}
	
	
	
	
	List<Book> searchBook(Book toSearch){
		
		List<Book> books = browseBooks();
		List<Book> matchingBooks= new ArrayList<Book>();
		int size = books.size();
		
		for (int i=0;i<size;i++) {
			
			Book book = books.get(i);
			
			if((book.getTitle().contains(toSearch.getTitle()) && (!toSearch.getTitle().equals(""))) && (book.getAuthor().contains(toSearch.getTitle()) && (!toSearch.getAuthor().equals("")))&& (book.getGenre().contains(toSearch.getGenre()) && (!toSearch.getGenre().equals(""))) && (book.getPublisher().contains(toSearch.getPublisher()) && (!toSearch.getPublisher().equals(""))) && (book.getISBN().contains(toSearch.getISBN()) && (Integer.parseInt(toSearch.getISBN())!=0))) {
				
				matchingBooks.add(book);
			}
		}
		
		return matchingBooks;
	}
	  
	
	
	public boolean borrowBook(int user_id, int bookId)
	{
		
		Connection connection;
		int i = 0;
		try {
			connection = dbUtil.getConnection();
			PreparedStatement ps;
			String query="SELECT COUNT(user_id) FROM currentlyIssued";			
			 Statement st = connection.createStatement();         
	         ResultSet rs = st.executeQuery(query);
	         int noOfBooks=rs.getInt(0);
	         
	         if(noOfBooks>=2) {
	        	 System.out.println("Only two books bro");
	         }
	         
			ps = connection.prepareStatement("UPDATE books SET book_available = (book_available - 1) WHERE bookId = ?");
			ps.setInt(1, bookId);
			ps.executeUpdate();
			ps = connection.prepareStatement("DELETE from waitlist WHERE user_id=? AND book_id=?;");
			ps.setInt(1, user_id);
			ps.setInt(2, bookId);
			ps.executeUpdate();
	        ps = connection.prepareStatement("INSERT INTO currentlyIssued (book_id, user_id, issue_date, due_date) VALUES (?, ?, ?, ?);");
            ps.setInt(1, bookId);
	        ps.setInt(2, user_id);
	        LocalDate idate = LocalDate.now();
	        LocalDate ddate = idate.plusDays(14);
	        ps.setDate(3, java.sql.Date.valueOf(idate));
	        ps.setDate(4, java.sql.Date.valueOf(ddate));
	        
	        i = ps.executeUpdate();
	        
	        connection.close();
	        
	        
		
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
	
	public double returnBook(int user_id, int bookId) 
	{
		Connection connection;
		int i = 0;
		try {
			connection = dbUtil.getConnection();
			PreparedStatement ps;
			ps = connection.prepareStatement("UPDATE books SET available_copies = (available_copies + 1) WHERE bookId = ?");
			ps.setInt(1, bookId);
			ps.executeUpdate();
	        ps = connection.prepareStatement("DELETE FROM currentlyIssued WHERE user_id=? AND bookId=?;");
            ps.setInt(1, user_id);
	        ps.setInt(2, bookId);
	        
	        ps = connection.prepareStatement("INSERT INTO lms_db.past_issues (bookId, user_id, num) VALUES (?, ?, 1) ON DUPLICATE KEY UPDATE num = num + 1;");
	        ps.setInt(1, bookId);
	        ps.setInt(2, user_id);
	        i = ps.executeUpdate();
	        
	        connection.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in returnbook in dbconnector");
		}
		return calcFine(user_id, bookId);
	}
	
	boolean hasIssuedThisBook(int book_id, String user_id)
	{
		Connection conn;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT book_id, user_Id, issue_date, due_date FROM currentlyIssued WHERE book_id = ? AND user_id = ?;");
	        ps.setInt(1, book_id);
	        ps.setString(2, user_id);
	        ResultSet rs = ps.executeQuery();
	        while(rs.next())
	        {
	        	return true;
	        }
	        
	        conn.close();
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in currentlyissued in dbconnector");
		}
		return false;
        
	}
	
	
	int posInWaitlist(int book_id,String user_id) {
		
		Connection conn;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT * FROM waitlist WHERE book_id = ?");
	        ps.setInt(1, book_id);
	        ps.setString(2, user_id);
	        ResultSet rs = ps.executeQuery();
	        for(int i=0;rs.next();i++)
	        {
	        	if(rs.getString("user_id").equals(user_id)) return i;
	        	
	        }
	        conn.close();
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in currentlyissued in dbconnector");
		}
		return -1;
		
	}
	
	
	
	
	
	
	
	
  public boolean deleteBook(int bookId)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("DELETE from books WHERE book_id = ?;");
	        ps.setInt(1, bookId);
	        x = ps.executeUpdate();
	        conn.close(); 
	        
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
	
	public double deleteMember(int user_id)
	{
		Connection conn; int x=0;
		double fine=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("DELETE from lms_db.member_list WHERE user_id = ?;");
	        ps.setInt(1, user_id);
	        x = ps.executeUpdate();
	        ps = conn.prepareStatement("SELECT book_Id, mem_Id FROM currentlyIssued WHERE user_id = ?;");
	        ResultSet rs = ps.executeQuery();
	        //int bookId = rs.getInt(1);
	        if(rs.next()) {
	        	PreparedStatement ps2 = conn.prepareStatement("UPDATE books SET available_copies = (available_copies + 1) WHERE bookId = ?;");
	        	ps2.setInt(1, rs.getInt(1));
	        	ps2.executeUpdate();
	            fine = calcFine(user_id, rs.getInt(1));
	        }
	        ps = conn.prepareStatement("DELETE from currentlyIssued WHERE user_id = ?;");
	        ps.setInt(1, user_id);
	        x = ps.executeUpdate();
	        conn.close();
	       
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in deletemember in dbconnector");
		}
		 return fine;
	}

	public boolean editUserDetails(User currentUser,User user) {
		
		Connection conn; 
		try {
			conn = dbUtil.getConnection();
			
			if(currentUser.getType().equals("admin")) {
				
				PreparedStatement ps;
				ps = conn.prepareStatement("UPDATE users SET name=?,email=?,type=? WHERE user_id=?");
				ps.setString(1,user.getName());
				ps.setString(2,user.getEmail());
				ps.setString(3,user.getPassword());
				ps.setString(4,user.getType());
				ps.executeQuery();
			
				conn.close();
				return true;
				
			}else {
				PreparedStatement ps;
				ps = conn.prepareStatement("UPDATE users SET name=?,email=?,password=? WHERE user_id=?");
				ps.setString(1,user.getName());
				ps.setString(2,user.getEmail());
				ps.setString(3,user.getPassword());
				ps.setInt(4,user.getMemId());
				ps.executeQuery();
			
				conn.close();
				return true;
				
				
			}
			
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in editUserDetails in dbconnector");
			return false;
		}
		
	}
	
	public boolean addBook(Book book)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("INSERT into books (book_id, book_ISBN, book_title, book_author,book_publisher,book_genre,book_quantity,book_available) values (NULL, ?, ?, ?, ?, ?, ?, 0);");
	        //generate an ID
			ps.setString(2, book.getISBN());
	        ps.setString(3, book.getTitle());
	        ps.setString(4, book.getAuthor());
	        ps.setString(5, book.getPublisher());
	        ps.setString(6, book.getGenre());
	        ps.setInt(7, book.getQuantity());
	        ps.setInt(8, book.getAvailable());
	        x = ps.executeUpdate();
	        
	        conn.close();
	        
	        
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
	
	public boolean addUser(User user)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("INSERT into member_list (user_id, user_type, user_email, user_name, user_password) values (0, ?, ?, ?, ?);");
	        ps.setString(2, user.getType());
	        ps.setString(3, user.getEmail());
	        ps.setString(4, user.getName());
	        ps.setString(5, user.getPassword());
	       
	        x = ps.executeUpdate();
	        
	        conn.close();
	        
	        
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
	
	boolean editBook(Book book)
	{
		Connection conn;
		int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("UPDATE books SET ISBN=?, title=?, author=?, publisher=?, genre=?, quantity=?,available=? WHERE user_id=?;");
	        ps.setString(1,book.getISBN() );
	        ps.setString(2, book.getTitle());
	        ps.setString(3, book.getAuthor());
	        ps.setString(4, book.getPublisher());
	        ps.setString(5, book.getGenre());
	        ps.setInt(6, book.getQuantity());
	        ps.setInt(7, book.getAvailable());
	        
	        x = ps.executeUpdate();
	        conn.close();
	        
	        
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
	
	
	List<User> getAllUsers(){
		
		List<User> users= new ArrayList<User>();
		Connection conn;
			try {
				conn = dbUtil.getConnection();
				
			  String query = "SELECT * FROM users";		        
		      Statement st = conn.createStatement();	     	      
		      ResultSet userSet = st.executeQuery(query);		      
		      
		      while(userSet.next()) {
		    	  User user = new User();
		    	  
		    	 user.setEmail(userSet.getString("user_email")); 
		    	 user.setMemId(userSet.getInt("user_id")); 
		    	 user.setName(userSet.getString("user_email")); 
		    	 user.setPassword(userSet.getString("user_password")); 
		    	 
		    	 users.add(user);
		    	  
		      }
		      
		      conn.close();
		      
		      
		      
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return users;
			
		
		
	}
	
	double calcFine(int user_id, int bookId) {
		Connection conn; int x=0;
		double fine=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT issue_date, due_date FROM currentlyIssued WHERE user_id =? AND bookId=?;");
	        ps.setInt(1, user_id);
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
	        conn.close();
	        
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in calcfine in dbconnector");
		}
		return fine;
		
	}
	
	

}
        
	


