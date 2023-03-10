package com.calmdowngirl.fatfinger

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calmdowngirl.fatfinger.components.About
import com.calmdowngirl.fatfinger.components.CanvasSettings
import com.calmdowngirl.fatfinger.components.PixelCanvas
import com.calmdowngirl.fatfinger.components.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val canvasState by viewModel.canvasState.collectAsStateWithLifecycle()
    val settingsState by viewModel.settingsState.collectAsStateWithLifecycle()

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PixelCanvas(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                columns = canvasState.columns,
                rows = canvasState.rows,
                gridLineColor = canvasState.gridLineColor,
                roundedCorner = canvasState.roundedCorner,
                bgColor = canvasState.canvasBgColor,
                pixels = canvasState.pixels,
                shadowCanvas = canvasState.shadowCanvas,
                onTap = viewModel::onFatFingerTap,
                setBitmap = viewModel::setBitmap,
            )

            Settings(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(MaterialTheme.colors.background),
                onClearCanvas = viewModel::clearCanvas,
                onSettingsClicked = viewModel::toggleCanvasSettings,
                onPaletteClicked = viewModel::togglePalette,
                onInfoClicked = viewModel::toggleInfo,
                onSaveClicked = {
                    canvasState.bitmap?.let {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "${viewModel.saveToFile(it)}",
                            )
                        }
                    }
                },
            )

            if (settingsState.shouldShowCanvasSettings)
                CanvasSettings(
                    selectedBgColor = canvasState.canvasBgColor,
                    selectedGridLineColor = canvasState.gridLineColor,
                    onGridLineColorSelected = viewModel::setGridLineColor,
                    onCanvasBgColorSelected = viewModel::setCanvasBgColor,
                    onGridSizeChange = viewModel::setGridSize,
                    onRestoreDefaultSettings = viewModel::restoreDefaultSettings,
                )

            if (settingsState.shouldShowPalette)
                FingerColorPalette(
                    canvasState.fingerColor, onFingerColorSelected = viewModel::setFingerColor
                )

            if (settingsState.shouldShowInfo)
                About()
        }
    }
}
