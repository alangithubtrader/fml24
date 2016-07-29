package com.example.fml24.fml24.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fml24.fml24.Model.MyNumbers;
import com.example.fml24.fml24.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by adu on 16-07-16.
 */
public class MyNumbersAdaptor extends ArrayAdapter<MyNumbers>{

    public MyNumbersAdaptor(Context context, ArrayList<MyNumbers> news) {
        super(context,0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        MyNumbers myNumbers = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_numbers_row, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.imageBubble1);
        ImageView image2 = (ImageView) convertView.findViewById(R.id.imageBubble2);
        ImageView image3 = (ImageView) convertView.findViewById(R.id.imageBubble3);
        ImageView image4 = (ImageView) convertView.findViewById(R.id.imageBubble4);

        if (position % 2 == 0) {
            // even
            image.setImageResource(R.mipmap.lotto_buttons);
            image2.setImageResource(R.mipmap.lotto_buttons);
            image3.setImageResource(R.mipmap.lotto_buttons);
            image4.setImageResource(R.mipmap.lotto_buttons);
        } else {
            // odd
            image.setImageResource(R.mipmap.lotto_buttons2);
            image2.setImageResource(R.mipmap.lotto_buttons2);
            image3.setImageResource(R.mipmap.lotto_buttons2);
            image4.setImageResource(R.mipmap.lotto_buttons2);
        }

        TextView textViewBubble1 = (TextView) convertView.findViewById(R.id.textInsideBubble1);
        TextView textViewBubble2 = (TextView) convertView.findViewById(R.id.textInsideBubble2);
        TextView textViewBubble3 = (TextView) convertView.findViewById(R.id.textInsideBubble3);
        TextView textViewBubble4 = (TextView) convertView.findViewById(R.id.textInsideBubble4);
        TextView myTimeStampTextView = (TextView) convertView.findViewById(R.id.myNumbersTime);
        TextView myStateTextView = (TextView) convertView.findViewById(R.id.state);

        //this should never throw exception. This should only throw when number is < 4 if user some how was able to submit < 4 numbers.  Hence why the try catch.
        try{
            textViewBubble1.setText(myNumbers.getNumbers().get(0));
        }catch(Exception e)
        {

        }

        try{
            textViewBubble2.setText(myNumbers.getNumbers().get(1));
        }catch(Exception e)
        {

        }

        try{
            textViewBubble3.setText(myNumbers.getNumbers().get(2));
        }catch(Exception e)
        {

        }

        try{
            textViewBubble4.setText(myNumbers.getNumbers().get(3));
        }catch(Exception e)
        {

        }

        myTimeStampTextView.setText(myNumbers.getTime());
        myStateTextView.setText(myNumbers.getState());

        return convertView;
    }
}
