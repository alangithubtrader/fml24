package com.example.fml24.fml24.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fml24.fml24.R;

import java.util.List;

/**
 * Created by adu on 16-07-16.
 */

public class PlayAdaptor extends ArrayAdapter<String>{

    List<String> listOfNumbers;

    public PlayAdaptor(Context c, List<String> numbers) {
        super(c, 0, numbers);
        listOfNumbers = numbers;
    }

    @Override
    public int getCount() {
        return listOfNumbers.size();
    }

    @Override
    public String getItem(int position) {
        return listOfNumbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        String number = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_play, parent, false);
        }

        TextView numberInView = (TextView) convertView.findViewById(R.id.textInsideBubble);


        numberInView.setText(number);

        return convertView;

    }

}
