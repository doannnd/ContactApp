package com.nguyendinhdoan.contactapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        init();
    }

    /**
     * Initialize the first fragment (ViewContactFragment)
     */
    private void init() {
        Log.d(TAG, "init: started.");

        ViewContactFragment viewContactFragment = new ViewContactFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // replace whatever is in the fragment_container view with this fragment
        transaction.replace(R.id.fragment_container, viewContactFragment);
        // add a back stack so the user navigate back
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
