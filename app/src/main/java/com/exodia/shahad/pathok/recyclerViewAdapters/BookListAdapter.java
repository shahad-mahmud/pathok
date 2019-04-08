package com.exodia.shahad.pathok.recyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exodia.shahad.pathok.activities.BookProfileActivity;
import com.exodia.shahad.pathok.R;

public class BookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;

    public BookListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.design_book_list, viewGroup, false);
        return new BookListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        BookListViewHolder bookListViewHolder = (BookListViewHolder) viewHolder;

        bookListViewHolder.bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookProfileActivity.class);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class BookListViewHolder extends RecyclerView.ViewHolder {

        private ImageView bookImage;
        private TextView bookName;

        BookListViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_list_book_image);
            bookName = itemView.findViewById(R.id.book_list_book_name);
        }


    }
}
