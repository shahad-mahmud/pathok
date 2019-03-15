package com.exodia.shahad.pathok.RecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exodia.shahad.pathok.R;

public class BrowseBookCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    public BrowseBookCategoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.design_book_suggesion_category, viewGroup, false);
        BookCategoryViewHolder viewHolder = new BookCategoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BookCategoryViewHolder bookCategoryViewHolder = (BookCategoryViewHolder) viewHolder;

        BrowseBookAdapter browseBookAdapter = new BrowseBookAdapter(context);
        bookCategoryViewHolder.booksRecyclerView.setAdapter(browseBookAdapter);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class BookCategoryViewHolder extends RecyclerView.ViewHolder{

        RecyclerView booksRecyclerView;

        public BookCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            booksRecyclerView = (RecyclerView) itemView.findViewById(R.id.fragment_books_category_recycler);
            booksRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            booksRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }
}
