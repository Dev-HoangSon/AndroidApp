package com.example.game;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MuaCreditAdapter extends RecyclerView.Adapter <MuaCreditAdapter.MuaCreditViewHolder>{
    private final ArrayList<GoiGredit> mListDSLS;
    private LayoutInflater mInflater;
    Context mua;
    public MuaCreditAdapter(Context context, ArrayList<GoiGredit> mListDSLS) {
        this.mListDSLS = mListDSLS;
        mua = context;
        mInflater =LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MuaCreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.lst_item_credit, parent, false);
        return new MuaCreditAdapter.MuaCreditViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MuaCreditViewHolder holder, int position) {
        GoiGredit lsCredit = mListDSLS.get(position);
        holder.CreditTextView.setText(lsCredit.getCredit());
        holder.SoTienTextView.setText(lsCredit.getSoTien());
        holder.TGTextView.setText(lsCredit.getTengoi());
    }

    @Override
    public int getItemCount() {
        return mListDSLS.size();
    }

    public class MuaCreditViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView TGTextView;
        final TextView SoTienTextView;
        final TextView CreditTextView;
        final MuaCreditAdapter creditAdapter;
        public MuaCreditViewHolder(@NonNull View itemView, MuaCreditAdapter creditAdapter) {
            super(itemView);
            this.TGTextView = itemView.findViewById(R.id.txtTengoi);
            this.SoTienTextView = itemView.findViewById(R.id.txtsoTien);
            this.CreditTextView = itemView.findViewById(R.id.txtcredit);
            this.creditAdapter =creditAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            GoiGredit lsCredit = mListDSLS.get(mPosition);
            String id = lsCredit.getId()+"";
            String cr = lsCredit.getCredit();
            String sotien = lsCredit.getSoTien();
            Dialog dialog = new Dialog(mua);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.custom_dialog_mua_credit);
            Button btn_dangnhap = dialog.findViewById(R.id.btn_dialog_dangnhap);
            Button btn_dong = dialog.findViewById(R.id.btn_dialog_dong);

            btn_dangnhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ConnectivityManager connectivityManager = (ConnectivityManager) mua.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = null;
                    if(connectivityManager !=null)
                    {
                        networkInfo = connectivityManager.getActiveNetworkInfo();
                    }
                    if(networkInfo != null && networkInfo.isConnected()) {
                        new LayGoiCredit() {
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
                                        Toast.makeText(mua, "Mua thành công!",Toast.LENGTH_SHORT).show();
                                        MainActivity.creditHienTai += Integer.parseInt(cr);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.execute("mua-goi-credit", "POST",MainActivity.token,id, cr, sotien);
                    }

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
    }
}
