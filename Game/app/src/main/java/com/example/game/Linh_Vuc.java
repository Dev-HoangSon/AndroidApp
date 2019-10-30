package com.example.game;

public class Linh_Vuc {
    int id;
    String TenlinhVuc;

    public Linh_Vuc(int id, String tenlinhVuc) {
        this.id = id;
        TenlinhVuc = tenlinhVuc;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenlinhVuc() {
        return TenlinhVuc;
    }

    public void setTenlinhVuc(String tenlinhVuc) {
        TenlinhVuc = tenlinhVuc;
    }
}
