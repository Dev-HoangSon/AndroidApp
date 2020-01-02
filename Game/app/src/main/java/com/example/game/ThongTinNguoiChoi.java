package com.example.game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinNguoiChoi extends AppCompatActivity {

    private String sharedPrefFile = "com.example.game";
    private static String token="";
    EditText username, email, credit, diemcao;
    Button btn_thaydoi;
    TextView delete;
    String txtImg = "";
    ImageView img;
    String path = "";
    Bitmap bitmap;
    SharedPreferences mPref;
    private int STORE_PERMISSION_CODE = 1;
    int requs = 123;
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
        btn_thaydoi= findViewById(R.id.btn_chinhsua);


        mPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        if(mPref.getString("TOKEN",null) !=null) {
            token = mPref.getString("TOKEN",null);
            goiAPI();
            Delete_Acc();
            SuaHinhAnh();
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
                        if(txtImg.length() > 4){
                            Picasso.get().load("http://10.0.2.2:8000/storage/"+txtImg).into(img);
                        }else{
                            img.setImageResource(R.drawable.default_avt);
                        }

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

    void SuaHinhAnh(){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ThongTinNguoiChoi.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ThongTinNguoiChoi.this, "Cấp bộ nhớ thành công", Toast.LENGTH_LONG).show();
                } else {
                    requesetStroagePermision();
                }

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, requs);
            }
        });
    }

    private void requesetStroagePermision() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Cấp bộ nhớ")
                    .setMessage("Cấp quyền bộ nhớ")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ThongTinNguoiChoi.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("can", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thành công", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Thất bại", Toast.LENGTH_LONG).show();
            }
        }
    }
    public String getRealPathFromURI(Uri contentUri) {


        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);

        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == requs && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void CapNhap(){
        btn_thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        File file = new File(path);
                        String file_path = file.getAbsolutePath();
                        String[] mangtenfile = file_path.split("\\.");
                        file_path = mangtenfile[0]+System.currentTimeMillis() + "."+mangtenfile[1];
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

                        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);

                        DataClient dataClient  = APIUtils.getData();
                        retrofit2.Call<String> callback = dataClient.UploadPhoto(body);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                                String meessage = response.body();
                                Log.d("BBB",""+meessage);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("AAA",t.getMessage());
                            }
                        });
            }
        });
    }
}
