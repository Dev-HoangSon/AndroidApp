package com.example.game;

import android.os.AsyncTask;

import java.util.HashMap;

public class FetchDangNhap extends AsyncTask <String,Void, String>{
    @Override
    protected String doInBackground(String... strings) {
        String uri = strings[0];
        String method = strings[1];
        String tenDN = strings[2];
        String MatKhau = strings[3];
        HashMap<String,String> params = new HashMap<>();
        params.put("ten_dang_nhap",tenDN);
        params.put("mat_khau",MatKhau);
        return NetworkUtils.doRequest(uri,method,params,null);
    }
}
