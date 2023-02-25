package com.calmdowngirl.fatfinger

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Square
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.calmdowngirl.fatfinger.ui.theme.lightColors
import com.calmdowngirl.fatfinger.ui.theme.saturatedColors

@Composable
fun FingerColorPalette(fingerColor: Color, onFingerColorSelected: (color: Color) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PaletteRow(Color.Red, onFingerColorSelected, fingerColor)
        PaletteRow(Color(0xfffc8c03), onFingerColorSelected, fingerColor)
        PaletteRow(Color.Yellow, onFingerColorSelected, fingerColor)
        PaletteRow(Color.Green, onFingerColorSelected, fingerColor)
        PaletteRow(Color.Cyan, onFingerColorSelected, fingerColor)
        PaletteRow(Color(0xFFBB86FC), onFingerColorSelected, fingerColor)
        PaletteRow(MaterialTheme.colors.primaryVariant, onFingerColorSelected, fingerColor)
        PaletteRow(Color(0xFF3700B3), onFingerColorSelected, fingerColor)
        PaletteRow(Color.Black, onFingerColorSelected, fingerColor)
        PaletteRow(Color.White, onFingerColorSelected, fingerColor)
    }
}

@Composable
fun CanvasColorPalette(
    selectedColor: Color,
    onFingerColorSelected: (color: Color) -> Unit,
    colors: List<Array<Color>> = listOf(saturatedColors, lightColors)
) {
    colors.forEach {
        Row {
            it.forEach { color ->
                IconButton(
                    modifier = Modifier.size(35.dp),
                    onClick = { onFingerColorSelected(color) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Square,
                        contentDescription = "Color",
                        tint = color,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = if (color == selectedColor) MaterialTheme.colors.primary else
                                    Color(0xfff5f5f5),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(2.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun PaletteRow(
    baseColor: Color,
    onFingerColorSelected: (color: Color) -> Unit,
    selectedColor: Color = Color.Unspecified,
) {
    Row {
        for (i in 10 downTo 1) {
            val color = baseColor.copy(alpha = i.toFloat() / 10f)
            IconButton(
                modifier = Modifier.size(35.dp),
                onClick = { onFingerColorSelected(color) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Square,
                    contentDescription = "Color with alpha ${i.toFloat() / 10f}",
                    tint = color,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if (color == selectedColor) MaterialTheme.colors.primary else
                                Color(0xfff5f5f5),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(2.dp),
                )
            }
        }
    }
}
