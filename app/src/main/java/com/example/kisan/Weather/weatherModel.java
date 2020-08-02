package com.example.kisan.Weather;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class weatherModel {
    private static final String TAG = "MyTagW";
    public String mcity;
    public String mtemp;
    public String mcon;
    public int mCon;

    public static weatherModel fromJson(JSONObject object){
        try {

            weatherModel weatherModeler = new weatherModel();

            weatherModeler.mcity = object.getString("name");
            weatherModeler.mCon= object.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherModeler.mcon=object.getJSONArray("weather").getJSONObject(0).getString("description");

            double temp=object.getJSONObject("main").getDouble("temp") -273.15;
            int roundValue = (int) Math.round(temp);
            weatherModeler.mtemp = Integer.toString(roundValue);
            return weatherModeler;
        } catch (Exception e) {
            Log.d(TAG, "fromJson: error catch");
            e.printStackTrace();
            return null;
        }
    }

    public String  getmtemp(){
        return mtemp + "°";
    }

    public String getmCity(){
        return mcity;
    }

}
