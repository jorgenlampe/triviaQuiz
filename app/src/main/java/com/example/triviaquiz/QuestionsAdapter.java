package com.example.triviaquiz;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class QuestionsAdapter extends FragmentStateAdapter {
    public QuestionsAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(QuestionFragment.ARG_QUESTION, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}