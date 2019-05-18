package com.example.wallto.network.services;

import com.example.wallto.model.User;
import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthService {
    @GET("login")
    Single<User> signIn(@Query("login") String login,
                        @Query("password") String password,
                        @Query("app") String app);

    @GET("register")
    Single<User> signUp(@Query("login") String login,
                        @Query("password") String password,
                        @Query("app") String app);
}