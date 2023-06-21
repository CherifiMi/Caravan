package com.example.caravan

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.caravan.navigation.Navigation
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import kotlinx.coroutines.CoroutineScope

lateinit var gAppState: com.example.common.CaravanAppState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMotionApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MainApp(
    viewModel: MainViewModel,
    cn: ContentResolver,
) {
    lateinit var navController: NavHostController
    val appState = rememberAppState()
    gAppState = appState


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = it,
                modifier = Modifier.padding(8.dp),
                snackbar = { snackbarData ->
                    Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                }
            )
        },
        scaffoldState = appState.scaffoldState
    ){
        navController = rememberNavController()
        Navigation(navController, viewModel, cn)
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: com.example.common.snackbar.SnackbarManager = com.example.common.snackbar.SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
    com.example.common.CaravanAppState(
        scaffoldState,
        navController,
        snackbarManager,
        resources,
        coroutineScope
    )
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}