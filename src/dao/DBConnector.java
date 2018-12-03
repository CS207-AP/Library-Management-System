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
            String query = "SELECT user_id, user_type, user_email, user_name, user_password FROM users WHERE user_email=\'"+email+"\';";
            
            Statement st = conn.createStatement();
           
            ResultSet rs = st.executeQuery(query);
            System.out.println(email);
            while(rs.next()) {
            	String em = rs.getString("user_email");
            	String pw = rs.getString("user_password");
            	System.out.println(em);
            	System.out.println(pw);
            	System.out.println(email);
            	System.out.println(password);
                if (em.equals(email) && pw.equals(password) ) {
                
                    user.setMemId(rs.getInt("user_id"));
//                    System.out.println(user.getMemId());
                    user.setEmail(em);
//                    System.out.println(user.getEmail());
                    user.setPassword(pw);
                    user.setType(rs.getString("user_type"));
                    user.setName(rs.getString("user_name"));
                   
                }}
            conn.close();
            
	}catch (SQLException e) {
            System.err.println("Got an exception in checkcredentials in dbconnector");
            System.err.println(e.getMessage());
        }
		return user;
	}
	
	
	
	/**
	 * This method returns all the books in the catalogue and enables
	 * 
	 * @param user_id user id of the current user in session
	 * @return returns a List with each element as an Object[] where object[0]=book,object[1]= buttons[]
	 */
	public List<Object[]> browseBooks(int user_id) {
		
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
		    	
		    	Object [] book = new Object[5];
		    	book[0]=bookSet.getString("book_id");
		    	book[1]=bookSet.getString("user_id");
		    	book[2]=bookSet.getString("book_title");
		    	book[3]=bookSet.getDate("issue_date");
		    	book[4]=bookSet.getDate("due_date");
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
			String query = "SELECT user_id,book_title,issue_date,return_date FROM issueHistory WHERE book_id ="+book_id+";";			
		    Statement st = conn.createStatement();	     	      
		    ResultSet userSet = st.executeQuery(query);
		    
		    while(userSet.next()) {
		    	
		    	Object [] issueData = new Object[4];
		    	issueData[0]=userSet.getInt("user_id");
		    	issueData[1]=userSet.getString("book_title");
		    	issueData[2]=userSet.getDate("issue_date");
		    	issueData[3]=userSet.getDate("return_date");
		    	issues.add(issueData);
		    	

		    }
			conn.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return issues;
		
	}
	
	public User getUserDetails(int user_id) {
		
		Connection conn;
	    User user= new User();
		try {
			conn = dbUtil.getConnection();
			String query= "SELECT user_name, user_email, user_password, user_type FROM users WHERE user_id = "+user_id+";";
			Statement st = conn.createStatement();
		    ResultSet userSet = st.executeQuery(query);
		    
		    while(userSet.next()) {
		    user.setMemId(user_id);
		   	user.setType(userSet.getString("user_type"));
		   	user.setName(userSet.getString("user_name"));
	    	user.setEmail(userSet.getString("user_email"));		    		 
	    	user.setPassword(userSet.getString("user_password"));	
	    	}	    		 
	    	conn.close();		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
		
	}
	
	
	public List<Object[]> getUserIssueHistory(int user_id){
		
		List<Object[]> issues = new ArrayList<Object[]>();
		
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = dbUtil.getConnection();
			String query = "SELECT * FROM issueHistory WHERE user_id ="+user_id;			
		    Statement st = conn.createStatement();	     	      
		    ResultSet userSet = st.executeQuery(query);
		    
		    while(userSet.next()) {
		    	
		    	Object [] issueData = new Object[3];
		    	issueData[0]=userSet.getString("book_id");
		    	issueData[1]=userSet.getString("book_name");
		    	issueData[2]=userSet.getDate("issue_date");
		    	issueData[3]=userSet.getDate("return_date");
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
		    	issueData[1]=userSet.getString("book_name");
		    	issueData[2]=userSet.getDate("issue_date");
		    	issueData[3]=userSet.getDate("due_date");
		    	issues.add(issueData);

		    }
			conn.close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return issues;
		
	}
	
	public List<Book> getAllBooks(){
		
		Connection conn;
		List<Book> combinedList= new ArrayList<Book>();
		try {
			conn = dbUtil.getConnection();
			
			  String query = "SELECT * FROM books;";		        
		      Statement st = conn.createStatement();	     	      
		      ResultSet bookSet = st.executeQuery(query);		      
		      
		      while(bookSet.next()) {
		    	 
		    	  Book book = new Book();
		    	  book.setTitle(bookSet.getString("book_title"));
		    	  book.setAuthor(bookSet.getString("book_author"));
		    	  book.setAvailable(bookSet.getInt("book_available"));
		    	  book.setQuantity(bookSet.getInt("book_quantity"));
		    	  book.setGenre(bookSet.getString("book_genre"));
		    	  book.setid(bookSet.getInt("book_id"));
		    	  book.setISBN(bookSet.getString("book_ISBN"));
		    	  book.setPublisher(bookSet.getString("book_publisher"));
		    	  combinedList.add(book);
		    	  
		      }
		      
		      conn.close();
		      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error while getting books");
			e.printStackTrace();
		}
		return combinedList;
	
	}
		
		
	
	
	List<Book> searchBook(Book toSearch){
		
		List<Book> books = getAllBooks();;
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
	
	boolean hasIssuedThisBook(int book_id, int user_id)
	{
		Connection conn;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT book_id, user_Id, issue_date, due_date FROM currentlyIssued WHERE book_id = ? AND user_id = ?;");
	        ps.setInt(1, book_id);
	        ps.setInt(2, user_id);
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
	
	
	int posInWaitlist(int book_id,int user_id) {
		
		Connection conn;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT * FROM waitlist WHERE book_id = ?");
	        ps.setInt(1, book_id);
	        ps.setInt(2, user_id);
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
			ps = conn.prepareStatement("DELETE from users WHERE user_id = ?;");
	        ps.setInt(1, user_id);
	        x = ps.executeUpdate();
	        ps = conn.prepareStatement("SELECT book_Id, mem_Id FROM currentlyIssued WHERE user_id = ?;");
	        ps.setInt(1, user_id);
	        ResultSet rs = ps.executeQuery();
	        //int bookId = rs.getInt(1);
	        if(rs.next()) {
	        	PreparedStatement ps2 = conn.prepareStatement("UPDATE books SET available_copies = (available_copies + 1) WHERE bookId = ?;");
	        	int id = rs.getInt(1);
	        	ps2.setInt(1, id);
	        	ps2.executeUpdate();
	            fine = calcFine(user_id, id);
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

	public boolean editUserDetails(User user) {
		System.out.println("Inside edit_user in dbconn");
		Connection conn; 
		int x=0;
		System.out.println(user.getEmail());
		try {
			conn = dbUtil.getConnection();
				PreparedStatement ps;
				ps = conn.prepareStatement("UPDATE users SET user_name=?,user_email=?,user_password=? WHERE user_id=?;");
				ps.setString(1,user.getName());
				ps.setString(2,user.getEmail());
				ps.setString(3,user.getPassword());
			    ps.setInt(4,user.getMemId());
			    System.out.println("Mem id is "+user.getMemId());
				x=ps.executeUpdate();
				conn.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Got an exception in editUserDetails in dbconnector");
		}
		
		if(x==1) {
			System.out.println("Edited successfully");
			return true;
		}else{
			return false;
		
		}
		
	}
	
	public void addtoWaitlist(int bookid,int userid) {
		
		Connection conn; 
		try {
			conn = dbUtil.getConnection();
				PreparedStatement ps;
				ps = conn.prepareStatement("INSERT INTO waitlist SET user_id=?,book_id=?");
				ps.setInt(1,userid);
				ps.setInt(2,bookid);
			    ps.executeQuery();
				conn.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void removeFromWaitlist(int bookid,int userid) {
		
		Connection conn; 
		try {
			conn = dbUtil.getConnection();
				PreparedStatement ps;
				ps = conn.prepareStatement("DELETE FROM waitlist WHERE user_id=? AND book_id=?");
				ps.setInt(1,userid);
				ps.setInt(2,bookid);
				ps.executeQuery();
				conn.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean addBook(Book book)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("INSERT into books (book_ISBN, book_title, book_author,book_publisher,book_genre,book_quantity,book_available) values (?, ?, ?, ?, ?, ?, ?);");
	        //generate an ID
			ps.setString(1, book.getISBN());
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
			System.err.println("Got an exception in addbook in dbconnector");
		}
		if (x == 1) 
            return true;
         else 
            return false;
	}
	
		
	public boolean editBook(Book book)
	{
		System.out.println("inside edit_book in editbook");
		Connection conn;
		int x=0;
		
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("UPDATE books SET book_ISBN=?, book_title=?, book_author=?, book_publisher=?, book_genre=?, book_quantity=?, book_available=? WHERE book_id=?;");
	        ps.setString(1,book.getISBN() );
	        ps.setString(2, book.getTitle());
	        ps.setString(3, book.getAuthor());
	        ps.setString(4, book.getPublisher());
	        ps.setString(5, book.getGenre());
	        ps.setInt(6, book.getQuantity());
	        ps.setInt(7, book.getAvailable());
	        ps.setInt(8, book.getid());
	        
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
	
	
	public List<User> getAllUsers(){
		
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
		    	 user.setName(userSet.getString("user_name")); 
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



	public boolean addUser(User user)
	{
		Connection conn; int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("INSERT into users (user_type, user_email, user_name, user_password) values  (?, ?, ?, ?);");
	        ps.setString(1, user.getType());
	        ps.setString(2, user.getEmail());
	        ps.setString(3, user.getName());
	        ps.setString(4, user.getPassword());
	       
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
	
	

}
        
	


