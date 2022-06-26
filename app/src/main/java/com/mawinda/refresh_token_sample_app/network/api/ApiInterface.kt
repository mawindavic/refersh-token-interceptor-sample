package com.mawinda.refresh_token_sample_app.network.api

import android.content.Context
import com.mawinda.refresh_token_sample_app.BuildConfig
import com.mawinda.refresh_token_sample_app.data.response.LoginResponse
import com.mawinda.refresh_token_sample_app.network.interceptors.ConnectionInterceptor
import com.mawinda.refresh_token_sample_app.network.interceptors.RefreshTokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @POST("auth/token/refresh/")
    @FormUrlEncoded
    suspend fun refreshToken(@Field("refresh") refreshToken: String): Response<LoginResponse>

    @POST("auth/login/")
    @FormUrlEncoded
   suspend fun login(
        @Field("email") mail: String,
        @Field("password") password: String
    ): Response<LoginResponse>


    companion object {
        operator fun invoke(context: Context, isAuthorised: Boolean): ApiInterface {
            val okhttpClientBuilder =
                OkHttpClient.Builder().addInterceptor(ConnectionInterceptor(context))
            if(BuildConfig.DEBUG){
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okhttpClientBuilder.addInterceptor(loggingInterceptor)
            }

            if (isAuthorised) {
                okhttpClientBuilder.addInterceptor(RefreshTokenInterceptor(context))
            }

            val okHttpClient = okhttpClientBuilder.build()
            return Retrofit.Builder().client(okHttpClient).baseUrl("https://one-stocks.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiInterface::class.java)
        }


    }

}