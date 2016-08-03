package com.example.fml24.fml24.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fml24.fml24.Model.GameRule;
import com.example.fml24.fml24.Model.QuestionAnswer;
import com.example.fml24.fml24.R;

import java.util.ArrayList;

/**
 * Created by adu on 16-07-15.
 */
public class GameRulesAdaptor extends ArrayAdapter<GameRule> {
    public GameRulesAdaptor(Context context, ArrayList<GameRule> news) {
        super(context,0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        GameRule gameRule = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_rule_row, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.game_rule_title);
        TextView description = (TextView) convertView.findViewById(R.id.game_rule_description);

        title.setText(gameRule.getTitle());
        description.setText(gameRule.getDescription());

        return convertView;
    }
}
