package com.example.game;

import android.os.AsyncTask;

import java.util.HashMap;

public class LayGoiCredit extends AsyncTask <String,Void, String>{
    @Override
    protected String doInBackground(String... strings) {
        String uri = strings[0];
        String method = strings[1];
        String token = strings[2];
        String id = strings[3];
        String credit = strings[4];
        String sotien = strings[5];
        HashMap<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("goi_credit_id",id);
        params.put("credit",credit);
        params.put("so_tien",sotien);
        return NetworkUtils.doRequest(uri,method,params,null);
    }
}
