package com.example.fml24.fml24.Adaptor;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fml24.fml24.Model.MyNumbers;
import com.example.fml24.fml24.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adu on 16-07-16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayAdaptor extends ArrayAdapter<String>{

    Context mContext;
    private static LayoutInflater inflater=null;
    List<String> listOfNumbers;

    public PlayAdaptor(Context c, List<String> numbers) {
        super(c, 0, numbers);
        listOfNumbers = numbers;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listOfNumbers.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return listOfNumbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
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

//
//        // TODO Auto-generated method stub
//        Holder holder=new Holder();
//        View rowView;
//
//        rowView = inflater.inflate(R.layout.grid_play, null);
//        holder.tv=(TextView) rowView.findViewById(R.id.myImageViewText);
//        holder.img=(ImageView) rowView.findViewById(R.id.myImageView);
//
//        holder.tv.setText(result[position]);
//        holder.img.setImageResource(imageId[position]);
//
//        rowView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
//            }
//        });
//
//        return rowView;
    }

}
