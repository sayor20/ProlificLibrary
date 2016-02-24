package com.sayor.org.prolificlibrary.services;

import com.sayor.org.prolificlibrary.models.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

// service created for using retrofit
public class BookService {

    private static final String WEB_SERVICE_BASE_URL = "http://prolific-interview.herokuapp.com";
    private static ProlificBookService mWebService;

  public static ProlificBookService getClient() {
    if (mWebService == null) {

      Retrofit client = new Retrofit.Builder()
          .baseUrl(WEB_SERVICE_BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build();

      mWebService = client.create(ProlificBookService.class);
    }
    return mWebService ;
  }

    public interface ProlificBookService {
      @GET("/56c77c0889f430000996afa9/books")
      Call<List<Book>> listBooks();
    }
  }
