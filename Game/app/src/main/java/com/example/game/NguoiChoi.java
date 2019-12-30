package com.example.game;

public class NguoiChoi {
    private  int id;
    private String ten_dang_nhap;
    private int diem_cao_nhat;

    public NguoiChoi(int id, String ten_dang_nhap, int diem_cao_nhat)
    {
        this.id = id;
        this.ten_dang_nhap = ten_dang_nhap;
        this.diem_cao_nhat = diem_cao_nhat;

    }

    public NguoiChoi( String ten_dang_nhap, int diem_cao_nhat)
    {
        this.id = 0;
        this.ten_dang_nhap = ten_dang_nhap;
        this.diem_cao_nhat = diem_cao_nhat;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_dang_nhap() {
        return ten_dang_nhap;
    }

    public void setTen_dang_nhap(String ten_dang_nhap) {
        this.ten_dang_nhap = ten_dang_nhap;
    }

    public int getDiem_cao_nhat() {
        return diem_cao_nhat;
    }

    public void setDiem_cao_nhat(int diem_cao_nhat) {
        this.diem_cao_nhat = diem_cao_nhat;
    }
}
