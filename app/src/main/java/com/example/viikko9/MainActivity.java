package com.example.viikko9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    Button button;
    Theaters theaters;
    ListView text;
    EditText dtm;
    EditText start;
    EditText end;
    EditText movieName;
    ArrayList <String> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = MainActivity.this;
        button = (Button) findViewById(R.id.button);
        spinner = (Spinner) findViewById(R.id.spinner_theater);
        dtm = (EditText) findViewById(R.id.editTextDate);
        start = (EditText) findViewById(R.id.editTextTime);
        end = (EditText) findViewById(R.id.endTime);
        movieName = (EditText) findViewById(R.id.movieName);
        StrictMode.ThreadPolicy strictpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(strictpolicy);
        theaters = new Theaters();
        dropdownMenu(context, spinner, theaters);
    }

    public void dropdownMenu(Context context, Spinner spinner, Theaters theaters){
        ArrayAdapter<String> data = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, theaters.getNames());
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(data);
    }

    public void searchTheaters(View v){
        String name = (String) spinner.getSelectedItem();
        String date = dtm.getText().toString();
        String userStart = start.getText().toString();
        String userEnd = end.getText().toString();
        String mvName = movieName.getText().toString();
        if (userStart.isEmpty()) {
            userStart = "00.01";
        }
        if (userEnd.isEmpty()) {
            userEnd = "23.59";
        }
        if (date.isEmpty()){

            date = "25.03.2022";
        }
        if (name.equals("Valitse alue/teatteri")){
            ArrayList<String> movies = theaters.searchMoviesName(name, date, userStart, userEnd, mvName);
            text = (ListView) findViewById(R.id._dynamic);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movies);
            text.setAdapter(adapter);
        }else if (!name.isEmpty()) {
            ArrayList<String> movies = theaters.searchMovies(name, date, userStart, userEnd, mvName);
            text = (ListView) findViewById(R.id._dynamic);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movies);
            text.setAdapter(adapter);
        }

    }
}