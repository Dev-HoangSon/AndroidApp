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

public class LichSuMuaGoiCredit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private final ArrayList<LSCredit> mListLSCredit = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CreditAdapter mCreditAdapter;
    private String sharedPrefFile = "com.example.game";
    public static String token;
    SharedPreferences mPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_lich_su_mua_credit);
        mPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        token = mPref.getString("TOKEN",null);

        mRecyclerView = findViewById(R.id.rcv_credit);

        mCreditAdapter = new CreditAdapter(this, mListLSCredit);

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
        return new LSCreditLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data==null){
            Toast.makeText(getApplicationContext(),"lỗi", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray itemsArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < itemsArray.length(); i++) {
                    String thoiGian = itemsArray.getJSONObject(i).getString("created_at");
                    int tenGoi = itemsArray.getJSONObject(i).getInt("goi_credit_id");
                    int soTien = itemsArray.getJSONObject(i).getInt("so_tien");
                    int credit = itemsArray.getJSONObject(i).getInt("credit");
                    this.mListLSCredit.add(new LSCredit(tenGoi,credit, soTien,thoiGian));
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
