    package com.example.triviaquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceManager;

import android.content.Context;
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
import java.util.ArrayList;
import java.util.List;

    public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

        DataViewModel dataViewModel = new DataViewModel(this);
       //private static List<Question> questionsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subscribe();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);  //Denne bruker getSharedPreferences(... , ...). Tilgjengelig fra alle aktiviteter.
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        dataViewModel.downloadQuestions(this, getUrl(sharedPreferences));

    }


        private void subscribe() {
            final Observer<List<Question>> questionsObserver = new Observer<List<Question>>() {

                @Override
                public void onChanged(final List<Question> questions) {


                    //usikker på dette

                    List<Question> questionsList = new ArrayList<>();

                    for (Question q : questions) {
                        questionsList.add(q);

                        LinearLayout linLayout = findViewById(R.id.mainLayout);
                        linLayout.addView(new QuestionView(getApplicationContext(), questionsList));

                    }

                //fra tidligere:
               //     questionsAdapter = new QuestionsAdapter(QuestionActivity.this, questions);
                 //   viewPager.setAdapter(questionsAdapter);
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

    public void startNewQuiz(View v){

//resette noen av preferencene...

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);  //Denne bruker getSharedPreferences(... , ...). Tilgjengelig fra alle aktiviteter.
        //sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        dataViewModel.downloadQuestions(this, getUrl(sharedPreferences));

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

            String amount = myprefs.getString("amount", "10");

            Toast.makeText(this, key + " endret seg...", Toast.LENGTH_SHORT).show();
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

                    startNewQuiz(null);

            }


            return false;

        }

        private void stopQuiz() {

        File file = getApplicationContext().getFileStreamPath("questions.json");
            file.delete();


        }

        @Override
        public void onDestroy(){


            super.onDestroy();
        }

}
