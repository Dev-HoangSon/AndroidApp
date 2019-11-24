package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;



public class MainActivity extends Activity {



    private Object NameNotFoundException;
    ProfilePictureView profilePictureView;
    LoginButton loginButton;
    SignInButton btndangnhapgoole;
    private String sharedPrefFile = "com.example.game";
    private static String token="";
    SharedPreferences mPref;
    CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;

    String  name;
    Bitmap bitmap;
    TextView txtname ;
    Button btndangnhap,btnDangXuat,btnchoingay, btnthongtin;
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
        if(mPref.getString("TOKEN",null) !=null)
        {
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
        }
    }


    private void signIn()
    {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void GiaoDien(MediaPlayer mediaPlayer){
        btnthongtin = findViewById(R.id.thongtin);
        imggoole = findViewById(R.id.imageViewgoogle);
        img_xoay = findViewById(R.id.img_xoay);
        btndangnhapgoole = findViewById(R.id.sign_in_button);
        profilePictureView  = findViewById(R.id.friendProfilePictureview);
        loginButton = findViewById(R.id.login_button);
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



    private void setLogout_Button() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
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
                imggoole.setVisibility(View.INVISIBLE);
                profilePictureView.setVisibility(View.VISIBLE);
                profilePictureView.setProfileId(null);
                loginButton.setVisibility(View.VISIBLE);
                Anim_DangNhapAnhDangKy();
            }
        });
    }

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
                Intent intent = new Intent(MainActivity.this,GiaoDienDangNhap.class);
                startActivityForResult(intent,requestcode);
                mediaPlayer.stop();
            }
        });
    }

    public void ChoiGame(MediaPlayer mediaPlayer){
        btnchoingay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this,GiaoDienChoiGame.class);
                    startActivityForResult(intent,requestcode);
                    mediaPlayer.stop();
                }catch (Exception Ex){
                    Intent intent = new Intent(MainActivity.this,GiaoDienChoiGame.class);
                    startActivityForResult(intent,requestcode);
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

}
