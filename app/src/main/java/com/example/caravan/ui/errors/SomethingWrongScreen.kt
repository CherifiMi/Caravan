package com.example.caravan.ui.errors

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SomethingWrongScreen(navController: NavHostController,) {
    Text(text = "something wrong")
}