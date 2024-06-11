package com.dicoding.harvestscan.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.harvestscan.data.UserRepository
import com.dicoding.harvestscan.data.remote.Result
import com.dicoding.harvestscan.data.remote.response.LoginResponse
import com.dicoding.harvestscan.data.remote.response.User
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel(){
    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult


    fun loginUser(email: String, password: String) {
        repository.loginUser(email, password).observeForever {
            _loginResult.value = it
        }
    }

    fun saveSession(user: User) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}