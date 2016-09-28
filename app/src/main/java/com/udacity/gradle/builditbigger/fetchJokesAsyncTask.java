package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jokesdisplayingandroidlibrary.JokeActivity;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pavan on 9/22/2016.
 */
public class fetchJokesAsyncTask extends AsyncTask {


    Context context;
    Intent intent;


    public fetchJokesAsyncTask(Context context) {
        this.context = context;
    }



    public fetchJokesAsyncTask() {
    }

    private String DEFAULT_RESPONSE = "No Joke Available";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://udacity-joke-tellar-app.appspot.com/jokesApi");
//        HttpGet httpGet  = new HttpGet("http://udacity-joke-tellar-app.appspot.com/jokeApi");
//        HttpGet httpGet  = new HttpGet("http://192.168.1.2:8080/jokeApi");

        org.apache.http.HttpResponse httpResponse = null;

        try {

//            httpResponse = httpClient.execute(httpGet);
            httpResponse = httpClient.execute(httpPost);
            // write response to log
            Log.d("Http Post Response:", httpResponse.toString());

            HttpEntity entity = httpResponse.getEntity();
            InputStream inputStream = entity.getContent();

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return DEFAULT_RESPONSE;
    }



    @Override
    protected void onPostExecute(Object o) {

        Log.e("joke string response",o.toString());

        String joke = o.toString();

        String[] Joke = joke.split(">");

        for (String str: Joke) {
            Log.e(getClass().getSimpleName(),str);
            joke = str;
        }

        intent = new Intent(context, JokeActivity.class);
        intent.putExtra("jokeString",joke);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
