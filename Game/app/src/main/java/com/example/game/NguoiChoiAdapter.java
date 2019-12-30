package com.example.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NguoiChoiAdapter extends RecyclerView.Adapter <NguoiChoiAdapter.NguoiChoiViewHolder> {

    private final ArrayList<NguoiChoi> mListNguoiChoi;
    private LayoutInflater mInflater;

    public NguoiChoiAdapter(Context context, ArrayList<NguoiChoi> mListNguoiChoi) {
        this.mListNguoiChoi = mListNguoiChoi;
        mInflater =LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public NguoiChoiAdapter.NguoiChoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.lst_bang_xep_hang, parent, false);
        return new NguoiChoiViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiChoiViewHolder holder, int position) {
        NguoiChoi nguoiChoi = mListNguoiChoi.get(position);
        holder.TenDangNhapTextView.setText(nguoiChoi.getTen_dang_nhap());
        holder.DiemTextView.setText(nguoiChoi.getDiem_cao_nhat()+"");

    }

    @Override
    public int getItemCount() {
        return mListNguoiChoi.size();
    }

    public class NguoiChoiViewHolder extends RecyclerView.ViewHolder {
        final TextView TenDangNhapTextView;
        final TextView DiemTextView;
        final NguoiChoiAdapter nguoiChoiAdapter;
        public NguoiChoiViewHolder(@NonNull View itemView, NguoiChoiAdapter nguoiChoiAdapter) {
            super(itemView);
            this.TenDangNhapTextView = itemView.findViewById(R.id.textView_ten);
            this.DiemTextView = itemView.findViewById(R.id.textView_diemcaonhat);
            this.nguoiChoiAdapter = nguoiChoiAdapter;
        }
    }
}
