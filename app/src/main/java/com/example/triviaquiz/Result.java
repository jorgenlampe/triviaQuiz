    package com.example.triviaquiz;

    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.lifecycle.MutableLiveData;
    import androidx.lifecycle.Observer;
    import androidx.preference.PreferenceManager;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.google.gson.Gson;

    import java.io.File;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Collections;
    import java.util.List;
    import java.util.Map;

    public class Result extends AppCompatActivity {//implements SharedPreferences.OnSharedPreferenceChangeListener {

        private DataViewModel dataViewModel = new DataViewModel(this);


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.result);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            showResult();
        }

        private void showResult() {

            TextView tv = findViewById(R.id.tvResult);
            Intent intent = getIntent();

            String result = intent.getStringExtra("result");


            tv.setText("YOUR SCORE: " + result + " POINTS");

        }


       /* private void subscribe() {
            final Observer<List<Question>> questionsObserver = new Observer<List<Question>>() {

                @Override
                public void onChanged(final List<Question> questions) {
                    Log.d("endring", "ja");
                    showResult(questions);
                }
            };
            dataViewModel.getmQuestions().observe(this, questionsObserver);

            final Observer<String> errorMessageObserver = new Observer<String>() {
                @Override
                public void onChanged(String errorMessage) {
Log.d("endring", "nei");
                }
            };
            dataViewModel.getmErrorMessage().observe(this, errorMessageObserver);
        }*/

            //       for (Question q : questions)
            //         correctAnswers.add(q.getCorrect_answer());


         /*   public String getPoints() {

                Intent intent = getIntent();

                List<String> correctAnswers = (List<String>) intent.getSerializableExtra("correctAnswers");
                String answers = intent.getStringExtra("answers");

                Object[] arr = correctAnswers.toArray();


                List<String> answersList = Arrays.asList(answers.split(","));

                Object[] arr2 = answersList.toArray();

                for (int i = 0; i < 10; i++)
                    if (arr2[i].equals(arr[i]))
                        Log.d("yyyy", "JADA");


                int count = 0;

               for (String s : answersList) {
                   s = s.replaceAll("^\"|\"$", "");
                        Log.d("oooo", s);
               }

               for (String str : correctAnswers)
                   Log.d("oooo", str);


                for (String y : answersList) {
                    if (correctAnswers.contains(y)) {
                        count++;
                        Log.d("oooo", "JADA");
                    }
                }
                return String.valueOf(count);

            }


        public void toMainActivity(){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
*/
         /*
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }*/



    }

