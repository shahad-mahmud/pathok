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

import com.exodia.shahad.pathok.backgroundWorkers.BackgroundBookCategoryLoader;
import com.exodia.shahad.pathok.R;
import com.exodia.shahad.pathok.recyclerViewAdapters.BrowseBookCategoryAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FragmentBooks extends Fragment {

    private View view;
    private RecyclerView bookSuggestionRecyclerView;
    private List<String> category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_books, container, false);
        findElements();

        bookSuggestionRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        bookSuggestionRecyclerView.setLayoutManager(linearLayoutManager);

        category = new ArrayList<>();

        BackgroundBookCategoryLoader categoryLoader = new BackgroundBookCategoryLoader(getActivity());
        try {
            String result = categoryLoader.execute().get();

            JSONObject jsonObject;
            jsonObject = new JSONObject(result);

            String success = jsonObject.getString("success");

            if(success.equals("true")){
                int size = jsonObject.length();

                for(int i = 0; i < size-1; i++){
                    String category_name = jsonObject.getString(String.valueOf(i));

                    if(!category.contains(category_name)){
                        category.add(category_name);
                    }
                }
            }
        } catch (ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BrowseBookCategoryAdapter adapter = new BrowseBookCategoryAdapter(getContext(), category);
        bookSuggestionRecyclerView.setAdapter(adapter);

        return view;
    }

    void findElements(){
        bookSuggestionRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_books_recycler);


    }
}
