package com.calmdowngirl.fatfinger

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileOutputStream
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
    val bitmap: Bitmap? = null,
    val shadowCanvas: Canvas = Canvas(),
    val triggerCanvasRedraw: Boolean = false,
)

data class SettingsState(
    val shouldShowCanvasSettings: Boolean = true,
    val shouldShowPalette: Boolean = false,
    val shouldShowInfo: Boolean = false,
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

    fun restoreDefaultSettings() = _canvasState.update {
        it.copy(
            fingerColor = Color(0xff6257ff),
            canvasBgColor = Color(0xfffffdd9),
            gridLineColor = Color(0xff6257ff),
            roundedCorner = true,
            columns = 16,
            rows = 16,
            pixels = mutableMapOf(),
        )
    }

    fun setBitmap(bitmap: Bitmap): Unit = _canvasState.update {
        it.copy(bitmap = bitmap, shadowCanvas = Canvas(bitmap))
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
            shouldShowInfo = false,
        )
    }

    fun togglePalette(): Unit = _settingsState.update {
        it.copy(
            shouldShowPalette = !it.shouldShowPalette,
            shouldShowCanvasSettings = false,
            shouldShowInfo = false,
        )
    }

    fun toggleInfo(): Unit = _settingsState.update {
        it.copy(
            shouldShowInfo = !it.shouldShowInfo,
            shouldShowCanvasSettings = false,
            shouldShowPalette = false,
        )
    }

    fun saveToFile(bitmap: Bitmap, format: String = "PNG") {
        val timestamp = System.currentTimeMillis()
        var file: File? = null
        lateinit var outputStream: FileOutputStream
        when (format) {
            "PNG" -> {
                file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "ffgr_$timestamp.png"
                )
                outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }
            "JPEG", "JPG" -> {
                file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "ffgr_$timestamp.jpg"
                )
                outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            else -> Unit
        }

        Log.d("ffgr", "File: $file")
        file?.let {
            outputStream.flush()
            outputStream.close()
        }
    }
}