package com.example.game;

public class GoiGredit {
    String credit;
    String SoTien;

    public GoiGredit(String credit, String soTien) {
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
}
