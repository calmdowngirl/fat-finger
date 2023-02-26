package com.calmdowngirl.fatfinger.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.toArgb
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
    shadowCanvas: Canvas,
    onTap: (canvasSize: Size, offset: Offset) -> Unit,
    setBitmap: (bitmap: Bitmap) -> Unit,
) {
    var canvasSize by remember { mutableStateOf(Size.Zero) }
    var isShadowCanvasInitialised by remember { mutableStateOf(false) }

    Row(modifier = modifier) {
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

            if (!isShadowCanvasInitialised) {
                setBitmap(
                    Bitmap.createBitmap(
                        canvasWidth.toInt(), canvasHeight.toInt(), Bitmap.Config.ARGB_8888
                    )
                )
                isShadowCanvasInitialised = true
            }

            val xSteps = canvasWidth / columns
            val ySteps = canvasHeight / rows
            val fingerSize = if (pixels.isEmpty()) Size.Zero else
                Size(xSteps - 5, ySteps - 5)

            val cr = if (roundedCorner) 15f else 0f
            val cornerRadius = CornerRadius(cr, cr)

            drawRoundRect(color = bgColor, cornerRadius = cornerRadius)
            drawRoundRect(
                color = gridLineColor,
                cornerRadius = cornerRadius,
                style = Stroke(width = lineWidth + 5)
            )
            shadowCanvas.drawRoundRect(
                0f, 0f, canvasWidth, canvasHeight, cr, cr,
                Paint().apply { color = bgColor.toArgb() }
            )
            shadowCanvas.drawRoundRect(
                0f, 0f, canvasWidth, canvasHeight, cr, cr,
                Paint().apply {
                    color = gridLineColor.toArgb()
                    strokeWidth = lineWidth + 5
                    style = Paint.Style.STROKE
                }
            )

            for (i in 1 until rows) {
                drawLine(
                    color = gridLineColor,
                    start = Offset(0f, ySteps * i),
                    end = Offset(canvasWidth, ySteps * i),
                    strokeWidth = lineWidth,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f), 0f)
                )
                shadowCanvas.drawLine(
                    0f, ySteps * i, canvasWidth, ySteps * i,
                    Paint().apply {
                        color = gridLineColor.toArgb()
                        strokeWidth = lineWidth
                        style = Paint.Style.STROKE
                        pathEffect = DashPathEffect(floatArrayOf(3f, 3f), 0f)
                    }
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
                shadowCanvas.drawLine(
                    xSteps * i, 0f, xSteps * i, canvasHeight,
                    Paint().apply {
                        color = gridLineColor.toArgb()
                        strokeWidth = lineWidth
                        style = Paint.Style.STROKE
                        pathEffect = DashPathEffect(floatArrayOf(3f, 3f), 0f)
                    }
                )
            }

            pixels.forEach {
                val theColor = it.value.keys.first()
                val theOffset = it.value[theColor]!!
                drawRoundRect(
                    color = theColor,
                    topLeft = theOffset,
                    cornerRadius = cornerRadius,
                    size = fingerSize,
                )
                shadowCanvas.drawRoundRect(
                    theOffset.x, theOffset.y,
                    theOffset.x + xSteps - 5, theOffset.y + ySteps - 5,
                    cr, cr, Paint().apply { color = theColor.toArgb() }
                )
            }
        }
    }
}