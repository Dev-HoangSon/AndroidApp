package com.example.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LuotChoiAdapter extends RecyclerView.Adapter <LuotChoiAdapter.LSChoiViewHolder>{
    private final ArrayList<LSChoi> mListDSLS;
    private LayoutInflater mInflater;

    public LuotChoiAdapter(Context context, ArrayList<LSChoi> mListDSLS) {
        this.mListDSLS = mListDSLS;
        mInflater =LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LSChoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.lst_lich_su_choi, parent, false);
        return new LSChoiViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull LSChoiViewHolder holder, int position) {
        LSChoi lsCredit = mListDSLS.get(position);
        holder.DiemTextView.setText(lsCredit.getDiem()+"");
        holder.SoCauTextView.setText(lsCredit.getSo_cau()+"");
        holder.TGTextView.setText(lsCredit.getNgay_gio());
    }

    @Override
    public int getItemCount() {
        return mListDSLS.size();
    }

    public class LSChoiViewHolder extends RecyclerView.ViewHolder {
        final TextView TGTextView;
        final TextView SoCauTextView;
        final TextView DiemTextView;
        final LuotChoiAdapter creditAdapter;
        public LSChoiViewHolder(@NonNull View itemView, LuotChoiAdapter creditAdapter) {
            super(itemView);
            this.TGTextView = itemView.findViewById(R.id.txtNgayGio);
            this.SoCauTextView = itemView.findViewById(R.id.txtSoCau);
            this.DiemTextView = itemView.findViewById(R.id.txtDiem);
            this.creditAdapter =creditAdapter;
        }
    }
}
