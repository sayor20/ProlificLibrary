package com.sayor.org.prolificlibrary.models;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

// Singleton class for arraylist to share across the app

public class BookList {
  private static BookList bookList;
  private List<Book> mBooks;

  public BookList(Context context) {
    if(context!=null)
      mBooks = new ArrayList<>();

  }

  public List<Book> getmBooks() {
    return mBooks;
  }

  public Book getmBooks(int pos) {
    return mBooks.get(pos);
  }

  public void addmBooks(Book book){
    mBooks.add(book);
  }

  public void addmBooks(int pos, Book book){
    mBooks.add(pos, book);
  }

  public void addAllmBooks(List<Book> bookList){
    mBooks.addAll(bookList);
  }

  public void removemBooks(int pos){
    mBooks.remove(pos);
  }

  public static BookList get(Context context){
    if(bookList == null){
      bookList = new BookList(context);
    }
    return bookList;
  }
}
