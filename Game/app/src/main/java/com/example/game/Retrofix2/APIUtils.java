package com.example.game.Retrofix2;

public class APIUtils {
    public static final String BASE_URL="http://10.0.2.2:8000/api/";

    // gui va nhan du lieu chua tring interface
    public static DataClient getData(){
        return RetrofixClient.getClient(BASE_URL).create(DataClient.class);
    }
}
