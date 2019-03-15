package com.exodia.shahad.pathok.RecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exodia.shahad.pathok.R;

import java.util.List;

public class BrowseBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> bookList;

    public BrowseBookAdapter(Context context) {
        this.context = context;
//        this.bookList = bookList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.design_book_recycler, viewGroup, false);
        BookSuggestionViewHolder viewHolder = new BookSuggestionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class BookSuggestionViewHolder extends RecyclerView.ViewHolder{

        public BookSuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
