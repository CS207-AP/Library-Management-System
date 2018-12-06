package dao;

import util.DButil;
import objects.Book;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import objects.User;
import java.sql.Connection;
import java.sql.Date;
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
			e.printStackTrace();
		}
		
		return combinedList;
		
	}
	
/**
 * This function returns all the books currently issued from the database with the book details,user id	
 * @return All the necessary details of the user,book and dates
 */
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
			e.printStackTrace();
		}
		
		return allBooks;
		
	}
	
/**
 * 	Gets the issue history of the book with the book_id
 * @param book_id The bookid corresponds to the book of which to get the history of
 * @return The user-id, book-title and both issue and return dates
 */
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
			e.printStackTrace();
		}
		
		return issues;
	}
	
	/**
	 *  Gets the user details of the given User
	 * @param user_id The for which we want the user's details of
	 * @return a User object with all relevant detials. Returns null if a the user doesn't exist
	 */
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
			e.printStackTrace();
		}
		
		return user;
	}
	
/**
 * 	Gets the history of the books issued by the user
 * @param user_id The id of the user to get his/her book history from
 * @return Details of the books the user has issued along with their respective issue and due dates
 */
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
		    	
		    	Object [] issueData = new Object[6];
		    	issueData[0]=userSet.getString("book_id");
		    	issueData[1]=userSet.getString("user_id");
		    	issueData[2]=userSet.getString("book_title");
		    	issueData[3]=userSet.getString("user_name");
		    	issueData[4]=userSet.getDate("issue_date");
		    	issueData[5]=userSet.getDate("return_date");
		    	issues.add(issueData);

		    }
		    conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return issues;
	}
/**
 * 	Gets the users who have borrowed the book with the given book id
 * @param book_id The book_id of the book to check which User has borrowed
 * @return The user(s) who have borrowed the book
 */
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
			e.printStackTrace();
		}
		
		return issues;
	}
	
/**
 * 	Gets the currently issued book(s) that the User with user_id has issued
 * @param user_id
 * @return
 */
	public List<Object[]> getUserCurrentIssue(int user_id){
		
		List<Object[]> issues = new ArrayList<Object[]>();
		
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = dbUtil.getConnection();
			String query = "SELECT * FROM currentlyIssued WHERE user_id ="+user_id;			
		    Statement st = conn.createStatement();	     	      
		    ResultSet userSet = st.executeQuery(query);
		    
		    while(userSet.next()) {
		    	
		    	Object [] issueData = new Object[6];
		    	issueData[0]=userSet.getString("book_id");
		    	
		    	issueData[1]=userSet.getString("user_id");
		    	issueData[2]=userSet.getString("book_title");
		    	//System.out.println("printing in getusercurrent"+ issueData[2]);
		    	issueData[3]=userSet.getString("user_name");
		    	issueData[4]=userSet.getDate("issue_date");
		    	issueData[5]=userSet.getDate("due_date");
		    	issues.add(issueData);

		    }
			conn.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return issues;
	}
	
