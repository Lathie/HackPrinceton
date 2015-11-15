package com.nishad.facedin;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Created by Victor on 9/3/2015.
 */
public class HTTPHelper {

    public static String GET(String url){
//        InputStream inputStream = null;
//        String data = "";
//        String Failiure_message = "search attempt failed";
//
//        AsyncTask<String, String, String> fetch = new AsyncTask<String, String, String>() {
//            @Override
//            protected String doInBackground(String[] params) {
//                String result = "";
//                try {
//
//                    // create HttpClient
//                    OkHttpClient client = new OkHttpClient();
//
//                    Request request = new Request.Builder()
//                            .url(params[0])
//                            .build();
//
//                    Response response = client.newCall(request).execute();
//                    return response.body().string();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                return result;
//            }
//
//        };
//
//        return fetch.execute(url).get();
            return null;
    }

//    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
//        String line = "";
//        String result = "";
//        while((line = bufferedReader.readLine()) != null)
//            result += line;
//
//        inputStream.close();
//        return result;
//
//    }


}
