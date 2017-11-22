package piyushchavan.contactapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
TextView textViewName;
TextView textviewNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        textviewNumber = findViewById(R.id.textViewNumber);
        textViewName = findViewById(R.id.textViewName);
        String name = getIntent().getStringExtra("Name");
        String number = getIntent().getStringExtra("Number");
        textViewName.setText(name);
        textviewNumber.setText(number);



            }
        }