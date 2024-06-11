package com.dicoding.harvestscan.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.harvestscan.data.UserRepository
import com.dicoding.harvestscan.data.remote.response.RegisterResponse
import com.dicoding.harvestscan.data.remote.Result

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> = _registerResult

    fun registerUser(email: String, password: String) {
        repository.registerUser(email, password).observeForever {
            _registerResult.value = it
        }
    }
}