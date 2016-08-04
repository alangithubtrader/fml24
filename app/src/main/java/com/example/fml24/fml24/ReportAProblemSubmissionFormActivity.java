package com.example.fml24.fml24;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adu on 16-08-03.
 */
public class ReportAProblemSubmissionFormActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String USER_ID = "user_id";
    public static final String TITTLE = "numbers";
    public static final String DESCRIPTION = "description";
    Button sendButton;
    Button cancelButton;
    private SubmitProblemTask task = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_a_problem_submission_form);

        sendButton = (Button)findViewById(R.id.rapSend);
        sendButton.setOnClickListener(this);

        cancelButton = (Button)findViewById(R.id.rapCancel);
        cancelButton.setOnClickListener(this);

        EditText et = (EditText)findViewById(R.id.rapEditText);
        et.addTextChangedListener(textWatcher);

        checkFieldsForEmptyValues();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gfSend:

                //Get user id from Login Activity
                String userId = "";
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                userId = sharedPreferences.getString(getString(R.string.user_id_after_login), "");

                TextView title = (TextView)findViewById(R.id.rapTitle);
                EditText feedbackBody = (EditText)findViewById(R.id.rapEditText);

                SubmitProblemTask myTask = new SubmitProblemTask(userId, title.getText().toString(), feedbackBody.getText().toString());
                myTask.execute();
                break;
            case R.id.rapCancel:
                this.finish();
                break;
            default:
                break;
        }
    }

    public class SubmitProblemTask extends AsyncTask<Void, Void, Boolean> {

        private final String userId;
        private final String title;
        private  final String description;
        String SEND_FEEDBACK_URL = "https://free-lottery.herokuapp.com/api/post_problems.php";

        SubmitProblemTask(String userIdd, String titless, String descriptionss) {
            userId = userIdd;
            title = titless;
            description = descriptionss;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                // TODO: attempt authentication against a network service.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_FEEDBACK_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), "Problem Sent.", Toast.LENGTH_LONG).show();
                                return;
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Server busy. Please try to again later.", Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(USER_ID, userId);
                        params.put(TITTLE, title);
                        params.put(DESCRIPTION, description);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    //TextWatcher
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private  void checkFieldsForEmptyValues(){
        Button b = (Button) findViewById(R.id.rapSend);
        EditText et = (EditText)findViewById(R.id.rapEditText);

        String s1 = et.getText().toString();

        if(s1.equals(""))
        {
            b.setEnabled(false);
        }
        else
        {
            b.setEnabled(true);
        }
    }
}
