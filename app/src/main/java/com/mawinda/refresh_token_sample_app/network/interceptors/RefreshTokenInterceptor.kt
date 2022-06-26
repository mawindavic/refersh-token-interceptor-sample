package com.mawinda.refresh_token_sample_app.network.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RefreshTokenInterceptor() : Interceptor {

    private val accessToken by lazy {
        // TODO: Add logic to get token from local storage
        ""
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
        // TODO: Add Logic to store token locally

        return ""
    }


}