package com.example.game;

public class GoiGredit {
    String credit;
    String SoTien;
    String tengoi;
    private int id;

    public GoiGredit(String credit, String soTien) {
        id = 0;
        tengoi = "";
        this.credit = credit;
        SoTien = soTien;
    }
    public GoiGredit(int id, String tengoi, String credit, String soTien) {
        this.id = id;
        this.tengoi = tengoi;
        this.credit = credit;
        SoTien = soTien;
    }
    public GoiGredit(String tengoi, String credit, String soTien) {
        this.id = 0;
        this.tengoi = tengoi;
        this.credit = credit;
        SoTien = soTien;
    }
    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getSoTien() {
        return SoTien;
    }

    public void setSoTien(String soTien) {
        SoTien = soTien;
    }

    public String getTengoi() {
        return tengoi;
    }

    public void setTengoi(String tengoi) {
        this.tengoi = tengoi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}