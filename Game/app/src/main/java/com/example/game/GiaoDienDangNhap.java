package com.example.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class GiaoDienDangNhap extends Activity {
    private SharedPreferences mPref;
    private String sharedPrefFile = "com.example.game";
    private String token ="";
    TextView txtDangKy;

    EditText txtTenDN, txtMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_giao_dien_dang_nhap);
        txtDangKy = findViewById(R.id.txtDangKy);
        DangKy();
        txtTenDN = findViewById(R.id.username);
        txtMatKhau = findViewById(R.id.passwword);
        mPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        SharedPreferences.Editor editor =mPref.edit();
        editor.clear();
        editor.apply();


//        if(mPref.getString("TOKEN",null) == null){
//            Intent intent = new Intent(this,MainActivity.class);
//            startActivity(intent);
//        }
    }
    private void DangKy(){
        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiaoDienDangNhap.this,GiaoDienDangKy.class);
                startActivity(intent);
            }
        });

    }



    public void dangNhap(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connectivityManager !=null)
        {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if(networkInfo != null && networkInfo.isConnected())
        {
            new FetchDangNhap(){
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if(jsonObject.getBoolean("success"))
                        {
                            SharedPreferences.Editor editor = mPref.edit();
                            editor.putString("TOKEN",jsonObject.getString("token"));
                            editor.apply();
                            Intent intent = new Intent(GiaoDienDangNhap.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(GiaoDienDangNhap.this,"Sai tài khoản hoặc mật khẩu",Toast.LENGTH_LONG).show();
                        }
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }.execute("dang-nhap","POST",txtTenDN.getText().toString(),txtMatKhau.getText().toString());
        }

    }
}
