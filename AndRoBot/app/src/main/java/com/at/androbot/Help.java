package com.at.androbot;

import android.app.Activity;
import android.os.Bundle;


public class Help extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup the window
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_help);
    }



}
