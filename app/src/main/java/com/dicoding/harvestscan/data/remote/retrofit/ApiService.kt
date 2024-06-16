package com.dicoding.harvestscan.data.remote.retrofit

import com.dicoding.harvestscan.data.remote.request.ForgotPasswordRequest
import com.dicoding.harvestscan.data.remote.request.LoginRequest
import com.dicoding.harvestscan.data.remote.request.RegisterRequest
import com.dicoding.harvestscan.data.remote.response.ForgotPasswordResponse
import com.dicoding.harvestscan.data.remote.response.LoginResponse
import com.dicoding.harvestscan.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun userRegister(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun userLogin(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("reset-password")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): ForgotPasswordResponse
}