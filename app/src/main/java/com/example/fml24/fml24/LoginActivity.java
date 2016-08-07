package com.example.fml24.fml24;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
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
import com.example.fml24.fml24.API.BaseApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email.
 */// implements LoaderCallbacks<Cursor>
public class LoginActivity extends AppCompatActivity {

    public static final String KEY_EMAIL = "email";
    public static final String USER_ID_AFTER_LOGIN = "user_id_after_login";

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private View mProgressView;
    private View mLoginFormView;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new UserSessionManager(getApplicationContext());

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        assert mEmailSignInButton != null;
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressView = findViewById(R.id.login_progress);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email);
            mAuthTask.execute(email);

            session.createUserLoginSession(email);

            Intent successLogin = new Intent(getApplicationContext(), TabbedActivity.class);
            startActivity(successLogin);
        }


    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<String, Void, Boolean> {

        private final String mEmail;

        public static final String REGISTER_URL = "https://free-lottery.herokuapp.com/api/post_user.php";


        UserLoginTask(String email) {
            mEmail = email;
        }

        @Override
        protected Boolean doInBackground(final String... params) {
            try {
                // Simulate network access.
                // TODO: attempt authentication against a network service.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.matches(".*\\d+.*")) //does response contains any numbers?
                                {
                                    int userId = Integer.parseInt(response.replaceAll("\\D", ""));

                                    //Store the user id in shared preferences
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(getString(R.string.user_id_after_login), String.valueOf(userId));
                                    editor.commit();


                                    Toast.makeText(LoginActivity.this, "Login success.", Toast.LENGTH_LONG).show();
                                    // TODO: some how we need to capture this user id for retrieving user info after successfully logged in.
                                    return;

                                }
                                String email = params[0];

                                StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://free-lottery.herokuapp.com/api/get_user.php?email=" + email,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                int userId = Integer.parseInt(response.replaceAll("\\D", ""));

                                                //Store the user id in shared preferences
                                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString(getString(R.string.user_id_after_login), String.valueOf(userId));
                                                editor.commit();


                                                Toast.makeText(LoginActivity.this, "Register success.", Toast.LENGTH_LONG).show();
                                                // TODO: some how we need to capture this user id for retrieving user info after successfully logged in.
                                                return;
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(LoginActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                                            }
                                        }){
                                    @Override
                                    protected Map<String,String> getParams(){
                                        Map<String,String> params = new HashMap<String, String>();
                                        params.put(KEY_EMAIL, mEmail);
                                        return params;
                                    }};

                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(stringRequest);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this,error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(KEY_EMAIL, mEmail);
                    return params;
                }};

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

            } catch (Exception e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }



        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

