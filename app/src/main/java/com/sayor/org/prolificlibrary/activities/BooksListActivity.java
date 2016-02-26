package com.sayor.org.prolificlibrary.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.sayor.org.prolificlibrary.R;
import com.sayor.org.prolificlibrary.adapters.BookAdapter;
import com.sayor.org.prolificlibrary.fragments.AddBooksFragment;
import com.sayor.org.prolificlibrary.models.Book;
import com.sayor.org.prolificlibrary.models.BookList;
import com.sayor.org.prolificlibrary.services.BookService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BooksListActivity extends AppCompatActivity implements AddBooksFragment.ListUpdateCallback{

    private FloatingActionButton fab;
    private RecyclerView rvBookList;
    private BookAdapter bookAdapter;
    public List<Book> books;
    BookService.ProlificBookService bookService;
    BookList appBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_books_list);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      books = new ArrayList<>();
      bookAdapter = new BookAdapter(new ArrayList<Book>());
      methodGET();

      fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
          AddBooksFragment df = new AddBooksFragment();
          df.show(manager, "add_dialog");
        }
      });

      // initializing recyclerview and it properties
      rvBookList = (RecyclerView) findViewById(R.id.rvBookList);
      rvBookList.setLayoutManager(new LinearLayoutManager(this));
      rvBookList.setHasFixedSize(true);
      rvBookList.setItemAnimator(new DefaultItemAnimator());
      rvBookList.getItemAnimator().setAddDuration(1000);
    }

  private void methodGET() {
    if(bookService==null)
      bookService = BookService.getClient();
    Call<List<Book>> getCall = bookService.getBooks();
    getCall.enqueue(new Callback<List<Book>>() {
      @Override public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
        Toast.makeText(getBaseContext(), "Book added successfully", Toast.LENGTH_SHORT).show();
        books = response.body();
        appBookList = BookList.get(getApplicationContext());
        appBookList.addAllmBooks(response.body());
        bookAdapter = new BookAdapter(appBookList.getmBooks());
        rvBookList.setAdapter(bookAdapter);
      }

      @Override public void onFailure(Call<List<Book>> call, Throwable t) {
        Log.e("retrofiterr", t.getMessage());
        Toast.makeText(getBaseContext(), "Error in adding book", Toast.LENGTH_SHORT).show();
      }
    });
  }

  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate menu resource file.
    getMenuInflater().inflate(R.menu.menu_book_list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.menu_delete) {
      setUpDialog();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void setUpDialog() {
    new AlertDialog.Builder(this).setTitle("Delete book")
        .setMessage("Do you want to delete this book?")
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            methodCLEAN();
            dialog.dismiss();
          }
        })
        .setNegativeButton("Cancel", null)
        .create().show();
  }

  private void methodCLEAN() {
    if(bookService ==null)
      bookService = BookService.getClient();
    Call<Book> postCall = bookService.deleteAllBooks();
    postCall.enqueue(new Callback<Book>() {
      @Override public void onResponse(Call<Book> call, Response<Book> response) {
        Toast.makeText(getBaseContext(), "All books deleted", Toast.LENGTH_SHORT).show();
      }

      @Override public void onFailure(Call<Book> call, Throwable t) {
        Log.e("retrofiterr", t.getMessage());
        Toast.makeText(getBaseContext(), "Error in deleting all books", Toast.LENGTH_SHORT).show();
      }
    });
  }

  protected void onResume(){
    super.onResume();
    rvUpdateCallback();
  }

  // custom callback for AddItemsFragment
  public void rvUpdateCallback() {
    methodGET();
    //bookAdapter.notifyItemInserted(bookAdapter.getItemCount());
    bookAdapter.notifyDataSetChanged();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
