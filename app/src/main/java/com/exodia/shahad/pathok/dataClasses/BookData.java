package com.exodia.shahad.pathok.dataClasses;

public class BookData {
    private String bookId, bookName, bookImage, bookDescription;

    public BookData() {

    }

    public String getBookId() {
        return bookId;
    }

    //setters
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }


    //getters

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }
}
