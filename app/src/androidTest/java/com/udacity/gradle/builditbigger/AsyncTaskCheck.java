package com.udacity.gradle.builditbigger;

import java.io.IOException;


import android.content.Context;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.util.Pair;
import com.example.pavan.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by pavan on 9/16/2016.
 */
public class AsyncTaskCheck extends InstrumentationTestCase {

    private static boolean called;
    private Context context;
    private static MyApi myApiService = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        called = false;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testSuccessfulFetch() throws Throwable {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                context = getInstrumentation().getContext();
                final EndpointsAsyncTask endpointsAsyncTask =  new EndpointsAsyncTask(){

                    @Override
                    protected String doInBackground(Pair<Context, String>... params) {
                        if(myApiService == null) {  // Only do this once
                            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                                    .setRootUrl("https://udacity-joke-tellar-app.appspot.com/_ah/api/");
                            // end options for devappserver

                            myApiService = builder.build();
                        }

                        context = params[0].first;
                        String name = params[0].second;

                        try {
                            String responseString;
                            responseString = myApiService.sayHi(name).execute().getData();

                            return responseString;
                        } catch (IOException e) {
                            return e.getMessage();
                        }
                    }

                    @Override
                    protected void onPostExecute(String result) {

                        fail("Hi, null");
                        assertEquals("String is null",null,result);
                        Log.i(getInstrumentation().getClass().getSimpleName(),"lauda lasson------" +result);
                        countDownLatch.countDown();
                    }
                };


                endpointsAsyncTask.execute(new Pair<Context, String>(getInstrumentation().getContext(),"null"));

                try {
                    countDownLatch.await(30,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
