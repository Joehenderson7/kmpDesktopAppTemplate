package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

/**
 * A vertical split pane component that allows resizing the top and bottom panels.
 * 
 * @param modifier The modifier to be applied to the component
 * @param splitFraction The initial position of the divider as a fraction (0.0-1.0) of the total height
 * @param onSplitChanged Callback that is invoked when the divider position changes
 * @param firstPane Composable for the top panel
 * @param secondPane Composable for the bottom panel
 */
@Composable
fun VerticalSplitPane(
    modifier: Modifier = Modifier,
    splitFraction: Float = 0.5f,
    onSplitChanged: (Float) -> Unit,
    firstPane: @Composable () -> Unit,
    secondPane: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(splitFraction)
            ) {
                firstPane()
            }

            // Draggable divider
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .pointerInput(splitFraction) {
                        var initialFraction = splitFraction
                        var dragStartHeight = 0f

                        detectDragGestures(
                            onDragStart = { offset ->
                                initialFraction = splitFraction
                                dragStartHeight = this.size.height.toFloat()
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val parentHeight = this.size.height.toFloat()
                                if (parentHeight > 0) {
                                    // Calculate the initial position in pixels
                                    val initialPosition = dragStartHeight * initialFraction
                                    // Calculate the cumulative drag amount
                                    val scaledDragAmount = dragAmount.y * 0.5f
                                    // Add the scaled drag amount to the initial position
                                    val newPosition = initialPosition + scaledDragAmount
                                    // Convert to a fraction
                                    val newSplitFraction = newPosition / parentHeight
                                    // Clamp the split fraction between 0.1 and 0.9
                                    onSplitChanged(newSplitFraction.coerceIn(0.1f, 0.9f))
                                }
                            }
                        )
                    }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                secondPane()
            }
        }
    }
}

/**
 * A horizontal split pane component that allows resizing the left and right panels.
 * 
 * @param modifier The modifier to be applied to the component
 * @param splitFraction The initial position of the divider as a fraction (0.0-1.0) of the total width
 * @param onSplitChanged Callback that is invoked when the divider position changes
 * @param firstPane Composable for the left panel
 * @param secondPane Composable for the right panel
 */
@Composable
fun HorizontalSplitPane(
    modifier: Modifier = Modifier,
    splitFraction: Float = 0.5f,
    onSplitChanged: (Float) -> Unit,
    firstPane: @Composable () -> Unit,
    secondPane: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(splitFraction)
            ) {
                firstPane()
            }

            // Draggable divider
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .pointerInput(splitFraction) {
                        var initialFraction = splitFraction
                        var dragStartWidth = 0f

                        detectDragGestures(
                            onDragStart = { offset ->
                                initialFraction = splitFraction
                                dragStartWidth = this.size.width.toFloat()
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val parentWidth = this.size.width.toFloat()
                                if (parentWidth > 0) {
                                    // Calculate the initial position in pixels
                                    val initialPosition = dragStartWidth * initialFraction
                                    // Calculate the cumulative drag amount
                                    val scaledDragAmount = dragAmount.x * 0.5f
                                    // Add the scaled drag amount to the initial position
                                    val newPosition = initialPosition + scaledDragAmount
                                    // Convert to a fraction
                                    val newSplitFraction = newPosition / parentWidth
                                    // Clamp the split fraction between 0.1 and 0.9
                                    onSplitChanged(newSplitFraction.coerceIn(0.1f, 0.9f))
                                }
                            }
                        )
                    }
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                secondPane()
            }
        }
    }
}
