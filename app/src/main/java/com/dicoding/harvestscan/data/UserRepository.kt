package com.dicoding.harvestscan.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.pref.UserModel
import com.dicoding.harvestscan.data.pref.UserPreference
import com.dicoding.harvestscan.data.remote.response.RegisterResponse
import com.dicoding.harvestscan.data.remote.retrofit.ApiService
import com.dicoding.harvestscan.data.remote.Result
import com.dicoding.harvestscan.data.remote.request.ForgotPasswordRequest
import com.dicoding.harvestscan.data.remote.request.LoginRequest
import com.dicoding.harvestscan.data.remote.request.RegisterRequest
import com.dicoding.harvestscan.data.remote.response.ErrorResponse
import com.dicoding.harvestscan.data.remote.response.ForgotPasswordResponse
import com.dicoding.harvestscan.data.remote.response.LoginResponse
import com.dicoding.harvestscan.data.remote.response.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val context: Context
) {

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun registerUser(email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val request = RegisterRequest(email, password)
            val response = apiService.userRegister(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(context.getString(R.string.email_already_in_use)))
        }
    }

    fun loginUser(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val request = LoginRequest(email, password)
            val response = apiService.userLogin(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(context.getString(R.string.invalid_email_or_password)))
        }
    }
    fun forgotPassword(email: String): LiveData<Result<ForgotPasswordResponse>> = liveData {
        emit(Result.Loading)
        try {
            val request = ForgotPasswordRequest(email)
            val response = apiService.forgotPassword(request)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(handleHttpException(e))
        }
    }

    private fun handleHttpException(e: HttpException): Result.Error {
        val jsonInString = e.response()?.errorBody()?.string()
        val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
        val errorMessage = errorBody.message
        val errorText= "An error occurred"
        return Result.Error(errorMessage ?: errorText)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference, context: Context
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference, context)
            }.also { instance = it }
    }
}