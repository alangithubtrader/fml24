package com.example.fml24.fml24;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.MyNumbersAdaptor;
import com.example.fml24.fml24.Adaptor.PlayAdaptor;
import com.example.fml24.fml24.Model.MyNumbers;
import com.example.fml24.fml24.Model.WinningNumbers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by adu on 16-07-12.
 */
public class FragmentMyNumbers extends ListFragment{

    private MyNumbersAdaptor myNumbersAdaptor;

    private GetNumbersTimeStateTask mAuthTask = null;

    public ArrayList<MyNumbers> myNumbers = null;
    public ArrayList<WinningNumbers> winningNumbers = null;

    private int asyncTaskCounter = 0;

    GridView grid;
    List<String> list;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Get user id from Login Activity
        String userId = "";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userId = sharedPreferences.getString(getString(R.string.user_id_after_login), "");

        mAuthTask = new GetNumbersTimeStateTask(userId);
        mAuthTask.execute((Void) null);


    }

    private ArrayList<MyNumbers> populateStatesInMyNumbersArray(ArrayList<MyNumbers> myNumbers, ArrayList<WinningNumbers> winningNumbers) throws ParseException {
        //we only care about this week (active), last week (check), and the week before last week (expired)

        String lastWinningTime = winningNumbers.get(0).getTime();
        String lastWinningTime2WeeksAgo = winningNumbers.get(1).getTime();

        for(MyNumbers myNumber:myNumbers)
        {
            if(isFirstDateAfterSecondDate(myNumber.getTime(), lastWinningTime))
            {
                myNumber.setState("Active");
                continue;
            }

            if(isFirstDateAfterSecondDate(myNumber.getTime(), lastWinningTime2WeeksAgo) && isFirstDateAfterSecondDate(lastWinningTime, myNumber.getTime()))
            {
                myNumber.setState("Check");
                continue;
            }

            if(isFirstDateAfterSecondDate(lastWinningTime2WeeksAgo, myNumber.getTime()))
            {
                myNumber.setState("Expired");
                continue;
            }
        }

        return myNumbers;

    }

    private boolean isFirstDateAfterSecondDate(String firstDate, String secondDate) throws ParseException {
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date dt1 = dtf.parse(firstDate);
        final Date dt2 = dtf.parse(secondDate);

        return  dt1.after(dt2);
    }

    private ArrayList<MyNumbers> addMyNumbersToArrayList(JSONArray jsonArray) {

        ArrayList<MyNumbers> myNumbersArrayList = new ArrayList<>();
        try {
            for(int index = 0; index < jsonArray.length(); index++)
            {
                JSONObject jsonObject = Common.getJsonObject(jsonArray, index);

                String numbers = jsonObject.getString("numbers");
                ArrayList<String> items = new ArrayList<String>(Arrays.asList(numbers.split("\\s*,\\s*")));

                String timeStamp = jsonObject.getString("timestamp");

                //"Active" is just a dummy value that i put here until i call populateStatesInMyNumbersArray
                myNumbersArrayList.add(new MyNumbers(items, timeStamp, "Active"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myNumbersArrayList;
    }

    private ArrayList<WinningNumbers> addWinningNumbersToArrayList(JSONArray jsonArray) {

        ArrayList<WinningNumbers> winningNumbersArrayList = new ArrayList<>();
        try {
            for(int index = 0; index < jsonArray.length(); index++)
            {
                JSONObject jsonObject = Common.getJsonObject(jsonArray, index);

                String numbers = jsonObject.getString("numbers");
                ArrayList<String> items = new ArrayList<String>(Arrays.asList(numbers.split("\\s*,\\s*")));

                String timeStamp = jsonObject.getString("timestamp");

                winningNumbersArrayList.add(new WinningNumbers(timeStamp, items));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return winningNumbersArrayList;
    }


    public class GetNumbersTimeStateTask extends AsyncTask<Void, Void, ArrayList<MyNumbers>> {

        private String userId = "";

        String GET_USER_NUMBERS_URL = "https://free-lottery.herokuapp.com/api/get_user_numbers.php?user_id=";
        String GET_WINNING_NUMBERS_URL = "https://free-lottery.herokuapp.com/api/get_winning_numbers.php";

        GetNumbersTimeStateTask(String userIdd) {
           userId = userIdd;
        }


        @Override
        protected ArrayList<MyNumbers> doInBackground(Void... params) {
            JSONArray jsonArrayOfUserNumbers = BaseApi.getHttpRequest(GET_USER_NUMBERS_URL + userId);
            myNumbers = addMyNumbersToArrayList(jsonArrayOfUserNumbers);

            JSONArray jsonArrayOfWinningNumbers = BaseApi.getHttpRequest(GET_WINNING_NUMBERS_URL);
            winningNumbers = addWinningNumbersToArrayList(jsonArrayOfWinningNumbers);

            try {
                myNumbers = populateStatesInMyNumbersArray(myNumbers, winningNumbers);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return myNumbers;
        }

        @Override
        protected void onPostExecute(ArrayList<MyNumbers> jsonArrayOfMyNumbers) {

            myNumbersAdaptor = new MyNumbersAdaptor(getActivity(), jsonArrayOfMyNumbers) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    //we are applying the colours here because we don't know the labels before hand
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(R.id.state);

                    if (text.getText() == "Active") {
                        text.setTextColor(Color.GREEN);
                    }

                    if (text.getText() == "Check") {
                        text.setTextColor(Color.YELLOW);
                    }

                    if (text.getText() == "Expired") {
                        text.setTextColor(Color.RED);
                    }

                    return view;

                }

            };

            //set all of my numbers into UI
            setListAdapter(myNumbersAdaptor);
        }

    }


}
