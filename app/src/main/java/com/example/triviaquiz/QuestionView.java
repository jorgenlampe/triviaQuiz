package com.example.triviaquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class QuestionView extends LinearLayout {

    TextView tv;
    RadioButton btn1;
    RadioButton btn2;
    RadioButton btn3;
    RadioButton btn4;

    public QuestionView(Context context) {
        super(context);

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater)getContext().getSystemService(infService);
        li.inflate(R.layout.question_view, this, true);

        tv = findViewById(R.id.question);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);

    }







}
