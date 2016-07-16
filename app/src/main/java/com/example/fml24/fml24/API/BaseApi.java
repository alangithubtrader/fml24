package com.example.fml24.fml24.API;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by adu on 16-07-15.
 */
public class BaseApi {

    public static JSONArray getHttpRequest(String urlInput) {

        try {
            URL url = new URL(urlInput);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                JSONTokener tokener = new JSONTokener(stringBuilder.toString());
                JSONArray finalResult = new JSONArray(tokener);
                return finalResult;
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }
        return null;
    }

    public static String getHttpRequest2(String urlInput) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget= new HttpGet(urlInput);

        HttpResponse response = httpclient.execute(httpget);

        if(response.getStatusLine().getStatusCode()==200){
             return EntityUtils.toString(response.getEntity());
        } else {
            Log.i("Server response", "Failed to get server response" );
        }
        return "";
    }

}
