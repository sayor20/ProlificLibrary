package com.sayor.org.prolificlibrary.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.sayor.org.prolificlibrary.R;
import com.sayor.org.prolificlibrary.activities.BookDetailActivity;

public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView tvTitle, tvAuthor, tvCategory;

    public BookHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
        tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
        itemView.setOnClickListener(this);
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
}
