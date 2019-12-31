package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game.Retrofix2.APIUtils;
import com.example.game.Retrofix2.DataClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinNguoiChoi extends AppCompatActivity {

    private String sharedPrefFile = "com.example.game";
    private static String token="";
    EditText username, email, credit, diemcao;
    TextView delete;
    String txtImg = "";
    ImageView img;
    SharedPreferences mPref;
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prsE = mPref.edit();
        prsE.putString("TOKEN",token);
        prsE.apply();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_choi);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        credit = findViewById(R.id.credit);
        img = findViewById(R.id.img);
        diemcao = findViewById(R.id.diemSo);
        delete = findViewById(R.id.btn_xoatk);


        mPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        if(mPref.getString("TOKEN",null) !=null) {
            token = mPref.getString("TOKEN",null);
            goiAPI();
            Delete_Acc();
        }
        username.setEnabled(false);
        credit.setEnabled(false);
        diemcao.setEnabled(false);
    }
    public void goiAPI(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connectivityManager !=null)
        {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if(networkInfo != null && networkInfo.isConnected()) {
            new FectAPIToken() {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                        username.setText(jsonObject.getString("ten_dang_nhap"));
                        credit.setText(jsonObject.getString("credit"));
                        diemcao.setText(jsonObject.getString("diem_cao_nhat"));
                        email.setText(jsonObject.getString("email"));
                        txtImg = jsonObject.getString("hinh_dai_dien");
                        Picasso.get().load("http://10.0.2.2:8000/storage/"+txtImg).into(img);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute("user-info", "GET", mPref.getString("TOKEN",null));
        }
    }

    public  void Delete_Acc(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataClient dataClient = APIUtils.getData();
                Call<String> callback = dataClient.DeleteData(email.getText().toString(),txtImg.toString());

                Dialog dialog = new Dialog(ThongTinNguoiChoi.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setContentView(R.layout.custom_dialog_xacnhan);
                Button btn_dog = dialog.findViewById(R.id.btn_dog);
                Button btn_dong_y = dialog.findViewById(R.id.bnt_dog_y);
                Button txtienThiGia = dialog.findViewById(R.id.txt_GiaTien);
                Button title = dialog.findViewById(R.id.btn_title);
                title.setText("Xóa tài khoản");
                txtienThiGia.setText("Đồng ý để xóa!");
                btn_dong_y.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                SharedPreferences.Editor editor =mPref.edit();
                                editor.clear();
                                editor.apply();
                                finish();
                                Toast.makeText(ThongTinNguoiChoi.this,"Xóa tài khoản thành công",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ThongTinNguoiChoi.this,GiaoDienDangNhap.class);
                                startActivity(intent);
                            }
                        });
                    }
                });

                btn_dog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });
    }

    public void moLSCredit(View view) {
        Intent intent = new Intent(this,LichSuMuaGoiCredit.class);
        startActivity(intent);
    }

    public void moLSChoi(View view) {
        Intent intent = new Intent(this,LichSuChoi.class);
        startActivity(intent);
    }
}
