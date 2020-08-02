package com.example.kisan.Alert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan.Dashboard;
import com.example.kisan.Feedback.feedback;
import com.example.kisan.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EnterAlert extends AppCompatActivity {

    EditText tile, desc;
    Button Submit;
    private FirebaseDatabase mDatabase;
    private DatabaseReference alertTitle;
    private DatabaseReference alertDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_alert);

        mDatabase = FirebaseDatabase.getInstance();
        alertTitle = mDatabase.getReference("Alert");

        tile = (EditText) findViewById(R.id.updateAlert);
        desc = (EditText) findViewById((R.id.UpdateDesc));
        Submit = (Button) findViewById(R.id.alertSubmit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Stitle = tile.getText().toString();
                String Sdesc = desc.getText().toString();

                Map<String, Object> updatedValues = new HashMap<>();
                updatedValues.put("AlertTitle", Stitle);
                updatedValues.put("AlertDesc", Sdesc);
                alertTitle.updateChildren(updatedValues);


                //  mRef4date.child("date").setValue(date);
                // Showing selected spinner item
                Toast.makeText(getApplicationContext(),"Alert Submitted",Toast.LENGTH_SHORT).show();
            }
        });

    }




}