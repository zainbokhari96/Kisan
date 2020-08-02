package com.example.kisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kisan.Alert.EnterAlert;
import com.example.kisan.Price.SelectOptionForPriceUpdate;

import java.util.ArrayList;
import java.util.List;

public class priceORalert extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_o_ralert);
        sp = (Spinner) findViewById(R.id.PAspinner);
        sp.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        List<String> language = new ArrayList<String>();
        language.add("Select Option");
        language.add("PRICE");
        language.add("ALERT");


        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, language);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(languageAdapter);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String language = parent.getItemAtPosition(position).toString();
        if(language == "PRICE")
        {
            Intent i = new Intent(this, SelectOptionForPriceUpdate.class);
            startActivity(i);
        }

        if(language == "ALERT")
        {
            Intent i = new Intent(this, EnterAlert.class);
            startActivity(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}