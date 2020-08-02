package com.example.kisan.Price;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.kisan.R;

import java.util.ArrayList;
import java.util.List;

public class SelectOptionForPriceList extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_for_price_list);
        sp = (Spinner) findViewById(R.id.LocationSpinner);
        sp.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        List<String> location = new ArrayList<String>();
        location.add("Select Location");
        location.add("Lahore");
        location.add("Attock");
        location.add("Bahawalpur");
        location.add("Faisalabad");
        location.add("Gujranwala");
        location.add("Khanewal");
        location.add("Multan");
        location.add("Rahim Yar Khan");
        location.add("Rawalpindi");
        location.add("Sahiwal");
        location.add("Sargodha");
        location.add("Sheikhupura");
        location.add("Sialkot");
        location.add("Kasur");

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, location);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(locationAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String location = parent.getItemAtPosition(position).toString();
        if(location == "Lahore")
        {
            Intent i = new Intent(this, LahorePriceList.class);
            startActivity(i);
        }

        if(location == "Attock")
        {
            Intent i = new Intent(this, AttockPriceList.class);
            startActivity(i);
        }

        if(location == "Bahawalpur")
        {
            Intent i = new Intent(this, BahawalpurPriceList.class);
            startActivity(i);
        }

        if(location == "Faisalabad")
        {
            Intent i = new Intent(this, FaisalabadPriceList.class);
            startActivity(i);
        }

        if(location == "Gujranwala")
        {
            Intent i = new Intent(this, GujranwalaPriceList.class);
            startActivity(i);
        }

        if(location == "Khanewal")
        {
            Intent i = new Intent(this, KhanewalPriceList.class);
            startActivity(i);
        }

        if(location == "Multan")
        {
            Intent i = new Intent(this, MultanPriceList.class);
            startActivity(i);
        }

        if(location == "Rahim Yar Khan")
        {
            Intent i = new Intent(this, RahimYarKhanPriceList.class);
            startActivity(i);
        }

        if(location == "Rawalpindi")
        {
            Intent i = new Intent(this, RawalpindiPriceList.class);
            startActivity(i);
        }

        if(location == "Sahiwal")
        {
            Intent i = new Intent(this, SahiwalPriceList.class);
            startActivity(i);
        }

        if(location == "Sargodha")
        {
            Intent i = new Intent(this, SargodhaPriceList.class);
            startActivity(i);
        }

        if(location == "Sheikhupura")
        {
            Intent i = new Intent(this, ShiekhupuraPriceList.class);
            startActivity(i);
        }

        if(location == "Sialkot")
        {
            Intent i = new Intent(this, SialkotPriceList.class);
            startActivity(i);
        }

        if(location == "Kasur")
        {
            Intent i = new Intent(this, KasurPriceList.class);
            startActivity(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
