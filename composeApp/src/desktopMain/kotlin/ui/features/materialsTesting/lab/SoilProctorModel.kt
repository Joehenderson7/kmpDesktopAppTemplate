package ui.features.materialsTesting.lab

/**
 * Data model representing a soil proctor test.
 */
data class SoilProctor(
    val id: String,
    val projectName: String,
    val sampleId: String,
    val date: String,
    val location: String,
    val maxDryDensity: Double,
    val optimumMoistureContent: Double,
    val testMethod: String,
    val technician: String,
    val status: String
)

/**
 * Provides sample data for soil proctor tests.
 */
object SampleSoilProctors {
    val sampleData = listOf(
        SoilProctor(
            id = "SP001",
            projectName = "Highway 101 Expansion",
            sampleId = "H101-S01",
            date = "2023-05-15",
            location = "Mile Marker 45",
            maxDryDensity = 125.4,
            optimumMoistureContent = 12.8,
            testMethod = "ASTM D698",
            technician = "John Smith",
            status = "Completed"
        ),
        SoilProctor(
            id = "SP002",
            projectName = "Downtown Office Building",
            sampleId = "DOB-S03",
            date = "2023-06-02",
            location = "Foundation Area B",
            maxDryDensity = 118.7,
            optimumMoistureContent = 14.2,
            testMethod = "ASTM D1557",
            technician = "Maria Rodriguez",
            status = "Completed"
        ),
        SoilProctor(
            id = "SP003",
            projectName = "Riverside Park",
            sampleId = "RP-S05",
            date = "2023-06-10",
            location = "Playground Area",
            maxDryDensity = 110.5,
            optimumMoistureContent = 16.5,
            testMethod = "ASTM D698",
            technician = "David Chen",
            status = "In Progress"
        ),
        SoilProctor(
            id = "SP004",
            projectName = "Highway 101 Expansion",
            sampleId = "H101-S08",
            date = "2023-06-18",
            location = "Mile Marker 47",
            maxDryDensity = 127.1,
            optimumMoistureContent = 11.9,
            testMethod = "ASTM D1557",
            technician = "John Smith",
            status = "Completed"
        ),
        SoilProctor(
            id = "SP005",
            projectName = "Mountain View Residential",
            sampleId = "MVR-S02",
            date = "2023-07-05",
            location = "Lot 23",
            maxDryDensity = 115.8,
            optimumMoistureContent = 15.3,
            testMethod = "ASTM D698",
            technician = "Sarah Johnson",
            status = "Pending Review"
        )
    )
}