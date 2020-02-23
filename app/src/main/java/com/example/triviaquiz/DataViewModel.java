package com.example.triviaquiz;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class DataViewModel {

    private DataRepository mRepository;
    private MutableLiveData<List<Question>> mQuestions;
    private MutableLiveData<String> mErrorMessage;

    public DataViewModel() {
        mRepository = new DataRepository();
        mQuestions = mRepository.getQuestions();
        mErrorMessage = mRepository.getErrorMessage();
    }

    public MutableLiveData<List<Question>> getmQuestions() {
        return mQuestions;
    }

    public MutableLiveData<String> getmErrorMessage() {
        return mErrorMessage;
    }

    public void downloadQuestions(Context context, String url) {
        mRepository.downloadQuestions(context, url);
    }
}
