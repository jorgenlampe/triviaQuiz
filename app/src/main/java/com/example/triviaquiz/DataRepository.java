package com.example.triviaquiz;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataRepository {


    public void downloadQuestions(String url, Context context){


        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Question>>(){}.getType();
     //   ArrayList<Question> tmpList = gson.fromJson(jsonArrayAsString, type);

    }


}
