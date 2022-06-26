package com.mawinda.refresh_token_sample_app.network.interceptors

import android.content.Context
import com.mawinda.refresh_token_sample_app.data.SessionManger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RefreshTokenInterceptor(private val context: Context) : Interceptor {

    private val accessToken by lazy {
        SessionManger.accessToken(context)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.addAuthorisation(accessToken)

        val response = chain.proceed(request)

        return when {
            response.code == 401 -> {
                val newToken = refreshToken()
                val newRequest = chain.addAuthorisation(newToken)
                chain.proceed(newRequest)
            }
            else -> response
        }

    }

    private fun Interceptor.Chain.addAuthorisation(token: String): Request {
        return this.request().newBuilder().addHeader("Authorization", token).build()
    }

    private fun refreshToken(): String {
        // TODO: Add logic to refresh token
        val newToken = ""

        return newToken.also {
            SessionManger.saveAccessToken(context = context, accessToken = it)
        }
    }


}