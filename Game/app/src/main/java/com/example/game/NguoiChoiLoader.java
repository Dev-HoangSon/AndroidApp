package com.example.game;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.HashMap;

public class NguoiChoiLoader extends AsyncTaskLoader <String>{

    public NguoiChoiLoader(@NonNull Context context) {
        super(context);

    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        String token = BangXepHangNguoiChoi.token;
        HashMap<String, String> params = new HashMap<>();
        params.put("token",token);
        return NetworkUtils.doRequest("bang-xep-hang","GET",params,null);
    }
}
