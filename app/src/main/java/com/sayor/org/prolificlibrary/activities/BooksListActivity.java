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
import java.util.ArrayList;
import java.util.List;

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
      book1  = new Book();
      book1.setTitle("Lord of the Rings");
      book1.setAuthor("Tolkien");
      book1.setCategories("Fiction Novel");
      book2  = new Book();
      book2.setTitle("Harry potter");
      book2.setAuthor("Rowling");
      book2.setCategories("Fiction Novel");
      books.add(book1);
      books.add(book2);

      fab = (FloatingActionButton) findViewById(R.id.fab);

      // initializing recyclerview and it properties
      rvBookList = (RecyclerView) findViewById(R.id.rvBookList);
      rvBookList.setLayoutManager(new LinearLayoutManager(this));
      rvBookList.setHasFixedSize(true);
      rvBookList.setItemAnimator(new DefaultItemAnimator());
      rvBookList.getItemAnimator().setAddDuration(1000);
      updateUI();
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
