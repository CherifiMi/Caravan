package com.example.caravan.ui.rep

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caravan.R
import com.example.caravan.common.components.MyTopBar
import com.example.caravan.domain.model.BottomNavItem
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.ui.rep.screens.MyBuyersScreen
import com.example.caravan.ui.rep.screens.MySellersScreen
import com.example.caravan.ui.seller.components.BottomNavigationBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RepHomeScreen(
    mainNavController: NavHostController?,
    viewModel: RepViewModel = hiltViewModel(),
) {

    val repNavController = rememberNavController()

    Scaffold(
        topBar = {
            MyTopBar(
                navController = mainNavController!!,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "My Sellers",
                        route = Screens.MySellers.route,
                        icon = painterResource(id = R.drawable.my_sellers),
                    ),
                    BottomNavItem(
                        name = "My Buyers",
                        route = Screens.MyBuyers.route,
                        icon = painterResource(id = R.drawable.my_buyers),
                    )
                ),
                navController = repNavController,
            ){
                repNavController.navigate(it.route)
            }
        }
    ) {

        NavHost(
            repNavController,
            startDestination = Screens.MySellers.route
        ) {
            composable(route = Screens.MySellers.route) {
                MySellersScreen(navController = mainNavController!!, viewModel = viewModel)
            }
            composable(route = Screens.MyBuyers.route) {
                MyBuyersScreen(navController = mainNavController!!, viewModel = viewModel)
            }
        }
    }
}