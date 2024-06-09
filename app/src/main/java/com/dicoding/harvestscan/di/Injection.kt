package com.dicoding.harvestscan.di

import android.content.Context
import com.dicoding.harvestscan.data.UserRepository
import com.dicoding.harvestscan.data.pref.UserPreference
import com.dicoding.harvestscan.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}