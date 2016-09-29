package com.udacity.gradle.builditbigger;

import java.io.BufferedReader;
import java.io.IOException;


import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.util.Pair;
import com.example.pavan.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.HttpResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by pavan on 9/16/2016.
 */
public class AsyncTaskCheck extends InstrumentationTestCase {

    private final String LOG_TAG = AsyncTaskCheck.class.getSimpleName();
    private CountDownLatch latch;


    @Before
    public void setUpTest(){
        latch = new CountDownLatch(1);

        fetchJokesAsyncTask fetchJokesAsyncTask= new fetchJokesAsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {


                HttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost("http://udacity-joke-tellar-app.appspot.com/jokesApi");
//        HttpGet httpGet  = new HttpGet("http://udacity-joke-tellar-app.appspot.com/jokesApi");
//        HttpGet httpGet  = new HttpGet("http://192.168.1.2:8080/jokeApi");
                HttpPost post = new HttpPost("http://192.168.1.2:8080/jokesApi");
                String DEFAULT_RESPONSE = "No Joke Available";

                org.apache.http.HttpResponse httpResponse = null;

                try {

//            httpResponse = httpClient.execute(httpGet);
                    httpResponse = httpClient.execute(post);
                    // write response to log
                    Log.d("Http Post Response:", httpResponse.toString());

                    HttpEntity entity = httpResponse.getEntity();
                    InputStream inputStream = entity.getContent();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    String result = "";
                    while ((line = bufferedReader.readLine()) != null)
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
                super.onPostExecute(o);
                assertNotNull(o);
            }

        };

       fetchJokesAsyncTask.execute();

        try {
            latch.await(30,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}