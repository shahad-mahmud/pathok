package com.exodia.shahad.pathok.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exodia.shahad.pathok.R;

public class FragmentCreatePost extends Fragment {

    private LinearLayout spinner, spinnerValueLayout;
    private boolean spinnerValueLayoutVisibilityFlag =false; //handles on click visibility functionality
    private TextView userName, spinnerValueCreative, spinnerValueReview, spinnerName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        //find elements
        findElements(view);

        //spinner onclick handler
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!spinnerValueLayoutVisibilityFlag){
                    spinnerValueLayout.setVisibility(View.VISIBLE);
                    spinnerValueLayoutVisibilityFlag = true;
                }else {
                    spinnerValueLayout.setVisibility(View.GONE);
                    spinnerValueLayoutVisibilityFlag = false;
                }
            }
        });

        spinnerValueCreative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerName.setText(spinnerValueCreative.getText()); //set the value selected
                spinnerValueLayout.setVisibility(View.GONE);
                spinnerValueLayoutVisibilityFlag = false;
            }
        });

        spinnerValueReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerName.setText(spinnerValueReview.getText()); //set the value selected
                spinnerValueLayout.setVisibility(View.GONE);
                spinnerValueLayoutVisibilityFlag = false;
            }
        });

        return view;
    }

    void findElements(View view){
        spinner = (LinearLayout) view.findViewById(R.id.create_post_spinner);
        spinnerValueLayout = (LinearLayout) view.findViewById(R.id.create_post_spinner_value_layout);

        //textViews
        userName = (TextView) view.findViewById(R.id.create_post_user_name);
        spinnerValueCreative = (TextView) view.findViewById(R.id.create_post_spinner_value_creative_writing);
        spinnerValueReview = (TextView) view.findViewById(R.id.create_post_spinner_value_book_review);
        spinnerName = (TextView) view.findViewById(R.id.create_post_spinner_value_shown);
    }
}
