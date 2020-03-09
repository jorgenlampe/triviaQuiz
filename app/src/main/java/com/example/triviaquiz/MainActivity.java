package com.example.triviaquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

 DataViewModel model = new DataViewModel(this);

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    sharedPreferences.registerOnSharedPreferenceChangeListener(this);


    if (sharedPreferences.getStringSet("answers", new HashSet<>()).size() ==
            Integer.parseInt(sharedPreferences.getString("amount", ""))) {

        Intent intent = getIntent();
        Set<String> answers = (Set<String>) intent.getSerializableExtra("answers");

        TextView tvResult = findViewById(R.id.tvResult);
        tvResult.setText("Number of correct answers: " + sharedPreferences.getInt("numberOfCorrectAnswers", 0)
                + "\n" + "Your answers: " + answers.toString() + "\n" + "Correct answers: "
        + sharedPreferences.getStringSet("correctAnswers", new HashSet<>()).toString());


    }


}

public void startNewQuiz(View v){



    Intent intent = new Intent(this, QuestionActivity.class);

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String url = getUrl(preferences);

    intent.putExtra("url", url);


    startActivity(intent);


}

public void continueQuiz(View v){

    //sjekk om fil eksisterer, få tak i status på quiz, innstillinger, etc.

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

    public void stopQuiz(View v){


    //slette fil

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

        }

        return false;

    }

}
