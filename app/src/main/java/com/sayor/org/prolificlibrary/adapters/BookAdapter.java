package com.sayor.org.prolificlibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sayor.org.prolificlibrary.R;
import com.sayor.org.prolificlibrary.models.Book;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookHolder> {

    private List<Book> mBooks;

    public BookAdapter(List<Book> Books){
        this.mBooks = Books;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new BookHolder(itemView);
    }

  @Override
    public void onBindViewHolder(final BookHolder holder, final int position) {
        final Book book = mBooks.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvCategory.setText(book.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}
