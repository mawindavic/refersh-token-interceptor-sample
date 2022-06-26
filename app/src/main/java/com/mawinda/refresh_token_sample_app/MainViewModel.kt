package com.mawinda.refresh_token_sample_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mawinda.refresh_token_sample_app.data.response.ResponseStatus
import com.mawinda.refresh_token_sample_app.network.repository.MerchantRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val repo = MerchantRepository(application)

    private val _status: MutableLiveData<ResponseStatus> = MutableLiveData()
    val status: LiveData<ResponseStatus>
        get() = _status

    fun login(mail: String, password: String) {
        viewModelScope.launch {
            Timber.i("Login Started")
            _status.value = repo.login(mail, password)
        }
    }
}