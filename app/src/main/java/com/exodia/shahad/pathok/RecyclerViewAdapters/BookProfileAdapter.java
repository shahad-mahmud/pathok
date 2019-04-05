package com.exodia.shahad.pathok.RecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exodia.shahad.pathok.R;

public class BookProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    String reference;

    public BookProfileAdapter(Context context, String reference) {
        this.context = context;
        this.reference = reference;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.design_book_profile, viewGroup,false);
        return new BookProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BookProfileViewHolder bookProfileViewHolder = (BookProfileViewHolder) viewHolder;

        //more books
        bookProfileViewHolder.moreBooks.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        bookProfileViewHolder.moreBooks.setLayoutManager(layoutManager);
        BookListAdapter bookListAdapter = new BookListAdapter(context);
        bookProfileViewHolder.moreBooks.setAdapter(bookListAdapter);

        //reviews
        bookProfileViewHolder.reviews.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        bookProfileViewHolder.reviews.setLayoutManager(layoutManager2);
        HomeFeedAdapter feedAdapter = new HomeFeedAdapter(context);
        bookProfileViewHolder.reviews.setAdapter(feedAdapter);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class BookProfileViewHolder extends RecyclerView.ViewHolder{

        private ImageView bookCoverBack, bookCover;
        private TextView bookName, authors, aboutBook, averageRating, hasRead, isReading, willRead;
        private RecyclerView moreBooks, reviews;

        BookProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            findElements(itemView);
        }

        void findElements(View view){
            //imageViews
            bookCoverBack = view.findViewWithTag(R.id.book_profile_book_image_back);
            bookCover = view.findViewWithTag(R.id.book_profile_book_image);

            //textViews
            bookName = view.findViewById(R.id.book_profile_book_name);

            //RecyclerViews
            moreBooks = view.findViewById(R.id.book_profile_more_books);
            reviews = view.findViewById(R.id.book_profile_reviews);
        }
    }
}
