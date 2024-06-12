import android.app.Application
import androidx.lifecycle.*
import com.dicoding.harvestscan.data.PlantRepository
import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.data.local.room.PlantDatabase
import kotlinx.coroutines.launch

class PlantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlantRepository
    val allPlants: LiveData<List<Plant>>

    init {
        val plantDao = PlantDatabase.getDatabase(application).plantDao()
        repository = PlantRepository(plantDao)
        allPlants = repository.allPlants.asLiveData()
    }

    fun insert(plant: Plant) {
        viewModelScope.launch {
            repository.insert(plant)
        }
    }

    fun delete(plant: Plant) {
        viewModelScope.launch {
            repository.delete(plant)
        }
    }
}

class PlantViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlantViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
