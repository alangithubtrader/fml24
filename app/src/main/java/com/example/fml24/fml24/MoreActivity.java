package com.example.fml24.fml24;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by adu on 16-07-12.
 */
public class MoreActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView changeEmailAddressTextView;
    private TextView reportAProblemTextView;
    private TextView faqTextView;
    private TextView gameRulesTextView;
    private TextView followUsOnFacebookTextView;
    private TextView termsAndConditionTextView;

    public MoreActivity()
    {
        Log.i("Fragment Check", "Fragment More Created.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        changeEmailAddressTextView = (TextView) findViewById(R.id.change_email_address);
        reportAProblemTextView = (TextView) findViewById(R.id.report_a_problem);
        termsAndConditionTextView = (TextView) findViewById(R.id.terms_and_conditions);
        faqTextView = (TextView) findViewById(R.id.faq);
        gameRulesTextView = (TextView) findViewById(R.id.game_rules);
        followUsOnFacebookTextView = (TextView)findViewById(R.id.follow_us_on_facebook);

        changeEmailAddressTextView.setOnClickListener(this);
        reportAProblemTextView.setOnClickListener(this);
        faqTextView.setOnClickListener(this);
        termsAndConditionTextView.setOnClickListener(this);
        gameRulesTextView.setOnClickListener(this);
        followUsOnFacebookTextView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.change_email_address:
                Toast.makeText(getApplicationContext(), "Change email address.", Toast.LENGTH_SHORT).show();
                Intent changeEmailAddressIntent = new Intent(getApplicationContext(), ChangeEmailAddress.class);
                startActivity(changeEmailAddressIntent);
                break;

            case R.id.report_a_problem:
                Toast.makeText(getApplicationContext(), "Report a problem.", Toast.LENGTH_SHORT).show();

                String names[] ={"Something isn't working","Gernal Feedback"};
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.custom_list_of_problems, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("List");
                ListView lv = (ListView) convertView.findViewById(R.id.listOfProblems);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
                lv.setAdapter(adapter);
                alertDialog.show();

                break;

            case R.id.terms_and_conditions:
                Toast.makeText(getApplicationContext(), "Terms and condition.", Toast.LENGTH_SHORT).show();
                Intent termsAndConditionsIntent = new Intent(getApplicationContext(), TermsAndConditions.class);
                startActivity(termsAndConditionsIntent);
                break;

            case R.id.faq:
                Toast.makeText(getApplicationContext(), "FAQ.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.game_rules:
                Toast.makeText(getApplicationContext(), "Game Rules.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.follow_us_on_facebook:
                Toast.makeText(getApplicationContext(), "Follow us on facebook.", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
