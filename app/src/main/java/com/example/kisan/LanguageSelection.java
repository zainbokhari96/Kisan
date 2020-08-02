package com.example.kisan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class LanguageSelection extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        sp = (Spinner) findViewById(R.id.LanguageSpinner);
        sp.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        List<String> language = new ArrayList<String>();
        language.add("Select Language");
        language.add("English");
        language.add("Urdu");


        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, language);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(languageAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String language = parent.getItemAtPosition(position).toString();
        if(language == "English")
        {
            Intent i = new Intent(this, Dashboard.class);
            startActivity(i);
        }

        if(language == "Urdu")
        {
            Intent i = new Intent(this, Dashboard_Urdu.class);
            startActivity(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
