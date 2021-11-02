package com.example.flowerparty;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppInterface {
    @FormUrlEncoded
    @POST("JournalSelect.php")
    Call<List<Journal>> selectJournal(
            @Field("userID") String userID
    );

    @FormUrlEncoded
    @POST("JournalInsert.php")
    Call<Journal> insertJournal(
            @Field("title") String title,
            @Field("content") String content,
            @Field("userID") String userID
    );

    @FormUrlEncoded
    @POST("JournalUpdate.php")
    Call<Journal> updateJournal(
            @Field("idx") int idx,
            @Field("title") String title,
            @Field("content") String content
    );

    @FormUrlEncoded
    @POST("JournalDelete.php")
    Call<Journal> deleteJournal(
            @Field("idx") int idx,
            @Field("userID") String userID
    );

}
