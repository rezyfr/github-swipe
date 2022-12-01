package io.rezyfr.githubswipe.utils

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

@ExperimentalSwipeableCardApi
fun Modifier.swipeableCard(
    state: SwipeableCardState,
    onSwipeCancel: () -> Unit = {},
) = pointerInput(Unit) {
    coroutineScope {
        detectDragGestures(
            onDragCancel = {
                launch {
                    state.reset()
                    onSwipeCancel()
                }
            },
            onDrag = { change, offset ->
                launch {
                    val original = state.offset.targetValue
                    val sum = original + offset
                    val newValue = Offset(
                        x = sum.x.coerceIn(-state.maxWidth, state.maxWidth),
                        y = sum.y.coerceIn(-state.maxHeight, state.maxHeight)
                    )
                    if (change.positionChange() != Offset.Zero) change.consume()
                    state.drag(newValue.x, newValue.y)
                }
            },
            onDragEnd = {
                when {
                    abs(state.offset.targetValue.x) < state.maxWidth / 4 -> {
                        launch {
                            state.reset()
                            onSwipeCancel()
                        }
                    }
                    state.offset.targetValue.x > 0 -> {
                        launch {
                            state.swipe(Direction.Right)
                        }
                    }
                    state.offset.targetValue.x < 0 -> {
                        launch {
                            state.swipe(Direction.Left)
                        }
                    }
                }
            }
        )
    }
}.graphicsLayer(
    translationX = state.offset.value.x,
    translationY = state.offset.value.y,
    rotationZ = (state.offset.value.x / 60).coerceIn(-40f, 40f),
)

