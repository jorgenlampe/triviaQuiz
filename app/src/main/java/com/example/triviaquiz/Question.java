package com.example.triviaquiz;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question    {

    private ArrayList<Question> results;

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private String [] incorrect_answers;
    private boolean answered = false;

    public Question(String category, String type, String difficulty, String question, String correct_answer, String [] incorrect_answers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }

    public String getQuestion() {
        return question;
    }
    public String getCorrect_answer() {
        return correct_answer;
    }

    public ArrayList<String> getIncorrect_answers() {
        return new ArrayList<>(Arrays.asList(incorrect_answers));
    }
    public boolean getAnswered() {
        return answered;
    }
    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
