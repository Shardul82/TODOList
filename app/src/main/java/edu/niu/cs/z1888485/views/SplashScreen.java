package edu.niu.cs.z1888485.views;
/***************************************************************
 *                                                             *
 * CSCI 522      Assignment 8                      Fall 2020   *
 *                                                             *
 * Class Name:  SplashScreen                                   *
 *                                                             *
 * Programmer: Shardul Deepak Arjunwadkar Z1888485             *
 *                                                             *
 * Due Date:   12/04/2020 11:59PM                              *
 *                                                             *
 * Purpose: SplashScreen is a main class, it has onCreate      *
 *         method which calls when we start the app and it     *
 *         only display the logo of the app and after 1 second *
 *         delay it will go to next activity.                  *
 *                                                             *
 ***************************************************************/
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import edu.niu.cs.z1888485.R;

public class SplashScreen extends AppCompatActivity {
    /*****************************************************************
     *                                                               *
     * Method Name:  onCreate                                        *
     *                                                               *
     *                                                               *
     * Purpose: The onCreate method is used to initialize activity   *
     *          here and handler is created to move to next activity *
     *          MainScreenActivity.                                  *
     *****************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // hiding support action-bar
        try{
            getSupportActionBar().hide();
        }catch (Exception e){
            Log.i("exception", e.getMessage());
        }

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainScreenActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}