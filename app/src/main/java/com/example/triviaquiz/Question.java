package com.example.triviaquiz;

import android.widget.LinearLayout;

import java.util.ArrayList;

public class Question  {

    private ArrayList<Question> results;

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private String incorrect_answer;

    public Question(String category, String type, String difficulty, String question, String correct_answer, String incorrect_answer) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answer = incorrect_answer;
    }
}
