package com.example.kisan.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kisan.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class feedback extends AppCompatActivity {

    private static final String TAG = "MyTag" ;

    private EditText feedbackMsg;
    private EditText feedbackNo;
    private Button feedbackSubmit;

    private FirebaseDatabase fdatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackMsg = (EditText) findViewById(R.id.feedText);
        feedbackNo = (EditText) findViewById(R.id.phone);
        feedbackSubmit = (Button) findViewById(R.id.submitButton);

        fdatabase = FirebaseDatabase.getInstance();
        mRef = fdatabase.getReference("Feedback");

        this.feedbackSubmit.setOnClickListener(this::uploadFeedback);
    }

    private void uploadFeedback(View view){
        String msg = feedbackMsg.getText().toString();
        String phone = feedbackNo.getText().toString();

        String key = mRef.push().getKey();
        mRef.child(key).child("Message").setValue(msg);
        mRef.child(key).child("Phone").setValue(phone);

        Log.d(TAG,"Data Inserted  " + msg + phone);
        Toast.makeText(this,"Feedback Submitted",Toast.LENGTH_LONG).show();
    }

}
