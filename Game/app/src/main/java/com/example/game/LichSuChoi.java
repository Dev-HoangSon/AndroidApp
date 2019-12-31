package com.example.game;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LichSuChoi extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private final ArrayList<LSChoi> mListLSCredit = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LuotChoiAdapter mCreditAdapter;
    private String sharedPrefFile = "com.example.game";
    public static String token;
    SharedPreferences mPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_lich_su_choi);
        mPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        token = mPref.getString("TOKEN",null);

        mRecyclerView = findViewById(R.id.rcv_lschoi);

        mCreditAdapter = new LuotChoiAdapter(this, mListLSCredit);

        mRecyclerView.setAdapter(mCreditAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(getSupportLoaderManager().getLoader(0)!= null)
        {
            getSupportLoaderManager().initLoader(0,null,this);
        }
        getSupportLoaderManager().restartLoader(0,null,this);
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new LSChoiLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data==null){
            Toast.makeText(getApplicationContext(),"lỗi", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray itemsArray =  jsonObject.getJSONArray("data");
                for (int i = 0; i < itemsArray.length(); i++) {
                    String thoiGian = itemsArray.getJSONObject(i).getString("ngay_gio");
                    int tenGoi = itemsArray.getJSONObject(i).getInt("so_cau");
                    int soTien = itemsArray.getJSONObject(i).getInt("diem");
                    this.mListLSCredit.add(new LSChoi(tenGoi, soTien,thoiGian));
                }
                this.mCreditAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void Dong(View view) {
        finish();
    }
}
