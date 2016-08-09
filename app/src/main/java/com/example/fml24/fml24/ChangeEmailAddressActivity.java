package com.example.fml24.fml24;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
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
 * Created by adu on 16-08-01.
 */
public class ChangeEmailAddressActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";
    AutoCompleteTextView textField;
    Button button;
    UserSessionManager session;
    TextView currentEmailAddressTextView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email_address);

        session = new UserSessionManager(getApplicationContext());
        Map<String, String> map = session.getUserDetails();
        String email = map.get("email");
        currentEmailAddressTextView = (TextView)findViewById(R.id.current_email_address);
        currentEmailAddressTextView.setText(email);

        textField = (AutoCompleteTextView)findViewById(R.id.changeEmailAddress);
        textField.addTextChangedListener(textWatcher);

        button = (Button)findViewById(R.id.changeEmailAddressButton);
        button.setOnClickListener(this);

        checkFieldsForEmptyValues();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeEmailAddressButton:

                AutoCompleteTextView email = (AutoCompleteTextView)findViewById(R.id.changeEmailAddress);

                String parsedEmail = email.getText().toString();
                SubmitChangeEmailAddressTask myTask = new SubmitChangeEmailAddressTask(parsedEmail);
                myTask.execute(parsedEmail);
                break;
            default:
                break;
        }
    }

    public class SubmitChangeEmailAddressTask extends AsyncTask<String, Void, Boolean> {

        private final String email;
        String SEND_FEEDBACK_URL = "https://free-lottery.herokuapp.com/api/post_user.php";

        SubmitChangeEmailAddressTask(String email) {
            this.email = email;
        }

        @Override
        protected Boolean doInBackground(final String... params) {
            try {
                // Simulate network access.
                // TODO: attempt authentication against a network service.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_FEEDBACK_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.matches(".*\\d+.*")) //does the email user wants to change to exists
                                {
                                    Toast.makeText(ChangeEmailAddressActivity.this, "Email exists. Please try another.", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                String emailParam = params[0];

                                StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free-lottery.herokuapp.com/api/get_user.php?email=" + emailParam,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                int userId = Integer.parseInt(response.replaceAll("\\D", ""));

                                                //Store the user id in shared preferences
                                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString(getString(R.string.user_id_after_login), String.valueOf(userId));
                                                editor.commit();

                                                Toast.makeText(ChangeEmailAddressActivity.this, "Email Changed success.", Toast.LENGTH_LONG).show();
                                                // TODO: some how we need to capture this user id for retrieving user info after successfully logged in.
                                                return;
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(ChangeEmailAddressActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }){
                                    @Override
                                    protected Map<String,String> getParams(){
                                        Map<String,String> params = new HashMap<String, String>();
                                        params.put(EMAIL, email);
                                        return params;
                                    }};

                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(stringRequest);
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
                        params.put(EMAIL, email);
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
        Button b = (Button) findViewById(R.id.changeEmailAddressButton);
        AutoCompleteTextView et = (AutoCompleteTextView) findViewById(R.id.changeEmailAddress);

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
