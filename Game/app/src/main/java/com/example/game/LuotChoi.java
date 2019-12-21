package com.example.game;

import android.os.AsyncTask;

import java.util.HashMap;

public class LuotChoi extends AsyncTask <String,Void, String>{
    @Override
    protected String doInBackground(String... strings) {
        String uri = strings[0];
        String method = strings[1];
        String token = strings[2];
        String ngay_gio = strings[3];
        HashMap<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("ngay_gio",ngay_gio);
        return NetworkUtils.doRequest(uri,method,params,null);
    }
}