/**
 * Gets a master list of all the Books in the catalogue
 * @return A List of all Books
 */
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
/**
 * Borrows the book (given it matches given constraints) on behalf of the user
 * @param user_id The user_id of the person borrowing the book
 * @param bookId The id of the book the User wants to borrow
 * @return a boolean value to indicate whether the book has been successfully issued or not
 */
	public boolean borrowBook(int user_id, int bookId)
	{
		int noOfBooks=0;
		Connection connection;
		int i = 0;
		try {
			connection = dbUtil.getConnection();
			PreparedStatement ps;
			 String query="SELECT COUNT(user_id) FROM currentlyIssued";			
			 Statement st = connection.createStatement();         
	         ResultSet rs = st.executeQuery(query);
	         rs.next(); 
	         noOfBooks=rs.getInt(1);
	         
	         if(noOfBooks==2) {
	        	 
	        	 connection.close();
	        	 return false;
	        	 
	         }
	         
	         
			ps = connection.prepareStatement("UPDATE books SET book_available = (book_available - 1) WHERE book_id = ?");
			ps.setInt(1, bookId);
			ps.executeUpdate();
			
			ps = connection.prepareStatement("DELETE from waitlist WHERE user_id=? AND book_id=?;");
			ps.setInt(1, user_id);
			ps.setInt(2, bookId);
			ps.executeUpdate();
			
	        ps = connection.prepareStatement("SELECT book_title FROM books WHERE book_id=?");
	        ps.setInt(1, bookId);
	        ResultSet rss= ps.executeQuery();
	        rss.next();
	        
	        ps = connection.prepareStatement("SELECT user_name FROM users WHERE user_id=?");
	        ps.setInt(1, user_id);
	        ResultSet username= ps.executeQuery();
	        username.next();
			
	        ps = connection.prepareStatement("INSERT INTO currentlyIssued (book_id, user_id,book_title,user_name,issue_date, due_date) VALUES (?,?,?,?,?,?);");
            ps.setInt(1, bookId);
	        ps.setInt(2, user_id);
	        LocalDate idate = LocalDate.now();
	        idate = idate.minusDays(1);
	        LocalDate ddate = idate.minusDays(3);
	        ps.setString(3, rss.getString("book_title"));
	        ps.setString(4,username.getString(1));
	        ps.setDate(5, java.sql.Date.valueOf(idate));
	        ps.setDate(6, java.sql.Date.valueOf(ddate));
	        
	        i = ps.executeUpdate();
	        
	        connection.close();
	        
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return true;
		
		
	}
		
	
/**
 * 	Returns the book with the given user-id and book id
 * @param user_id The id of the user returning the book
 * @param bookId The id of the book to be returned
 * @return the fine if a book is returned more than 20 days late. It's 0 if it's returned before time.
 */
	public double returnBook(int user_id, int bookId) 
	{
		Connection connection;
		double fine=0;
		try {
			connection = dbUtil.getConnection();
			PreparedStatement ps;
			
			ps = connection.prepareStatement("SELECT book_id,user_id,book_title,user_name,issue_date,due_date FROM currentlyIssued WHERE user_id=? AND book_id=?;");
	        ps.setInt(1, user_id);
	        ps.setInt(2, bookId);
			ResultSet fullSet=ps.executeQuery();
	        fullSet.next();
			
	        ps = connection.prepareStatement("DELETE FROM currentlyIssued WHERE user_id=? AND book_id=?;");
            ps.setInt(1, user_id);
	        ps.setInt(2, bookId);
	        ps.executeUpdate();
			
			ps = connection.prepareStatement("UPDATE books SET book_available = (book_available + 1) WHERE book_id = ?");
			ps.setInt(1, bookId);
			ps.executeUpdate();
			 
	        
	        ps = connection.prepareStatement("INSERT INTO issueHistory (book_id,user_id,book_title,user_name,issue_date,return_date) VALUES (?,?,?,?,?,?);");
            ps.setInt(1, bookId);
	        ps.setInt(2, user_id);
	        ps.setString(3, fullSet.getString("book_title"));
	        ps.setString(4, fullSet.getString("user_name"));
	        ps.setDate(5,fullSet.getDate("issue_date"));
	       // System.out.println(fullSet.getDate("issue_date"));
	        ps.setDate(6,fullSet.getDate("due_date"));
	       // System.out.println(fullSet.getDate("due_date"));

	        
	        ps.executeUpdate();
	        
	        
	        //fine = calcFine(fullSet.getDate("issue_date"),fullSet.getDate("due_date"));
	        LocalDate rdate = LocalDate.now();
	        Date date = Date.valueOf(rdate);
	        fine = calcFine(date,fullSet.getDate("due_date"));
	        connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fine; 

	}
	
/**
 *Checks if the user currently has possession of the book
 * @param book_id The id of the book
 * @param user_id The id of the user
 * @return
 */
	boolean hasIssuedThisBook(int book_id, int user_id)
	{
		Connection conn;
		
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT book_title, issue_date, due_date FROM currentlyIssued WHERE book_id = ? AND user_id = ?;");
	        ps.setInt(1, book_id);
	        ps.setInt(2, user_id);
	        ResultSet rs = ps.executeQuery();
	        
	        while(rs.next()) {
	        	
	        	return true;
	        }
	        
	        conn.close();
	        
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return false;
	}
	
	/**
	 * Returns the position the user(user_id) is in for the book(book_id). -1 if he/she's not in the wait-list
	 * @param book_id The id of the book
	 * @param user_id The id of the user
	 * @return The position the user is in the wait-list for the book
	 */
	int posInWaitlist(int book_id,int user_id) {
		
		Connection conn;
		try {
			
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT user_id FROM waitlist WHERE book_id = ?;");
	        ps.setInt(1, book_id);
	        ResultSet rs = ps.executeQuery();
	        
	        for(int i=0;rs.next();i++) {
	        
	        	if(rs.getInt("user_id")==user_id) return i;
	        	
	        }
	        conn.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

/**
 * Deletes a book from the catalogue
 * @param bookId The id of the book to delete
 * @return boolean value representing whether the book was deleted or not
 */
  public boolean deleteBook(int bookId)
	{
		Connection conn; 
		int x=0;
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			
			ps = conn.prepareStatement("SELECT COUNT(book_id) FROM currentlyIssued WHERE book_id = ?;");
			ps.setInt(1, bookId);
			ResultSet rs1=ps.executeQuery();
			rs1.next();
			
			ps = conn.prepareStatement("SELECT COUNT(book_id) FROM issueHistory WHERE book_id = ?;");
			ps.setInt(1, bookId);
			ResultSet rs2=ps.executeQuery();
			rs2.next();
			
			if(rs1.getInt(1)>0|| rs2.getInt(1)>0) {
				
				conn.close(); 
				return false;
				
			}
			
			ps = conn.prepareStatement("DELETE from books WHERE book_id = ?;");
	        ps.setInt(1, bookId);
	        x = ps.executeUpdate();
	        conn.close(); 
	        
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	        return true;
		
	}
/**
 * Deletes a member given the user_id 
 * @param user_id The id of the member to delete
 * @return Whether you can delete the member or not
 */
	public boolean deleteMember(int user_id)
	{
		Connection conn; int x=0;
		
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			
			ps = conn.prepareStatement("SELECT COUNT FROM currentlyIssued WHERE user_id = ?;");
			ResultSet rs1=ps.executeQuery();
			rs1.next();
			
			ps = conn.prepareStatement("SELECT COUNT FROM issueHistory WHERE user_id = ?;");
			ResultSet rs2=ps.executeQuery();
			rs2.next();
			
			if(rs1.getInt(1)>0|| rs2.getInt(1)>0) {
				
				conn.close(); 
				return false;
				
			}
			
			ps = conn.prepareStatement("DELETE from users WHERE user_id = ?;");
	        ps.setInt(1, user_id);
	        x = ps.executeUpdate();
	        conn.close(); 
	        
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	        return true;
	}

/**
 * Edits a user's details	
 * @param user The user who's details to update
 * @return a boolean representing whether the User's details has been updated
 */
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
			//System.out.println("Edited successfully");
			return true;
		}else{
			return false;
		
		}
		
	}
	
/**
 * 	Adds a user(user_id) to the wait-list of the book (bbok_id)
 * @param bookid The id of the book whose wait-list to be put in
 * @param userid The id of the user to be put in the wait-list
 */
	public boolean addtoWaitlist(int bookid,int userid) {
		Connection conn; 
		int x=0;
		try {
			
			conn = dbUtil.getConnection();
				PreparedStatement ps;
				ps = conn.prepareStatement("INSERT INTO waitlist SET user_id=?,book_id=?");
				ps.setInt(1,userid);
				ps.setInt(2,bookid);
			    x= ps.executeUpdate();
				conn.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	    if(x==1)
	    	return true;
	    else return false;
	}
/**
 * 	Removes a user from a wait-list
 * @param bookid The bookid's wait-list from which to remove the user from
 * @param userid The user to remove from the given bookid's wait-list
 */
	public boolean removeFromWaitlist(int bookid,int userid) {
		
		Connection conn; 
		int x=0;
		try {
			conn = dbUtil.getConnection();
				PreparedStatement ps;
				ps = conn.prepareStatement("DELETE FROM waitlist WHERE user_id=? AND book_id=?");
				ps.setInt(1,userid);
				ps.setInt(2,bookid);
				x= ps.executeUpdate();
				conn.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(x==1)
			return true;
		else return false;
	}
	
/**
 * Adds a book to catalogue
 * @param book The book object which contains the details of the book to add
 * @return A boolean value representing whether the book was successfully added or no
 */
	public boolean addBook(Book book) {
		
		Connection conn; int x=0;
		try {
			
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement("INSERT into books (book_ISBN, book_title, book_author,book_publisher,book_genre,book_quantity,book_available) values (?, ?, ?, ?, ?, ?, ?);");
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
			e.printStackTrace();
		}
		
		if (x == 1) {
            return true;
		}else {
            return false;
		}
	}
	
/**
 * Edits a book details in the catalogue	
 * @param book The book object contains the details of the book to edit
 * @return A boolean value representing whether the book was edited or not
 */
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
			e.printStackTrace();
			System.err.println("Got an exception in editbook in dbconnector");
		}
		
		if (x == 1) 
            return true;
         else 
            return false;
	}
	
/**
 * Gets the master list of all the users
 * @return the master list of all the users
 */
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
				e.printStackTrace();
			}
			
			return users;
	}
	
