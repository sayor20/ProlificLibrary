package com.sayor.org.prolificlibrary.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.sayor.org.prolificlibrary.R;
import com.sayor.org.prolificlibrary.models.Book;
import com.sayor.org.prolificlibrary.services.BookService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBooksFragment extends android.support.v4.app.DialogFragment {

    public interface ListUpdateCallback {
        public void rvUpdateCallback();
    }

    BookService.ProlificBookService bookService;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add, null);
        final EditText etTitle = (EditText) v.findViewById(R.id.etTitle);
        final EditText etAuthor = (EditText) v.findViewById(R.id.etAuthor);
        final EditText etPub = (EditText) v.findViewById(R.id.etPub);
        final EditText etCat = (EditText) v.findViewById(R.id.etCat);

        AlertDialog ad = new AlertDialog.Builder(getActivity()).setTitle("Add Book")
                .setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    Book book = new Book();
                    book.setTitle(etTitle.getText().toString());
                    book.setAuthor(etAuthor.getText().toString());
                    book.setPublisher(etPub.getText().toString());
                    book.setCategories(etCat.getText().toString());
                    methodPOST(book, v);
                    mListener.rvUpdateCallback();
                  }
                })
            .setNegativeButton("Cancel", null)
            .create();
        ad.show();

      // text watchers for validation
      final View positiveAction = ad.getButton(DialogInterface.BUTTON_POSITIVE);
      positiveAction.setEnabled(false);

      etTitle.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          if (etTitle.getText().toString().trim().isEmpty() || etAuthor.getText().toString().trim().isEmpty()
              || etPub.getText().toString().trim().isEmpty() || etCat.getText().toString().trim().isEmpty())
            positiveAction.setEnabled(false);
          else
            positiveAction.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {}
      });

      etAuthor.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          if (etTitle.getText().toString().trim().isEmpty() || etAuthor.getText().toString().trim().isEmpty()
              || etPub.getText().toString().trim().isEmpty() || etCat.getText().toString().trim().isEmpty())
            positiveAction.setEnabled(false);
          else
            positiveAction.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {}
      });

      etPub.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          if (etTitle.getText().toString().trim().isEmpty() || etAuthor.getText().toString().trim().isEmpty()
              || etPub.getText().toString().trim().isEmpty() || etCat.getText().toString().trim().isEmpty())
            positiveAction.setEnabled(false);
          else
            positiveAction.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {}
      });

      etCat.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          if (etTitle.getText().toString().trim().isEmpty() || etAuthor.getText().toString().trim().isEmpty()
              || etPub.getText().toString().trim().isEmpty() || etCat.getText().toString().trim().isEmpty())
            positiveAction.setEnabled(false);
          else
            positiveAction.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable s) {}
      });

        return ad;
    }

  private void methodPOST(Book book, final View v) {
    if(bookService ==null)
      bookService = BookService.getClient();
    Call<Book> postCall = bookService.addBooks(book);
    postCall.enqueue(new Callback<Book>() {
      @Override public void onResponse(Call<Book> call, Response<Book> response) {
        Toast.makeText(v.getContext(), "Book posted successfully", Toast.LENGTH_SHORT).show();
      }

      @Override public void onFailure(Call<Book> call, Throwable t) {
        Log.e("retrofiterr", t.getMessage());
        Toast.makeText(v.getContext(), "Error in posting book", Toast.LENGTH_SHORT).show();
      }
    });
  }

    // Use this instance of the interface to deliver action events
    ListUpdateCallback mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            if(mListener==null)
                mListener = (ListUpdateCallback) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
