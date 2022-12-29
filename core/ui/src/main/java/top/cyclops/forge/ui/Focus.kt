package top.cyclops.forge.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.debugInspectorInfo

fun Modifier.onFirstLoseFocus(onFirstLoseFocus: () -> Unit): Modifier =
    composed(
        inspectorInfo = debugInspectorInfo {
            name = "onFirstLoseFocus"
            properties["onFirstLoseFocus"] = onFirstLoseFocus
        }
    ) {
        val state = remember {
            mutableStateOf<FocusEventState>(FocusEventState.None)
        }
        Modifier.onFocusChanged {
            state.updateFocus(it.hasFocus)
            if (state.value == FocusEventState.FirstLost) {
                onFirstLoseFocus()
            }
        }
    }


private sealed interface FocusEventState {
    object None : FocusEventState
    object FirstFocused : FocusEventState
    object FirstLost : FocusEventState
    object Done : FocusEventState
}

private fun MutableState<FocusEventState>.updateFocus(hasFocused: Boolean) {
    if (value == FocusEventState.None && hasFocused) {
        value = FocusEventState.FirstFocused
    } else if (value == FocusEventState.FirstFocused && !hasFocused) {
        value = FocusEventState.FirstLost
    } else if (value == FocusEventState.FirstLost) {
        value = FocusEventState.Done
    }
}