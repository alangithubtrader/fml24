package com.example.fml24.fml24;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by adu on 16-07-12.
 */
public class FragmentNews extends ListFragment{

    private ArrayList<News> news;
    private NewsAdaptor newsAdaptor;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String test = "";
        try {
            URL url = new URL("https://free-lottery.herokuapp.com/api/get_announcement.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                test = stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        news = new ArrayList<>();
        news.add(new News("News Title 1", "Feb 6, 2010, 2:30pm", test));
        news.add(new News("News Title 2", "Feb 29, 2011, 7:30pm", "Body2"));
        news.add(new News("News Title 3", "Feb 19, 2016, 9:30pm", "Body3"));

        newsAdaptor = new NewsAdaptor(getActivity(), news);

        setListAdapter(newsAdaptor);


    }
}
