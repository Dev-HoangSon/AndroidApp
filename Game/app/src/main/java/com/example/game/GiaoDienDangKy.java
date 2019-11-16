package com.example.game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.game.Retrofix2.APIUtils;
import com.example.game.Retrofix2.DataClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiaoDienDangKy extends Activity {

    ImageView imgdangky;
    Button btn_dangky;
    int requs = 123;
    Bitmap bitmap;
    String path = "";
    private int STORE_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_giao_dien_dang_ky);
        Connect();
        OnilckImg();
        OnclickDangKy();

    }

    private void OnclickDangKy() {
        btn_dangky.setOnClickListener(new View.OnClickListener() {
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
                        if(response != null){
                            String meessage = response.body();
                            Log.d("BBB",""+meessage);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("AAA",t.getMessage());
                    }
                });
            }
        });
    }

    private void OnilckImg(){
        imgdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(GiaoDienDangKy.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(GiaoDienDangKy.this, "Cấp bộ nhớ thành công", Toast.LENGTH_LONG).show();
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
                            ActivityCompat.requestPermissions(GiaoDienDangKy.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORE_PERMISSION_CODE);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == requs && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgdangky.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    private void Connect(){
        imgdangky = findViewById(R.id.img);
        btn_dangky = findViewById(R.id.btn_dangky);
    }
}
