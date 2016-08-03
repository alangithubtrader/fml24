package com.example.fml24.fml24.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fml24.fml24.Model.News;
import com.example.fml24.fml24.Model.QuestionAnswer;
import com.example.fml24.fml24.R;

import java.util.ArrayList;

/**
 * Created by adu on 16-07-15.
 */
public class FAQAdaptor extends ArrayAdapter<QuestionAnswer> {
    public FAQAdaptor(Context context, ArrayList<QuestionAnswer> news) {
        super(context,0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        QuestionAnswer questionAnswer = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.faq_row, parent, false);
        }

        TextView question = (TextView) convertView.findViewById(R.id.question);
        TextView answer = (TextView) convertView.findViewById(R.id.answer);

        question.setText(questionAnswer.getQuestion());
        answer.setText(questionAnswer.getAnswer());

        return convertView;
    }
}
