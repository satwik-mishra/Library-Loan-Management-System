package model;

public class Book {

    private int bookId;
    private String title;
    private String isbn;
    private boolean available;

    public Book(
            int bookId,
            String title,
            String isbn,
            boolean available
    ) {

        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.available = available;
    }

    public int getBookId() {

        return bookId;
    }

    public String getTitle() {

        return title;
    }

    public String getIsbn() {

        return isbn;
    }

    public boolean isAvailable() {

        return available;
    }

    public void setBookId(int bookId) {

        this.bookId = bookId;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setIsbn(String isbn) {

        this.isbn = isbn;
    }

    public void setAvailable(boolean available) {

        this.available = available;
    }

    @Override
    public String toString() {

        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", available=" + available +
                '}';
    }
}