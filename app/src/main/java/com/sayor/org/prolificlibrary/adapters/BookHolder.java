package com.sayor.org.prolificlibrary.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.sayor.org.prolificlibrary.R;
import com.sayor.org.prolificlibrary.activities.BookDetailActivity;
import com.sayor.org.prolificlibrary.models.Book;
import com.sayor.org.prolificlibrary.models.BookList;
import com.sayor.org.prolificlibrary.services.BookService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
    View.OnLongClickListener {
    TextView tvTitle, tvAuthor, tvCategory;
    BookList appBookList;
    BookService.ProlificBookService bookService;

    public BookHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
        tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(v.getContext(), BookDetailActivity.class);

        i.putExtra("pos", getAdapterPosition());

        // code for shared elements transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair<View, String> p1 = Pair.create((View) tvTitle, "title");
            Pair<View, String> p2 = Pair.create((View) tvAuthor, "auth");
            Pair<View, String> p3 = Pair.create((View) tvCategory, "cat");
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) v.getContext(), p1, p2, p3);
            v.getContext().startActivity(i, options.toBundle());
        } else {
            v.getContext().startActivity(i);
        }
    }

  @Override public boolean onLongClick(final View v) {
    new AlertDialog.Builder(v.getContext()).setTitle("Delete Items")
        .setMessage("Do you want to delete this book?")
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            //changes to singleton class
            BookList appBookList = BookList.get(v.getContext());
            Book book = appBookList.getmBooks(getAdapterPosition());
            methodDELETE(Integer.parseInt(book.getId()), v);
            dialog.dismiss();
          }
        })
        .setNegativeButton("Cancel", null)
        .create().show();
      return true;
  }

  private void methodDELETE(int bookID, final View v) {
    if(bookService ==null)
      bookService = BookService.getClient();
    Call<Book> postCall = bookService.deleteBooks(bookID);
    postCall.enqueue(new Callback<Book>() {
      @Override public void onResponse(Call<Book> call, Response<Book> response) {
        Toast.makeText(v.getContext(), "Book deleted successfully", Toast.LENGTH_SHORT).show();
      }

      @Override public void onFailure(Call<Book> call, Throwable t) {
        Log.e("retrofiterr", t.getMessage());
        Toast.makeText(v.getContext(), "Error in deleting book", Toast.LENGTH_SHORT).show();
      }
    });

  }
}