/**
 * Calculates the fine for a given bookid and user	
 * @param user_id The user who has to pay the fine
 * @param bookId The book which the user borrowed
 * @return The fine which is calculated as noOfDaysExceeded * 20
 */
	double calcFine(Date returnDate, Date dueDate) {
		
		if(returnDate.after(dueDate)) {
			int days= daysBetween(returnDate,dueDate);
			return days*20.0;
			
		}else {
			
			return 0.0;
		}
		
	}
	
/**
 * A simple function to return the number of days between d1 and d2
 * @param d1 The first date
 * @param d2 The second date
 * @return The number of days between d1 and d2
 */
	public int daysBetween(Date d1, Date d2){
		
        return (int)( (d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
        
	}


/**
 * Adds a user to the master list
 * @param user The user object to add with the details of the new User
 * @return a boolean value representing whether the user was added successfully 
 */
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
			e.printStackTrace();
		}
		
		if (x == 1) {
	        return true;
	        
		}else {
	        return false;
		}
		
	}

/**
 * 	Returns all books that match at least one search parameter in the searchBook object
 * @param searchBook A book object with all the fields to search for
 * @param user_id The user who has searched for the books
 * @return A list of all the books that match the search terms and their respective buttons
 */
	public List<Object[]> searchBooks(Book searchBook,int user_id) {
		
		Connection conn;
		List<Object[]> combinedList= new ArrayList<Object[]>();
		System.out.println("Reached searchBooks in dbconn");
		try {
			conn = dbUtil.getConnection();
			PreparedStatement ps;
			
			  ps = conn.prepareStatement("SELECT * FROM books WHERE IF(?!='',LOCATE(?,book_title)>0,FALSE) OR IF( ?!='',LOCATE(?,book_author)>0,FALSE) OR IF(?!='',LOCATE(?,book_ISBN)>0,FALSE) OR IF(?!='',LOCATE(?,book_publisher)>0,FALSE) OR IF(?!='',LOCATE(?,book_genre)>0,FALSE)");		        

			 
			  ps.setString(1, searchBook.getTitle());
			  ps.setString(2, searchBook.getTitle());
			  System.out.println(searchBook.getTitle());
		      ps.setString(3, searchBook.getAuthor());
			  System.out.println(searchBook.getAuthor());
		      ps.setString(4, searchBook.getAuthor());
			  ps.setString(5, searchBook.getISBN());
			  ps.setString(6, searchBook.getISBN());
			  ps.setString(7, searchBook.getPublisher());
			  ps.setString(8, searchBook.getPublisher());
			  ps.setString(9, searchBook.getGenre());
			  ps.setString(10, searchBook.getGenre());
		      ResultSet bookSet = ps.executeQuery();
		      
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
			e.printStackTrace();
		}
		return combinedList;
	}
}
        
	


