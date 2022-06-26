package com.mawinda.refresh_token_sample_app.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.mawinda.refresh_token_sample_app.BuildConfig

object SessionManger {

    private const val ACCESS_TOKEN = "access_token"

    private fun Context.manager(): SharedPreferences {
        val mainKey = MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        return EncryptedSharedPreferences.create(
            this.applicationContext,
            BuildConfig.APPLICATION_ID.plus("_session"),
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun accessToken(context: Context): String {
        return context.manager().getString(ACCESS_TOKEN, "")!!
    }

    fun saveAccessToken(context: Context, accessToken: String) {
        context.manager().edit().putString(ACCESS_TOKEN, accessToken).apply()
    }
}