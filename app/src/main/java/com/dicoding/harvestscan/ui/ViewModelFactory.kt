//package com.dicoding.harvestscan.ui
//
//import android.app.Application
//import android.content.Context
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.dicoding.harvestscan.data.UserRepository
//import com.dicoding.harvestscan.data.local.room.HarvestScanDao
//import com.dicoding.harvestscan.di.Injection
//import com.dicoding.harvestscan.ui.auth.login.LoginViewModel
//import com.dicoding.harvestscan.ui.auth.register.RegisterViewModel
//import com.dicoding.harvestscan.ui.home.HomeViewModel
//import com.dicoding.harvestscan.ui.myplant.MyPlantViewModel
//import com.dicoding.harvestscan.ui.scan.ScanViewModel
//import com.dicoding.harvestscan.viewmodel.ReminderViewModel
//
//class ViewModelFactory(
//    private val repository: UserRepository,
//    private val application: Application,
//    private val harvestScanDao: HarvestScanDao // Tambahkan ini
//) : ViewModelProvider.NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
//                LoginViewModel(repository) as T
//            }
//            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
//                RegisterViewModel(repository) as T
//            }
//            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
//                HomeViewModel(repository) as T
//            }
//            modelClass.isAssignableFrom(MyPlantViewModel::class.java) -> {
//                MyPlantViewModel(application) as T
//            }
//            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
//                ScanViewModel() as T
//            }
//            modelClass.isAssignableFrom(ReminderViewModel::class.java) -> {
//                ReminderViewModel(harvestScanDao) as T // Modifikasi ini
//            }
//            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//        }
//    }
//
//    companion object {
//        @Volatile
//        private var INSTANCE: ViewModelFactory? = null
//        @JvmStatic
//        fun getInstance(context: Context): ViewModelFactory {
//            if (INSTANCE == null) {
//                synchronized(ViewModelFactory::class.java) {
//                    val application = context.applicationContext as Application
//                    val harvestScanDao = Injection.provideRepository(context).harvestScanDao() // Tambahkan ini
//                    INSTANCE = ViewModelFactory(Injection.provideRepository(context), application, harvestScanDao) // Modifikasi ini
//                }
//            }
//            return INSTANCE as ViewModelFactory
//        }
//    }
//}
