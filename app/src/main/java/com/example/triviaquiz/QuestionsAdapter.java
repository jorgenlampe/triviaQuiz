package com.example.triviaquiz;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import static com.example.triviaquiz.QuestionFragment.ARG_INC_ALT;


public class QuestionsAdapter extends FragmentStateAdapter {
    List<Question> questions;
    public QuestionsAdapter(FragmentActivity fragment, List<Question> questions) {
        super(fragment);
        this.questions = questions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Question question = questions.get(position);
        Fragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(QuestionFragment.ARG_QUESTION, position + 1);
        args.putString(QuestionFragment.ARG_QUESTION, question.getQuestion());
        args.putStringArrayList(ARG_INC_ALT, question.getIncorrect_answers());
        args.putString(QuestionFragment.ARG_ALT_CORRECT, question.getCorrect_answer());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}