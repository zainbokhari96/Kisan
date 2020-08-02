package com.example.kisan.Weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kisan.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SearchbyCity extends AppCompatActivity {

    private EditText cityText;
    private ImageButton backbtn;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchby_city);
        cityText = (EditText) findViewById(R.id.cityname);
        backbtn = (ImageButton) findViewById(R.id.back);
        search = (Button) findViewById(R.id.search);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        cityText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String city = cityText.getText().toString();

                Intent cityIntent = new Intent(SearchbyCity.this,weather.class);
                cityIntent.putExtra("city",city);
                startActivity(cityIntent);
                return false;
            }
        });
    }
    public void find(View view){
        String city = cityText.getText().toString();

        Intent cityIntent = new Intent(SearchbyCity.this,weather.class);
        cityIntent.putExtra("city",city);
        startActivity(cityIntent);
    }
}

