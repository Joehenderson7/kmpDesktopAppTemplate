package ui.components

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Tests for the split pane components to identify any remaining issues with dragging behavior.
 * 
 * This test focuses on the mathematical calculations used in the drag handling logic
 * rather than the UI interactions, which would require the Compose UI testing framework.
 */
class SplitPaneTest {

    /**
     * Test the calculation logic for horizontal split pane dragging.
     * 
     * This test simulates the mathematical calculations that happen when dragging
     * the divider in a horizontal split pane.
     */
    @Test
    fun testHorizontalSplitPaneCalculations() {
        // Given: Initial parameters
        val initialSplitFraction = 0.5f
        val parentWidth = 1000f
        val dragAmountX = 100f
        val scalingFactor = 0.5f

        // When: Calculating the new split fraction
        // 1. Calculate the current position in pixels
        val currentPosition = parentWidth * initialSplitFraction
        // 2. Scale down the drag amount
        val scaledDragAmount = dragAmountX * scalingFactor
        // 3. Add the scaled drag amount to get the new position
        val newPosition = currentPosition + scaledDragAmount
        // 4. Convert back to a fraction
        val newSplitFraction = newPosition / parentWidth

        // Then: Verify the calculations
        assertEquals(500f, currentPosition, "Current position should be half the parent width")
        assertEquals(50f, scaledDragAmount, "Scaled drag amount should be half the original drag amount")
        assertEquals(550f, newPosition, "New position should be current position plus scaled drag amount")
        assertEquals(0.55f, newSplitFraction, "New split fraction should be 0.55")

        // Verify that the scaling factor reduces the movement
        val unscaledNewPosition = currentPosition + dragAmountX
        val unscaledNewSplitFraction = unscaledNewPosition / parentWidth
        assertEquals(600f, unscaledNewPosition, "Unscaled new position should be current position plus full drag amount")
        assertEquals(0.6f, unscaledNewSplitFraction, "Unscaled new split fraction should be 0.6")

        // Verify that the scaling factor is working as expected
        assertTrue(newSplitFraction < unscaledNewSplitFraction, "Scaled split fraction should be less than unscaled")

        // Use a delta to account for floating-point precision
        val difference = unscaledNewSplitFraction - newSplitFraction
        val expectedDifference = 0.05f
        val delta = 0.0001f // Allow for small floating-point precision differences

        assertTrue(
            difference >= expectedDifference - delta && difference <= expectedDifference + delta,
            "Difference should be approximately $expectedDifference (was $difference)"
        )
    }

    /**
     * Test the calculation logic for vertical split pane dragging.
     * 
     * This test simulates the mathematical calculations that happen when dragging
     * the divider in a vertical split pane.
     */
    @Test
    fun testVerticalSplitPaneCalculations() {
        // Given: Initial parameters
        val initialSplitFraction = 0.5f
        val parentHeight = 800f
        val dragAmountY = 80f
        val scalingFactor = 0.5f

        // When: Calculating the new split fraction
        // 1. Calculate the current position in pixels
        val currentPosition = parentHeight * initialSplitFraction
        // 2. Scale down the drag amount
        val scaledDragAmount = dragAmountY * scalingFactor
        // 3. Add the scaled drag amount to get the new position
        val newPosition = currentPosition + scaledDragAmount
        // 4. Convert back to a fraction
        val newSplitFraction = newPosition / parentHeight

        // Then: Verify the calculations
        assertEquals(400f, currentPosition, "Current position should be half the parent height")
        assertEquals(40f, scaledDragAmount, "Scaled drag amount should be half the original drag amount")
        assertEquals(440f, newPosition, "New position should be current position plus scaled drag amount")
        assertEquals(0.55f, newSplitFraction, "New split fraction should be 0.55")

        // Verify that the scaling factor reduces the movement
        val unscaledNewPosition = currentPosition + dragAmountY
        val unscaledNewSplitFraction = unscaledNewPosition / parentHeight
        assertEquals(480f, unscaledNewPosition, "Unscaled new position should be current position plus full drag amount")
        assertEquals(0.6f, unscaledNewSplitFraction, "Unscaled new split fraction should be 0.6")

        // Verify that the scaling factor is working as expected
        assertTrue(newSplitFraction < unscaledNewSplitFraction, "Scaled split fraction should be less than unscaled")

        // Use a delta to account for floating-point precision
        val difference = unscaledNewSplitFraction - newSplitFraction
        val expectedDifference = 0.05f
        val delta = 0.0001f // Allow for small floating-point precision differences

        assertTrue(
            difference >= expectedDifference - delta && difference <= expectedDifference + delta,
            "Difference should be approximately $expectedDifference (was $difference)"
        )
    }

