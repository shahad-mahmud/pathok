package com.exodia.shahad.pathok.recyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exodia.shahad.pathok.R;

public class HomeFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    public HomeFeedAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.design_post_block, viewGroup, false);
        return new FeedBlockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class FeedBlockViewHolder extends RecyclerView.ViewHolder{

        FeedBlockViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
