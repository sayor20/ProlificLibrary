package com.sayor.org.prolificlibrary.models;

/**
 * Created by Sayor on 2/22/16.
 */

// model class for JSON data from server
public class Book {

  public  String id;
  public String author;
  public String categories;
  public String publisher;
  public String title;
  public String url;
  public String lastCheckedOut;
  public String lastCheckedOutBy;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getCategories() {
    return categories;
  }

  public void setCategories(String categories) {
    this.categories = categories;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLastCheckedOut() {
    return lastCheckedOut;
  }

  public void setLastCheckedOut(String lastCheckedOut) {
    this.lastCheckedOut = lastCheckedOut;
  }

  public String getLastCheckedOutBy() {
    return lastCheckedOutBy;
  }

  public void setLastCheckedOutBy(String lastCheckedOutBy) {
    this.lastCheckedOutBy = lastCheckedOutBy;
  }

}
