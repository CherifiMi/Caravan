package com.example.caravan.ui.signup.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caravan.R
import com.example.caravan.theme.Typography

@Composable
fun WaitForAdminScreen(
    navController: NavHostController,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "caravan",
                style = Typography.h1,
                color = Color.White
            )
        })
        Image(
            painter = painterResource(id = R.drawable.wait_pic),
            contentDescription = null,
            modifier = Modifier.padding(88.dp)
        )

        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = "Our admins are chaking your requast to join",
            style = Typography.h6,
            textAlign = TextAlign.Center
        )
    }
}