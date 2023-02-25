package com.calmdowngirl.fatfinger

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun PixelCanvas(
    modifier: Modifier = Modifier,
    lineWidth: Float = 1f,
    rows: Int,
    columns: Int,
    gridLineColor: Color,
    roundedCorner: Boolean,
    bgColor: Color,
    pixels: MutableMap<String, Map<Color, Offset>>,
    onTap: (canvasSize: Size,offset: Offset) -> Unit,
) {
    var canvasSize by remember { mutableStateOf(Size.Zero) }

    Row(modifier = modifier.background(bgColor)) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectTapGestures { offset ->
                        onTap(canvasSize, offset)
                    }
                }
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            canvasSize = Size(canvasWidth, canvasHeight)
            val xSteps = canvasWidth / columns
            val ySteps = canvasHeight / rows
            val fingerSize = if (pixels.isEmpty()) Size.Zero else
                Size(xSteps - 5, ySteps - 5)

            drawRoundRect(
                color = gridLineColor,
                cornerRadius = if (roundedCorner) CornerRadius(15f, 15f) else
                    CornerRadius(0f, 0f),
                style = Stroke(
                    width = lineWidth + 5
                )
            )

            for (i in 1 until rows) {
                drawLine(
                    color = gridLineColor,
                    start = Offset(0f, ySteps * i),
                    end = Offset(canvasWidth, ySteps * i),
                    strokeWidth = lineWidth,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f), 0f)
                )
            }
            for (i in 1 until columns) {
                drawLine(
                    color = gridLineColor,
                    start = Offset(xSteps * i, 0f),
                    end = Offset(xSteps * i, canvasHeight),
                    strokeWidth = lineWidth,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f), 0f),
                )
            }

            pixels.forEach {
                val theColor = it.value.keys.first()
                val theOffset = it.value[theColor]!!
                drawRoundRect(
                    color = theColor,
                    topLeft = theOffset,
                    cornerRadius = if (roundedCorner) CornerRadius(15f, 15f) else
                        CornerRadius(0f, 0f),
                    size = fingerSize,
                )
            }
        }
    }
}