package com.example.game;

import android.os.AsyncTask;

import java.util.HashMap;

public class CapNhatLuotChoi extends AsyncTask <String,Void, String>{
    @Override
    protected String doInBackground(String... strings) {
        String uri = strings[0];
        String method = strings[1];
        String token = strings[2];
        String diem = strings[3];
        String so_cau = strings[4];
        HashMap<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("diem",diem);
        params.put("so_cau",so_cau);
        return NetworkUtils.doRequest(uri,method,params,null);
    }
}