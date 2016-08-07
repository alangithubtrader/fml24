package com.example.fml24.fml24;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.jirbo.adcolony.AdColony;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.jirbo.adcolony.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomBar mBottomBar;
    private FragNavController fragNavController;

    //indices to fragments
    private final int TAB_FIRST = FragNavController.TAB1;
    private final int TAB_SECOND = FragNavController.TAB2;
    private final int TAB_THIRD = FragNavController.TAB3;
    private final int TAB_FOURTH = FragNavController.TAB4;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_main);

        AdColony.configure(this, "version:1.0,store:google", "app340f07867cd4457887", "vz05b54d2d677d478683");

        session = new UserSessionManager(getApplicationContext());

        if(session.checkLogin())
        {
            Intent toIntroToLoginActivity = new Intent(getApplicationContext(), IntroToLoginActivity.class);
            startActivity(toIntroToLoginActivity);
        }
        else if(session.isUserLoggedIn())
        {
            Intent toTabbedActivity = new Intent(getApplicationContext(), TabbedActivity.class);
            startActivity(toTabbedActivity);
        }
        else
        {
            Intent toLoginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(toLoginActivity);
        }

    }

    @Override
    public void onBackPressed() {
        if (fragNavController.getCurrentStack().size() > 1) {
            fragNavController.pop();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
