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
import com.exodia.shahad.pathok.RecyclerViewAdapters.NotificationAdapter;

public class FragmentNotifications extends Fragment {
    private RecyclerView notificationFeedRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        findElements(view); //find all the elements

        notificationFeedRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        notificationFeedRecycler.setLayoutManager(layoutManager);

        NotificationAdapter notificationAdapter = new NotificationAdapter(getContext());
        notificationFeedRecycler.setAdapter(notificationAdapter);

        return view;
    }

    void findElements(View view){
        notificationFeedRecycler = (RecyclerView) view.findViewById(R.id.fragment_notification_recycler_view);
    }
}
