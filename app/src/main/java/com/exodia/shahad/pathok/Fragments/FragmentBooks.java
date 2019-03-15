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
import com.exodia.shahad.pathok.RecyclerViewAdapters.BrowseBookAdapter;
import com.exodia.shahad.pathok.RecyclerViewAdapters.BrowseBookCategoryAdapter;

public class FragmentBooks extends Fragment {

    private View view;
    private RecyclerView bookSuggestionRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_books, container, false);
        findElements();

        bookSuggestionRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bookSuggestionRecyclerView.setLayoutManager(linearLayoutManager);

        BrowseBookCategoryAdapter adapter = new BrowseBookCategoryAdapter(getContext());
        bookSuggestionRecyclerView.setAdapter(adapter);

        return view;
    }

    void findElements(){
        bookSuggestionRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_books_recycler);


    }
}
