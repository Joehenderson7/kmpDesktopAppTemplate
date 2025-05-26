package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.features.materialsTesting.lab.MaterialsTestingLabUi
import ui.features.materialsTesting.lab.SoilProctorDetailUi
import ui.features.materialsTesting.lab.CreateSoilProctorUi
import ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    AppTheme(
        darkTheme = true,
    ) {
        Surface(
            Modifier.fillMaxSize()
        ) {
            var isRailExpanded by remember { mutableStateOf(true) }
            // State to track the current screen
            var currentScreen by remember { mutableStateOf("MaterialsTesting") }

            Scaffold(
                topBar = {
                    AppTopBar(
                        onMenuClick = { isRailExpanded = !isRailExpanded }
                    )
                }
            ) { paddingValues ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Collapsable Navigation Rail
                    CollapsableNavigationRail(
                        isExpanded = isRailExpanded,
                        currentScreen = currentScreen,
                        onScreenSelected = { screen ->
                            currentScreen = screen
                        }
                    )

                    // Main content with panels
                    PanelLayout(currentScreen = currentScreen)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar(onMenuClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("KMP Desktop App") },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle Navigation Rail"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Composable
private fun CollapsableNavigationRail(
    isExpanded: Boolean,
    currentScreen: String,
    onScreenSelected: (String) -> Unit
) {
    AnimatedVisibility(
        visible = isExpanded,
        enter = slideInHorizontally { -it },
        exit = slideOutHorizontally { -it }
    ) {
        NavigationRail(
            modifier = Modifier.fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Projects navigation item
                RailItem(
                    text = "Projects",
                    icon = Icons.Filled.Folder,
                    selected = currentScreen == "Projects",
                    onClick = { onScreenSelected("Projects") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Materials Testing navigation item
                RailItem(
                    text = "Materials Testing",
                    icon = Icons.Filled.Science,
                    selected = currentScreen == "MaterialsTesting",
                    onClick = { onScreenSelected("MaterialsTesting") }
                )
            }
        }
    }
}

@Composable
private fun PanelLayout(currentScreen: String) {
    // State for the selected proctor ID
    var selectedProctorId by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Vertical panel (left) - Soil Proctors List
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .width(250.dp)
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            ) {
                when (currentScreen) {
                    "MaterialsTesting" -> {
                        MaterialsTestingLabUi(
                            navigateToProctorDetails = { proctorId ->
                                selectedProctorId = proctorId
                            },
                            navigateToCreateProctor = { /* Handle navigation to create proctor */ },
                            navigateToSettings = { /* Handle navigation to settings */ }
                        )
                    }
                    "Projects" -> {
                        // Placeholder for Projects screen
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Projects List")
                        }
                    }
                    else -> {
                        // Default placeholder
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Select a screen from the navigation rail")
                        }
                    }
                }
            }
        }

        // Two horizontal panels stacked (right)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            // Top horizontal panel - Soil Proctor Details
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        currentScreen == "MaterialsTesting" && selectedProctorId != null -> {
                            // Display the selected proctor's details
                            SoilProctorDetailUi(proctorId = selectedProctorId!!)
                        }
                        currentScreen == "MaterialsTesting" -> {
                            // Display a placeholder when no proctor is selected
                            Text("Select a soil proctor to view details")
                        }
                        currentScreen == "Projects" -> {
                            // Placeholder for Project details
                            Text("Select a project to view details")
                        }
                        else -> {
                            // Default placeholder
                            Text("Select a screen from the navigation rail")
                        }
                    }
                }
            }

            // Bottom horizontal panel - Create New Soil Proctor
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when (currentScreen) {
                        "MaterialsTesting" -> {
                            // Create New Soil Proctor UI
                            CreateSoilProctorUi(
                                onSave = { /* Handle save */ },
                                onCancel = { /* Handle cancel */ }
                            )
                        }
                        "Projects" -> {
                            // Placeholder for Project creation
                            Text("Create New Project")
                        }
                        else -> {
                            // Default placeholder
                            Text("Select a screen from the navigation rail")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RailItem(
    text: String, 
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(imageVector = icon, contentDescription = text) },
        label = { Text(text) }
    )
}
