package com.sayor.org.prolificlibrary.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.sayor.org.prolificlibrary.R;
import com.sayor.org.prolificlibrary.adapters.BookAdapter;
import com.sayor.org.prolificlibrary.models.Book;
import com.sayor.org.prolificlibrary.services.BookService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksListActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView rvBookList;
    private BookAdapter bookAdapter;
    List<Book> books;
    Book book1, book2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_books_list);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      // loading dummy data
      books = new ArrayList<>();

      // used retrofit to get JSON data from server

      BookService.ProlificBookService bookService= BookService.getClient();
      Call<List<Book>> call = bookService.listBooks();
      call.enqueue(new Callback<List<Book>>() {
        @Override public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
          books = response.body();
          bookAdapter = new BookAdapter(books);
          rvBookList.setAdapter(bookAdapter);

        }

        @Override public void onFailure(Call<List<Book>> call, Throwable t) {

        }
      });

      fab = (FloatingActionButton) findViewById(R.id.fab);

      // initializing recyclerview and it properties
      rvBookList = (RecyclerView) findViewById(R.id.rvBookList);
      rvBookList.setLayoutManager(new LinearLayoutManager(this));
      rvBookList.setHasFixedSize(true);
      rvBookList.setItemAnimator(new DefaultItemAnimator());
      rvBookList.getItemAnimator().setAddDuration(1000);
    }

    private void updateUI() {
      if (bookAdapter == null) {
        bookAdapter = new BookAdapter(books);
        rvBookList.setAdapter(bookAdapter);
      } else {
        bookAdapter.notifyDataSetChanged();
      }
    }
}
