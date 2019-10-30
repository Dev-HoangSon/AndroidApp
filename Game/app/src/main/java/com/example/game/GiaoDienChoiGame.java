package com.example.game;




import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


public class GiaoDienChoiGame extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    ImageView img_xoay, img5050, img_ykienkhangia;
    Button btn_cau_1,btn_cau_2,btn_cau_3,btn_cau_4,btn_cau_5,btn_cau_6,btn_cau_7,btn_cau_8,btn_cau_9,btn_cau_10;
    Button btn_A , btn_B , btn_C , btn_D , btn_sansang , btn_noidungcauhoi;
    TextView txt ;
    ArrayList<Linh_Vuc>  linh_vucArrayList = new ArrayList<>();
   public static  String LinhVucDuocChon = "" , LinhVucChoi = "";
    String[] arr={"200","300","500","1000","2000","3500","6000","9000","13000","20000"};
    String id_cauhoi = "";
    String DapAnDung = "" ;
    boolean key = true , keyKQ = false;
    int SoDapAnChinhXac = 0 , Vitridapanchinhxac = 0 , giatri5050so1 = 0 , giattri5050so2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_giao_dien_choi_game);
           if(getSupportLoaderManager().getLoader(0)!=null) {
           getSupportLoaderManager().initLoader(0, null, this);
       }

        LoadGiaoDien();
        Click_A();
        Click_B();
        Click_C();
        Click_D();
        SanSang();
        _50and50();
        HoiyKienKhanGia();
    }

    private void LoadDiemThuog(){
        btn_cau_1 = findViewById(R.id.btn_cau_1);
        btn_cau_2 = findViewById(R.id.btn_cau_2);
        btn_cau_3 = findViewById(R.id.btn_cau_3);
        btn_cau_4 = findViewById(R.id.btn_cau_4);
        btn_cau_5 = findViewById(R.id.btn_cau_5);
        btn_cau_6 = findViewById(R.id.btn_cau_6);
        btn_cau_7 = findViewById(R.id.btn_cau_7);
        btn_cau_8 = findViewById(R.id.btn_cau_8);
        btn_cau_9 = findViewById(R.id.btn_cau_9);
        btn_cau_10 = findViewById(R.id.btn_cau_10);


        btn_cau_1.setText(arr[0]);
        btn_cau_2.setText(arr[1]);
        btn_cau_3.setText(arr[2]);
        btn_cau_4.setText(arr[3]);
        btn_cau_5.setText(arr[4]);
        btn_cau_6.setText(arr[5]);
        btn_cau_7.setText(arr[6]);
        btn_cau_8.setText(arr[7]);
        btn_cau_9.setText(arr[8]);
        btn_cau_10.setText(arr[9]);
    }

    public void LoadGiaoDien(){
        txt = findViewById(R.id.time_giay);
        img5050 = findViewById(R.id.img_5050);
        img_ykienkhangia = findViewById(R.id.img_ykienkhangia);
        btn_noidungcauhoi = findViewById(R.id.btn_noidung);
        btn_A = findViewById(R.id.btn_A);
        btn_B = findViewById(R.id.btn_B);
        btn_C = findViewById(R.id.btn_C);
        btn_D = findViewById(R.id.btn_D);
        btn_sansang = findViewById(R.id.btn_sansang);
        btn_sansang.setEnabled(false);
        img5050.setEnabled(false);
        img_ykienkhangia.setEnabled(false);
        img_xoay = findViewById(R.id.img_xoay_choi);
        txt.setEnabled(false);
        LoadDiemThuog();
      //  MediaPlayer  mediaPlayer = MediaPlayer.create(this,R.raw.start_1);
        //mediaPlayer.start();
        Animation animationalphaxoay = AnimationUtils.loadAnimation(GiaoDienChoiGame.this,R.anim.img_xoay);
        img_xoay.startAnimation(animationalphaxoay);
        // private static final String BASE_URL =  "http://10.0.2.2:8001/api/linh-vuc/"; // AVD
        GetJson("http://10.0.3.2:8001/api/linh-vuc/");
    }


    private void set_laiketquaBtn(){
        btn_A.setEnabled(true);
        btn_B.setEnabled(true);
        btn_C.setEnabled(true);
        btn_D.setEnabled(true);
    }
    private void Finish(Button btn){
        Hienthilainut5050();
        keyKQ = true;
      //  LoadThoiGian20s(21000,1000);
       // txt.setText(""+20);
        getSupportLoaderManager().restartLoader(0, null, GiaoDienChoiGame.this);
        btn.setBackgroundResource(R.drawable.btn_khungtraloi);
        SoDapAnChinhXac++;
        TangDiemSo(SoDapAnChinhXac);
        set_laiketquaBtn();
    }
    private void Click_A(){
        btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimer countDownTimer = new CountDownTimer(1000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn_A.setBackgroundResource(R.drawable.btn_traloi_sai);
                    }
                    @Override
                    public void onFinish() {
                        btn_B.setEnabled(false);
                        btn_C.setEnabled(false);
                        btn_D.setEnabled(false);
                        btn_sansang.setEnabled(true);
                        if(LinhVucDuocChon == ""){
                            LinhVucDuocChon = String.valueOf(linh_vucArrayList.get(0).getId());
                            LinhVucChoi = LinhVucDuocChon;
                        }else{
                            LinhVucDuocChon = LinhVucChoi;
                        }
                        if(btn_sansang.getVisibility() == View.INVISIBLE){
                            if(KiemTraDapAn(btn_A.getText().toString(),DapAnDung)){
                                CountDownTimer countDownTimerchinhxac = new CountDownTimer(1000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        btn_A.setBackgroundResource(R.drawable.btn_traloi_dung);
                                    }
                                    @Override
                                    public void onFinish() {
                                        Finish(btn_A);
                                    }
                                }.start();

                            }else{
                                Toast.makeText(GiaoDienChoiGame.this,"Sai",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }.start();

            }
        });
    }
    private void Click_B(){
        btn_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimer countDownTimer = new CountDownTimer(1000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn_B.setBackgroundResource(R.drawable.btn_traloi_sai);
                    }
                    @Override
                    public void onFinish() {
                        btn_A.setEnabled(false);
                        btn_C.setEnabled(false);
                        btn_D.setEnabled(false);
                        btn_sansang.setEnabled(true);
                        if(LinhVucDuocChon == ""){
                            LinhVucDuocChon = String.valueOf(linh_vucArrayList.get(1).getId());
                            LinhVucChoi = LinhVucDuocChon;
                        }else{
                            LinhVucDuocChon = LinhVucChoi;
                        }
                        if(btn_sansang.getVisibility() == View.INVISIBLE){
                            if(KiemTraDapAn(btn_B.getText().toString(),DapAnDung)){
                                CountDownTimer countDownTimerchinhxac = new CountDownTimer(1000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        btn_B.setBackgroundResource(R.drawable.btn_traloi_dung);
                                    }
                                    @Override
                                    public void onFinish() {
                                        Finish(btn_B);
                                    }
                                }.start();

                            }else{
                                Toast.makeText(GiaoDienChoiGame.this,"Sai",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }.start();

            }
        });
    }
    private void Click_C(){
        btn_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimer countDownTimer = new CountDownTimer(1000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn_C.setBackgroundResource(R.drawable.btn_traloi_sai);
                    }
                    @Override
                    public void onFinish() {
                        btn_A.setEnabled(false);
                        btn_B.setEnabled(false);
                        btn_D.setEnabled(false);
                        btn_sansang.setEnabled(true);
                        if(LinhVucDuocChon == ""){
                            LinhVucDuocChon = String.valueOf(linh_vucArrayList.get(2).getId());
                            LinhVucChoi = LinhVucDuocChon;
                        }else{
                            LinhVucDuocChon = LinhVucChoi;
                        }
                        if(btn_sansang.getVisibility() == View.INVISIBLE){
                            if(KiemTraDapAn(btn_C.getText().toString(),DapAnDung)){
                                CountDownTimer countDownTimerchinhxac = new CountDownTimer(1000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        btn_C.setBackgroundResource(R.drawable.btn_traloi_dung);
                                    }
                                    @Override
                                    public void onFinish() {
                                        Finish(btn_C);
                                    }
                                }.start();
                            }else{
                                Toast.makeText(GiaoDienChoiGame.this,"Sai",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }.start();
            }
        });
    }
    private void Click_D(){
        btn_D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimer countDownTimer = new CountDownTimer(1000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn_D.setBackgroundResource(R.drawable.btn_traloi_sai);
                    }
                    @Override
                    public void onFinish() {
                        btn_A.setEnabled(false);
                        btn_B.setEnabled(false);
                        btn_C.setEnabled(false);
                        btn_sansang.setEnabled(true);
                        if(LinhVucDuocChon == ""){
                            LinhVucDuocChon = String.valueOf(linh_vucArrayList.get(3).getId());
                            LinhVucChoi = LinhVucDuocChon;
                        }else{
                            LinhVucDuocChon = LinhVucChoi;
                        }
                        if(btn_sansang.getVisibility() == View.INVISIBLE){
                            if(KiemTraDapAn(btn_D.getText().toString(),DapAnDung)){
                                CountDownTimer countDownTimerchinhxac = new CountDownTimer(1000,1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        btn_C.setBackgroundResource(R.drawable.btn_traloi_dung);
                                    }
                                    @Override
                                    public void onFinish() {
                                       Finish(btn_D);
                                    }
                                }.start();
                            }else{
                                Toast.makeText(GiaoDienChoiGame.this,"Sai",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }.start();

            }


        });
    }

    public void GetJson(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for(int i=0;i<=3;i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String ten = jsonObject.getString("ten_linh_vuc");
                        linh_vucArrayList.add(new Linh_Vuc(Integer.valueOf(id),ten));
                    }
                    if(linh_vucArrayList.size() != 0){
                        btn_A.setText(String.valueOf(linh_vucArrayList.get(0).getTenlinhVuc()));
                        btn_B.setText(String.valueOf(linh_vucArrayList.get(1).getTenlinhVuc()));
                        btn_C.setText(String.valueOf(linh_vucArrayList.get(2).getTenlinhVuc()));
                        btn_D.setText(String.valueOf(linh_vucArrayList.get(3).getTenlinhVuc()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GiaoDienChoiGame.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void LoadThua(){
        Toast.makeText(GiaoDienChoiGame.this,"Thua chưa chọn đáp án",Toast.LENGTH_LONG).show();
    }

    private void LoadThoiGian20s(int tongtime , int giay){
        CountDownTimer countDownTimer = new CountDownTimer(tongtime,giay) {
            @Override
            public void onTick(long millisUntilFinished) {
                int Time = Integer.valueOf(txt.getText().toString());
                    Time--;
                    txt.setText(String.valueOf(Time));
                    if(keyKQ == true){
                        cancel();
                        keyKQ = false;
                    }
            }
            @Override
            public void onFinish() {
                LoadThua();
            }
        }.start();
    }
    private void SanSang(){
        btn_sansang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LinhVucDuocChon != null){
                //    LoadThoiGian20s(21000,1000);
                    btn_A.setBackgroundResource(R.drawable.btn_khungtraloi);
                    btn_B.setBackgroundResource(R.drawable.btn_khungtraloi);
                    btn_C.setBackgroundResource(R.drawable.btn_khungtraloi);
                    btn_D.setBackgroundResource(R.drawable.btn_khungtraloi);
                    btn_A.setEnabled(true);
                    btn_B.setEnabled(true);
                    btn_C.setEnabled(true);
                    btn_D.setEnabled(true);
                    img5050.setEnabled(true);
                    img_ykienkhangia.setEnabled(true);
                    btn_sansang.setVisibility(View.INVISIBLE);
                    txt.setEnabled(true);
                    getSupportLoaderManager().restartLoader(0, null, GiaoDienChoiGame.this);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    private void TangDiemSo(int i){
        switch (i){
            case 1: btn_cau_1.setBackgroundResource(R.drawable.reward_chinhxac);break;
            case 2: btn_cau_2.setBackgroundResource(R.drawable.reward_chinhxac);break;
            case 3: btn_cau_3.setBackgroundResource(R.drawable.reward_chinhxac);break;
            case 4: btn_cau_4.setBackgroundResource(R.drawable.reward_chinhxac);break;
            case 5: btn_cau_5.setBackgroundResource(R.drawable.reward_chinhxac);break;
            case 6: btn_cau_6.setBackgroundResource(R.drawable.reward_chinhxac);break;
            case 7: btn_cau_7.setBackgroundResource(R.drawable.reward_chinhxac);break;
            case 8: btn_cau_8.setBackgroundResource(R.drawable.reward_chinhxac);break;
            case 9: btn_cau_9.setBackgroundResource(R.drawable.reward_chinhxac);break;
            case 10: btn_cau_10.setBackgroundResource(R.drawable.reward_chinhxac);
                Toast.makeText(GiaoDienChoiGame.this,"Bạn đã chiến thắng",Toast.LENGTH_LONG).show();break;
        }
    }

    private boolean KiemTraDapAn(String dapantraloi , String ketqua){
        if(dapantraloi.equals(ketqua)){
            return true;
        }
        return false;
    }

    private int RanDom_one(int min , int max){
        Random random = new Random();
        int So_one =  random.nextInt(max-min+1)+min;
        return So_one;
    }
    private int RanDom_two(int min , int max){
        Random random = new Random();
        int two =  random.nextInt(max-min+1)+min;
        return two;
    }
    private int LayVitriDapAnDung(){
          if(DapAnDung.equals(btn_A.getText().toString())){
              return 1;
          }else if(DapAnDung.equals(btn_B.getText().toString())){
              return 2;
          }else if(DapAnDung.equals(btn_C.getText().toString())){
              return 3;
        }else {
              return 4;
          }
    }
    private  void Hienthilainut5050(){
        btn_A.setVisibility(View.VISIBLE);
        btn_B.setVisibility(View.VISIBLE);
        btn_C.setVisibility(View.VISIBLE);
        btn_D.setVisibility(View.VISIBLE);
        key = true ;
    }
    private void _50and50(){
        img5050.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number1 = 0;
                int number2 = 0;
                key = false;
                do{
                    int DapAn1 = RanDom_one(1,4);
                    int DapAn2 = RanDom_two(1,4);
                    // 4 4
                    Vitridapanchinhxac = LayVitriDapAnDung();
                    if(DapAn1 == DapAn2){
                        DapAn1++;
                        if(DapAn1 == 5){
                            DapAn1 = RanDom_one(1,3);
                        }
                    }
                    if(DapAn1 == Vitridapanchinhxac){
                        number1 = (DapAn1 + Vitridapanchinhxac) % 4;//
                        if(number1 == 0){
                            number1 = 4;
                        }
                    }else{
                        number1 = DapAn1;
                    }

                    if(DapAn2 == Vitridapanchinhxac){
                        number2  = (DapAn2 + Vitridapanchinhxac) % 4;//1
                        if(number2 == 0){
                            number2 = 4;
                        }
                    }else{
                        number2 = DapAn2;
                    }
                }while (number1 == number2);
                switch (number1){
                    case 1 : btn_A.setVisibility(View.INVISIBLE);
                            giatri5050so1 = 1;break;
                    case 2 : btn_B.setVisibility(View.INVISIBLE);
                        giatri5050so1 = 2;break;
                    case 3 : btn_C.setVisibility(View.INVISIBLE);
                        giatri5050so1 = 3;break;
                    case 4 : btn_D.setVisibility(View.INVISIBLE);
                        giatri5050so1 = 4;break;
                    default:
                        Toast.makeText(GiaoDienChoiGame.this,"Fail",Toast.LENGTH_SHORT).show();
                        break;
                }
                switch (number2){
                    case 1 : btn_A.setVisibility(View.INVISIBLE);
                        giattri5050so2 = 1;break;
                    case 2 : btn_B.setVisibility(View.INVISIBLE);
                        giattri5050so2 = 2;break;
                    case 3 : btn_C.setVisibility(View.INVISIBLE);
                        giattri5050so2 = 3;break;
                    case 4 : btn_D.setVisibility(View.INVISIBLE);
                        giattri5050so2 = 4;break;
                    default:
                    Toast.makeText(GiaoDienChoiGame.this,"Fail",Toast.LENGTH_SHORT).show();
                    break;
                }
              //  Toast.makeText(GiaoDienChoiGame.this,""+DapAn1+" "+DapAn2+""+number1+" "+number2,Toast.LENGTH_LONG).show();img5050.setVisibility(View.INVISIBLE);
           img5050.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void HoiyKienKhanGia(){
        img_ykienkhangia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = 77 , dieman1 = 0 , dieman2 = 0;
                int CauDung = 24;
                Random random = new Random();
                int A = random.nextInt(76);
                int B = random.nextInt(sum - A);
                int C = random.nextInt(sum - A - B);
                int D = random.nextInt(sum - A - B - C);
                if(img5050.getVisibility() == View.VISIBLE || key == true){
                    // vote 4 dap an
                        switch (LayVitriDapAnDung()){
                            case 1: A = A+CauDung;break;
                            case 2: B = B+CauDung;break;
                            case 3: C = C+CauDung;break;
                            case 4: D = D+CauDung;break;
                        }
                        Toast.makeText(GiaoDienChoiGame.this," "+A+" "+B+" "+C+" "+D,Toast.LENGTH_LONG).show();
                        dialog(A,B,C,D);
                        key = false;
                }else{
                    // vote 2 dap an
                    switch (giatri5050so1){
                        case 1: dieman1 = A;
                            A = 0;
                            break;
                        case 2: dieman1 = B;
                            B = 0;
                            break;
                        case 3: dieman1 = C;
                            C = 0;
                            break;
                        case 4: dieman1 = D;
                            D = 0;break;
                        default:
                            Toast.makeText(GiaoDienChoiGame.this,"Fail",Toast.LENGTH_LONG).show();break;
                    }
                    switch (giattri5050so2){
                        case 1: dieman2 = A;
                            A = 0;
                           break;
                       case 2: dieman2 = B;
                            B = 0;
                            break;
                        case 3: dieman2 = C;
                            C = 0;
                            break;
                        case 4: dieman2 = D;
                            D = 0;break;
                        default:
                            Toast.makeText(GiaoDienChoiGame.this,"Fail",Toast.LENGTH_LONG).show();break;
                    }
                  if(LayVitriDapAnDung() == 1){
                      A = A + dieman1 + dieman2+CauDung;
                  } if(LayVitriDapAnDung() == 2){
                      B = B + dieman1 + dieman2+CauDung;
                  } if(LayVitriDapAnDung() == 3){
                      C = C + dieman1 + dieman2+CauDung;
                  }if(LayVitriDapAnDung() == 4){
                      D = D + dieman1 + dieman2+CauDung;
                  }
                    //Toast.makeText(GiaoDienChoiGame.this," "+dieman1+" "+dieman2+" /"+A+" "+B+" "+C+" "+D,Toast.LENGTH_LONG).show();
                    dialog(A,B,C,D);
                }
                img_ykienkhangia.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void setDoCao(ImageView img , int Cau){
        if(Cau == 0){
            Cau=1;
        }
        ConstraintLayout.LayoutParams lpA = (ConstraintLayout.LayoutParams) img.getLayoutParams();
        lpA.height = Cau*4 ;
        img.setLayoutParams(lpA);
    }
    private  void dialog(int A , int B , int C ,int D){
        Dialog dialog = new Dialog(GiaoDienChoiGame.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        ImageView imgA = dialog.findViewById(R.id.docaoA);
        ImageView imgB = dialog.findViewById(R.id.docaoB);
        ImageView imgC = dialog.findViewById(R.id.docaoC);
        ImageView imgD = dialog.findViewById(R.id.docaoD);
        setDoCao(imgA , A);
        setDoCao(imgB , B);
        setDoCao(imgC , C);
        setDoCao(imgD , D);
        Button btn_dong = dialog.findViewById(R.id.btn_dong);
        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
