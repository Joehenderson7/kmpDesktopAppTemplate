package ui.features.materialsTesting.lab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * Main UI component for the Soil Proctors list view.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoilProctorsListUi(
    viewModel: MaterialsTestingLabViewModel,
    onProctorSelected: (SoilProctor) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Header
        Text(
            text = "Soil Proctors",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Search bar
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Search by project or sample ID") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            singleLine = true
        )
        
        // List of soil proctors
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.getFilteredProctors()) { proctor ->
                SoilProctorItem(
                    proctor = proctor,
                    onClick = { onProctorSelected(proctor) },
                    isSelected = viewModel.selectedProctor == proctor
                )
                Divider()
            }
        }
    }
}

/**
 * Individual item in the soil proctors list.
 */
@Composable
private fun SoilProctorItem(
    proctor: SoilProctor,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        // Project name and sample ID
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = proctor.projectName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            
            Text(
                text = proctor.sampleId,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Location and date
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Location: ${proctor.location}",
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            
            Text(
                text = proctor.date,
                style = MaterialTheme.typography.bodySmall,
                color = contentColor
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Test results
        Text(
            text = "Max Dry Density: ${proctor.maxDryDensity} pcf | OMC: ${proctor.optimumMoistureContent}%",
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Status chip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Method: ${proctor.testMethod}",
                style = MaterialTheme.typography.bodySmall,
                color = contentColor
            )
            
            StatusChip(status = proctor.status)
        }
    }
}
