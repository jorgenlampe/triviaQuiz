package com.example.triviaquiz;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private RequestQueue queue = null;
    private MutableLiveData<List<Question>> questions = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public void downloadQuestions(Context context,String url) {
        //Type type = new TypeToken<ArrayList<Question>>(){}.getType();
     //   ArrayList<Question> tmpList = gson.fromJson(jsonArrayAsString, type);
        String mUrlString = "https://opentdb.com/api.php?amount=10&category=12&difficulty=easy&type=multiple";
        queue = MySingletonQueue.getInstance(context).getRequestQueue();
        // Laster ned en LISTE med JSON-objekter:
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mUrlString,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{Gson gson = new Gson();
                            // Loop gjennom returnert JSON-array:
                            ArrayList<Question> tmpList = new ArrayList<>();
                            for(int i=0; i < response.length(); i++)    {
                                JSONObject questionsAsJson = response.getJSONObject(i);
                                Question question = gson.fromJson(questionsAsJson.toString(), Question.class);
                                tmpList.add(question);

                            }
                            questions.postValue(tmpList);
                        }  catch (JSONException e)  {
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
        queue.add(jsonArrayRequest);
    }

    public MutableLiveData<List<Question>> getQuestions() { return questions; }
    public MutableLiveData<String> getErrorMessage() { return errorMessage; }


}
