package com.example.fml24.fml24;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.GameRulesAdaptor;
import com.example.fml24.fml24.Adaptor.PlayAdaptor;
import com.example.fml24.fml24.Model.GameRule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by adu on 16-07-12.
 */
public class FragmentPlay extends Fragment implements View.OnClickListener{

    private PlayTask mAuthTask = null;
    private GetJackPotTask getJackPotTask = null;

    public static final String USER_ID = "user_id";
    public static final String NUMBERS = "numbers";

    public static final Integer maxNumberSelection = 4;
    public static final Integer minNumber = 1;
    public static final Integer maxNumber = 49;

    ArrayList<Integer> listOfRandomNumbers;
    List<String> list;

    Button randomButton, playButton;
    EditText selectedNumbersEditText;
    TextView jackPotText;
    GridView grid;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list=new ArrayList<>();
        grid = (GridView)  getView().findViewById(R.id.gridView1);

        for(int index = minNumber; index <= maxNumber; index++)
        {
            list.add(String.valueOf(index));
        }

        PlayAdaptor adp=new PlayAdaptor (getContext(),
                list);
        grid.setAdapter(adp);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,int position, long id)
            {
                //Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                listOfRandomNumbers = SendSelectedNumbersToDisplay(position + 1);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myView =  inflater.inflate(R.layout.fragment_tabbed, container, false);
        jackPotText = (TextView)myView.findViewById(R.id.jackPotText);
        playButton = (Button) myView.findViewById(R.id.playButton);
        getJackPotTask = new GetJackPotTask();
        getJackPotTask.execute();

        randomButton = (Button) myView.findViewById(R.id.randomButton);
        randomButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        selectedNumbersEditText = (EditText) myView.findViewById(R.id.selectedNumbers);
        return myView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.randomButton:
                listOfRandomNumbers = SendRandomNumbersToDisplay();
                break;

            case R.id.playButton:

                Integer calculatedNumber;

                if(listOfRandomNumbers == null)
                {
                    calculatedNumber = maxNumberSelection;
                    Toast.makeText(getActivity(), "Please select " + calculatedNumber + " more number(s).", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(listOfRandomNumbers.size() < maxNumberSelection)
                {
                    calculatedNumber = maxNumberSelection - listOfRandomNumbers.size();
                    Toast.makeText(getActivity(), "Please select " + calculatedNumber + " more number(s).", Toast.LENGTH_SHORT).show();
                    return;
                }

                SendRandomNumbersToServer(listOfRandomNumbers);
                break;


            default:
                break;
        }
    }

    private boolean SendRandomNumbersToServer(ArrayList<Integer> listOfRandomNumbers) {

        //format the numbers in the form "#,#,#,#"
        String formattedNumbersForApi = "";
        for(int index = 0; index < listOfRandomNumbers.size(); index++)
        {
            formattedNumbersForApi = formattedNumbersForApi.concat(listOfRandomNumbers.get(index).toString());
            if(index != listOfRandomNumbers.size() - 1)
            {
                formattedNumbersForApi =  formattedNumbersForApi.concat(",");
            }
        }

        //Get user id from Login Activity
        String userId = "";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userId = sharedPreferences.getString(getString(R.string.user_id_after_login), "");

        //Send the numbers to the server as a background task
        mAuthTask = new PlayTask(userId, formattedNumbersForApi);
        mAuthTask.execute((Void) null);

        return true;
    }

    public class GetJackPotTask extends AsyncTask<Void, Void, JSONObject>
    {
        String GET_JACKPOT_URL = "https://free-lottery.herokuapp.com/api/get_jackpot_amount.php";

        GetJackPotTask() {
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return BaseApi.getHttpRequestReturnTokener(GET_JACKPOT_URL);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObjectJackpot)
        {
            //todo: add error handling when no internet / JSONArray is null
            try {
                String jackpotAmount = jsonObjectJackpot.getString("amount");
                jackPotText.setText("$" + jackpotAmount);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public class PlayTask extends AsyncTask<Void, Void, Boolean> {

        private final String userId;
        private final String numbers;

        String REGISTER_URL = "https://free-lottery.herokuapp.com/api/post_user_numbers.php";


        PlayTask(String userIdd, String numberss) {
            userId = userIdd;
            numbers = numberss;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                // TODO: attempt authentication against a network service.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getActivity(), "Good Luck!", Toast.LENGTH_LONG).show();
                                return;
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "Server busy. Please try to again later.", Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(USER_ID, userId);
                        params.put(NUMBERS, numbers);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
                return true;
            }catch (Exception e) {
                return false;
            }
        }
    }

    public class MyIntComparable implements Comparator<Integer> {

        @Override
        public int compare(Integer o2, Integer o1) {
            return (o1>o2 ? -1 : (o1==o2 ? 0 : 1));
        }
    }

    private ArrayList<Integer> SendSelectedNumbersToDisplay(int selectedNumber)
    {
        if(listOfRandomNumbers != null)
        {
            if (RemoveExistingNumberInDisplayField(selectedNumber)) return listOfRandomNumbers;

            //if array length is 4, then delete lowest number in array list
            if(listOfRandomNumbers.size() == maxNumberSelection)
            {
                Collections.sort(listOfRandomNumbers, new MyIntComparable());
                listOfRandomNumbers.remove(0);
            }
        }
        else
        {
            listOfRandomNumbers = new ArrayList<Integer>();
        }

        //add number to array list
        listOfRandomNumbers.add(selectedNumber);

        SendSortedNumbersToDisplay(listOfRandomNumbers);
        return listOfRandomNumbers;
    }

    private boolean RemoveExistingNumberInDisplayField(int selectedNumber) {
        //get current numbers displayed
        //if selectedNumber exists in array list
        // then delete the selected number from array list
        // sort and send list of numbers to UI
        for(int index = 0; index < listOfRandomNumbers.size(); index++)
        {
            if(listOfRandomNumbers.get(index) == selectedNumber)
            {
                listOfRandomNumbers.remove(index);

                //if there are no more numbers in the array, then display 'Pick 4 Numbers'
                if(listOfRandomNumbers.size() == 0)
                {
                    selectedNumbersEditText.setText(R.string.defaultTextNumberField);
                    return true;
                }

                SendSortedNumbersToDisplay(listOfRandomNumbers);
                return true;
            }
        }
        return false;
    }

    private void SendSortedNumbersToDisplay(ArrayList<Integer> list) {

        if(list.size() > 1)
        {
            //sort, then display to UI
            Collections.sort(list, new MyIntComparable());
        }

        StringBuilder sb = new StringBuilder();
        for (Integer number : list) {
            sb.append(number != null ? "  " + number.toString() + "  " : "");
        }

        selectedNumbersEditText.setText(sb.toString());
    }

    private ArrayList<Integer> SendRandomNumbersToDisplay()
    {
        int min = minNumber;
        int max = maxNumber;
        int maxNumberToRandomize = maxNumberSelection;
        boolean isRandomNumberRepeated = false;
        ArrayList<Integer> listOfRandomNumbers = new ArrayList<>();

        int randomNum = randInt(min, max);
        listOfRandomNumbers.add(randomNum);

        do
        {
            randomNum = randInt(min, max);

            for (int randomNumberInList: listOfRandomNumbers)
            {
                if(randomNum == randomNumberInList)
                {
                    isRandomNumberRepeated = true;
                    break;
                }
            }

            if(!isRandomNumberRepeated)
            {
                listOfRandomNumbers.add(randomNum);
            }
            isRandomNumberRepeated = false;

        }while(listOfRandomNumbers.size() < maxNumberToRandomize);

        SendSortedNumbersToDisplay(listOfRandomNumbers);
        return listOfRandomNumbers;
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


}
