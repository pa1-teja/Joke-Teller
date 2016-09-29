package com.udacity.gradle.builditbigger.paid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.fetchJokesAsyncTask;

public class MainActivity extends AppCompatActivity {

    TextView joke_display_textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joke_display_textView = (TextView) findViewById(R.id.joke_display_textView);
        joke_display_textView.setText("Press the Button below to fetch a joke");
    }



    public void tellJoke(View view) {
        new fetchJokesAsyncTask(getApplicationContext()).execute();
    }
}
