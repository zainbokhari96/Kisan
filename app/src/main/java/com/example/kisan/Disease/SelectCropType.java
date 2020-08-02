package com.example.kisan.Disease;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kisan.Dashboard;
import com.example.kisan.Dashboard_Urdu;
import com.example.kisan.R;

import java.util.ArrayList;
import java.util.List;

public class SelectCropType extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crop_type);
        sp = (Spinner) findViewById(R.id.Cropspinner);
        sp.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        List<String> language = new ArrayList<String>();
        language.add("Please Select Crop");
        language.add("Corn");



        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, language);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(languageAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String language = parent.getItemAtPosition(position).toString();
        if(language == "Corn")
        {
            Intent i = new Intent(this, DiseaseDetection.class);
            startActivity(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
