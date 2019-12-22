package com.example.game.Retrofix2;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataClient {
    @Multipart
    @POST("UploadImg")
    Call<String> UploadPhoto(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("Dangky")
    Call<String> Insertdata(@Field("ten_dang_nhap") String ten_dang_nhap,
                            @Field("mat_khau") String mat_khau,
                            @Field("email") String email,
                            @Field("hinh_dai_dien") String hinh_dai_dien);

    @FormUrlEncoded
    @POST("xoa-tai-khoan")
    Call<String> DeleteData(@Field("email") String email,
                            @Field("img") String img);

}
