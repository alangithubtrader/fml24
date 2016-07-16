package com.example.fml24.fml24.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

        TextView myNumbersTextView = (TextView) convertView.findViewById(R.id.myNumbers);
        TextView myTimeStampTextView = (TextView) convertView.findViewById(R.id.myNumbersTime);
        TextView myStateTextView = (TextView) convertView.findViewById(R.id.state);

        myNumbersTextView.setText(myNumbers.getNumbers());
        myTimeStampTextView.setText(myNumbers.getTime());
        myStateTextView.setText(myNumbers.getState());

        return convertView;
    }
}
