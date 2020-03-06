package com.example.triviaquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_QUESTION = "object";
    public static final String ARG_INC_ALT = "object2";
    public static final String ARG_ALT_CORRECT = "object5";

    private TextView question;
    private TextView alt1;
    private TextView alt2;
    private TextView alt3;
    private TextView alt4;

    private String correctAnswer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_page, container, false);
        question = view.findViewById(R.id.question);
        alt1 = view.findViewById(R.id.alt1);
        alt2 = view.findViewById(R.id.alt2);
        alt3 = view.findViewById(R.id.alt3);
        alt4 = view.findViewById(R.id.alt4);

        question.setOnClickListener(this);
        alt1.setOnClickListener(this);
        alt2.setOnClickListener(this);
        alt3.setOnClickListener(this);
        alt4.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        question.setText(args.getString(ARG_QUESTION));
        correctAnswer = args.getString(ARG_ALT_CORRECT);

        ArrayList<String> answers = args.getStringArrayList(ARG_INC_ALT);
        answers.add(correctAnswer);
        Collections.shuffle(answers);
        if(answers.size() > 2) {
            alt1.setText(answers.remove(0));
            alt2.setText(answers.remove(0));
            alt3.setText(answers.remove(0));
            alt4.setText(answers.remove(0));
        } else {
            alt1.setText(answers.remove(0));
            alt2.setText(answers.remove(0));
        }
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.alt1:
                if(alt1.getText() == correctAnswer) {
                    alt1.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                } else {
                    alt1.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                }
                break;
            case R.id.alt2:
                if(alt2.getText() == correctAnswer) {
                    alt2.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                } else {
                    alt2.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                }

                break;
            case R.id.alt3:
                if(alt3.getText() == correctAnswer) {
                    alt3.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                } else {
                    alt3.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                }
                break;
            case R.id.alt4:
                if(alt4.getText() == correctAnswer) {
                    alt4.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                } else {
                    alt4.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                }
                break;
        }
    }
}