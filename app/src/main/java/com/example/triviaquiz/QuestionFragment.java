package com.example.triviaquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {
    public static final String ARG_QUESTION = "object";
    public static final String ARG_ALT1 = "objec2t";
    public static final String ARG_ALT2 = "object3";
    public static final String ARG_ALT3 = "object4";
    public static final String ARG_ALT_CORRECT = "object5";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        ((TextView) view.findViewById(R.id.question)).setText(args.getString(ARG_QUESTION));
        ((TextView) view.findViewById(R.id.alt1)).setText(args.getString(ARG_ALT1));
        ((TextView) view.findViewById(R.id.alt2)).setText(args.getString(ARG_ALT2));
        ((TextView) view.findViewById(R.id.alt3)).setText(args.getString(ARG_ALT3));
        ((TextView) view.findViewById(R.id.alt4)).setText(args.getString(ARG_ALT_CORRECT));

    }
}