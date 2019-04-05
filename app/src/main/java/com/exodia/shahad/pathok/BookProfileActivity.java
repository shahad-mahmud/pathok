package com.exodia.shahad.pathok;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.exodia.shahad.pathok.RecyclerViewAdapters.BookProfileAdapter;

public class BookProfileActivity extends AppCompatActivity {

    private RecyclerView bookProfileRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_profile);

        findElements();

        bookProfileRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        bookProfileRecycler.setLayoutManager(layoutManager);
        BookProfileAdapter bookProfileAdapter = new BookProfileAdapter(getApplicationContext(), "ss");
        bookProfileRecycler.setAdapter(bookProfileAdapter);
    }

    void findElements(){
        bookProfileRecycler = (RecyclerView) findViewById(R.id.book_profile_recycler);
    }
}
