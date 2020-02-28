package com.example.triviaquiz;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private RequestQueue queue = null;
    private MutableLiveData<List<Question>> questions = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public void downloadQuestions(Context context,String url) {

        String mUrlString = "https://opentdb.com/api.php?amount=10&category=12&difficulty=easy&type=multiple";
        queue = MySingletonQueue.getInstance(context).getRequestQueue();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mUrlString,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try{Gson gson = new Gson();


                            ArrayList<Question> temp = new ArrayList<>();
                           JSONArray array = response.getJSONArray("results");

                           int length = array.length();


                           for (int i = 0; i < length; i++){

                               JSONObject obj = array.getJSONObject(i);
                                Question q = gson.fromJson(obj.toString(), Question.class);
                               temp.add(q);

                           }

                            questions.postValue(temp);

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
        queue.add(jsonObjectRequest);
    }

    public MutableLiveData<List<Question>> getQuestions() { return questions; }
    public MutableLiveData<String> getErrorMessage() { return errorMessage; }


}
