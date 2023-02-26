package com.calmdowngirl.fatfinger.components

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
import com.calmdowngirl.fatfinger.CanvasColorPalette

@Composable
fun Settings(
    modifier: Modifier,
    onClearCanvas: () -> Unit,
    onSettingsClicked: () -> Unit,
    onPaletteClicked: () -> Unit,
    onInfoClicked: () -> Unit,
    onSaveClicked: () -> Unit,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly) {
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
        IconButton(onClick = onSaveClicked) {
            Icon(
                Icons.Outlined.Save,
                contentDescription = "Save to File",
            )
        }
        IconButton(onClick = onInfoClicked) {
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
    onRestoreDefaultSettings: () -> Unit,
) {
    var err by remember {
        mutableStateOf<String?>(null)
    }

    var number by remember {
        mutableStateOf<Int?>(null)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 35.dp /*top = 16.dp*/),
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

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .width(250.dp)
                    .padding(end = 15.dp),
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

            if (err == null && number != null) {
                Button(
                    modifier = Modifier
                        .size(56.dp)
                        .padding(top = 8.dp),
                    onClick = { number?.let { onGridSizeChange(it) } }
                ) {
                    Text("SET", fontSize = 10.sp)
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

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                modifier = Modifier
                    .size(width = 250.dp, height = 40.dp)
                    .padding(top = 8.dp),
                shape = MaterialTheme.shapes.large,
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = onRestoreDefaultSettings,
            ) {
                Text("Restore Default Settings", fontSize = 10.sp)
            }
        }
    }
}