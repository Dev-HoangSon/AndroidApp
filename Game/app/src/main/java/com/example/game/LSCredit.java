package com.example.game;

public class LSCredit {
    private int id;
    private int nguoi_choi_id;
    private int ten_goi_credit;
    private int credit;
    private int so_tien;
    private String thoi_gian;

    public LSCredit(int ten_goi_credit, int credit, int so_tien, String thoi_gian) {
        this();
        this.ten_goi_credit = ten_goi_credit;
        this.credit = credit;
        this.so_tien = so_tien;
        this.thoi_gian = thoi_gian;
    }

    public LSCredit() {
        this.id = 0;
        this.nguoi_choi_id = 0;
        this.ten_goi_credit = 0;
        this.credit = 0;
        this.so_tien = 0;
        this.thoi_gian = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNguoi_choi_id() {
        return nguoi_choi_id;
    }

    public void setNguoi_choi_id(int nguoi_choi_id) {
        this.nguoi_choi_id = nguoi_choi_id;
    }

    public int getTen_goi_credit() {
        return ten_goi_credit;
    }

    public void setTen_goi_credit(int ten_goi_credit) {
        this.ten_goi_credit = ten_goi_credit;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getSo_tien() {
        return so_tien;
    }

    public void setSo_tien(int so_tien) {
        this.so_tien = so_tien;
    }

    public String getThoi_gian() {
        return thoi_gian;
    }

    public void setThoi_gian(String thoi_gian) {
        this.thoi_gian = thoi_gian;
    }
}
