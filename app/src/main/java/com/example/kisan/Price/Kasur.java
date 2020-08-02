package com.example.kisan.Price;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kisan.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kasur extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "MyTag";

    private Button zupload;
    private EditText zproduct;
    private EditText zprice;
    private EditText zdate;
    private TextView zread;
    private TextView zpriceRead;
    private Button zreadButton;
    private Spinner sp;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef, mRef2;
    private DatabaseReference mRef4date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lahore);
        mDatabase = FirebaseDatabase.getInstance();
        // mRef=mDatabase.getReference();
        mRef = mDatabase.getReference("Kasur");
        mRef4date = mDatabase.getReference("KsrDate");
        // zupload = (Button) findViewById(R.id.upload);
        zproduct = (EditText) findViewById(R.id.product);
        zprice = (EditText) findViewById(R.id.priceheading);
        zdate = (EditText) findViewById(R.id.uploaddate);
        // zread = (TextView) findViewById(R.id.readView);
        // zpriceRead = (TextView) findViewById(R.id.priceView);
        // zreadButton = (Button) findViewById(R.id.read);
        sp = (Spinner) findViewById(R.id.spinnerOptions);

        // this.zupload.setOnClickListener(this::updateData);
        // this.zreadButton.setOnClickListener(this::dataRead);
        // dataRead(null);


        // Spinner click listener
        sp.setOnItemSelectedListener(this);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Option");
        categories.add("Patato");
        categories.add("Onion");
        categories.add("Tomato");
        categories.add("Rice");
        categories.add("Wheat");
        categories.add("SugarCane");
        categories.add("Pulse");
        categories.add("Maize");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?>sp,View view, int position, long id) {
        // On selecting a spinner item
        String data = zproduct.getText().toString();
        String price= zprice.getText().toString();
        String item = sp.getItemAtPosition(position).toString();
        String date = zdate.getText().toString();

        if (item == "Patato") {

            Map<String, Object> updatedValues = new HashMap<>();
            updatedValues.put("Patato", data);
            updatedValues.put("PatatoPrice", price);
            updatedValues.put("date", date);
            mRef.updateChildren(updatedValues);

            //  mRef4date.child("date").setValue(date);
            // Showing selected spinner item
            Toast.makeText(sp.getContext(), item + " Price Updated Successfully", Toast.LENGTH_LONG).show();
        }
        if (item == "Onion") {
            /*PriceListPOJO Product = new PriceListPOJO(data,price,"","","","","","","","","","","","","","");
            String key = mRef.push().getKey();
            mRef.child(key).setValue(Product); */

            Map<String, Object> updatedValues = new HashMap<>();
            updatedValues.put("Onion", data);
            updatedValues.put("OnionPrice", price);

            mRef.updateChildren(updatedValues);

            mRef4date.child("date").setValue(date);
            Toast.makeText(sp.getContext(), item + " Price Updated Successfully", Toast.LENGTH_LONG).show();
        }
        if (item == "Tomato") {
            Map<String, Object> updatedValues = new HashMap<>();
            updatedValues.put("Tomato", data);
            updatedValues.put("TomatoPrice", price);

            mRef.updateChildren(updatedValues);

            mRef4date.child("date").setValue(date);
            Toast.makeText(sp.getContext(), item + " Price Updated Successfully", Toast.LENGTH_LONG).show();
        }
        if (item == "Rice") {
            Map<String, Object> updatedValues = new HashMap<>();
            updatedValues.put("Rice", data);
            updatedValues.put("RicePrice", price);

            mRef.updateChildren(updatedValues);

            mRef4date.child("date").setValue(date);
            Toast.makeText(sp.getContext(), item + " Price Updated Successfully", Toast.LENGTH_LONG).show();
        }
        if (item == "Wheat") {
            Map<String, Object> updatedValues = new HashMap<>();
            updatedValues.put("Wheat", data);
            updatedValues.put("WheatPrice", price);

            mRef.updateChildren(updatedValues);

            mRef4date.child("date").setValue(date);
            Toast.makeText(sp.getContext(), item + " Price Updated Successfully", Toast.LENGTH_LONG).show();
        }
        if (item == "SugarCane") {
            Map<String, Object> updatedValues = new HashMap<>();
            updatedValues.put("SugarCane", data);
            updatedValues.put("SugarCanePrice", price);

            mRef.updateChildren(updatedValues);

            mRef4date.child("date").setValue(date);
            Toast.makeText(sp.getContext(), item + " Price Updated Successfully", Toast.LENGTH_LONG).show();
        }
        if (item == "Pulse") {
            Map<String, Object> updatedValues = new HashMap<>();
            updatedValues.put("Pulse", data);
            updatedValues.put("PulsePrice", price);

            mRef.updateChildren(updatedValues);

            mRef4date.child("date").setValue(date);
            Toast.makeText(sp.getContext(), item + " Price Updated Successfully", Toast.LENGTH_LONG).show();
        }
        if (item == "Maize") {
            Map<String, Object> updatedValues = new HashMap<>();
            updatedValues.put("Maize", data);
            updatedValues.put("MaizePrice", price);

            mRef.updateChildren(updatedValues);

            mRef4date.child("date").setValue(date);
            Toast.makeText(sp.getContext(), item + " Price Updated Successfully", Toast.LENGTH_LONG).show();
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
