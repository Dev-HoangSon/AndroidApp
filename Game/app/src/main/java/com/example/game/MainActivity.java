    package com.example.game;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.app.Dialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.content.pm.PackageInfo;
    import android.content.pm.PackageManager;
    import android.content.pm.Signature;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.media.MediaPlayer;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.net.Uri;
    import android.os.Bundle;

    import android.util.Base64;
    import android.util.Log;
    import android.view.View;
    import android.view.Window;
    import android.view.animation.Animation;
    import android.view.animation.AnimationUtils;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.bumptech.glide.Glide;
    import com.facebook.AccessToken;
    import com.facebook.CallbackManager;
    import com.facebook.FacebookCallback;
    import com.facebook.FacebookException;
    import com.facebook.FacebookSdk;
    import com.facebook.GraphRequest;
    import com.facebook.GraphResponse;
    import com.facebook.Profile;
    import com.facebook.login.LoginManager;
    import com.facebook.login.LoginResult;
    import com.facebook.login.widget.LoginButton;
    import com.facebook.login.widget.ProfilePictureView;
    import com.google.android.gms.auth.api.signin.GoogleSignIn;
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
    import com.google.android.gms.auth.api.signin.GoogleSignInClient;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.common.SignInButton;
    import com.google.android.gms.common.api.ApiException;
    import com.google.android.gms.tasks.Task;
    import com.squareup.picasso.Picasso;


    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.io.InputStream;
    import java.net.URISyntaxException;
    import java.security.MessageDigest;
    import java.security.NoSuchAlgorithmException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Date;


    public class MainActivity extends Activity {


        public static final int TEXT_REQUEST = 1;
        private Object NameNotFoundException;
        ProfilePictureView profilePictureView;
        private Dialog dialog;
        LoginButton loginButton;
        SignInButton btndangnhapgoole;
        private String sharedPrefFile = "com.example.game";
        public static String token="";
        public static int creditHienTai;
        SharedPreferences mPref;
        CallbackManager callbackManager;
        GoogleSignInClient mGoogleSignInClient;
        String txtImg = "";
        String  name;
        Bitmap bitmap;
        Button txtname, credit;
        Button btndangnhap,btnDangXuat,btnchoingay, btnthongtin;
        Button btn_kc_200, btn_kc_400, btn_kc_800, btn_kc_1500, btn_kc_2500, btn_kc_5000 ;
        public static ArrayList<GoiGredit> goiGreditArrayList = new ArrayList<>();
        ImageButton btn_volume_on , icon_share;
        ImageView img_led, img_bangxephang ,hinhanh , imggoole ,img_xoay;
        boolean Amthanh = true;
        int requestcode = 123;
        int RC_SIGN_IN = 10;
        @Override
        protected void onPause() {
            super.onPause();
            SharedPreferences.Editor prsE = mPref.edit();
            prsE.putString("TOKEN",token);
            prsE.apply();
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
            layCauHinh();
            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            PackageInfo info = null;
            try {
                info = getPackageManager().getPackageInfo(
                        "com.example.game",
                        PackageManager.GET_SIGNATURES);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            for (Signature signature : info.signatures) {
                MessageDigest md = null;

                try {
                    md = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

            MediaPlayer  mediaPlayer = MediaPlayer.create(this,R.raw.bgwarm);
            GiaoDien(mediaPlayer);
            Tat_Nhac(mediaPlayer);
            DangNhap(mediaPlayer);
            ChoiGame(mediaPlayer);
            btnDangXuat.setVisibility(View.INVISIBLE);
            loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
            btndangnhap.setVisibility(View.VISIBLE);
            setLogin_Button();
            setLogout_Button();
            ThongTinTaiKhoan();
            btndangnhapgoole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.sign_in_button:
                            signIn();
                            break;
                        // ...
                    }
                }
            });
//            SharedPreferences.Editor editor =mPref.edit();
//            editor.clear();
//            editor.apply();
//            token =null;
            if(mPref.getString("TOKEN",null) !=null)
            {
                token = mPref.getString("TOKEN",null);
                Animation animationscale = AnimationUtils.loadAnimation(MainActivity.this,R.anim.button_rcale);
                btnDangXuat.startAnimation(animationscale);
                btndangnhap.clearAnimation();
                profilePictureView.setVisibility(View.VISIBLE);
                //btndangky.clearAnimation();
                btndangnhap.setVisibility(View.INVISIBLE);
                //btndangky.setVisibility(View.INVISIBLE);
                imggoole.setVisibility(View.INVISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
                btndangnhapgoole.setVisibility(View.INVISIBLE);
                btnDangXuat.setVisibility(View.VISIBLE);
                goiAPI();
            }
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
                            txtname.setText("Xin chào " + jsonObject.getString("ten_dang_nhap"));
                            credit.setText("Credit: " + jsonObject.getString("credit"));
                            creditHienTai = Integer.parseInt(jsonObject.getString("credit"));
                            txtImg = jsonObject.getString("hinh_dai_dien");
                            profilePictureView.setVisibility(View.INVISIBLE);
                            imggoole.setVisibility(View.VISIBLE);
                            if(txtImg.length() > 4){
                                Picasso.get().load("http://10.0.2.2:8000/storage/"+txtImg).into(imggoole);
                            }else{
                                imggoole.setImageResource(R.drawable.default_avt);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute("user-info", "GET", token);
            }
        }


        private void signIn()
        {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
        }

        public void GiaoDien(MediaPlayer mediaPlayer){
            credit = findViewById(R.id.textView7);
            btnthongtin = findViewById(R.id.thongtin);
            imggoole = findViewById(R.id.imageViewgoogle);
            img_xoay = findViewById(R.id.img_xoay);
            btndangnhapgoole = findViewById(R.id.sign_in_button);
            profilePictureView  = findViewById(R.id.friendProfilePictureview);
            loginButton = findViewById(R.id.login_button);
            credit.setText("Credit: 0");
            btnDangXuat = findViewById(R.id.dangxuat);
            txtname = findViewById(R.id.name);
            btndangnhap = findViewById(R.id.btndangnhap);
            btnchoingay = findViewById(R.id.btnplaynow);
            btn_volume_on = findViewById(R.id.btn_volume_on);
            img_led = findViewById(R.id.img_led);
            icon_share = findViewById(R.id.icon_share);
            img_bangxephang = findViewById(R.id.img_bangxephang);
            Anim_DangNhapAnhDangKy();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            Animation animationalpha = AnimationUtils.loadAnimation(MainActivity.this,R.anim.img_alpha);
            img_led.startAnimation(animationalpha);
            Animation animationalphaxoay = AnimationUtils.loadAnimation(MainActivity.this,R.anim.img_xoay);
            img_xoay.startAnimation(animationalphaxoay);
        }

        private void ThongTinTaiKhoan(){
                btnthongtin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(btndangnhap.getVisibility() == View.VISIBLE){
                            Toast.makeText(MainActivity.this,"Bạn chưa đăng nhập",Toast.LENGTH_LONG).show();
                        }else {
                            Intent intent = new Intent(MainActivity.this, ThongTinNguoiChoi.class);
                            startActivity(intent);
                        }
                    }
                });
        }

        public void showAlertDialog(){
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.custom_dialog_dang_xuat);
            Button btn_dangxuat = dialog.findViewById(R.id.btn_dialog_dangxuat);
            Button btn_dong = dialog.findViewById(R.id.btn_dialog_dong);

            btn_dangxuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor =mPref.edit();
                    editor.clear();
                    editor.apply();
                    token =null;
                    btndangnhapgoole.setVisibility(View.VISIBLE);
                    btnDangXuat.clearAnimation();
                    mGoogleSignInClient.signOut();
                    LoginManager.getInstance().logOut();
                    btndangnhap.setVisibility(View.INVISIBLE);
                    //btndangky.setVisibility(View.INVISIBLE);
                    btnDangXuat.setVisibility(View.INVISIBLE);
                    txtname.setText("Username");
                    credit.setText("Credit: 0");
                    imggoole.setVisibility(View.INVISIBLE);
                    profilePictureView.setVisibility(View.VISIBLE);
                    profilePictureView.setProfileId(null);
                    loginButton.setVisibility(View.VISIBLE);
                    Anim_DangNhapAnhDangKy();

                    dialog.dismiss();
                }
            });

            btn_dong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }

    //    public void showAlertDialog(){
    //        AlertDialog.Builder builder = new AlertDialog.Builder(this);
    //        builder.setTitle("Thông báo");
    //        builder.setMessage("Bạn có muốn đăng xuất không?");
    //        builder.setCancelable(false);
    //        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //
    //            }
    //        });
    //        builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                SharedPreferences.Editor editor =mPref.edit();
    //                editor.clear();
    //                editor.apply();
    //                token =null;
    //                btndangnhapgoole.setVisibility(View.VISIBLE);
    //                btnDangXuat.clearAnimation();
    //                mGoogleSignInClient.signOut();
    //                LoginManager.getInstance().logOut();
    //                btndangnhap.setVisibility(View.INVISIBLE);
    //                //btndangky.setVisibility(View.INVISIBLE);
    //                btnDangXuat.setVisibility(View.INVISIBLE);
    //                txtname.setText("Username");
    //                credit.setText("Credit: 0");
    //
    //                imggoole.setVisibility(View.INVISIBLE);
    //                profilePictureView.setVisibility(View.VISIBLE);
    //                profilePictureView.setProfileId(null);
    //                loginButton.setVisibility(View.VISIBLE);
    //                Anim_DangNhapAnhDangKy();
    //
    //                dialogInterface.dismiss();
    //
    //            }
    //        });
    //        AlertDialog alertDialog = builder.create();
    //        alertDialog.show();
    //
    //    }

        public void LuuLuotChoi(){
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if(connectivityManager !=null)
            {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            String currentDateTime;

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            currentDateTime = sdf1.format(new Date());
            if(networkInfo != null && networkInfo.isConnected()) {
                new LuotChoi() {
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            if(jsonObject.getBoolean("success"))
                            {
//                                Intent intent = new Intent(MainActivity.this, GiaoDienChoiGame.class);
//                                startActivityForResult(intent, requestcode);
                                Toast.makeText(MainActivity.this,"Load thành công",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute("luu-luot-choi", "POST", mPref.getString("TOKEN",null),currentDateTime);
            }
        }

        private void setLogout_Button() {
            btnDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialog();
                }
            });
        }

        public void showAlertDangNhap(){
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.custom_dialog_dang_nhap);
            Button btn_dangnhap = dialog.findViewById(R.id.btn_dialog_dangnhap);
            Button btn_dong = dialog.findViewById(R.id.btn_dialog_dong);

            btn_dangnhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(MainActivity.this,GiaoDienDangNhap.class);
                    startActivityForResult(intent,requestcode);
                    dialog.dismiss();
                }
            });

            btn_dong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }

    //    public void showAlertDangNhap(){
    //        AlertDialog.Builder builder = new AlertDialog.Builder(this);
    //        builder.setTitle("Thông báo");
    //        builder.setMessage("Bạn chưa đăng nhập!");
    //        builder.setCancelable(false);
    //        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //
    //            }
    //        });
    //        builder.setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                finish();
    //                Intent intent = new Intent(MainActivity.this,GiaoDienDangNhap.class);
    //                startActivityForResult(intent,requestcode);
    //                dialogInterface.dismiss();
    //
    //            }
    //        });
    //        AlertDialog alertDialog = builder.create();
    //        alertDialog.show();
    //
    //    }

        private  void  Anim_DangNhapAnhDangKy(){
             Animation animationscale = AnimationUtils.loadAnimation(MainActivity.this,R.anim.button_rcale);
             btndangnhap.startAnimation(animationscale);
             btnthongtin.startAnimation(animationscale);
        }

        private void setLogin_Button() {
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Animation animationscale = AnimationUtils.loadAnimation(MainActivity.this,R.anim.button_rcale);
                    btnDangXuat.startAnimation(animationscale);
                    btndangnhap.clearAnimation();
                    profilePictureView.setVisibility(View.VISIBLE);
                    //btndangky.clearAnimation();
                    btndangnhap.setVisibility(View.INVISIBLE);
                    //btndangky.setVisibility(View.INVISIBLE);
                    imggoole.setVisibility(View.INVISIBLE);
                    loginButton.setVisibility(View.INVISIBLE);
                    btndangnhapgoole.setVisibility(View.INVISIBLE);
                    btnDangXuat.setVisibility(View.VISIBLE);
                    result();
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
        }

        private void result() {
            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    // Log.d("JSON",response.getJSONObject().toString());
                    try {
                        name = object.getString("name");
                        profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                        txtname.setText(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "name");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
        }


        public void Tat_Nhac(final MediaPlayer mediaPlayer){
            btn_volume_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Amthanh == true){
                        mediaPlayer.stop();
                        btn_volume_on.setImageResource(R.drawable.volume_off);
                        Amthanh = false;
                        }else{
                            try {
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                mediaPlayer.release();
                            btn_volume_on.setImageResource(R.drawable.volume_on);
                            Amthanh = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }

        public void DangNhap(MediaPlayer mediaPlayer){
            btndangnhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent intent = new Intent(MainActivity.this,GiaoDienDangNhap.class);
                    //mediaPlayer.stop();
                    startActivityForResult(intent,requestcode);
                }
            });
        }

        public void layCauHinh(){
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if(connectivityManager !=null)
            {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            if(networkInfo != null && networkInfo.isConnected()) {
                new LayCauHinh() {
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            if(jsonObject.getBoolean("success"))
                            {
                                SharedPreferences.Editor editor = mPref.edit();
                                editor.putInt("COHOI",jsonObject.getInt("co_hoi"));
                                editor.putInt("THOIGIAN",jsonObject.getInt("thoi_gian"));
                                editor.putInt("DIEM",jsonObject.getInt("diem"));
                                editor.apply();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute("lay-cau-hinh", "GET");
            }
        }

        public void ChoiGame(MediaPlayer mediaPlayer){
            btnchoingay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(mPref.getString("TOKEN",null) !=null || btndangnhap.getVisibility() == View.INVISIBLE) {
                            Intent intent = new Intent(MainActivity.this, GiaoDienChoiGame.class);
                            LuuLuotChoi();
                            startActivityForResult(intent, requestcode);
                            mediaPlayer.stop();
                        }
                        else {
                            showAlertDangNhap();
                        }
                    }catch (Exception Ex){
//                        Intent intent = new Intent(MainActivity.this,GiaoDienChoiGame.class);
//                        startActivityForResult(intent,requestcode);
                        Toast.makeText(MainActivity.this,"Lỗi đăng nhập",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode,resultCode,data);
            if(requestCode == requestcode && resultCode ==RESULT_CANCELED){
                Toast.makeText(MainActivity.this,"Đã thoát",Toast.LENGTH_SHORT).show();
            }
            if(requestCode == requestcode && resultCode == RESULT_OK){
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                     bitmap = BitmapFactory.decodeStream(inputStream);
                     hinhanh.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == TEXT_REQUEST) {
                if (resultCode == RESULT_OK) {
                    String reply = data.getStringExtra(MuaCredit.EXTRA_REPLY);
                    credit.setText(reply);
                    credit.setVisibility(View.VISIBLE);
                }
            }
            if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }

        private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

            try {
                GoogleSignInAccount account = completedTask.getResult(ApiException.class);

                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                if (acct != null) {
                    Animation animationscale = AnimationUtils.loadAnimation(MainActivity.this,R.anim.button_rcale);
                    btnDangXuat.startAnimation(animationscale);
                    btndangnhap.clearAnimation();
                    //btndangky.clearAnimation();
                    btndangnhap.setVisibility(View.INVISIBLE);
                    //btndangky.setVisibility(View.INVISIBLE);
                    loginButton.setVisibility(View.INVISIBLE);
                    profilePictureView.setVisibility(View.INVISIBLE);
                    String personName = acct.getDisplayName();
                    btndangnhapgoole.setVisibility(View.INVISIBLE);
                    Uri personPhoto = acct.getPhotoUrl();
                    imggoole.setVisibility(View.VISIBLE);
                    txtname.setText(personName);
                    Glide.with(this).load(String.valueOf(personPhoto)).into(imggoole);
                }
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            }
        }

        @Override
        protected void onStart() {
            LoginManager.getInstance().logOut();
            super.onStart();
        }

        public void moBangXepHang(View view) {
            if(mPref.getString("TOKEN",null) !=null || btndangnhap.getVisibility() == View.INVISIBLE) {
                Intent intent = new Intent(this,BangXepHangNguoiChoi.class);
                startActivity(intent);
            }
            else {
                showAlertDangNhap();
            }
        }

        public void layGoiCredit(){

        }

        public void Muacredit(View view) {
            if(mPref.getString("TOKEN",null) !=null || btndangnhap.getVisibility() == View.INVISIBLE) {
                Intent intent = new Intent(this,MuaCredit.class);
                startActivityForResult(intent, TEXT_REQUEST);
            }
            else {
                showAlertDangNhap();
            }
        }
    }
