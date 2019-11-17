package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class GiaoDienDangNhap extends Activity {

    TextView txtDangKy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_giao_dien_dang_nhap);
        txtDangKy = findViewById(R.id.txtDangKy);
        DangKy();
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
}
