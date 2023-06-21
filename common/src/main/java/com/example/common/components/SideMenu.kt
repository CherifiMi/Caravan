package com.example.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SideMenu(
    navController: NavHostController,
){
    
    Column(Modifier.fillMaxSize()) {

        Button(onClick = {  }) {
            Text(text = "sign out")
        }

    }
}