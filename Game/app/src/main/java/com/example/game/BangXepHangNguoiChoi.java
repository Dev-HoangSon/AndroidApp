package com.example.game;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class BangXepHangNguoiChoi extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private final ArrayList<NguoiChoi> mListNguoiChoi = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private NguoiChoiAdapter mNguoiChoiAdapter;
    private String sharedPrefFile = "com.example.game";
    public static String token;
    Button btn_dong;
    SharedPreferences mPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_giao_dien_xep_hang);
        mPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        token = mPref.getString("TOKEN",null);
        mRecyclerView = findViewById(R.id.rcv_nguoi_choi);
        btn_dong = findViewById(R.id.btn_dong);
        mNguoiChoiAdapter = new NguoiChoiAdapter(this,mListNguoiChoi);

        mRecyclerView.setAdapter(mNguoiChoiAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(getSupportLoaderManager().getLoader(0)!= null)
        {
            getSupportLoaderManager().initLoader(0,null,this);
        }
        getSupportLoaderManager().restartLoader(0,null,this);

        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new NguoiChoiLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if(data==null){
            Toast.makeText(getApplicationContext(),"lỗi", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray itemsArray =   jsonObject.getJSONArray("data");
                for (int i = 0; i < itemsArray.length(); i++) {
                    int id = itemsArray.getJSONObject(i).getInt("id");
                    String tenDangNhap = itemsArray.getJSONObject(i).getString("ten_dang_nhap");
                    int diemCaoNhat = itemsArray.getJSONObject(i).getInt("diem_cao_nhat");
                    mListNguoiChoi.add(new NguoiChoi(id,tenDangNhap,diemCaoNhat));
                }
                mNguoiChoiAdapter.notifyDataSetChanged();
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
