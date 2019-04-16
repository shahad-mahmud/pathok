package com.exodia.shahad.pathok.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.exodia.shahad.pathok.R;
import com.exodia.shahad.pathok.dataClasses.BookData;
import com.exodia.shahad.pathok.recyclerViewAdapters.BookSearchAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivitySearchBook extends AppCompatActivity {

    private EditText searchingKey;
    private List<BookData> bookDataList, searchList;
    private RecyclerView bookSearchRecycler;
    private BookSearchAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);
        FirebaseApp.initializeApp(ActivitySearchBook.this);

        //find elements
        searchingKey = findViewById(R.id.book_search_search_text);
        bookSearchRecycler = findViewById(R.id.book_search_recycler);

        bookDataList = new ArrayList<>();


        //getting data from fireBase
        getDataFromFireBase();

        //the recycler
        bookSearchRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        bookSearchRecycler.setLayoutManager(layoutManager);

//        Log.e("bookList", String.valueOf(bookDataList.size()));

        adapter = new BookSearchAdapter(this, bookDataList);
        bookSearchRecycler.setAdapter(adapter);

        searchingKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchList = new ArrayList<>();

                String key = s.toString();

                if (key.length() == 0) {
                    adapter.updateList(bookDataList);
                } else {
                    for (BookData book : bookDataList) {
                        String bookNameSearch = book.getBookName();

                        if (bookNameSearch.contains(key)) {
                            searchList.add(book);
                            adapter.updateList(searchList);
                        } else {
                            adapter.updateList(searchList);
                        }

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void getDataFromFireBase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("books");
        reference.keepSynced(true);

//        Log.e("fissdsdrebase", String.valueOf(reference));

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    BookData bookData = new BookData();
                    List<String> author_names, author_ids;

                    author_ids = new ArrayList<>();
                    author_names = new ArrayList<>();

                    String book_id = Objects.requireNonNull(data.child("bookId").getValue()).toString();
                    String book_name = Objects.requireNonNull(data.child("bookName").getValue()).toString();
                    String book_image = Objects.requireNonNull(data.child("bookImage").getValue()).toString();
                    String book_des = Objects.requireNonNull(data.child("bookDescription").getValue()).toString();

                    DataSnapshot ds = data.child("bookAuthors");
                    for (DataSnapshot authors : ds.getChildren()) {
                        String author_id = Objects.requireNonNull(authors.child("author_id").getValue()).toString();
                        String author_name = Objects.requireNonNull(authors.child("author_name").getValue()).toString();

                        author_ids.add(author_id);
                        author_names.add(author_name);
                    }


//                    Log.e("num of author", String.valueOf(data.child("bookAuthors").getChildrenCount()));

                    bookData.setBookId(book_id);
                    bookData.setBookName(book_name);
                    bookData.setBookImage(book_image);
                    bookData.setBookDescription(book_des);
                    bookData.setAuthorIds(author_ids);
                    bookData.setAuthorNames(author_names);

                    bookDataList.add(bookData);
//                    Log.e("size", String.valueOf(bookDataList.size()));
                    adapter.updateList(bookDataList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("error", String.valueOf(databaseError));
            }
        });
    }
}
