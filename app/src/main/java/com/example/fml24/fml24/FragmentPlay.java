package com.example.fml24.fml24;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by adu on 16-07-12.
 */
public class FragmentPlay extends Fragment{

    public FragmentPlay()
    {
        Log.i("Fragment Check", "Fragment Play Created.");
    }

    public static FragmentPlay newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt("argsInstance", instance);
        FragmentPlay firstFragment = new FragmentPlay();
        firstFragment.setArguments(args);
        return firstFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play, container, false);
    }


}
