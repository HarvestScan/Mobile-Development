package com.dicoding.harvestscan.di

import android.content.Context
import com.dicoding.harvestscan.data.UserRepository
import com.dicoding.harvestscan.data.pref.UserPreference
import com.dicoding.harvestscan.data.pref.dataStore
import com.dicoding.harvestscan.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        // val database = PlantDatabase.getDatabase(context)
        return UserRepository.getInstance(apiService, pref)
    }
}