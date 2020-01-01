package com.example.game;

import android.content.Intent;
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

public class MuaCredit extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    private final ArrayList<GoiGredit> mListLSCredit = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MuaCreditAdapter mCreditAdapter;
    public static final String EXTRA_REPLY ="com.example.game.extra.REPLY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_credit);

        mRecyclerView = findViewById(R.id.rcv_muacredit);

        mCreditAdapter = new MuaCreditAdapter(this, mListLSCredit);

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
        return new MuaCreditLoader(this);
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
                    int id = itemsArray.getJSONObject(i).getInt("id");
                    String tenGoi = itemsArray.getJSONObject(i).getString("ten_goi");
                    String soTien = itemsArray.getJSONObject(i).getString("so_tien");
                    String credit = itemsArray.getJSONObject(i).getString("credit");
                    this.mListLSCredit.add(new GoiGredit(id, tenGoi,credit, soTien));
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
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, MainActivity.creditHienTai+"");
        setResult(RESULT_OK, replyIntent);
        finish();
    }


}
