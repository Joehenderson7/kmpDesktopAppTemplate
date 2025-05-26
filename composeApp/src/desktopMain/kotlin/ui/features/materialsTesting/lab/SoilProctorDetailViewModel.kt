package ui.features.materialsTesting.lab

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * ViewModel for the Soil Proctor Detail feature.
 * Manages the state and business logic for displaying soil proctor details.
 */
class SoilProctorDetailViewModel {
    
    // State for the currently displayed soil proctor
    private val _proctor = mutableStateOf<SoilProctor?>(null)
    val proctor get() = _proctor.value
    
    // State for loading status
    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading.value
    
    // State for error message
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage get() = _errorMessage.value
    
    /**
     * Loads a soil proctor by its ID.
     * In a real app, this would fetch the proctor from a repository or API.
     * For now, we're just using the sample data.
     */
    fun loadProctor(proctorId: String) {
        _isLoading.value = true
        _errorMessage.value = null
        
        try {
            // Simulate network delay
            // In a real app, this would be an async call to a repository
            val foundProctor = SampleSoilProctors.sampleData.find { it.id == proctorId }
            
            if (foundProctor != null) {
                _proctor.value = foundProctor
            } else {
                _errorMessage.value = "Proctor not found with ID: $proctorId"
            }
        } catch (e: Exception) {
            _errorMessage.value = "Error loading proctor: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * Clears the currently displayed soil proctor.
     */
    fun clearProctor() {
        _proctor.value = null
        _errorMessage.value = null
    }
}