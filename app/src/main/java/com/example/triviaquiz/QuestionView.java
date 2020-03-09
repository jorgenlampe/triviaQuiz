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

    public QuestionView(Context context, List<Question> q) { //vet ikke om liste med spørsmål skal sendes med her....
        super(context);

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater)getContext().getSystemService(infService);
        li.inflate(R.layout.question_view, this, true);

        TextView qTv = findViewById(R.id.question);
        Button alt1Btn = findViewById(R.id.alt1);
        Button alt2Btn = findViewById(R.id.alt2);
        Button alt3Btn = findViewById(R.id.alt3);
        Button alt4Btn = findViewById(R.id.alt4);

    }

    public QuestionView(Context context, AttributeSet attr) {
        super(context);

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater)getContext().getSystemService(infService);
        li.inflate(R.layout.question_view, this, true);

        TextView qTv = findViewById(R.id.question);
        Button alt1Btn = findViewById(R.id.alt1);
        Button alt2Btn = findViewById(R.id.alt2);
        Button alt3Btn = findViewById(R.id.alt3);
        Button alt4Btn = findViewById(R.id.alt4);

    }









}
