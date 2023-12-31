package com.example.rep.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.caravan.domain.navigation.Screens
import com.example.rep.presentation.RepViewModel
import com.example.rep.presentation.components.MyUserRepItem

@Composable
fun MyBuyersScreen(navController: NavHostController, viewModel: RepViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(viewModel.repMyBuyers.value){item ->
            MyUserRepItem(name = item!!.owner, brand = item.brand){

                navController.navigate(Screens.Main.passItem(item.autheId))

            }
        }
    }
}