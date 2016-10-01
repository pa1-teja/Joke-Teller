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

        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(){

            @Override
            protected void onPostExecute(String result) {
                assertNotNull(result);
            }
        };


        endpointsAsyncTask.execute(new Pair<Context, String>(getInstrumentation().getContext(),"pavan"));

        try {
            latch.await(30,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}