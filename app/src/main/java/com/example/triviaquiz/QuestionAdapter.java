package com.example.triviaquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    private List<Question> questions;
    private String[] answersChosen;
    private OnCheckedChangeListener mListener;

    public interface OnCheckedChangeListener {
        void onItemChanged(int position, String answer);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mListener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public QuestionView questionView;


        public MyViewHolder(QuestionView v, final OnCheckedChangeListener listener, String[] answersChosen) {
            super(v);
            questionView = v;
            String answerChosen = null;

            System.out.println(getLayoutPosition());

            if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                answerChosen = answersChosen[getAdapterPosition()];
            }
            RadioGroup rGroup = v.findViewById(R.id.radioGroup);
            int count = rGroup.getChildCount();
            if(answerChosen != null) {
                for(int i = 0; i<count; i++) {
                    RadioButton rb = (RadioButton) rGroup.getChildAt(i);
                    if(rb.getText().equals(answerChosen)) {
                        rb.setChecked(true);
                    }
                }
            }
            rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            RadioButton selected = v.findViewById(checkedId);
                            listener.onItemChanged(position, selected.getText().toString());
                        }
                    }
                }
            });
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public QuestionAdapter(List<Question> myDataset, String[] answersChosen) {
        questions = myDataset;
        this.answersChosen = answersChosen;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QuestionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        QuestionView questionView = new QuestionView(parent.getContext());
        MyViewHolder vh = new MyViewHolder(questionView, mListener, answersChosen);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.questionView.setQuestion(questions.get(position));
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return questions.size();
    }
}


