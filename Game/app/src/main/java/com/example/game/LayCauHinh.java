package com.example.game;

import android.os.AsyncTask;

import java.util.HashMap;

public class LayCauHinh extends AsyncTask <String,Void, String>{
    @Override
    protected String doInBackground(String... strings) {
        String uri = strings[0];
        String method = strings[1];
        return NetworkUtils.doRequest(uri,method,null,null);
    }
}