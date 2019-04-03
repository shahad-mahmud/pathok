package com.exodia.shahad.pathok.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exodia.shahad.pathok.R;
import com.exodia.shahad.pathok.RecyclerViewAdapters.HomeFeedAdapter;

public class FragmentHome extends Fragment {
    RecyclerView homePostFeedRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //find the elements
        findElements(view);

        homePostFeedRecycler.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homePostFeedRecycler.setLayoutManager(linearLayoutManager);

        HomeFeedAdapter feedAdapter = new HomeFeedAdapter(getContext());
        homePostFeedRecycler.setAdapter(feedAdapter);

        return view;
    }

    void findElements(View view){
        homePostFeedRecycler = (RecyclerView) view.findViewById(R.id.fragment_home_post_feed_recycler);
    }
}
