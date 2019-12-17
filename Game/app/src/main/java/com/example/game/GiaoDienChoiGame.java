package com.example.game;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TooManyListenersException;


public class GiaoDienChoiGame extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    SharedPreferences sharedPreferences;
    private String sharedPrefFile = "com.example.game";
    private static String token="";
    private static int SoCau = 0;
    SharedPreferences mPref;
    private static final long START_TIME_IN_MILLIS = 30000;
    private MediaPlayer mediaPlayerCauhoi;
    private CountDownTimer countDownTimerTime;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    ImageView img_xoay, img5050, img_ykienkhangia, img_GoiNguoithan, img_tuvantaicho;
    ImageView img_nagluot_mua, img5050_mua, img_ykienkhangia_mua, img_GoiNguoithan_mua, img_tuvantaicho_mua;
    ImageView img_mang1, img_mang2, img_mang3, img_mang4, img_mang5, img_cua_hang;
    Button btn_A, btn_B, btn_C, btn_D, btn_sansang, btn_noidungcauhoi, btn_diem_so,btn_kimcuong;
    Button btn_lv_1, btn_lv_2, btn_lv_3, btn_lv_4;
    TextView txt;
    public static ArrayList<Linh_Vuc> linh_vucArrayList = new ArrayList<>();
    public static ArrayList<GoiGredit> goiGreditArrayList = new ArrayList<>();
    public static String LinhVucDuocChon = "", LinhVucChoi = "";
    String id_cauhoi = "";
    String DapAnDung = "";
    int Tongdiem = 0, TongSoMang = 4;
    boolean key = true, keyKQ = false;
    int SoDapAnChinhXac = 0, Vitridapanchinhxac = 0, giatri5050so1 = 0, giattri5050so2 = 0;
    Button btn_kc_200, btn_kc_400, btn_kc_800, btn_kc_1500, btn_kc_2500, btn_kc_5000 ;
    String keymp3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_giao_dien_choi_game);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        LoadGiaoDien();
        Linh_Vuc_1();
        Linh_Vuc_2();
        Linh_Vuc_3();
        Linh_Vuc_4();
        Click_A();
        Click_B();
        Click_C();
        Click_D();
        SanSang();
        _50and50();
        HoiyKienKhanGia();
        GoiDienNguoiThan();
        TuVanTaiCho();
        CuaHang();
        Mua_Kc();

    }

    private void LoadDiemThuog() {


    }

    public void LuuChiTietLuotChoi(String id, String phuongan, String diem){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connectivityManager !=null)
        {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if(networkInfo != null && networkInfo.isConnected()) {
            new ChiTietLuotChoi() {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                        if(jsonObject.getBoolean("success"))
                        {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute("luu-chi-tiet-luot-choi", "POST", mPref.getString("TOKEN",null),id,phuongan,diem);
        }
    }

    public void CapNhatLuotChoi(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connectivityManager !=null)
        {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if(networkInfo != null && networkInfo.isConnected()) {
            new CapNhatLuotChoi() {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(s);
                        if(jsonObject.getBoolean("success"))
                        {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute("cap-nhat-luot-choi", "POST", mPref.getString("TOKEN",null), sharedPreferences.getString("diemso",null),SoCau+"");
        }
    }

    public void LoadGiaoDien() {
        btn_kimcuong = findViewById(R.id.btn_kimcuong);
        img_cua_hang = findViewById(R.id.img_cua_hang);
        btn_lv_1 = findViewById(R.id.btn_lv_1);
        btn_lv_2 = findViewById(R.id.btn_lv_2);
        btn_lv_3 = findViewById(R.id.btn_lv_3);
        btn_lv_4 = findViewById(R.id.btn_lv_4);
        txt = findViewById(R.id.time_giay);
        btn_diem_so = findViewById(R.id.btn_diem_so);
        img5050 = findViewById(R.id.img_5050);
        img_GoiNguoithan = findViewById(R.id.img_goinguoithan);
        img_ykienkhangia = findViewById(R.id.img_ykienkhangia);
        btn_noidungcauhoi = findViewById(R.id.btn_noidung);
        img_tuvantaicho = findViewById(R.id.img_tuvantaicho);
        btn_A = findViewById(R.id.btn_A);
        btn_B = findViewById(R.id.btn_B);
        btn_C = findViewById(R.id.btn_C);
        btn_D = findViewById(R.id.btn_D);
        btn_sansang = findViewById(R.id.btn_sansang);
        btn_sansang.setEnabled(false);
        tat_battrogiup(false);
        img_xoay = findViewById(R.id.img_xoay_choi);
        txt.setEnabled(false);
        set_bat_laiketquaBtn(false);
        LoadDiemThuog();
        sharedPreferences = getSharedPreferences("TongDiem",MODE_PRIVATE);
        btn_diem_so.setText(sharedPreferences.getString("diemso","0"));
        Animation animationalphaxoay = AnimationUtils.loadAnimation(GiaoDienChoiGame.this, R.anim.img_xoay);
        img_xoay.startAnimation(animationalphaxoay);
        // private static final String BASE_URL =  "http://10.0.2.2:8001/api/linh-vuc/"; // AVD
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connectivityManager !=null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if(networkInfo !=null && networkInfo.isConnected()){
          //new AsyntalkLinhVuc(btn_lv_1, btn_lv_2, btn_lv_3, btn_lv_4).execute("https://hoangsonapp.000webhostapp.com/api/linh-vuc/");
            new AsyntalkLinhVuc(btn_lv_1, btn_lv_2, btn_lv_3, btn_lv_4).execute("http://10.0.3.2:8000/api/linh-vuc/");
        }else{
            Toast.makeText(GiaoDienChoiGame.this,"Lỗi Internet",Toast.LENGTH_LONG).show();
        }

    }

    private void setTextbuton() {
        btn_noidungcauhoi.setText("Chọn lĩnh vực");
        btn_A.setText("");
        btn_B.setText("");
        btn_C.setText("");
        btn_D.setText("");
    }

    private void set_bat_laiketquaBtn(boolean bool) {
        btn_A.setEnabled(bool);
        btn_B.setEnabled(bool);
        btn_C.setEnabled(bool);
        btn_D.setEnabled(bool);
    }

    private void tat_bat_cicklinhvuc(boolean bool) {
        btn_lv_1.setEnabled(bool);
        btn_lv_2.setEnabled(bool);
        btn_lv_3.setEnabled(bool);
        btn_lv_4.setEnabled(bool);
    }

    private void setMauLinhvuc() {
        btn_lv_1.setBackgroundResource(R.drawable.reward_datbiet);
        btn_lv_2.setBackgroundResource(R.drawable.reward_datbiet);
        btn_lv_3.setBackgroundResource(R.drawable.reward_datbiet);
        btn_lv_4.setBackgroundResource(R.drawable.reward_datbiet);
    }

    private void tat_battrogiup(boolean bool) {
        img5050.setEnabled(bool);
        img_ykienkhangia.setEnabled(bool);
        img_GoiNguoithan.setEnabled(bool);
        img_tuvantaicho.setEnabled(bool);
    }

    private void Anhthanhcaudung() {
        Random random = new Random();
        int sound = random.nextInt(2) + 1;
        switch (sound) {
            case 1:
                MediaPlayer mediaPlayer1 = MediaPlayer.create(GiaoDienChoiGame.this, R.raw.true_1);
                mediaPlayer1.start();
                mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                break;
            case 2:
                MediaPlayer mediaPlayer2 = MediaPlayer.create(GiaoDienChoiGame.this, R.raw.true_3);
                mediaPlayer2.start();
                mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                break;
        }
    }

    private void Finish(Button btn) {
        LinhVucDuocChon = "";
        Hienthilainut5050();
        setTextbuton();
        keyKQ = true;
        setMauLinhvuc();
        tat_bat_cicklinhvuc(true);
        tat_battrogiup(false);
        btn.setBackgroundResource(R.drawable.btn_khungtraloi);
        btn_diem_so.setText("" + TangDiemSo());
    }

    private void LoadLaiKhiDung() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connectivityManager !=null){
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if(networkInfo !=null && networkInfo.isConnected()){
            linh_vucArrayList.clear();
            new AsyntalkLinhVuc(btn_lv_1, btn_lv_2, btn_lv_3, btn_lv_4).execute("http://10.0.3.2:8000/api/linh-vuc/");
        }else{
            Toast.makeText(GiaoDienChoiGame.this,"Lỗi Internet",Toast.LENGTH_LONG).show();
        }
    }

    private void LoadThua() {
        Hienthilainut5050();
        mediaPlayerCauhoi.stop();
        TruMangKhiChonSai();
        switch (LayVitriDapAnDung()) {
            case 1:
                MediaPlayer mediaPlayer1 = MediaPlayer.create(GiaoDienChoiGame.this, R.raw.lose_a);
                mediaPlayer1.start();
                mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                break;
            case 2:
                MediaPlayer mediaPlayer2 = MediaPlayer.create(GiaoDienChoiGame.this, R.raw.lose_b);
                mediaPlayer2.start();
                mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                break;
            case 3:
                MediaPlayer mediaPlayer3 = MediaPlayer.create(GiaoDienChoiGame.this, R.raw.lose_c);
                mediaPlayer3.start();
                mediaPlayer3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                break;
            case 4:
                MediaPlayer mediaPlayer4 = MediaPlayer.create(GiaoDienChoiGame.this, R.raw.lose_d);
                mediaPlayer4.start();
                mediaPlayer4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                break;
        }
        Toast.makeText(GiaoDienChoiGame.this, "Bạn đã bị mất 1 lượt chơi", Toast.LENGTH_LONG).show();
    }

    private void Clickchonlinhvuc(Button btn, int thutu, Button btnOne, Button btnTwo, Button btnThree) {
            if(linh_vucArrayList.size() == 0){
                Toast.makeText(GiaoDienChoiGame.this,"Lỗi internet",Toast.LENGTH_LONG).show();
                setMauLinhvuc();
                return;
            }else {
                try {
                    btn_sansang.setEnabled(true);
                    LinhVucDuocChon = "";
                    if (LinhVucDuocChon == "") {
                        LinhVucDuocChon = String.valueOf(linh_vucArrayList.get(thutu).getId());
                        SoCau +=1;
                    }
                    if (btn_sansang.getVisibility() == View.INVISIBLE) {
                        btn_sansang.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ex) {
                    LinhVucDuocChon = String.valueOf(linh_vucArrayList.get(thutu).getId());
                    Toast.makeText(GiaoDienChoiGame.this, "Chơi lại đi má ơi", Toast.LENGTH_LONG).show();
                }
            }



        MediaPlayer mediaPlayer = MediaPlayer.create(GiaoDienChoiGame.this, R.raw.san_sang_choi_chua);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnOne.setBackgroundResource(R.drawable.reward_datbiet);
                btnTwo.setBackgroundResource(R.drawable.reward_datbiet);
                btnThree.setBackgroundResource(R.drawable.reward_datbiet);
                btn.setBackgroundResource(R.drawable.reward_chinhxac);
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }

    private void Linh_Vuc_1() {
        btn_lv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                Clickchonlinhvuc(btn_lv_1, 0, btn_lv_2, btn_lv_3, btn_lv_4);
            }
        });
    }

    private void Linh_Vuc_2() {
        btn_lv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                Clickchonlinhvuc(btn_lv_2, 1, btn_lv_1, btn_lv_3, btn_lv_4);
            }
        });
    }

    private void Linh_Vuc_3() {
        btn_lv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                Clickchonlinhvuc(btn_lv_3, 2, btn_lv_1, btn_lv_2, btn_lv_4);
            }
        });
    }

    private void Linh_Vuc_4() {
        btn_lv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
                Clickchonlinhvuc(btn_lv_4, 3, btn_lv_1, btn_lv_2, btn_lv_3);
            }
        });
    }

    private void Click_A() {
        btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerCauhoi.stop();
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
                CountDownTimer countDownTimer = new CountDownTimer(500, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn_A.setBackgroundResource(R.drawable.btn_traloi_sai);
                        set_bat_laiketquaBtn(false);
                    }

                    @Override
                    public void onFinish() {
                        btn_B.setEnabled(false);
                        btn_C.setEnabled(false);
                        btn_D.setEnabled(false);
                        if (btn_sansang.getVisibility() == View.INVISIBLE) {
                            if (KiemTraDapAn("A", DapAnDung)) {
                                LoadLaiKhiDung();
                                LuuChiTietLuotChoi(id_cauhoi,"A","100");
                                CountDownTimer countDownTimerchinhxac = new CountDownTimer(1000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        Anhthanhcaudung();
                                        btn_A.setBackgroundResource(R.drawable.btn_traloi_dung);
                                    }

                                    @Override
                                    public void onFinish() {
                                        Finish(btn_A);
                                    }
                                }.start();

                            } else {
                                btn_A.setBackgroundResource(R.drawable.btn_khungtraloi);
                                LuuChiTietLuotChoi(id_cauhoi,"A","0");
                                LoadThua();
                            }
                        }
                    }
                }.start();
            }
        });
    }

    private void Click_B() {
        btn_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerCauhoi.stop();
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
                CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn_B.setBackgroundResource(R.drawable.btn_traloi_sai);
                        set_bat_laiketquaBtn(false);
                    }

                    @Override
                    public void onFinish() {
                        btn_A.setEnabled(false);
                        btn_C.setEnabled(false);
                        btn_D.setEnabled(false);
                        if (btn_sansang.getVisibility() == View.INVISIBLE) {
                            if (KiemTraDapAn("B", DapAnDung)) {
                                LoadLaiKhiDung();
                                LuuChiTietLuotChoi(id_cauhoi,"B","100");
                                CountDownTimer countDownTimerchinhxac = new CountDownTimer(1000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        Anhthanhcaudung();
                                        btn_B.setBackgroundResource(R.drawable.btn_traloi_dung);
                                    }

                                    @Override
                                    public void onFinish() {
                                        Finish(btn_B);
                                    }
                                }.start();

                            } else {
                                btn_B.setBackgroundResource(R.drawable.btn_khungtraloi);
                                LuuChiTietLuotChoi(id_cauhoi,"B","0");
                                LoadThua();
                            }
                        }
                    }
                }.start();
            }
        });
    }

    private void Click_C() {
        btn_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerCauhoi.stop();
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
                CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn_C.setBackgroundResource(R.drawable.btn_traloi_sai);
                        set_bat_laiketquaBtn(false);
                    }

                    @Override
                    public void onFinish() {
                        btn_A.setEnabled(false);
                        btn_B.setEnabled(false);
                        btn_D.setEnabled(false);
                        if (btn_sansang.getVisibility() == View.INVISIBLE) {
                            if (KiemTraDapAn("C", DapAnDung)) {
                                LoadLaiKhiDung();
                                LuuChiTietLuotChoi(id_cauhoi,"C","100");
                                CountDownTimer countDownTimerchinhxac = new CountDownTimer(1000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        Anhthanhcaudung();
                                        btn_C.setBackgroundResource(R.drawable.btn_traloi_dung);
                                    }

                                    @Override
                                    public void onFinish() {
                                        Finish(btn_C);
                                    }
                                }.start();
                            } else {
                                btn_C.setBackgroundResource(R.drawable.btn_khungtraloi);
                                LuuChiTietLuotChoi(id_cauhoi,"C","0");
                                LoadThua();
                            }
                        }
                    }
                }.start();
            }
        });
    }

    private void Click_D() {
        btn_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerCauhoi.stop();
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
                CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn_D.setBackgroundResource(R.drawable.btn_traloi_sai);
                        set_bat_laiketquaBtn(false);
                    }

                    @Override
                    public void onFinish() {
                        btn_A.setEnabled(false);
                        btn_B.setEnabled(false);
                        btn_C.setEnabled(false);
                        if (btn_sansang.getVisibility() == View.INVISIBLE) {
                            if (KiemTraDapAn("D", DapAnDung)) {
                                LoadLaiKhiDung();
                                LuuChiTietLuotChoi(id_cauhoi,"D","100");
                                CountDownTimer countDownTimerchinhxac = new CountDownTimer(1000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        Anhthanhcaudung();
                                        btn_D.setBackgroundResource(R.drawable.btn_traloi_dung);
                                    }

                                    @Override
                                    public void onFinish() {
                                        Finish(btn_D);
                                    }
                                }.start();
                            } else {
                                btn_D.setBackgroundResource(R.drawable.btn_khungtraloi);
                                LuuChiTietLuotChoi(id_cauhoi,"D","0");
                                LoadThua();
                            }
                        }
                    }
                }.start();
            }
        });
    }

