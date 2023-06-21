package com.example.caravan.navigation

import android.content.ContentResolver
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.caravan.MainApp
import com.example.caravan.MainViewModel
import com.example.caravan.ui.rep.RepHomeScreen
import com.example.caravan.ui.seller.SellerHomeScreen
import com.example.login.presentation.LoginScreen
import com.stripe.android.payments.paymentlauncher.PaymentLauncher


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMotionApi::class)
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    cn: ContentResolver,
) {

    NavHost(
        navController,
        startDestination =
        when (viewModel.firstScreen) {
            "login" -> Screens.Login.route
            "wait" -> Screens.Wait.route
            "buyer" -> Screens.HomeBuyer.route
            "seller" -> Screens.HomeSeller.route
            "rep" -> Screens.HomeRep.route
            "nonet" -> Screens.NoNetError.route
            else -> Screens.SWError.route
        }
    ) {

        composable(
            route = Screens.Main.route,
            arguments = listOf(navArgument(name = "userId"){type = NavType.StringType})
        ) {
            MainApp(viewModel, cn)
        }

        composable(route = Screens.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screens.SelectUserType.route) {
            com.example.signup.presentation.screens.SelectUserScreen(navController = navController)
        }

        composable(route = Screens.InfoBuyer.route) {
            com.example.signup.presentation.screens.InfoBuyerScreen(navController = navController)
        }
        composable(route = Screens.InfoSeller.route) {
            com.example.signup.presentation.screens.InfoSellerScreen(navController = navController)
        }
        composable(route = Screens.InfoRep.route) {
            com.example.signup.presentation.screens.InfoRepScreen(navController = navController)
        }
        composable(route = Screens.Wait.route) {
            com.example.signup.presentation.screens.WaitForAdminScreen(navController = navController)
        }


        composable(route = Screens.HomeBuyer.route) {
            com.example.buyer.presentation.screens.BuyerHomeScreen(
                navController = navController,
                userId = ""
            )
        }
        composable(route = Screens.CartBuyer.route) {
            com.example.buyer.presentation.screens.CartScreen(
                navController = navController,
                userId = "",
                paymentLauncher = paymentLauncher
            )
        }
        composable(
            route = Screens.ProductBuyer.route,
            arguments = listOf(navArgument(name = "index"){type = NavType.StringType})
        ) {
            var index = it.arguments
            com.example.buyer.presentation.screens.BuyerProductScreen(
                navController = navController,
                args = index,
                paymentLauncher = paymentLauncher,
                userId = userId
            )
        }


        composable(route = Screens.HomeSeller.route) {
            SellerHomeScreen(mainNavController = navController,userId = userId)
        }
        composable(
            route = Screens.ProductSeller.route,
            arguments = listOf(navArgument(name = "item"){type = NavType.StringType})
        ) {
            var item = it.arguments
            com.example.seller.presentation.screens.SellerEditProductScreen(
                navController = navController,
                cn = cn,
                args = item,
                userId = userId
            )
        }


        composable(route = Screens.HomeRep.route) {
            RepHomeScreen(mainNavController = navController)
        }

        composable(route = Screens.SWError.route) {
            com.example.common.screens.errors.SomethingWrongScreen(navController = navController)
        }
        composable(route = Screens.NoNetError.route) {
            com.example.common.screens.errors.NoNetScreen()
        }
    }

}



