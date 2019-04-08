package com.exodia.shahad.pathok.recyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exodia.shahad.pathok.R;
import com.exodia.shahad.pathok.dataClasses.BookData;

import java.util.ArrayList;
import java.util.List;

public class BookSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<BookData> bookDataList;

    public BookSearchAdapter(Context context, List<BookData> bookDataList) {
        this.context = context;
        this.bookDataList = bookDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.design_book_search, viewGroup, false);
        return new BookSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BookSearchViewHolder bookSearchViewHolder = (BookSearchViewHolder) viewHolder;
        BookData currentBook = bookDataList.get(i);

        String imageUrl = "https://db.pathok.xyz/uploads/books/" + currentBook.getBookImage();

        Log.e("image", imageUrl);

        bookSearchViewHolder.bookName.setText(currentBook.getBookName());
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(bookSearchViewHolder.bookImage);

    }

    @Override
    public int getItemCount() {
        return bookDataList.size();
    }

    public void updateList(List<BookData> dataList) {
//        Log.e("adapter", String.valueOf(bookDataList.size()));

        bookDataList = new ArrayList<>();
        bookDataList.addAll(dataList);

//        Log.e("adapter end", String.valueOf(bookDataList.size()));
        notifyDataSetChanged();
    }

    class BookSearchViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImage;
        TextView bookName, author;

        BookSearchViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_search_list_book_image);
            bookName = itemView.findViewById(R.id.book_search_list_book_name);
            author = itemView.findViewById(R.id.book_search_list_book_author);
        }
    }
}
