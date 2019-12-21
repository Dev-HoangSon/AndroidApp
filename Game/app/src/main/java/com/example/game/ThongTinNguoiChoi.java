package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ThongTinNguoiChoi extends AppCompatActivity {

    private String sharedPrefFile = "com.example.game";
    private static String token="";
    EditText username, email, credit, diemcao;
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

        mPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        if(mPref.getString("TOKEN",null) !=null) {
            token = mPref.getString("TOKEN",null);
            goiAPI();
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

}
