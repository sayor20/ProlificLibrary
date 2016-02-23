package com.sayor.org.prolificlibrary.services;

import com.sayor.org.prolificlibrary.models.Book;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class BookService {

    private static final String WEB_SERVICE_BASE_URL = "http://prolific-interview.herokuapp.com/56c77c0889f430000996afa9";
    //private final ProlificBookService mWebService;

    public BookService() {

      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(WEB_SERVICE_BASE_URL)
          .build();

      ProlificBookService service = retrofit.create(ProlificBookService.class);
    }

    private interface ProlificBookService {
      @GET("/books")
      Call<List<Book>> listBooks(@Path("books") String book);
    }

  }
