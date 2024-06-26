package com.dicoding.harvestscan.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.UserRepository
import com.dicoding.harvestscan.di.Injection
import com.dicoding.harvestscan.ui.auth.login.LoginViewModel
import com.dicoding.harvestscan.ui.auth.register.RegisterViewModel
import com.dicoding.harvestscan.ui.home.HomeViewModel
import com.dicoding.harvestscan.ui.menumyplant.myplant.MyPlantViewModel
import com.dicoding.harvestscan.ui.menumyplant.reminder.ReminderViewModel
import com.dicoding.harvestscan.ui.scan.ScanViewModel

class ViewModelFactory(
    private val repository: UserRepository,
    private val application: Application,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel() as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(application) as T
            }
            modelClass.isAssignableFrom(MyPlantViewModel::class.java) -> {
                MyPlantViewModel(application) as T
            }
            modelClass.isAssignableFrom(ReminderViewModel::class.java) -> {
                ReminderViewModel(application) as T
            }
            else -> throw IllegalArgumentException(application.getString(R.string.unknown_viewmodel_class) + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val application = context.applicationContext as Application
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context), application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
