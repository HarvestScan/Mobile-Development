package com.dicoding.harvestscan.data.remote.retrofit

import com.dicoding.harvestscan.data.remote.request.LoginRequest
import com.dicoding.harvestscan.data.remote.request.RegisterRequest
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
}