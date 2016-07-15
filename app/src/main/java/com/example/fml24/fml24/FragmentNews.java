package com.example.fml24.fml24;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adu on 16-07-12.
 */
public class FragmentNews extends ListFragment{

    private ArrayList<News> news;
    private NewsAdaptor newsAdaptor;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        news = new ArrayList<>();
        news.add(new News("News Title 1", "Feb 6, 2010, 2:30pm", "Body1"));
        news.add(new News("News Title 2", "Feb 29, 2011, 7:30pm", "Body2"));
        news.add(new News("News Title 3", "Feb 19, 2016, 9:30pm", "Body3"));

        newsAdaptor = new NewsAdaptor(getActivity(), news);

        setListAdapter(newsAdaptor);


    }

}
