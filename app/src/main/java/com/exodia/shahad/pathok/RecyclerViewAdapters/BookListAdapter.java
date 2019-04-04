package com.exodia.shahad.pathok.RecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class BookListViewHolder extends RecyclerView.ViewHolder {

        BookListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
