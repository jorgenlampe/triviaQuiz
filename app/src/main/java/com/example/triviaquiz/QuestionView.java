package com.example.triviaquiz;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionView extends LinearLayout {
    TextView qTv;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    RadioButton r4;

    public QuestionView(Context context) { //vet ikke om liste med spørsmål skal sendes med her....
        super(context);

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.question_view, this, true);

        qTv = findViewById(R.id.question);
        r1 = findViewById(R.id.alt1Button);
        r2 = findViewById(R.id.alt2Button);
        r3 = findViewById(R.id.alt3Button);
        r4 = findViewById(R.id.alt4Button);


    }

    public QuestionView(Context context, AttributeSet attr) {
        super(context);

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.question_view, this, false);

        qTv = findViewById(R.id.question);


    }
    public void setQuestion(Question q, String answerChosen) {
        List<String> answers = q.getIncorrect_answers();
        answers.add(q.getCorrect_answer());
        qTv.setText(q.getQuestion());
        if(answers.size() > 2) {
            r1.setText(answers.get(0));
            r2.setText(answers.get(1));
            r3.setText(answers.get(2));
            r4.setText(answers.get(3));
        } else {
            r1.setText(answers.get(0));
            r2.setText(answers.get(1));
            r3.setButtonDrawable(new StateListDrawable());
            r4.setButtonDrawable(new StateListDrawable());

        }

        //Setter check dersom chosen answer er lik et alternativ
        RadioGroup rGroup = findViewById(R.id.radioGroup);
        int count = rGroup.getChildCount();
        if(answerChosen != null) {
            for(int i = 0; i<count; i++) {
                RadioButton rb = (RadioButton) rGroup.getChildAt(i);
                if(rb.getText().equals(answerChosen)) {
                    rb.setChecked(true);
                }
            }
        }
    }
    public void setText(String txt) {
        r1.setText(txt);
    }









}
