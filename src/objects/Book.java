package objects;

public class Book {
	private String title;
	private String author;
	private String genre;
	private String ISBN;
	private String ID;
	private int quantity;
	private int available;

	public String getTitle() {
	return this.title;
	}

	public void setTitle(String title) {
	this.title = title;
	}

	public String getAuthor() {
	return author;
	}

	public void setAuthor(String author) {
	this.author = author;
	}

	public String getGenre(){
	return genre;
	}

	public void setGenre(String genre){
	this.genre=genre;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
	this.ISBN=ISBN;
	}
		
	public String getid() {
	return ID;
	}

	public void setid(String ID) {
	this.ID = ID;
	}

	public int getQuantity() {
	return quantity;
	}

	public void setQuantity(int quantity) {
	this.quantity = quantity;
	}

	public int getAvailable() {
	return available;
	}
	public void setAvailable(int available) {
	this.available = available;
	}


}
