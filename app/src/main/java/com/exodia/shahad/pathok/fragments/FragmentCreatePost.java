package com.exodia.shahad.pathok.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.exodia.shahad.pathok.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentCreatePost extends Fragment {

    private LinearLayout spinner, spinnerValueLayout;
    private boolean spinnerValueLayoutVisibilityFlag =false; //handles on click visibility functionality
    private TextView userNameTextView, spinnerValueCreativeTextView, spinnerValueReviewTextView, spinnerNameTextView;
    private CircleImageView profileImage;
    private EditText editText;
    private RelativeLayout selectBook;

    private String userName, userEmail, userImage, userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);

        //find elements
        findElements(view);

        getUserDataFromCache();

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

        spinnerValueCreativeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerNameTextView.setText(spinnerValueCreativeTextView.getText()); //set the value selected
                spinnerValueLayout.setVisibility(View.GONE);
                selectBook.setVisibility(View.GONE);
                spinnerValueLayoutVisibilityFlag = false;
            }
        });

        spinnerValueReviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerNameTextView.setText(spinnerValueReviewTextView.getText()); //set the value selected
                spinnerValueLayout.setVisibility(View.GONE);
                selectBook.setVisibility(View.VISIBLE);
                spinnerValueLayoutVisibilityFlag = false;
            }
        });

        userNameTextView.setText(userName);
        Glide.with(Objects.requireNonNull(getContext()))
                .asBitmap()
                .load(userImage)
                .into(profileImage);

        return view;
    }

    void findElements(View view){
        spinner = (LinearLayout) view.findViewById(R.id.create_post_spinner);
        spinnerValueLayout = (LinearLayout) view.findViewById(R.id.create_post_spinner_value_layout);

        //textViews
        userNameTextView = (TextView) view.findViewById(R.id.create_post_user_name);
        spinnerValueCreativeTextView = (TextView) view.findViewById(R.id.create_post_spinner_value_creative_writing);
        spinnerValueReviewTextView = (TextView) view.findViewById(R.id.create_post_spinner_value_book_review);
        spinnerNameTextView = (TextView) view.findViewById(R.id.create_post_spinner_value_shown);

        profileImage = (CircleImageView) view.findViewById(R.id.create_post_profile_image);

        editText = view.findViewById(R.id.create_post_edit_the_post);

        selectBook = view.findViewById(R.id.create_post_select_book);
    }

    public void makePost(){
        String post_data = editText.getText().toString();
        Toast.makeText(getContext(), post_data, Toast.LENGTH_SHORT).show();

        if(post_data.equals("")){
            Toast.makeText(getContext(), "You must write your post.", Toast.LENGTH_SHORT).show();
        }
    }

    void getUserDataFromCache(){
        SharedPreferences userDetails = Objects.requireNonNull(getContext()).getSharedPreferences(getString(R.string.user_info_file), Context.MODE_PRIVATE);

        userId = userDetails.getString(getString(R.string.user_info_file_userID), "");
        userName = userDetails.getString(getString(R.string.user_info_file_name), "");
        userEmail = userDetails.getString(getString(R.string.user_info_file_email), "");
        userImage = userDetails.getString(getString(R.string.user_info_file_profileImage), "");
    }
}
