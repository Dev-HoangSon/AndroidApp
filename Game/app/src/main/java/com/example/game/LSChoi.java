package com.example.game;

public class LSChoi {
    private int id;
    private int so_cau;
    private int diem;
    private String ngay_gio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LSChoi(int so_cau, int diem, String ngay_gio) {
        this.id = 0;
        this.so_cau = so_cau;
        this.diem = diem;
        this.ngay_gio = ngay_gio;
    }

    public int getSo_cau() {
        return so_cau;
    }

    public void setSo_cau(int so_cau) {
        this.so_cau = so_cau;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }

    public String getNgay_gio() {
        return ngay_gio;
    }

    public void setNgay_gio(String ngay_gio) {
        this.ngay_gio = ngay_gio;
    }
}
