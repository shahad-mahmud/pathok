package com.exodia.shahad.pathok.recyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exodia.shahad.pathok.R;

import java.util.ArrayList;
import java.util.List;

public class BrowseBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> bookList;

    public BrowseBookAdapter(Context context) {
        this.context = context;
//        this.bookList = bookList;
        bookList = new ArrayList<>();
        for(int i = 0; i< 10; i++){
            this.bookList.add("a");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.design_book_recycler, viewGroup, false);
        return new BookSuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

//        Log.d("in_view", String.valueOf(i));

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookSuggestionViewHolder extends RecyclerView.ViewHolder{

        public BookSuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    void add(){
        Log.e("added called", "added to the list");
        for(int j = 0; j< 10; j++){
            this.bookList.add("a");
        }
        this.notifyDataSetChanged();
//        notifyDataSetChanged();
    }
}