//    public void GetJson(String url){
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    Log.d("Response", response.toString());
//                    JSONArray jsonArray = response.getJSONArray("data");
//                    for(int i=0;i<=3;i++){
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            String id = jsonObject.getString("id");
//                        String ten = jsonObject.getString("ten_linh_vuc");
//                        linh_vucArrayList.add(new Linh_Vuc(Integer.valueOf(id),ten));
//                    }
//                    if(linh_vucArrayList.size() != 0){
//                        btn_lv_1.setText(String.valueOf(linh_vucArrayList.get(0).getTenlinhVuc()));
//                        btn_lv_2.setText(String.valueOf(linh_vucArrayList.get(1).getTenlinhVuc()));
//                        btn_lv_3.setText(String.valueOf(linh_vucArrayList.get(2).getTenlinhVuc()));
//                        btn_lv_4.setText(String.valueOf(linh_vucArrayList.get(3).getTenlinhVuc()));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Response", error.toString());
//                Toast.makeText(GiaoDienChoiGame.this,error.toString(),Toast.LENGTH_LONG).show();
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//    }


    private void startTimer() {
        countDownTimerTime = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                LoadThua();
            }
        }.start();
        mTimerRunning = true;
    }

    private void pauseTimer() {
        countDownTimerTime.cancel();
        mTimerRunning = false;
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    private void updateCountDownText() {
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d", seconds);

        txt.setText(timeLeftFormatted);
    }

    private void SanSang() {
        btn_sansang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LinhVucDuocChon != null) {
                    tat_bat_cicklinhvuc(false);
                    btn_A.setBackgroundResource(R.drawable.btn_khungtraloi);
                    btn_B.setBackgroundResource(R.drawable.btn_khungtraloi);
                    btn_C.setBackgroundResource(R.drawable.btn_khungtraloi);
                    btn_D.setBackgroundResource(R.drawable.btn_khungtraloi);
                    set_bat_laiketquaBtn(true);
                    tat_battrogiup(true);
                    btn_sansang.setVisibility(View.INVISIBLE);
                    txt.setEnabled(true);
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = null;
                    if(connectivityManager !=null){
                        networkInfo = connectivityManager.getActiveNetworkInfo();
                    }
                    if(networkInfo !=null && networkInfo.isConnected()){
//
                        getSupportLoaderManager().restartLoader(0, null, GiaoDienChoiGame.this);
                    }else{
                        Toast.makeText(GiaoDienChoiGame.this,"Lỗi Internet",Toast.LENGTH_LONG).show();
                    }
                    startTimer();
                }
            }
        });
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new CauHoiLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            id_cauhoi = jsonArray.getJSONObject(0).getString("id");
            btn_noidungcauhoi.setText(jsonArray.getJSONObject(0).getString("noi_dung"));
            btn_A.setText(jsonArray.getJSONObject(0).getString("phuong_an_a"));
            btn_B.setText(jsonArray.getJSONObject(0).getString("phuong_an_b"));
            btn_C.setText(jsonArray.getJSONObject(0).getString("phuong_an_c"));
            btn_D.setText(jsonArray.getJSONObject(0).getString("phuong_an_d"));
            DapAnDung = jsonArray.getJSONObject(0).getString("dap_an");
            keymp3 = jsonArray.getJSONObject(0).getString("key");
            try {
                Resources res = getResources();
                int soundId = res.getIdentifier(keymp3, "raw", getPackageName());
                mediaPlayerCauhoi = MediaPlayer.create(this, soundId);
                mediaPlayerCauhoi.start();
            } catch (Exception e) {
                Toast.makeText(GiaoDienChoiGame.this, "No file" + keymp3, Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    private int TangDiemSo() {

        SharedPreferences.Editor editor =sharedPreferences.edit();

        int Diem = Integer.valueOf(txt.getText().toString());
        int TongDiem1cau = (Diem + 100);
        Tongdiem = TongDiem1cau;
        editor.putString("diemso",""+Tongdiem);
        editor.commit();
        return Tongdiem;
    }

    private boolean KiemTraDapAn(String dapantraloi, String ketqua) {
        if (dapantraloi.equals(ketqua)) {
            return true;
        }
        return false;
    }

    private int RanDom_one(int min, int max) {
        Random random = new Random();
        int So_one = random.nextInt(max - min + 1) + min;
        return So_one;
    }

    private int RanDom_two(int min, int max) {
        Random random = new Random();
        int two = random.nextInt(max - min + 1) + min;
        return two;
    }

    private int LayVitriDapAnDung() {
        if (DapAnDung.equals("A")) {
            return 1;
        } else if (DapAnDung.equals("B")) {
            return 2;
        } else if (DapAnDung.equals("C")) {
            return 3;
        } else {
            return 4;
        }
    }

    private void Hienthilainut5050() {
        btn_A.setVisibility(View.VISIBLE);
        btn_B.setVisibility(View.VISIBLE);
        btn_C.setVisibility(View.VISIBLE);
        btn_D.setVisibility(View.VISIBLE);
        key = true;
    }

    private void _50and50() {
        img5050.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number1 = 0;
                int number2 = 0;
                key = false;
                do {
                    int DapAn1 = RanDom_one(1, 4);
                    int DapAn2 = RanDom_two(1, 4);
                    // 4 4
                    Vitridapanchinhxac = LayVitriDapAnDung();
                    if (DapAn1 == DapAn2) {
                        DapAn1++;
                        if (DapAn1 == 5) {
                            DapAn1 = RanDom_one(1, 3);
                        }
                    }
                    if (DapAn1 == Vitridapanchinhxac) {
                        number1 = (DapAn1 + Vitridapanchinhxac) % 4;//
                        if (number1 == 0) {
                            number1 = 4;
                        }
                    } else {
                        number1 = DapAn1;
                    }

                    if (DapAn2 == Vitridapanchinhxac) {
                        number2 = (DapAn2 + Vitridapanchinhxac) % 4;//1
                        if (number2 == 0) {
                            number2 = 4;
                        }
                    } else {
                        number2 = DapAn2;
                    }
                } while (number1 == number2);
                switch (number1) {
                    case 1:
                        btn_A.setVisibility(View.INVISIBLE);
                        giatri5050so1 = 1;
                        break;
                    case 2:
                        btn_B.setVisibility(View.INVISIBLE);
                        giatri5050so1 = 2;
                        break;
                    case 3:
                        btn_C.setVisibility(View.INVISIBLE);
                        giatri5050so1 = 3;
                        break;
                    case 4:
                        btn_D.setVisibility(View.INVISIBLE);
                        giatri5050so1 = 4;
                        break;
                    default:
                        Toast.makeText(GiaoDienChoiGame.this, "Fail", Toast.LENGTH_SHORT).show();
                        break;
                }
                switch (number2) {
                    case 1:
                        btn_A.setVisibility(View.INVISIBLE);
                        giattri5050so2 = 1;
                        break;
                    case 2:
                        btn_B.setVisibility(View.INVISIBLE);
                        giattri5050so2 = 2;
                        break;
                    case 3:
                        btn_C.setVisibility(View.INVISIBLE);
                        giattri5050so2 = 3;
                        break;
                    case 4:
                        btn_D.setVisibility(View.INVISIBLE);
                        giattri5050so2 = 4;
                        break;
                    default:
                        Toast.makeText(GiaoDienChoiGame.this, "Fail", Toast.LENGTH_SHORT).show();
                        break;
                }
                img5050.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void HoiyKienKhanGia() {
        img_ykienkhangia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = 77, dieman1 = 0, dieman2 = 0;
                int CauDung = 24;
                Random random = new Random();
                int A = random.nextInt(76);
                int B = random.nextInt(sum - A);
                int C = random.nextInt(sum - A - B);
                int D = random.nextInt(sum - A - B - C);
                if (img5050.getVisibility() == View.VISIBLE || key == true) {
                    // vote 4 dap an
                    switch (LayVitriDapAnDung()) {
                        case 1:
                            A = A + CauDung;
                            break;
                        case 2:
                            B = B + CauDung;
                            break;
                        case 3:
                            C = C + CauDung;
                            break;
                        case 4:
                            D = D + CauDung;
                            break;
                    }
                    Toast.makeText(GiaoDienChoiGame.this, " " + A + " " + B + " " + C + " " + D, Toast.LENGTH_LONG).show();
                    dialog(A, B, C, D);
                    key = false;
                } else {
                    // vote 2 dap an
                    switch (giatri5050so1) {
                        case 1:
                            dieman1 = A;
                            A = 0;
                            break;
                        case 2:
                            dieman1 = B;
                            B = 0;
                            break;
                        case 3:
                            dieman1 = C;
                            C = 0;
                            break;
                        case 4:
                            dieman1 = D;
                            D = 0;
                            break;
                        default:
                            Toast.makeText(GiaoDienChoiGame.this, "Fail", Toast.LENGTH_LONG).show();
                            break;
                    }
                    switch (giattri5050so2) {
                        case 1:
                            dieman2 = A;
                            A = 0;
                            break;
                        case 2:
                            dieman2 = B;
                            B = 0;
                            break;
                        case 3:
                            dieman2 = C;
                            C = 0;
                            break;
                        case 4:
                            dieman2 = D;
                            D = 0;
                            break;
                        default:
                            Toast.makeText(GiaoDienChoiGame.this, "Fail", Toast.LENGTH_LONG).show();
                            break;
                    }
                    if (LayVitriDapAnDung() == 1) {
                        A = A + dieman1 + dieman2 + CauDung;
                    }
                    if (LayVitriDapAnDung() == 2) {
                        B = B + dieman1 + dieman2 + CauDung;
                    }
                    if (LayVitriDapAnDung() == 3) {
                        C = C + dieman1 + dieman2 + CauDung;
                    }
                    if (LayVitriDapAnDung() == 4) {
                        D = D + dieman1 + dieman2 + CauDung;
                    }
                    //Toast.makeText(GiaoDienChoiGame.this," "+dieman1+" "+dieman2+" /"+A+" "+B+" "+C+" "+D,Toast.LENGTH_LONG).show();
                    dialog(A, B, C, D);
                }
                img_ykienkhangia.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setDoCao(ImageView img, int Cau) {
        if (Cau == 0) {
            Cau = 1;
        }
        ConstraintLayout.LayoutParams lpA = (ConstraintLayout.LayoutParams) img.getLayoutParams();
        lpA.height = Cau * 4;
        img.setLayoutParams(lpA);
    }

    private void dialog(int A, int B, int C, int D) {
        Dialog dialog = new Dialog(GiaoDienChoiGame.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        ImageView imgA = dialog.findViewById(R.id.docaoA);
        ImageView imgB = dialog.findViewById(R.id.docaoB);
        ImageView imgC = dialog.findViewById(R.id.docaoC);
        ImageView imgD = dialog.findViewById(R.id.docaoD);
        setDoCao(imgA, A);
        setDoCao(imgB, B);
        setDoCao(imgC, C);
        setDoCao(imgD, D);
        Button btn_dong = dialog.findViewById(R.id.btn_dong);
        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void GoiDienNguoiThan() {
        img_GoiNguoithan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_goinguoithan(DapAnDung);
                img_GoiNguoithan.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void dialog_goinguoithan(String dapan) {
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.custom_dialog_goingoithan);
        Button btn_dong = dialog.findViewById(R.id.btn_dong);
        TextView txtKQ = dialog.findViewById(R.id.txtDapannguoithan);
        switch (LayVitriDapAnDung()) {
            case 1:
                txtKQ.setText("Đáp án A");
                break;
            case 2:
                txtKQ.setText("Đáp án B");
                break;
            case 3:
                txtKQ.setText("Đáp án C");
                break;
            case 4:
                txtKQ.setText("Đáp án D");
                break;
            default:
                txtKQ.setText("Tôi không biết");
                break;
        }

        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void TuVanTaiCho() {
        img_tuvantaicho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(GiaoDienChoiGame.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.custom_dialog_tuvantaicho);
                TextView txtnguoi1 = dialog.findViewById(R.id.txtnguoi1);
                TextView txtnguoi2 = dialog.findViewById(R.id.txtnguoi2);
                TextView txtnguoi3 = dialog.findViewById(R.id.txtnguoi3);
                Button butdong = dialog.findViewById(R.id.btn_dong);
                butdong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                int phamtramketqua = 24;
                int Vitri = 0;
                int Nguoi1 = 0, Nguoi2 = 0;
                Random random = new Random();
                Nguoi1 = random.nextInt(76);
                Nguoi2 = random.nextInt(76 - Nguoi1);

                Vitri = random.nextInt(4) + 1;
                int Vitri2 = random.nextInt(4) + 1;
                img_tuvantaicho.setVisibility(View.INVISIBLE);
                //vote 4 dap an
                if (img5050.getVisibility() == View.VISIBLE || key == true) {
                    int number1 = random.nextInt(10) + 1;
                    int number2 = random.nextInt(10) + 1;

                    if (number1 == number2) {
                        switch (LayVitriDapAnDung()) {
                            case 1:
                                txtnguoi1.setText("Tôi chọn đáp án A");
                                txtnguoi2.setText("Tôi chọn đáp án A");
                                txtnguoi3.setText("Tôi chọn đáp án A");
                                break;
                            case 2:
                                txtnguoi1.setText("Tôi chọn đáp án B");
                                txtnguoi2.setText("Tôi chọn đáp án B");
                                txtnguoi3.setText("Tôi chọn đáp án B");
                                break;
                            case 3:
                                txtnguoi1.setText("Tôi chọn đáp án C");
                                txtnguoi2.setText("Tôi chọn đáp án C");
                                txtnguoi3.setText("Tôi chọn đáp án C");
                                break;
                            case 4:
                                txtnguoi1.setText("Tôi chọn đáp án D");
                                txtnguoi2.setText("Tôi chọn đáp án D");
                                txtnguoi3.setText("Tôi chọn đáp án D");
                                break;
                            default:
                                txtnguoi1.setText("Không có đáp án");
                                break;
                        }
                    } else if (number1 != number2) {
                        switch (LayVitriDapAnDung()) {
                            case 1:
                                Nguoi1 += phamtramketqua;
                                if (Vitri2 % 2 == 0) {
                                    txtnguoi1.setText("Tôi chọn đáp án A");
                                } else {
                                    txtnguoi1.setText("Tôi chọn đáp án B");
                                }
                                if (Nguoi1 > Nguoi2) {
                                    txtnguoi2.setText("Tôi chọn đáp án A");
                                } else {
                                    txtnguoi2.setText("Tôi chọn đáp án C");
                                }
                                if (Vitri % 2 == 0) {
                                    txtnguoi3.setText("Tôi chọn đáp án A");
                                } else {
                                    txtnguoi3.setText("Tôi chọn đáp án C");
                                }
                                break;
                            case 2:
                                Nguoi1 += phamtramketqua;
                                if (Vitri2 % 2 == 0) {
                                    txtnguoi2.setText("Tôi chọn đáp án B");
                                } else {
                                    txtnguoi2.setText("Tôi chọn đáp án A");
                                }
                                if (Nguoi1 > Nguoi2) {
                                    txtnguoi1.setText("Tôi chọn đáp án B");
                                } else {
                                    txtnguoi1.setText("Tôi chọn đáp án C");
                                }
                                if (Vitri % 2 == 0) {
                                    txtnguoi3.setText("Tôi chọn đáp án A");
                                } else {
                                    txtnguoi3.setText("Tôi chọn đáp án C");
                                }
                                break;
                            case 3:
                                Nguoi1 += phamtramketqua;
                                if (Vitri2 % 2 == 0) {
                                    txtnguoi3.setText("Tôi chọn đáp án C");
                                } else {
                                    txtnguoi3.setText("Tôi chọn đáp án B");
                                }
                                if (Nguoi1 > Nguoi2) {
                                    txtnguoi2.setText("Tôi chọn đáp án C");
                                } else {
                                    txtnguoi2.setText("Tôi chọn đáp án B");
                                }
                                if (Vitri % 2 == 0) {
                                    txtnguoi1.setText("Tôi chọn đáp án A");
                                } else {
                                    txtnguoi1.setText("Tôi chọn đáp án B");
                                }
                                break;
                            case 4:
                                Nguoi1 += phamtramketqua;
                                if (Vitri2 % 2 == 0) {
                                    txtnguoi3.setText("Tôi chọn đáp án D");
                                } else {
                                    txtnguoi3.setText("Tôi chọn đáp án B");
                                }
                                if (Nguoi1 > Nguoi2) {
                                    txtnguoi2.setText("Tôi chọn đáp án D");
                                } else {
                                    txtnguoi2.setText("Tôi chọn đáp án C");
                                }
                                if (Vitri % 2 == 0) {
                                    txtnguoi1.setText("Tôi chọn đáp án D");
                                } else {
                                    txtnguoi1.setText("Tôi chọn đáp án C");
                                }
                                break;
                            default:
                                txtnguoi1.setText("Không có đáp án");
                                break;
                        }

                    }

                    key = false;
                } else {
                    int Vitridapanhien1;
                    do {
                        Vitridapanhien1 = random.nextInt(4) + 1;
                    } while (Vitridapanhien1 == giatri5050so1 || Vitridapanhien1 == giattri5050so2);

                    int Vitridapanhien2;
                    do {
                        Vitridapanhien2 = random.nextInt(4) + 1;
                    } while (Vitridapanhien2 == giatri5050so1 || Vitridapanhien2 == giattri5050so2 || Vitridapanhien2 == Vitridapanhien1);
                    Toast.makeText(GiaoDienChoiGame.this, "" + Vitridapanhien1 + " " + Vitridapanhien2, Toast.LENGTH_LONG).show();
                    switch (LayVitriDapAnDung()) {
                        case 1:
                            txtnguoi1.setText("Tôi chọn đáp án A");
                            if (Vitridapanhien1 == 1 || Vitridapanhien2 == 1) {
                                if (Vitri % 2 == 0) {
                                    txtnguoi2.setText("Tôi chọn đáp án A");
                                    LoadCauTraLoiTuvan(Vitridapanhien1, txtnguoi3);
                                } else {
                                    txtnguoi3.setText("Tôi chon đáp án A");
                                    LoadCauTraLoiTuvan(Vitridapanhien1, txtnguoi2);
                                }
                            }
                            break;
                        case 2:
                            txtnguoi1.setText("Tôi chọn đáp án B");
                            if (Vitridapanhien1 == 2 || Vitridapanhien2 == 2) {
                                if (Vitri % 2 == 0) {
                                    txtnguoi2.setText("Tôi chọn đáp án B");
                                    LoadCauTraLoiTuvan(Vitridapanhien1, txtnguoi3);
                                } else {
                                    txtnguoi3.setText("Tôi chon đáp án B");
                                    LoadCauTraLoiTuvan(Vitridapanhien1, txtnguoi2);
                                }
                            }
                            break;
                        case 3:
                            txtnguoi1.setText("Tôi chọn đáp án C");
                            if (Vitridapanhien1 == 3 || Vitridapanhien2 == 3) {
                                if (Vitri % 2 == 0) {
                                    txtnguoi2.setText("Tôi chọn đáp án C");
                                    LoadCauTraLoiTuvan(Vitridapanhien1, txtnguoi3);
                                } else {
                                    txtnguoi3.setText("Tôi chon đáp án C");
                                    LoadCauTraLoiTuvan(Vitridapanhien1, txtnguoi2);
                                }
                            }
                            break;
                        case 4:
                            txtnguoi1.setText("Tôi chọn đáp án D");
                            if (Vitridapanhien1 == 4 || Vitridapanhien2 == 4) {
                                if (Vitri % 2 == 0) {
                                    txtnguoi2.setText("Tôi chọn đáp án D");
                                    LoadCauTraLoiTuvan(Vitridapanhien1, txtnguoi3);
                                } else {
                                    txtnguoi3.setText("Tôi chon đáp án D");
                                    LoadCauTraLoiTuvan(Vitridapanhien1, txtnguoi2);
                                }
                            }
                            break;
                    }
                    butdong.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
                dialog.show();
            }
        });
    }

    private void LoadCauTraLoiTuvan(int Vitri, TextView txt) {
        switch (Vitri) {
            case 1:
                txt.setText("Tôi chọn đáp án A");
                break;
            case 2:
                txt.setText("Tôi chon đáp án B");
                break;
            case 3:
                txt.setText("Tôi chọn đáp án C");
                break;
            case 4:
                txt.setText("Tôi chọn đáp án D");
                break;
        }
    }

    private void TruMangKhiChonSai() {
        ImageView[] imageViews = new ImageView[]{
                img_mang1 = findViewById(R.id.mang1),
                img_mang2 = findViewById(R.id.mang2),
                img_mang3 = findViewById(R.id.mang3),
                img_mang4 = findViewById(R.id.mang4),
                img_mang5 = findViewById(R.id.mang5)
        };
        for (int i = 0; i <= 4; i++) {
            if (i == TongSoMang) {
                TongSoMang--;
                imageViews[i].setVisibility(View.INVISIBLE);
                break;
            }
        }
        setTextbuton();
        tat_bat_cicklinhvuc(true);
        LoadLaiKhiDung();
        if (TongSoMang == -1) {
            CapNhatLuotChoi();
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_thanh_tich);
            dialog.show();
        }
    }

    private void XacNhanMua(ImageView imageView, String txt,int Key) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_xacnhan);
        Button btn_dog = dialog.findViewById(R.id.btn_dog);
        Button btn_dong_y = dialog.findViewById(R.id.bnt_dog_y);
        TextView txtienThiGia = dialog.findViewById(R.id.txt_GiaTien);
        txtienThiGia.setText("-" + txt + " Điểm");
        btn_dong_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(Tongdiem) < Integer.valueOf(txt)){
                    Toast.makeText(GiaoDienChoiGame.this,"Không đủ điễm để mua",Toast.LENGTH_LONG).show();
                }else{
                    switch (Key){
                        case 0:
                            img5050.setVisibility(View.VISIBLE);break;
                        case 1:
                            img_ykienkhangia.setVisibility(View.VISIBLE);break;
                        case 2:
                            img_GoiNguoithan.setVisibility(View.VISIBLE);break;
                        case 3:
                            img_tuvantaicho.setVisibility(View.VISIBLE);break;
                        case 4:
                            img_tuvantaicho.setVisibility(View.VISIBLE);break;
                            default:
                                Toast.makeText(GiaoDienChoiGame.this,"Fail",Toast.LENGTH_LONG).show();break;
                    }
                    Tongdiem = (Integer.valueOf(Tongdiem) - Integer.valueOf(txt));
                    btn_diem_so.setText(""+Tongdiem);
                    Toast.makeText(GiaoDienChoiGame.this,"-"+txt,Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }
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

    private void CuaHang() {
        img_cua_hang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(GiaoDienChoiGame.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.dialog_cua_hang);
                Button btn_dog = dialog.findViewById(R.id.btn_dog);
                ImageView[] imageViews = new ImageView[]{
                        img5050_mua = dialog.findViewById(R.id.img_5050_dis),
                        img_ykienkhangia_mua = dialog.findViewById(R.id.img_hoiykienkhangia_dis),
                        img_tuvantaicho_mua = dialog.findViewById(R.id.img_tuvantaicho_dis),
                        img_GoiNguoithan_mua = dialog.findViewById(R.id.img_call),
                        img_nagluot_mua = dialog.findViewById(R.id.img_luoichoi)
                };
                imageViews[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (img5050.getVisibility() == View.VISIBLE) {
                            Toast.makeText(GiaoDienChoiGame.this, "Chưa sài xong mà mua rồi :))))", Toast.LENGTH_LONG).show();
                        } else {
                            XacNhanMua(imageViews[0], "100",0);
                        }
                    }
                });

                imageViews[1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (img_ykienkhangia.getVisibility() == View.VISIBLE) {
                            Toast.makeText(GiaoDienChoiGame.this, "Chưa sài xong mà mua rồi :))))", Toast.LENGTH_LONG).show();
                        } else {
                            XacNhanMua(imageViews[1], "150",1);
                        }
                    }
                });
                imageViews[2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (img_tuvantaicho.getVisibility() == View.VISIBLE) {
                            Toast.makeText(GiaoDienChoiGame.this, "Chưa sài xong mà mua rồi :))))", Toast.LENGTH_LONG).show();
                        } else {
                            XacNhanMua(imageViews[2], "175",2);
                        }
                    }
                });

                imageViews[3].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (img_GoiNguoithan.getVisibility() == View.VISIBLE) {
                            Toast.makeText(GiaoDienChoiGame.this, "Chưa sài xong mà mua rồi :))))", Toast.LENGTH_LONG).show();
                        } else {
                            XacNhanMua(imageViews[3], "250",3);
                        }
                    }
                });

                imageViews[4].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TongSoMang == 4) {
                            Toast.makeText(GiaoDienChoiGame.this, "Full mạng rồi mà mua gì :))))", Toast.LENGTH_LONG).show();
                        } else {
                            XacNhanMua(imageViews[4], "300",4);
                        }
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
    private void Mua_Kc() {
        btn_kimcuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(GiaoDienChoiGame.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.dialog_mua_kc);
                Button[] btn_kc = new Button[]{
                        btn_kc_200 = dialog.findViewById(R.id.btn_kc_200),
                        btn_kc_400 = dialog.findViewById(R.id.btn_kc_400),
                        btn_kc_800 = dialog.findViewById(R.id.btn_kc_800),
                        btn_kc_1500 = dialog.findViewById(R.id.btn_kc_1500),
                        btn_kc_2500 = dialog.findViewById(R.id.btn_kc_2500),
                        btn_kc_5000 = dialog.findViewById(R.id.btn_kc_5000),
                };
                Button btn_dong = dialog.findViewById(R.id.btn_dog);
                goiGreditArrayList.clear();
                new AsynTalkGoiCredit(btn_kc).execute("http://10.0.3.2:8000/api/lay_gredit/");
                btn_dong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();

                    }
                });
                dialog.show();
            }
        });
    }
}