package com.example.fml24.fml24;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.FAQAdaptor;
import com.example.fml24.fml24.Adaptor.NewsAdaptor;
import com.example.fml24.fml24.Model.News;
import com.example.fml24.fml24.Model.QuestionAnswer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by adu on 16-08-01.
 */
public class FragmentFAQ extends ListFragment{

    private FAQAdaptor faqAdaptor = null;
    private GetFAQTask mAuthTask = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Fetch all news using async task
        mAuthTask = new GetFAQTask();
        mAuthTask.execute((Void) null);
    }


    public class GetFAQTask extends AsyncTask<Void, Void, JSONArray> {

        String FAQ_URL = "https://free-lottery.herokuapp.com/api/get_faq.php";

        GetFAQTask() {
        }

        @Override
        protected JSONArray doInBackground(Void... params) {

            return BaseApi.getHttpRequest(FAQ_URL);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArrayOfFAQ)
        {
            //todo: add error handling when no internet / JSONArray is null

            ArrayList<QuestionAnswer> questionAnswer = AddFAQToArrayList(jsonArrayOfFAQ);
            faqAdaptor = new FAQAdaptor(getActivity(), questionAnswer);

            //set all news into UI
            setListAdapter(faqAdaptor);

        }
    }


    private ArrayList<QuestionAnswer> AddFAQToArrayList(JSONArray jsonArray) {
        ArrayList<QuestionAnswer> questionsAndAnswersArrayList = new ArrayList<>();
        try {
            for(int index = 0; index < jsonArray.length(); index++)
            {
                JSONObject jsonObject = Common.getJsonObject(jsonArray, index);

                String question = jsonObject.getString("question");
                String answer = jsonObject.getString("answer");

                questionsAndAnswersArrayList.add(new QuestionAnswer(question, answer));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questionsAndAnswersArrayList;
    }
}
