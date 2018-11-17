package objects;

public class Book {
	private String title;
	private String author;
	private String genre;
	private String ISBN;
	private String ID;
	private String publisher;
	private int quantity;
	private int available;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre(){
		return this.genre;
	}

	public void setGenre(String genre){
		this.genre=genre;
	}

	public String getISBN() {
		return this.ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN=ISBN;
	}
		
	public String getid() {
		return this.ID;
	}

	public void setid(String ID) {
		this.ID = ID;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAvailable() {
		return this.available;
	}
	
	public void setAvailable(int available) {
		this.available = available;
	}
	
	public void setPublisher(String publisher) {
		this.publisher=publisher;
	}
	
	public String getPublisher() {
		return this.publisher;
	}


}
