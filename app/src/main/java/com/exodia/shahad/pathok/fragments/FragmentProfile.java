package com.exodia.shahad.pathok.fragments;

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
import com.exodia.shahad.pathok.recyclerViewAdapters.UserProfileAdapter;

public class FragmentProfile extends Fragment {
    RecyclerView userProfileRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findElements(view); //find elements

        userProfileRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        userProfileRecycler.setLayoutManager(layoutManager);

        UserProfileAdapter profileAdapter = new UserProfileAdapter(getContext(), "self");
        userProfileRecycler.setAdapter(profileAdapter);

        return view;
    }

    private void findElements(View view) {
        userProfileRecycler = (RecyclerView) view.findViewById(R.id.fragment_user_profile_recycler);
    }
}
