package com.sayor.org.prolificlibrary.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.sayor.org.prolificlibrary.R;

public class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    CheckBox cbDone;
    TextView tvName, tvDesc, tvQty;

    public BookHolder(View itemView) {
        super(itemView);
        cbDone = (CheckBox) itemView.findViewById(R.id.cbDone);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
        tvQty = (TextView) itemView.findViewById(R.id.tvQty);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent i = new Intent(v.getContext(), EditItemActivity.class);
        i.putExtra("pos", getAdapterPosition());

        // code for shared elements transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair<View, String> p1 = Pair.create((View) tvName, "name");
            Pair<View, String> p2 = Pair.create((View) tvDesc, "desc");
            Pair<View, String> p3 = Pair.create((View) tvQty, "qty");
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) v.getContext(), p1, p2, p3);
            v.getContext().startActivity(i, options.toBundle());
        } else {
            v.getContext().startActivity(i);
        }
    }
}
