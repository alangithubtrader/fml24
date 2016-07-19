package com.example.fml24.fml24;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.NewsAdaptor;
import com.example.fml24.fml24.Adaptor.PlayAdaptor;
import com.example.fml24.fml24.Model.News;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by adu on 16-07-12.
 */
public class FragmentPlay extends Fragment{

    List<String> list;
    GridView grid;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list=new ArrayList<String>();
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = (View) inflater.from(getContext()).inflate(R.layout.fragment_tabbed, null);
        grid = (GridView)  getView().findViewById(R.id.gridView1);

        for(int index = 1; index <= 49; index++)
        {
            list.add(String.valueOf(index));
        }

        ArrayAdapter<String> adp=new ArrayAdapter<String> (getContext(),
                android.R.layout.simple_list_item_1,list);
        grid.setAdapter(adp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_tabbed, container, false);
        return v;
    }


}
