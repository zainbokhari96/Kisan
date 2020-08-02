package com.example.kisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kisan.Alert.AlertUpdates;
import com.example.kisan.Disease.DiseaseDetection;
import com.example.kisan.Disease.SelectCropType;
import com.example.kisan.Feedback.feedback;
import com.example.kisan.Newsfeed.NewsFeed;
import com.example.kisan.Price.AdminPanel;
import com.example.kisan.Price.SelectOptionForPriceList;
import com.example.kisan.Price.SelectOptionForPriceUpdate;
import com.example.kisan.Videos.videos;
import com.example.kisan.Weather.weather;

public class Dashboard_Urdu extends AppCompatActivity {

    Button weather_Btn,news_Btn,video_Btn,price_Btn,feedback_Btn,disease_Btn,language_btn,admin_btn,alert_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard__urdu);

        weather_Btn = (Button)findViewById(R.id.weatherButton);
        news_Btn = (Button)findViewById(R.id.newsButton);
        video_Btn = (Button)findViewById(R.id.videoButton);
        price_Btn = (Button)findViewById(R.id.priceButton);
        feedback_Btn = (Button)findViewById(R.id.feedbackButton);
        disease_Btn = (Button)findViewById(R.id.diseaseButton);
        language_btn = (Button) findViewById(R.id.LanguageButton);
        admin_btn = (Button) findViewById(R.id.AdminButton);
        alert_btn = (Button) findViewById(R.id.alertsButton);




        weather_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard_Urdu.this, "Check Weather", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Dashboard_Urdu.this, weather.class);
                startActivity(i);
            }
        });

        news_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard_Urdu.this, NewsFeed.class);
                startActivity(i);
            }
        });

        price_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Dashboard_Urdu.this, SelectOptionForPriceList.class);
                startActivity(i);
            }
        });


        video_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard_Urdu.this, videos.class);
                startActivity(i);
            }
        });

        feedback_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard_Urdu.this, feedback.class);
                startActivity(i);
            }
        });

        language_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard_Urdu.this, LanguageSelection.class);
                startActivity(i);
            }
        });

        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard_Urdu.this, AdminPanel.class);
                startActivity(i);
            }
        });

        disease_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard_Urdu.this, SelectCropType.class);
                startActivity(i);
            }
        });

        alert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard_Urdu.this, AlertUpdates.class);
                startActivity(i);
            }
        });
    }
}
