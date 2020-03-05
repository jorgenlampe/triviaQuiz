package com.example.triviaquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class QuestionActivity extends FragmentActivity {


    private static final int NUM_PAGES = 3;
    private ViewPager2 viewPager;
    private FragmentStateAdapter questionsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        viewPager = findViewById(R.id.pager);
        questionsAdapter = new QuestionsAdapter(this);
        viewPager.setAdapter(questionsAdapter);

    }
}
