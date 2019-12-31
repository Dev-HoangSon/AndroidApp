package com.example.game;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.HashMap;

public class LSChoiLoader extends AsyncTaskLoader <String>{


    public LSChoiLoader(@NonNull Context context) {
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

        String token = LichSuChoi.token;
        HashMap<String,String> params = new HashMap<>();
        params.put("token",token);
        return NetworkUtils.doRequest("lich-su-choi","GET",params,null);
    }
}
