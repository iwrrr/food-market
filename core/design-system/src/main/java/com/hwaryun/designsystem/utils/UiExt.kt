package com.hwaryun.designsystem.utils

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

fun Modifier.singleClick(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    )
}

enum class ButtonState { Pressed, Released }

fun Modifier.bounceClick() = composed {
    var state by remember { mutableStateOf(ButtonState.Released) }
    val scale by animateFloatAsState(if (state == ButtonState.Pressed) 0.98f else 1f)

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {}
        )
        .pointerInput(state) {
            awaitPointerEventScope {
                state = if (state == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Released
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}

fun Modifier.springClick() = composed {
    var state by remember { mutableStateOf(ButtonState.Released) }
    val scale by animateFloatAsState(
        targetValue = if (state == ButtonState.Pressed) 0.85f else 1f,
        animationSpec = if (state == ButtonState.Released) {
            spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        } else {
            spring()
        }
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(state) {
            awaitPointerEventScope {
                state = if (state == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Released
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}