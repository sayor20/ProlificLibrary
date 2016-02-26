package com.sayor.org.prolificlibrary.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sayor.org.prolificlibrary.models.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

// service created for using retrofit
public class BookService {

    private static final String WEB_SERVICE_BASE_URL = "http://prolific-interview.herokuapp.com";
    private static ProlificBookService mWebService;

  public static ProlificBookService getClient() {
    if (mWebService == null) {

      Gson gson = new GsonBuilder()
          .setDateFormat("yyyy-MM-dd HH:mm:ss zzz")
          .create();

      Retrofit client = new Retrofit.Builder()
          .baseUrl(WEB_SERVICE_BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .build();

      mWebService = client.create(ProlificBookService.class);
    }
    return mWebService ;
  }

  // REST Methods
    public interface ProlificBookService {
      @GET("/56c77c0889f430000996afa9/books")
      Call<List<Book>> getBooks();

      @POST("/56c77c0889f430000996afa9/books/")
      Call<Book> addBooks(@Body Book book);

      @DELETE("/56c77c0889f430000996afa9/books/{id}/")
      Call<Book> deleteBooks(@Path("id") int bookID);

      @DELETE("/56c77c0889f430000996afa9/clean/")
      Call<Book> deleteAllBooks();

      @PUT("/56c77c0889f430000996afa9/books/{id}/")
      Call<Book> updateBook(@Path("id") int id , @Body Book book);
    }
  }
