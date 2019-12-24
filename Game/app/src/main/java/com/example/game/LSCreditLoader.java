package com.example.game;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.HashMap;

public class LSCreditLoader extends AsyncTaskLoader <String>{


    public LSCreditLoader(@NonNull Context context) {
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

        String token = LichSuMuaGoiCredit.token;
        HashMap<String,String> params = new HashMap<>();
        params.put("token",token);
        return NetworkUtils.doRequest("lich-su-mua-credit","GET",params,null);
    }
}
