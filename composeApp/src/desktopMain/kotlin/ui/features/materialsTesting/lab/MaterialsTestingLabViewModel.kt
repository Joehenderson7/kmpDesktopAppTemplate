package ui.features.materialsTesting.lab

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * ViewModel for the Materials Testing Lab feature.
 * Manages the state and business logic for soil proctor tests.
 */
class MaterialsTestingLabViewModel {
    
    // State for the list of soil proctors
    private val _soilProctors = mutableStateOf(SampleSoilProctors.sampleData)
    val soilProctors get() = _soilProctors.value
    
    // State for the currently selected soil proctor
    private val _selectedProctor = mutableStateOf<SoilProctor?>(null)
    val selectedProctor get() = _selectedProctor.value
    
    // State for search query
    var searchQuery by mutableStateOf("")
        private set
    
    /**
     * Updates the search query and filters the soil proctors list.
     */
    fun updateSearchQuery(query: String) {
        searchQuery = query
        // In a real app, this would filter the list based on the query
        // For now, we're just using the sample data
    }
    
    /**
     * Selects a soil proctor from the list.
     */
    fun selectProctor(proctor: SoilProctor) {
        _selectedProctor.value = proctor
    }
    
    /**
     * Clears the currently selected soil proctor.
     */
    fun clearSelection() {
        _selectedProctor.value = null
    }
    
    /**
     * Returns filtered soil proctors based on the current search query.
     * For demonstration purposes, this is a simple filter on project name and sample ID.
     */
    fun getFilteredProctors(): List<SoilProctor> {
        if (searchQuery.isBlank()) {
            return soilProctors
        }
        
        val lowercaseQuery = searchQuery.lowercase()
        return soilProctors.filter { proctor ->
            proctor.projectName.lowercase().contains(lowercaseQuery) ||
            proctor.sampleId.lowercase().contains(lowercaseQuery)
        }
    }
}