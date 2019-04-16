package com.exodia.shahad.pathok.recyclerViewAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exodia.shahad.pathok.R;
import com.exodia.shahad.pathok.dataClasses.BookData;

import java.util.ArrayList;
import java.util.List;

public class BookSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
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
        final BookData currentBook = bookDataList.get(i);
        List<String> authorNameList;
        String author_name = "";

        authorNameList = new ArrayList<>();

        final String imageUrl = "https://db.pathok.xyz/uploads/books/" + currentBook.getBookImage();

//        Log.e("image", imageUrl);

        bookSearchViewHolder.bookName.setText(currentBook.getBookName());
        Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(bookSearchViewHolder.bookImage);

        authorNameList = currentBook.getAuthorNames();

        if (authorNameList.size() == 1) {
            author_name = author_name + authorNameList.get(0);
        } else if (authorNameList.size() == 2) {
            author_name = author_name + authorNameList.get(0) + " ও " + authorNameList.get(1);
        } else {
            int size = authorNameList.size();
            int j;
            for (j = 0; j < size - 2; j++) {
                author_name = author_name + authorNameList.get(i) + ", ";
            }
            author_name = author_name + authorNameList.get(j) + " ও " + authorNameList.get(j + 1);
        }

        bookSearchViewHolder.author.setText(author_name);


        //onclick book add to post handler
        final String finalAuthor_name = author_name;
        bookSearchViewHolder.bookSearchResultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                returnIntent.putExtra("book_id", currentBook.getBookId());
                returnIntent.putExtra("book_name", currentBook.getBookName());
                returnIntent.putExtra("book_author", finalAuthor_name);
                returnIntent.putExtra("book_image", imageUrl);

                ((Activity) context).setResult(Activity.RESULT_OK, returnIntent);
                ((Activity) context).finish();
            }
        });

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
        RelativeLayout bookSearchResultLayout;

        BookSearchViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_search_list_book_image);
            bookName = itemView.findViewById(R.id.book_search_list_book_name);
            author = itemView.findViewById(R.id.book_search_list_book_author);
            bookSearchResultLayout = itemView.findViewById(R.id.book_search_list_layout);
        }
    }
}
