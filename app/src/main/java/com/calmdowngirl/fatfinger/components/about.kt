package com.calmdowngirl.fatfinger.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.calmdowngirl.fatfinger.AppRoutes
import com.calmdowngirl.fatfinger.R
import com.calmdowngirl.fatfinger.ui.theme.fontFamilyLobster2
import com.calmdowngirl.fatfinger.ui.theme.gajrajOne

@Composable
fun About(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp, start = 40.dp, end = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.background(Color.White)) {
            Text(
                "Fat Finger",
                style = TextStyle(
                    fontFamily = gajrajOne,
                    fontSize = 32.sp,
                    color = MaterialTheme.colors.primaryVariant,
                ),
                modifier = Modifier.padding(end = 12.dp)
            )
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .padding(top = 3.dp),
                painter = painterResource(id = R.drawable.outline_touch_app_24),
                contentDescription = "Finger Tap Gesture"
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .width(400.dp)
        ) {
            Text(
                "Eh? Just use ur Fat Finger tap on the canvas :P You can find the source code at github.com/calmdowngirl/fat-finger too. ",
                style = TextStyle(
                    fontFamily = fontFamilyLobster2,
                    fontSize = 24.sp,
                    color = Color(0xffdfdfdf),
                    shadow = Shadow(
                        color = Color(0xff9f9f9f), offset = Offset(1f, 1f),
                        blurRadius = 1f
                    )
                )
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .width(400.dp)
        ) {
            Button(onClick = {
                Log.d("ffgr", "btn clicked")
                navController.navigate(AppRoutes.Support.route)
            }) {
                Text(
                    "Buy Me a Coffee",
                    style = TextStyle(
                        fontFamily = fontFamilyLobster2,
                        color = Color(0xffdfdfdf),
                        shadow = Shadow(
                            color = Color(0xff9f9f9f), offset = Offset(1f, 1f),
                            blurRadius = 1f
                        )
                    )
                )
            }
        }
    }
}