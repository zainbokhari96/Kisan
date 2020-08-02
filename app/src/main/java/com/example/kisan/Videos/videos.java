package com.example.kisan.Videos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kisan.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class videos extends AppCompatActivity {

    RecyclerView mRecylerView;
    FirebaseDatabase database;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        mRecylerView = findViewById(R.id.recylerview_video);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Video");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<videoPOJO,videoViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<videoPOJO, videoViewHolder>(videoPOJO.class,R.layout.player,videoViewHolder.class,mRef) {
                    @Override
                    protected void populateViewHolder(videoViewHolder videoViewHolder, videoPOJO videoPOJO, int i) {
                        videoViewHolder.setVideo(getApplication(),videoPOJO.getTitle(),videoPOJO.getUrl());
                    }
                };
        mRecylerView.setAdapter(firebaseRecyclerAdapter);
    }

}