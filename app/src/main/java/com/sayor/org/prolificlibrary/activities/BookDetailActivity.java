package com.sayor.org.prolificlibrary.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.sayor.org.prolificlibrary.R;
import com.sayor.org.prolificlibrary.models.Book;
import com.sayor.org.prolificlibrary.models.BookList;
import com.sayor.org.prolificlibrary.services.BookService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BookDetailActivity extends AppCompatActivity {

  public TextView ttvTitle, ttvAuthor, ttvPub, ttvCategory;
  BookList appBookList;
  public EditText etChkBook;
  private ShareActionProvider mShareActionProvider;
  BookService.ProlificBookService bookService;
  Intent share;
  int pos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
    setSupportActionBar(toolbar);

    ttvTitle = (TextView) findViewById(R.id.ttvTitle);
    ttvAuthor = (TextView) findViewById(R.id.ttvAuthor);
    ttvPub = (TextView) findViewById(R.id.ttvPub);
    ttvCategory = (TextView) findViewById(R.id.ttvCategory);

    Intent i = getIntent();
    pos = i.getIntExtra("pos", -1);

    appBookList = BookList.get(getApplicationContext());
    Book book = appBookList.getmBooks(pos);
    share = new Intent(Intent.ACTION_SEND);
    share.setType("text/plain");
    share.putExtra(Intent.EXTRA_SUBJECT, book.getTitle());
    share.putExtra(Intent.EXTRA_TEXT, book.getAuthor());
    share.putExtra(Intent.EXTRA_TEXT, book.getPublisher());
    share.putExtra(Intent.EXTRA_TEXT, book.getCategories());
    share.putExtra(Intent.EXTRA_TEXT, book.getLastCheckedOut());
    share.putExtra(Intent.EXTRA_TEXT, book.getLastCheckedOutBy());

    ttvTitle.setText(book.getTitle());
    ttvAuthor.setText(book.getAuthor());
    ttvPub.setText(book.getPublisher());
    ttvCategory.setText(book.getCategories());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate menu resource file.
    getMenuInflater().inflate(R.menu.menu_book_details, menu);

    // Locate MenuItem with ShareActionProvider
    MenuItem item = menu.findItem(R.id.menu_item_share);

    // Fetch and store ShareActionProvider
    mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
    setShareIntent(share);

    // Return true to display menu
    return true;
  }

  // Call to update the share intent
  private void setShareIntent(Intent shareIntent) {
    if (mShareActionProvider != null) {
      mShareActionProvider.setShareIntent(shareIntent);
    }
  }

  public void onCheckOut(View v){
    final BookList bookList = BookList.get(getParent());
    AlertDialog ad = new AlertDialog.Builder(this).setTitle(getString(R.string.checkbook))
        .setView(R.layout.checkout_book)
        .setMessage(getString(R.string.checkmsg))
        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Dialog dial = (Dialog) dialog;
            etChkBook = (EditText) dial.findViewById(R.id.etCheckOutName);
            appBookList = BookList.get(getApplicationContext());
            Book book = appBookList.getmBooks(pos);
            Book newBook = new Book();
            newBook.setLastCheckedOutBy(etChkBook.getText().toString());
            methodPUT(Integer.parseInt(book.getId()), newBook);
            dialog.dismiss();
          }
        })
        .setNegativeButton(getString(R.string.cancel), null)
        .create();

      ad.show();
  }

  private void methodPUT(int id, Book book) {
    if(bookService ==null)
      bookService = BookService.getClient();
    Call<Book> postCall = bookService.updateBook(id, book);
    postCall.enqueue(new Callback<Book>() {
      @Override public void onResponse(Call<Book> call, Response<Book> response) {
        Toast.makeText(getBaseContext(), getString(R.string.putmsg), Toast.LENGTH_SHORT).show();
      }

      @Override public void onFailure(Call<Book> call, Throwable t) {
        Log.e(getString(R.string.retroerr), t.getMessage());
        Toast.makeText(getBaseContext(), getString(R.string.puterr), Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
