package com.example.caravan.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.MainViewModel

@Composable
fun SideMenu(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
){
    
    Column(Modifier.fillMaxSize()) {

        Button(onClick = { viewModel.signOut(navController) }) {
            Text(text = "sign out")
        }

    }

    
}