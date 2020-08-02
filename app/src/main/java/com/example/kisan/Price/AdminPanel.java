package com.example.kisan.Price;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kisan.Dashboard;
import com.example.kisan.R;
import com.example.kisan.Weather.weather;
import com.example.kisan.priceORalert;

public class AdminPanel extends AppCompatActivity {

    EditText userInput;
    String Password;
    Button login_Btn;
    String Pass = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        userInput = (EditText) findViewById(R.id.editTextTextPassword2);
        login_Btn = (Button)findViewById(R.id.Loginbutton);

        login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password = userInput.getText().toString();
                if(Password.equals(Pass)) {
                    Intent i = new Intent(AdminPanel.this, priceORalert.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Welcome Admin", Toast.LENGTH_LONG).show();
                }
                    else
                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();

            }
        });
    }
}