package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.example.jokesdisplayingandroidlibrary.JokeActivity;
import com.example.pavan.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pavan on 9/15/2016.
 */
public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
//    private static MyApi myJokesApi = null;
    private Context context;
    Intent intent;
    private String DEFAULT_RESPONSE = "No Joke Available";
    HttpClient httpClient;
    HttpPost post;
    org.apache.http.HttpResponse httpResponse = null;


    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://udacity-joke-tellar-app.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost("http://udacity-joke-tellar-app.appspot.com/jokesApi");
//        HttpGet httpGet  = new HttpGet("http://udacity-joke-tellar-app.appspot.com/jokesApi");
//        HttpGet httpGet  = new HttpGet("http://192.168.1.2:8080/jokeApi");
        post = new HttpPost("http://192.168.1.2:8080/jokesApi");

        try {

//            httpResponse = httpClient.execute(httpGet);
            httpResponse = httpClient.execute(post);
            // write response to log
            Log.d("Http Post Response:", httpResponse.toString());

            HttpEntity entity = httpResponse.getEntity();
            InputStream inputStream = entity.getContent();

            context = params[0].first;
            String name = params[0].second;
            myApiService.sayHi(name).execute().getData();

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
            return e.getMessage();
        }

        return DEFAULT_RESPONSE;
    }

    @Override
    protected void onPostExecute(String result) {
//        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        Log.e("joke string response",result);

        String joke = result;

        String[] Joke = joke.split(">");

        for (String str: Joke) {
            Log.e(getClass().getSimpleName(),str);
            joke = str;
        }



        if (joke.contains("</html"))
            joke = DEFAULT_RESPONSE;

        intent = new Intent(context, JokeActivity.class);
        intent.putExtra("jokeString",joke);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


    }
}