package com.example.triviaquiz;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

public class QuestionView extends LinearLayout {
    TextView qTv;
    RadioButton r1;

    public QuestionView(Context context) { //vet ikke om liste med spørsmål skal sendes med her....
        super(context);

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.question_view, this, true);

        qTv = findViewById(R.id.question);
        r1 = findViewById(R.id.alt1Button);


    }

    public QuestionView(Context context, Question q) { //vet ikke om liste med spørsmål skal sendes med her....
        super(context);

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.question_view, this, true);

        qTv = findViewById(R.id.question);


    }

    public QuestionView(Context context, AttributeSet attr) {
        super(context);

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.question_view, this, false);

        qTv = findViewById(R.id.question);


    }
    public void setQuestion(Question q) {
        qTv.setText(q.getQuestion());
    }
    public void setText(String txt) {
        r1.setText(txt);
    }









}
