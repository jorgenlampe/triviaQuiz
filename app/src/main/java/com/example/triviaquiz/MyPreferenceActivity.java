package com.example.triviaquiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MyPreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.preference_activity);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preference_container, new MyPreferenceFragment())
                .commit();
    }


}
