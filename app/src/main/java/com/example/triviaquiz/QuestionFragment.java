package com.example.triviaquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class QuestionFragment extends Fragment implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String ARG_QUESTION = "object";
    public static final String ARG_INC_ALT = "object2";
    public static final String ARG_ALT_CORRECT = "object5";

    private TextView question;
    private TextView alt1;
    private TextView alt2;
    private TextView alt3;
    private TextView alt4;

    private static String correctAnswer;
   // private DataViewModel model;

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

      //  model = (DataViewModel) new ViewModelProvider(requireActivity()).get(ViewModel.class);


     //   correctAnswers.add(getCorrectAnswers().toString());
       // correctAnswers.add(correctAnswer);



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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());  //Denne bruker getSharedPreferences(... , ...). Tilgjengelig fra alle aktiviteter.
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        String amount = sharedPreferences.getString("amount", "");
        int numberOfQuestions = Integer.parseInt(amount);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch(view.getId()) {
            case R.id.alt1:
                if(alt1.getText() == correctAnswer) {
                    int correct = sharedPreferences.getInt("numberOfCorrectAnswers", 0);
                    editor.putInt("numberOfCorrectAnswers", correct + 1);
                    Set<String> answers = sharedPreferences.getStringSet("answers", new HashSet<>());
                    answers.add(alt1.getText().toString());
                    editor.putStringSet("answers", answers);
                    editor.apply();


                    alt1.setBackgroundColor(getResources().getColor(R.color.correctAnswer));

                    if (numberOfQuestions == answers.size()){
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("answers", (Serializable) answers);
                        startActivity(intent);
                    }


                } else {
                    Set<String> answers = sharedPreferences.getStringSet("answers", new HashSet<>());
                    answers.add(alt1.getText().toString());
                    editor.putStringSet("answers", answers);
                    editor.apply();

                    alt1.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                    if (numberOfQuestions == answers.size()){
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("answers", (Serializable) answers);
                        startActivity(intent);
                    }




                }
                disableClicks();
                break;
            case R.id.alt2:
                if(alt2.getText() == correctAnswer) {
                    int correct = sharedPreferences.getInt("numberOfCorrectAnswers", 0);
                    editor.putInt("numberOfCorrectAnswers", correct + 1);
                    alt2.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                    Set<String> answers = sharedPreferences.getStringSet("answers", new HashSet<>());
                    answers.add(alt1.getText().toString());
                    editor.putStringSet("answers", answers);
                    editor.apply();

                    if (numberOfQuestions == answers.size()){
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("answers", (Serializable) answers);
                        startActivity(intent);
                    }
                } else {
                    alt2.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                    Set<String> answers = sharedPreferences.getStringSet("answers", new HashSet<>());
                    answers.add(alt1.getText().toString());
                    editor.putStringSet("answers", answers);
                    editor.apply();

                    if (numberOfQuestions == answers.size()){
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("answers", (Serializable) answers);
                        startActivity(intent);
                    }
                }
                disableClicks();
                break;
            case R.id.alt3:
                if(alt3.getText() == correctAnswer) {
                    int correct = sharedPreferences.getInt("numberOfCorrectAnswers", 0);
                    editor.putInt("numberOfCorrectAnswers", correct + 1);
                    Set<String> answers = sharedPreferences.getStringSet("answers", new HashSet<>());
                    answers.add(alt1.getText().toString());
                    editor.putStringSet("answers", answers);
                    editor.apply();

                    alt3.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                    if (numberOfQuestions == answers.size()){
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("answers", (Serializable) answers);
                        startActivity(intent);
                    }
                } else {
                    alt3.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                    Set<String> answers = sharedPreferences.getStringSet("answers", new HashSet<>());
                    answers.add(alt1.getText().toString());
                    editor.putStringSet("answers", answers);
                    editor.apply();

                    if (numberOfQuestions == answers.size()){
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("answers", (Serializable) answers);
                        startActivity(intent);
                    }
                }
                disableClicks();
                break;
            case R.id.alt4:
                if(alt4.getText() == correctAnswer) {
                    int correct = sharedPreferences.getInt("numberOfCorrectAnswers", 0);
                    editor.putInt("numberOfCorrectAnswers", correct + 1);
                    Set<String> answers = sharedPreferences.getStringSet("answers", new HashSet<>());
                    answers.add(alt1.getText().toString());
                    editor.putStringSet("answers", answers);
                    editor.apply();

                    alt4.setBackgroundColor(getResources().getColor(R.color.correctAnswer));
                    if (numberOfQuestions == answers.size()){
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("answers", (Serializable) answers);
                        startActivity(intent);
                    }
                } else {
                    alt4.setBackgroundColor(getResources().getColor(R.color.incorrectAnswer));
                    Set<String> answers = sharedPreferences.getStringSet("answers", new HashSet<>());
                    answers.add(alt1.getText().toString());
                    editor.putStringSet("answers", answers);
                    editor.apply();

                    if (numberOfQuestions == answers.size()){
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("answers", (Serializable) answers);
                        startActivity(intent);
                    }

                }
                disableClicks();
                break;
        }
    }

    public void disableClicks() {
        alt1.setClickable(false);
        alt2.setClickable(false);
        alt3.setClickable(false);
        alt4.setClickable(false);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}