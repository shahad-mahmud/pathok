package com.exodia.shahad.pathok.recyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exodia.shahad.pathok.R;

import java.util.List;
import java.util.Objects;

public class BrowseBookCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> categoryList;

    public BrowseBookCategoryAdapter(Context context, List<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
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
        final BookCategoryViewHolder bookCategoryViewHolder = (BookCategoryViewHolder) viewHolder;

        bookCategoryViewHolder.categoryName.setText(categoryList.get(i));

        final BrowseBookAdapter browseBookAdapter = new BrowseBookAdapter(context);
        bookCategoryViewHolder.booksRecyclerView.setAdapter(browseBookAdapter);

        bookCategoryViewHolder.booksRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager layoutManager = (LinearLayoutManager) bookCategoryViewHolder.booksRecyclerView.getLayoutManager();
                int pos = Objects.requireNonNull(layoutManager).findLastVisibleItemPosition();
                int size = browseBookAdapter.getItemCount();

                if(pos >= size-3){
                    if(size <= 200) {
                        browseBookAdapter.add();
//                        browseBookAdapter.notifyDataSetChanged();
                    }

                }

                Log.d("state", String.valueOf(size));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class BookCategoryViewHolder extends RecyclerView.ViewHolder{

        RecyclerView booksRecyclerView;
        TextView categoryName;

        public BookCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = (TextView) itemView.findViewById(R.id.fragment_books_category_name);
            booksRecyclerView = (RecyclerView) itemView.findViewById(R.id.fragment_books_category_recycler);
            booksRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            booksRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }
}
