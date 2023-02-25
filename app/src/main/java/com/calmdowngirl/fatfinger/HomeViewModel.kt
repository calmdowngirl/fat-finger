package com.calmdowngirl.fatfinger

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CanvasState(
    val fingerColor: Color = Color(0xff6257ff),
    val canvasBgColor: Color = Color(0xfffffdd9),
    val gridLineColor: Color = Color(0xff6257ff),
    val roundedCorner: Boolean = true,
    val columns: Int = 16,
    val rows: Int = 16,
    val pixels: MutableMap<String, Map<Color, Offset>> = mutableMapOf(),
    val triggerCanvasRedraw: Boolean = false,
)

data class SettingsState(
    val shouldShowCanvasSettings: Boolean = false,
    val shouldShowPalette: Boolean = false,
    val shouldShowAbout: Boolean = false,
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _canvasState = MutableStateFlow(CanvasState())
    val canvasState: StateFlow<CanvasState>
        get() = _canvasState.asStateFlow()

    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState>
        get() = _settingsState.asStateFlow()

    fun clearCanvas(): Unit = _canvasState.update {
        it.copy(
            pixels = mutableMapOf(),
            triggerCanvasRedraw = !it.triggerCanvasRedraw
        )
    }

    fun setGridSize(n: Int): Unit = _canvasState.update {
        it.copy(pixels = mutableMapOf(), columns = n, rows = n)
    }

    fun setCanvasBgColor(color: Color): Unit = _canvasState.update {
        it.copy(canvasBgColor = color)
    }

    fun setGridLineColor(color: Color): Unit = _canvasState.update {
        it.copy(gridLineColor = color)
    }

    fun setFingerColor(color: Color): Unit = _canvasState.update {
        it.copy(fingerColor = color)
    }

    fun onFatFingerTap(
        canvasSize: Size,
        offset: Offset,
    ) {
        with(canvasState.value) {
            val xSteps = canvasSize.width / columns
            val ySteps = canvasSize.height / rows
            val column =
                (offset.x / canvasSize.width * columns).toInt() + 1
            val row = (offset.y / canvasSize.height * rows).toInt() + 1
            if (!pixels.containsKey("$column-$row") ||
                (fingerColor != (pixels["$column-$row"]?.keys?.first() ?: Color.Unspecified))
            ) {
                pixels["$column-$row"] = mapOf(
                    fingerColor to
                        Offset((column - 1) * xSteps + 2.5f, (row - 1) * ySteps + 2.5f)
                )
            } else {
                pixels.remove("$column-$row")
            }
            _canvasState.update {
                it.copy(
                    pixels = pixels,
                    triggerCanvasRedraw = !it.triggerCanvasRedraw
                )
            }
        }
    }

    fun toggleCanvasSettings(): Unit = _settingsState.update {
        it.copy(
            shouldShowCanvasSettings = !it.shouldShowCanvasSettings,
            shouldShowPalette = false,
        )
    }

    fun togglePalette(): Unit = _settingsState.update {
        it.copy(
            shouldShowPalette = !it.shouldShowPalette,
            shouldShowCanvasSettings = false,
        )
    }
}