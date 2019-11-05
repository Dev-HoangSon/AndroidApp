package com.example.game;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CauHoiLoader extends AsyncTaskLoader<String> {
    private static final String BASE_URL_CAU_HOI =  "https://hoangsonapp.000webhostapp.com/api/cau-hoi/"; // Genymotion
    public CauHoiLoader(@NonNull Context context) {
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
        String id = GiaoDienChoiGame.LinhVucDuocChon;
        return NetworkUtils.getJSONData(id,"GET",BASE_URL_CAU_HOI);
    }
}