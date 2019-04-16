package com.exodia.shahad.pathok.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.exodia.shahad.pathok.R;
import com.exodia.shahad.pathok.activities.ActivitySearchBook;
import com.exodia.shahad.pathok.backgroundWorkers.BackgroundPostHandler;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentCreatePost extends Fragment {

    private LinearLayout spinner, spinnerValueLayout;
    private boolean spinnerValueLayoutVisibilityFlag =false; //handles on click visibility functionality
    private TextView userNameTextView, spinnerValueCreativeTextView, spinnerValueReviewTextView, spinnerNameTextView;
    private CircleImageView profileImage;
    private EditText editText;
    private RelativeLayout selectBook;

    private final static int BOOK_SEARCH_CODE = 1;
    private TextView selectedBookNameTv, selectedBookAuthorTv;
    private ImageView selectedBookImageIv;
    private String selectedBookId;

    private String userName, userEmail, userImage, userId;
    private boolean isABookSelected = false;

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

        //set user details
        userNameTextView.setText(userName);
        Glide.with(Objects.requireNonNull(getContext()))
                .asBitmap()
                .load(userImage)
                .into(profileImage);

        selectBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivitySearchBook.class);
                startActivityForResult(intent, BOOK_SEARCH_CODE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BOOK_SEARCH_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //make items visible
                selectedBookImageIv.setVisibility(View.VISIBLE);
                selectedBookAuthorTv.setVisibility(View.VISIBLE);

                //get data
                selectedBookId = data.getStringExtra("book_id");
                Toast.makeText(getContext(), selectedBookId, Toast.LENGTH_SHORT).show();

                //set data
                selectedBookNameTv.setText(data.getStringExtra("book_name"));
                selectedBookAuthorTv.setText(data.getStringExtra("book_author"));
                Glide
                        .with(this)
                        .asBitmap()
                        .load(data.getStringExtra("book_image"))
                        .into(selectedBookImageIv);

                isABookSelected = true;
            } else if (resultCode == Activity.RESULT_CANCELED && !isABookSelected) {
                Toast.makeText(getContext(), "You must select a book.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void findElements(View view){
        //Layouts
        spinner = (LinearLayout) view.findViewById(R.id.create_post_spinner);
        spinnerValueLayout = (LinearLayout) view.findViewById(R.id.create_post_spinner_value_layout);
        selectBook = view.findViewById(R.id.create_post_select_book);

        //textViews
        userNameTextView = (TextView) view.findViewById(R.id.create_post_user_name);
        spinnerValueCreativeTextView = (TextView) view.findViewById(R.id.create_post_spinner_value_creative_writing);
        spinnerValueReviewTextView = (TextView) view.findViewById(R.id.create_post_spinner_value_book_review);
        spinnerNameTextView = (TextView) view.findViewById(R.id.create_post_spinner_value_shown);
        selectedBookNameTv = view.findViewById(R.id.create_post_selected_book_name);
        selectedBookAuthorTv = view.findViewById(R.id.create_post_selected_book_author);

        //imageViews
        profileImage = (CircleImageView) view.findViewById(R.id.create_post_profile_image);
        selectedBookImageIv = view.findViewById(R.id.create_post_selected_book_image);

        //EditTextViews
        editText = view.findViewById(R.id.create_post_edit_the_post);

    }

    public void makePost(){
        String postData = editText.getText().toString();

        if (postData.equals("")) {
            Toast.makeText(getContext(), "You must write your post.", Toast.LENGTH_SHORT).show();
        } else {
            if (spinnerNameTextView.getText().equals("Book review") && selectedBookAuthorTv.getVisibility() != View.VISIBLE) {
                Toast.makeText(getContext(), "You must select a book", Toast.LENGTH_SHORT).show();
            } else if (spinnerNameTextView.getText().equals("Book review") && selectedBookAuthorTv.getVisibility() == View.VISIBLE) {
                //post the book review
                Toast.makeText(getContext(), "Book review is being posted", Toast.LENGTH_SHORT).show();

                BackgroundPostHandler postHandler = new BackgroundPostHandler(getContext(), "book_review", userId, selectedBookId, postData);
                postHandler.execute();

                editText.setText("");
            } else if (spinnerNameTextView.getText().equals("Creative Writing")) {
                //post the creative writing

                Toast.makeText(getContext(), "Your creative writing is being posted", Toast.LENGTH_SHORT).show();

                BackgroundPostHandler postHandler = new BackgroundPostHandler(getContext(), "creative_writing", userId, postData);
                postHandler.execute();

                editText.setText("");
            }


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
