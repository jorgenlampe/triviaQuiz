package com.example.triviaquiz;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataRepository implements SharedPreferences.OnSharedPreferenceChangeListener{
    private RequestQueue queue = null;
    private MutableLiveData<List<Question>> questions = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private String filename = "questions.json";
    private Set<String> answers = new HashSet<>();
    private Set<String> correct_Answers = new HashSet<>();



    public void addAnswer(String answer) {
        answers.add(answer);
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public void downloadQuestions(Context context, String url) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);  //Denne bruker getSharedPreferences(... , ...). Tilgjengelig fra alle aktiviteter.
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        System.out.println(url);
       // String mUrlString = "https://opentdb.com/api.php?amount=10&category=12&difficulty=easy&type=multiple";
        queue = MySingletonQueue.getInstance(context).getRequestQueue();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{Gson gson = new Gson();
                           JSONArray array = response.getJSONArray("results");
                            int length = array.length();
                            ArrayList<Question> temp = new ArrayList<>();
                            for (int i = 0; i < length; i++){
                                JSONObject obj = array.getJSONObject(i);
                                Question q = gson.fromJson(obj.toString(), Question.class);
                                correct_Answers.add(q.getCorrect_answer());
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putStringSet("correctAnswers", correct_Answers);
                                editor.apply();

                                temp.add(q);
                            }

                            questions.postValue(temp);
                            String qs = gson.toJson(temp);
                            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                            System.out.println(qs);
                            outputStream.write(qs.getBytes());
                        }  catch (Exception e)  {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        errorMessage.postValue(error.getMessage());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public Set<String> getCorrect_Answers() {
        return correct_Answers;
    }

    public MutableLiveData<List<Question>> getQuestions(Context context) {
        loadData(context);

        return questions; }
    public MutableLiveData<String> getErrorMessage() { return errorMessage; }

    public void loadData(Context context) {
       FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();
            while(line != null) {
                sb.append(line);
                line= bufferedReader.readLine();
            }
            inputStreamReader.close();
            String json = sb.toString();
            Type type = new TypeToken<ArrayList<Question>>(){}.getType();
            Gson gson = new Gson();
            List<Question> temp = gson.fromJson(json, type);
            System.out.println(temp.get(0).getQuestion());

            questions.postValue(temp);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
