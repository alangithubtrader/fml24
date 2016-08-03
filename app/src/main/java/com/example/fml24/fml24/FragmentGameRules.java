package com.example.fml24.fml24;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.GameRulesAdaptor;
import com.example.fml24.fml24.Model.GameRule;
import com.example.fml24.fml24.Model.QuestionAnswer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by adu on 16-08-01.
 */
public class FragmentGameRules extends ListFragment{

    private GameRulesAdaptor gameRulesAdaptor = null;
    private GetGameRulesTask mAuthTask = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Fetch all news using async task
        mAuthTask = new GetGameRulesTask();
        mAuthTask.execute((Void) null);
    }


    public class GetGameRulesTask extends AsyncTask<Void, Void, JSONArray> {

        String GAME_RULES_URL = "https://free-lottery.herokuapp.com/api/get_rules.php";

        GetGameRulesTask() {
        }

        @Override
        protected JSONArray doInBackground(Void... params) {

            return BaseApi.getHttpRequest(GAME_RULES_URL);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArrayOfFAQ)
        {
            //todo: add error handling when no internet / JSONArray is null

            ArrayList<GameRule> gameRule = AddGameRuleToArrayList(jsonArrayOfFAQ);
            gameRulesAdaptor = new GameRulesAdaptor(getActivity(), gameRule);

            //set all news into UI
            setListAdapter(gameRulesAdaptor);

        }
    }


    private ArrayList<GameRule> AddGameRuleToArrayList(JSONArray jsonArray) {
        ArrayList<GameRule> gameRulesArrayList = new ArrayList<>();
        try {
            for(int index = 0; index < jsonArray.length(); index++)
            {
                JSONObject jsonObject = Common.getJsonObject(jsonArray, index);

                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");

                gameRulesArrayList.add(new GameRule(title, description));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gameRulesArrayList;
    }
}
