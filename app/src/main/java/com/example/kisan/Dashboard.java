package com.example.kisan;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class Dashboard extends AppCompatActivity {

    Button weather_Btn, news_Btn, video_Btn, price_Btn, feedback_Btn, disease_Btn, language_btn, admin_btn, alert_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        CheckPermission();

        weather_Btn = (Button) findViewById(R.id.weatherButton);
        news_Btn = (Button) findViewById(R.id.newsButton);
        video_Btn = (Button) findViewById(R.id.videoButton);
        price_Btn = (Button) findViewById(R.id.priceButton);
        feedback_Btn = (Button) findViewById(R.id.feedbackButton);
        disease_Btn = (Button) findViewById(R.id.diseaseButton);
        language_btn = (Button) findViewById(R.id.LanguageButton);
        admin_btn = (Button) findViewById(R.id.AdminButton);
        alert_btn = (Button) findViewById(R.id.alertsButton);


        weather_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Check Weather", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Dashboard.this, weather.class);
                startActivity(i);
            }
        });

        news_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, NewsFeed.class);
                startActivity(i);
            }
        });

        price_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Dashboard.this, SelectOptionForPriceList.class);
                startActivity(i);
            }
        });


        video_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, videos.class);
                startActivity(i);
            }
        });

        feedback_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, feedback.class);
                startActivity(i);
            }
        });

        language_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, LanguageSelection.class);
                startActivity(i);
            }
        });

        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, AdminPanel.class);
                startActivity(i);
            }
        });

        disease_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, SelectCropType.class);
                startActivity(i);
            }
        });

        alert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this, AlertUpdates.class);
                startActivity(i);
            }
        });


    }

    public void CheckPermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }


                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }
}