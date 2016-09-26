package com.example.jokesdisplayingandroidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String responseString = getIntent().getStringExtra("jokeString");
        TextView textView = (TextView) findViewById(R.id.textView);

        textView.setText(responseString);
    }
}
