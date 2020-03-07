package com.example.triviaquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class QuestionActivity extends FragmentActivity {

    DataViewModel dataViewModel;
    private static final int NUM_PAGES = 3;
    private ViewPager2 viewPager;
    private FragmentStateAdapter questionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        String url = getIntent().getStringExtra("url");

        dataViewModel = new DataViewModel(this);
        dataViewModel.downloadQuestions(this, url);

        viewPager = findViewById(R.id.pager);
        this.subscribe();
    }



    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private void subscribe() {
        final Observer<List<Question>> questionsObserver = new Observer<List<Question>>() {

            @Override
            public void onChanged(final List<Question> questions) {
                questionsAdapter = new QuestionsAdapter(QuestionActivity.this, questions);
                viewPager.setAdapter(questionsAdapter);
            }
        };
        dataViewModel.getmQuestions().observe(this,questionsObserver);

        final Observer<String> errorMessageObserver = new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                TextView tvResult = findViewById(R.id.question);
                //tvResult.setBackgroundColor(Color.RED);
                tvResult.setText(errorMessage);
            }
        };
        dataViewModel.getmErrorMessage().observe(this, errorMessageObserver);
    }

}
