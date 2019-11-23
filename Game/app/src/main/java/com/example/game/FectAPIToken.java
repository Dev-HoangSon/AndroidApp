package com.example.game;

import android.os.AsyncTask;

import java.util.HashMap;

public class FectAPIToken extends AsyncTask <String, Void,String>{
    @Override
    protected String doInBackground(String... strings) {
        String uri = strings[0];
        String method = strings[1];
        String token = strings[2];
        HashMap<String,String> params = new HashMap<>();
        params.put("token",token);
        return NetworkUtils.doRequest(uri,method,params,null);
    }
}
