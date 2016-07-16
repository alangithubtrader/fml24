package com.example.fml24.fml24.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fml24.fml24.Model.News;
import com.example.fml24.fml24.R;

import java.util.ArrayList;

/**
 * Created by adu on 16-07-15.
 */
public class NewsAdaptor extends ArrayAdapter<News> {
    public NewsAdaptor(Context context, ArrayList<News> news) {
        super(context,0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        News news = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_row, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.newsTitle);
        TextView time = (TextView) convertView.findViewById(R.id.newsTime);
        TextView body = (TextView) convertView.findViewById(R.id.newsBody);

        title.setText(news.getTitle());
        time.setText(news.getTime());
        body.setText(news.getBody());

        return convertView;
    }
}
