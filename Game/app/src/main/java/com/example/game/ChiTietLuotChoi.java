package com.example.game;

import android.os.AsyncTask;

import java.util.HashMap;

public class ChiTietLuotChoi extends AsyncTask <String,Void, String>{
    @Override
    protected String doInBackground(String... strings) {
        String uri = strings[0];
        String method = strings[1];
        String token = strings[2];
        String cau_hoi_id = strings[3];
        String phuong_an = strings[4];
        String diem = strings[5];
        HashMap<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("cau_hoi_id",cau_hoi_id);
        params.put("phuong_an",phuong_an);
        params.put("diem",diem);
        return NetworkUtils.doRequest(uri,method,params,null);
    }
}