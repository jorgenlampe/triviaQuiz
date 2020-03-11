    package com.example.triviaquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

    public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

        private DataViewModel dataViewModel = new DataViewModel(this);
        private RecyclerView recyclerView;
        private QuestionAdapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;
        private String[] answers;
        private String[] correctList = new String[10];

        public int getAmount() {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            return Integer.parseInt(sharedPreferences.getString("amount", "10"));
        }

        private static final String SAVED_ANSWERS = "SAVED";
        private static final String KEY = "STRINGS";


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                sharedPreferences.registerOnSharedPreferenceChangeListener(this);

             /*   if(getIntent()!= null){
                    answers = new String[10];
                }
*/
                loadAnswers();

                dataViewModel.getmQuestions();
                recyclerView = findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true); //bedre ytelse med fast størrelse på layout

                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                subscribe();
                dataViewModel.downloadQuestions(this, getUrl(sharedPreferences));

Log.d("amount", String.valueOf(getAmount()));
    }

    //disse to burde kanskje flyttes til datarepository? Usikker, funker slik de er nå da. Må teste de
    private void saveAnswers() {
        SharedPreferences sharedPreferences = getSharedPreferences(SAVED_ANSWERS, MODE_PRIVATE);  //Denne bruker getSharedPreferences(... , ...). Tilgjengelig fra alle aktiviteter.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(answers);
        editor.putString(KEY, json);
        editor.apply();
    }

    private void loadAnswers() {
        SharedPreferences sharedPreferences = getSharedPreferences(SAVED_ANSWERS, MODE_PRIVATE);  //Denne bruker getSharedPreferences(... , ...). Tilgjengelig fra alle aktiviteter.
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY, null);
        answers = gson.fromJson(json, String[].class);
        System.out.println(json);
        if(json == null) {
            answers = new String[10];
        }
    }
    private void subscribe() {
         final Observer<List<Question>> questionsObserver = new Observer<List<Question>>() {
            @Override
            public void onChanged(final List<Question> questions) {
                 mAdapter = new QuestionAdapter(questions, answers);
                 recyclerView.setAdapter(mAdapter);
                 for (int i = 0; i<10;i++) {
                    correctList[i] = questions.get(i).getCorrect_answer();
                     System.out.println(questions.get(i).getCorrect_answer());
                 }
                 mAdapter.setOnCheckedChangeListener(new QuestionAdapter.OnCheckedChangeListener() {
                     @Override
                     public void onItemChanged(int position, String answer) {
                         answers[position] = answer;
                         saveAnswers();
                         int count = 0;
                        for (int i = 0; i < answers.length; i++)
                            if (answers[i]==null) {
                                count++;

                            }

                        if (count == 0) {


                            stopQuiz();
                            getResult();

                        }
                            }

                 });
            }
         };
         dataViewModel.getmQuestions().observe(this,questionsObserver);

         final Observer<String> errorMessageObserver = new Observer<String>() {
             @Override
             public void onChanged(String errorMessage) {
                    TextView tvResult = findViewById(R.id.question);
                    //tvResult.setBackgroundColor(Color.RED);
                    tvResult.setText(errorMessage);
                }
            };
            dataViewModel.getmErrorMessage().observe(this, errorMessageObserver);

        }

        private void getResult() {


            int noOfCorrectAnswers = 0;
            for (int i = 0; i < 10; i++) {
                Log.d("getres", correctList[i]);
                Log.d("getres2", answers[i]);
                if (answers[i].equals(correctList[i])) {

                    noOfCorrectAnswers++;
                }

            }

            AlertDialog.Builder alert = new AlertDialog.Builder((this));

            alert.setMessage("Your score: " + noOfCorrectAnswers);
            alert.setTitle("Result");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", null);
            alert.create().show();

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });


/*
            Intent intent = new Intent(this, Result.class);

            intent.putExtra("result", String.valueOf(noOfCorrectAnswers));
            startActivity(intent);
*/
        }


        public void startNewQuiz(){


        //slette noe fra sharedpreferences
        answers = new String[10];

        stopQuiz();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        dataViewModel.downloadQuestions(getApplicationContext(), getUrl(preferences));


    }


        public String getUrl(SharedPreferences preferences){

            StringBuilder url = new StringBuilder();

            url.append("https://opentdb.com/api.php?amount=");

            url.append(getNumberOfQuestions(preferences));
            url.append(getCategory(preferences));
            url.append(getDifficulty(preferences));
            url.append(getType(preferences));

            return url.toString();

        }

        public String getNumberOfQuestions(SharedPreferences preferences){

            String amount = preferences.getString("amount", "10");
            return amount;
        }

        public String getCategory(SharedPreferences preferences){

            String category = preferences.getString("category", "");

            return "&category=" + category;
        }

        public String getType(SharedPreferences preferences){

            Boolean anyType = preferences.getBoolean("anyType", false);
            Boolean multipleChoice = preferences.getBoolean("multipleChoice", false);
            Boolean trueOrFalse = preferences.getBoolean("trueOrFalse", false);

            if (anyType)
                return "";
            if (multipleChoice)
                return "&type=multiple";
            if (trueOrFalse)
                return "&type=boolean";

            return "";

        }

        //https://opentdb.com/api.php?amount=10&category=24&difficulty=easy&type=multiple

        public String getDifficulty(SharedPreferences preferences){

            Boolean diffAny = preferences.getBoolean("diffAny", false);
            Boolean diffEasy = preferences.getBoolean("diffEasy", false);
            Boolean diffMedium = preferences.getBoolean("diffMedium", false);
            Boolean diffHard = preferences.getBoolean("diffHard", false);

            if (diffAny)
                return "";
            if (diffEasy)
                return "&difficulty=easy";
            if (diffMedium)
                return "&difficulty=medium";
            if (diffHard)
                return "&difficulty=hard";

            return "";

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            // TODO Check the shared preference and key parameters
            SharedPreferences myprefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.mainmenu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_settings:
                    Intent intent = new Intent(this, MyPreferenceActivity.class);
                    startActivity(intent);
                    return true;



                case R.id.stopBtn:

                    stopQuiz();

                return true;

                case R.id.newQuizBtn:

                    startNewQuiz();

            }


            return false;

        }

        private void stopQuiz() {


            File file = getApplicationContext().getFileStreamPath("questions.json");

            if (file.exists()) {
                file.delete();
            }

        }


        @Override
        public void onDestroy(){
            saveAnswers();
            super.onDestroy();
        }

}
