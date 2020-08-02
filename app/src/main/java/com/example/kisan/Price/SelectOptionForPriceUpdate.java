package com.example.kisan.Price;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.kisan.R;

import java.util.ArrayList;
import java.util.List;

public class SelectOptionForPriceUpdate extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner sp;
    EditText userInput;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_for_price_update);
        sp = (Spinner) findViewById(R.id.LocationSpinner);
        sp.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        userInput = (EditText) findViewById(R.id.editTextTextPassword);

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
        Password = userInput.getText().toString();
        if(location == "Lahore")
        {
            String LPas = "lahore123";
            if(Password.equals(LPas)) {
                Intent i = new Intent(this, Lahore.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Lahore Admin", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Attock") {
            String LPas = "attock123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Attock.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Attock Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Bahawalpur")
        {
            String LPas = "bahawalpur123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Bahawalpur.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Bahawalpur Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Faisalabad")
        {
            String LPas = "faisalabad123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Faisalabad.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Faisalabad Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Gujranwala")
        {
            String LPas = "gujranwala123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Gujranwala.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Gujranwala Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Khanewal")
        {
            String LPas = "khanewal123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Khanewal.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Khanewal Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Multan")
        {
            String LPas = "multan123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Multan.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Multan Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Rahim Yar Khan")
        {
            String LPas = "rahim123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, RahimYarKhan.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Rahim Yar Khan Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Rawalpindi")
        {
            String LPas = "rawalpindi123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Rawalpindi.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Rawalpindi Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Sahiwal")
        {
            String LPas = "sahiwal123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Sahiwal.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Sahiwal Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Sargodha")
        {
            String LPas = "sargodha123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Sargodha.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Sargodha Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Sheikhupura")
        {
            String LPas = "sheikhupura123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Shiekhupura.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Sheikhupura Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Sialkot")
        {
            String LPas = "sialkot123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Sialkot.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Sialkot Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }

        if(location == "Kasur")
        {
            String LPas = "kasur123";
            if (Password.equals(LPas)) {
                Intent i = new Intent(this, Kasur.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome Kasur Admin", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
