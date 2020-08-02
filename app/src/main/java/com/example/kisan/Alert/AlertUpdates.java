package com.example.kisan.Alert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kisan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AlertUpdates extends AppCompatActivity {

    TextView tile, desc;
    private FirebaseDatabase mDatabase;
    private DatabaseReference alertdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_updates);
        mDatabase = FirebaseDatabase.getInstance();
        alertdb = mDatabase.getReference("Alert");

        tile = (TextView) findViewById(R.id.aleartTitle);
        desc = (TextView) findViewById((R.id.alertDesc));

        dataRead(null);

    }

    private void dataRead(View view) {

        alertdb.child("AlertTitle").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                tile.setText(data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        alertdb.child("AlertDesc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String price = dataSnapshot.getValue(String.class);
                desc.setText(price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}