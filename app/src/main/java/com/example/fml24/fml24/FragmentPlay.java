package com.example.fml24.fml24;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fml24.fml24.Adaptor.PlayAdaptor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by adu on 16-07-12.
 */
public class FragmentPlay extends Fragment implements View.OnClickListener{

    private PlayTask mAuthTask = null;

    public static final String USER_ID = "user_id";
    public static final String NUMBERS = "numbers";


    ArrayList<Integer> listOfRandomNumbers;
    List<String> list;

    Button randomButton, playButton;
    EditText selectedNumbersEditText;
    GridView grid;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list=new ArrayList<>();
        grid = (GridView)  getView().findViewById(R.id.gridView1);

        for(int index = 1; index <= 49; index++)
        {
            list.add(String.valueOf(index));
        }

        PlayAdaptor adp=new PlayAdaptor (getContext(),
                list);
        grid.setAdapter(adp);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View myView =  inflater.inflate(R.layout.fragment_tabbed, container, false);
        playButton = (Button) myView.findViewById(R.id.playButton);
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
                SendRandomNumbersToServer(listOfRandomNumbers);
                break;


            default:
                break;
        }
    }

    private boolean SendRandomNumbersToServer(ArrayList<Integer> listOfRandomNumbers) {

        String formattedNumbersForApi = "";
        for(int index = 0; index < listOfRandomNumbers.size(); index++)
        {
            formattedNumbersForApi = formattedNumbersForApi.concat(listOfRandomNumbers.get(index).toString());
            if(index != listOfRandomNumbers.size() - 1)
            {
                formattedNumbersForApi =  formattedNumbersForApi.concat(",");
            }
        }

        mAuthTask = new PlayTask("92", formattedNumbersForApi);
        mAuthTask.execute((Void) null);

        return true;
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

                                if (response.matches(".*\\d+.*")) //does response contains any numbers?
                                {
                                    //Toast.makeText(TabbedActivity.this, "Login success.", Toast.LENGTH_LONG).show();
                                    // TODO: some how we need to capture this user id for retrieving user info after successfully logged in.
                                    return;

                                }
                                //Toast.makeText(TabbedActivity.this, "Register success.", Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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

        private ArrayList<Integer> SendRandomNumbersToDisplay()
    {
        int min = 1;
        int max = 49;
        int maxNumberToRandomize = 4;
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

        StringBuilder sb = new StringBuilder();
        for (Integer number : listOfRandomNumbers) {
            sb.append(number != null ? "  " + number.toString() + "  " : "");
        }

        selectedNumbersEditText.setText(sb.toString());

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
