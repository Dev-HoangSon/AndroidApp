package com.example.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CreditAdapter extends RecyclerView.Adapter <CreditAdapter.CreditViewHolder>{
    private final ArrayList<LSCredit> mListDSLS;
    private LayoutInflater mInflater;

    public CreditAdapter(Context context, ArrayList<LSCredit> mListDSLS) {
        this.mListDSLS = mListDSLS;
        mInflater =LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.lst_lich_su_mua_credit, parent, false);
        return new CreditViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditViewHolder holder, int position) {
        LSCredit lsCredit = mListDSLS.get(position);
        holder.GiaTienTextView.setText(lsCredit.getSo_tien()+"");
        holder.TenGoiTextView.setText(lsCredit.getTen_goi_credit()+"");
        holder.CreditTextView.setText(lsCredit.getCredit()+"");
        holder.TGTextView.setText(lsCredit.getThoi_gian());
    }

    @Override
    public int getItemCount() {
        return mListDSLS.size();
    }

    public class CreditViewHolder extends RecyclerView.ViewHolder {
        final TextView TGTextView;
        final TextView TenGoiTextView;
        final TextView CreditTextView;
        final TextView GiaTienTextView;
        final CreditAdapter creditAdapter;
        public CreditViewHolder(@NonNull View itemView, CreditAdapter creditAdapter) {
            super(itemView);
            this.TGTextView = itemView.findViewById(R.id.txtNgay);
            this.CreditTextView = itemView.findViewById(R.id.txtCredit);
            this.TenGoiTextView = itemView.findViewById(R.id.txtTenGoi);
            this.GiaTienTextView = itemView.findViewById(R.id.txtGiaTien);
            this.creditAdapter =creditAdapter;
        }
    }
}
