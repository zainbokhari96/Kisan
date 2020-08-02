package com.example.kisan.Weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kisan.Dashboard;
import com.example.kisan.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class weather extends AppCompatActivity {
    private static final String TAG = "MyTagW";

    final int REQUEST_CODE = 123;

    final String weatherURL = "http://api.openweathermap.org/data/2.5/weather";
    final String appID = "5ccc42f1f1b8940662ca2f90aa594495";
    final long minTime = 500;
    final long minDis = 100;

    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    TextView mcity, mcondition, mtemperature;
    ImageButton mainMenu;
    ImageButton changeCity;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mcity = (TextView) findViewById(R.id.city);
        mcondition = (TextView) findViewById(R.id.cond);
        mtemperature = (TextView) findViewById(R.id.temp);
        changeCity = (ImageButton) findViewById(R.id.search);
        mainMenu = (ImageButton) findViewById(R.id.back);

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Menu = new Intent(weather.this, Dashboard.class);
                startActivity(Menu);
            }
        });

        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchCity = new Intent(weather.this,SearchbyCity.class);
                startActivity(searchCity);
            }
        });
        Log.d(TAG,"OnCreate Run" );


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Getting Location of user");
        Intent myIntent = getIntent();
        String city = myIntent.getStringExtra("city");

        if(city != null){
            getCityWeather(city);
            Log.d(TAG, "City!=NULL");
        }
        else {
            getWeatherForCurrentLocation();
            Log.d(TAG, "Else: Get CurrentWeather");
        }
    }

    private void getWeatherForCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String Longitude = String.valueOf(location.getLongitude());
                String Latitude = String.valueOf(location.getLatitude());

                RequestParams requestParmas = new RequestParams();
                requestParmas.put("lat", Latitude);
                requestParmas.put("lon", Longitude);
                requestParmas.put("appid", appID);

                Log.d(TAG,"LOG + LAT"+ Longitude + Latitude);

                networking(requestParmas);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d(TAG,"onProviderDisabled" );
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, minTime, minDis, locationListener);
    }

    private void getCityWeather(String city)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("q",city);
        requestParams.put("appId",appID);
        networking(requestParams);
        Log.d(TAG,"City" + city + "appid" + appID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager!=null){
            locationManager.removeUpdates(locationListener);
        }
    }



    private void networking(RequestParams requestParams){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(weatherURL,requestParams,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        weatherModel weatherModeler = weatherModel.fromJson(response);
                        //assert weatherModeler != null;
                        mcity.setText(weatherModeler.getmCity());
                        mtemperature.setText(weatherModeler.getmtemp());
                        mcondition.setText(weatherModeler.mcon);

                        //Log.d(TAG,"Network"+ client + mcity + mtemperature + mcondition + appID + response );

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                        Log.d(TAG,"Network Failure");
                        Toast.makeText(weather.this, "Request Failed", Toast.LENGTH_SHORT).show();
                    }
                }

        );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("clima", "onRequestPermissionsResult: permission granted");
                getWeatherForCurrentLocation();
            }
        }
    }
}