    /**
     * Test the calculation logic for different scaling factors.
     * 
     * This test explores how different scaling factors affect the dragging behavior.
     */
    @Test
    fun testDifferentScalingFactors() {
        // Given: Initial parameters
        val initialSplitFraction = 0.5f
        val parentWidth = 1000f
        val dragAmountX = 100f

        // Test with different scaling factors
        val scalingFactors = listOf(0.25f, 0.5f, 0.75f, 1.0f)
        val expectedNewSplitFractions = listOf(0.525f, 0.55f, 0.575f, 0.6f)

        for (i in scalingFactors.indices) {
            val scalingFactor = scalingFactors[i]
            val expectedNewSplitFraction = expectedNewSplitFractions[i]

            // When: Calculating the new split fraction with this scaling factor
            val currentPosition = parentWidth * initialSplitFraction
            val scaledDragAmount = dragAmountX * scalingFactor
            val newPosition = currentPosition + scaledDragAmount
            val newSplitFraction = newPosition / parentWidth

            // Then: Verify the calculation
            assertEquals(expectedNewSplitFraction, newSplitFraction, 
                "With scaling factor $scalingFactor, new split fraction should be $expectedNewSplitFraction")

            println("[DEBUG_LOG] Scaling factor: $scalingFactor, New split fraction: $newSplitFraction")
        }
    }

    /**
     * Test the clamping logic to ensure split fractions stay within bounds.
     */
    @Test
    fun testClampingLogic() {
        // Given: Some extreme drag amounts
        val initialSplitFraction = 0.5f
        val parentWidth = 1000f
        val largeDragAmount = 500f  // This would push the split fraction to 0.75 with scaling factor 0.5
        val negativeDragAmount = -500f  // This would push the split fraction to 0.25 with scaling factor 0.5
        val veryLargeDragAmount = 1000f  // This would push the split fraction to 1.0 with scaling factor 0.5
        val veryNegativeDragAmount = -1000f  // This would push the split fraction to 0.0 with scaling factor 0.5
        val scalingFactor = 0.5f

        // When/Then: Calculate and verify clamped split fractions

        // Large drag amount
        val largePosition = parentWidth * initialSplitFraction + largeDragAmount * scalingFactor
        val largeSplitFraction = (largePosition / parentWidth).coerceIn(0.1f, 0.9f)
        assertEquals(0.75f, largeSplitFraction, "Large drag should result in split fraction of 0.75")

        // Negative drag amount
        val negativePosition = parentWidth * initialSplitFraction + negativeDragAmount * scalingFactor
        val negativeSplitFraction = (negativePosition / parentWidth).coerceIn(0.1f, 0.9f)
        assertEquals(0.25f, negativeSplitFraction, "Negative drag should result in split fraction of 0.25")

        // Very large drag amount (should be clamped)
        val veryLargePosition = parentWidth * initialSplitFraction + veryLargeDragAmount * scalingFactor
        val veryLargeSplitFraction = (veryLargePosition / parentWidth).coerceIn(0.1f, 0.9f)
        assertEquals(0.9f, veryLargeSplitFraction, "Very large drag should be clamped to 0.9")

        // Very negative drag amount (should be clamped)
        val veryNegativePosition = parentWidth * initialSplitFraction + veryNegativeDragAmount * scalingFactor
        val veryNegativeSplitFraction = (veryNegativePosition / parentWidth).coerceIn(0.1f, 0.9f)
        assertEquals(0.1f, veryNegativeSplitFraction, "Very negative drag should be clamped to 0.1")

        println("[DEBUG_LOG] Clamping test results:")
        println("[DEBUG_LOG] Large drag: $largeSplitFraction")
        println("[DEBUG_LOG] Negative drag: $negativeSplitFraction")
        println("[DEBUG_LOG] Very large drag (clamped): $veryLargeSplitFraction")
        println("[DEBUG_LOG] Very negative drag (clamped): $veryNegativeSplitFraction")
    }
}
