package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class GiaoDienDangNhap extends Activity {

    TextView txtDangKy;
    Button btnDangNhap;
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
        btnDangNhap = findViewById(R.id.btnDangNhapGame);
        DangNhap();
        btnDangNhap.setVisibility(View.VISIBLE);

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

    private void DangNhap(){
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchDangNhap(){
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if(jsonObject.getBoolean("success"))
                            {
                                Intent intent = new Intent(GiaoDienDangNhap.this, GiaoDienChoiGame.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(GiaoDienDangNhap.this,"Đăng nhập thất bại!",Toast.LENGTH_SHORT);
                            }
                        }catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }.execute("dang-nhap","POST",txtTenDN.getText().toString(),txtMatKhau.getText().toString());
            }
        });

    }
}
