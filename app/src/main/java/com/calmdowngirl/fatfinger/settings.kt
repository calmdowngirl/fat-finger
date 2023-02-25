package com.calmdowngirl.fatfinger

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Settings(
    modifier: Modifier,
    onClearCanvas: () -> Unit,
    onSettingsClicked: () -> Unit,
    onPaletteClicked: () -> Unit,
) {
    Row(modifier = modifier) {
        IconButton(onClick = onClearCanvas) {
            Icon(
                Icons.Outlined.LayersClear,
                contentDescription = "Clear",
            )
        }
        IconButton(onClick = onSettingsClicked) {
            Icon(
                Icons.Outlined.Settings,
                contentDescription = "Canvas Settings",
            )
        }
        IconButton(onClick = onPaletteClicked) {
            Icon(
                Icons.Outlined.Palette,
                contentDescription = "Color Palette",
            )
        }
        IconButton(onClick = onPaletteClicked) {
            Icon(
                Icons.Outlined.Info,
                contentDescription = "About",
            )
        }
    }
}

@Composable
fun CanvasSettings(
    selectedBgColor: Color,
    selectedGridLineColor: Color,
    onCanvasBgColorSelected: (color: Color) -> Unit,
    onGridLineColorSelected: (color: Color) -> Unit,
    onGridSizeChange: (n: Int) -> Unit,
) {
    var err by remember {
        mutableStateOf<String?>(null)
    }

    var number by remember {
        mutableStateOf<Int?>(null)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text("Set Grid Line Color")
            }
            CanvasColorPalette(selectedGridLineColor, onGridLineColorSelected)

            Spacer(modifier = Modifier.padding(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text("Set Canvas Background Color")
            }
            CanvasColorPalette(selectedBgColor, onCanvasBgColorSelected)

            Spacer(modifier = Modifier.padding(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                OutlinedTextField(
                    value = if (number == null) "" else number.toString(),
                    onValueChange = {
                        if (it.toIntOrNull() != null && it.toIntOrNull() != 0) {
                            err = if ((it.toIntOrNull() ?: 31) <= 30) null else
                                "Must be a Positive Integer Not Greater than 30 "
                            number = it.toIntOrNull()
                        } else {
                            err = null
                            number = null
                        }
                    },
                    label = { Text("Set Number of Grids 16x16 by Default", fontSize = 11.sp) },
                    isError = err != null,
                )
                Box(Modifier.size(12.dp))
                if (err == null && number != null) {
                    Box(Modifier.padding(top = 8.dp)) {
                        Button(
                            modifier = Modifier.size(56.dp),
                            onClick = { number?.let { onGridSizeChange(it) } }
                        ) {
                            Text("SET", fontSize = 10.sp)
                        }
                    }
                }
            }

            if (err != null) {
                Row {
                    Text(
                        text = err.toString(),
                        style = TextStyle(
                            color = Color.Red, fontSize = 11.sp, textAlign = TextAlign.End,
                        ),
                    )
                }
            }
        }
    }
}