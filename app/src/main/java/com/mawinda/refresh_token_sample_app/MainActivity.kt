package com.mawinda.refresh_token_sample_app

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.status.observe(this){
            Timber.i("Response: $it")
        }

        findViewById<TextView>(R.id.test_btn).setOnClickListener {
            viewModel.login(mail = "fieldenrono97@gmail.com", password = "string")
        }

    }
}