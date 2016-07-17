package com.example.fml24.fml24;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.NewsAdaptor;
import com.example.fml24.fml24.Adaptor.PlayAdaptor;
import com.example.fml24.fml24.Model.News;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by adu on 16-07-12.
 */
public class FragmentPlay extends Fragment{

    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};
    GridView gv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View myFragmentView = inflater.inflate(R.layout.grid_play, container, false);

        return myFragmentView;
    }


}
