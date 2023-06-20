package com.example.caravan


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.caravan.common.snackbar.SnackbarManager
import com.example.caravan.domain.ConnectivityObserver
import com.example.caravan.domain.NetworkConnectivityObserver
import com.example.caravan.theme.CaravanTheme
import com.example.caravan.ui.buyer.BuyerViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.payments.paymentlauncher.PaymentResult
import dagger.hilt.android.AndroidEntryPoint

val canOrder = mutableStateOf(false)

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var buyerViewModel: BuyerViewModel
    lateinit var connectivityObserver: ConnectivityObserver

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalMotionApi
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)

        configAWS()

        configStripe()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.spalsh.value
            }
        }

        val paymentConfiguration = PaymentConfiguration.getInstance(applicationContext)

        val paymentLauncher = PaymentLauncher.Companion.create(
            this,
            paymentConfiguration.publishableKey,
            paymentConfiguration.stripeAccountId,
            ::onPaymentResult
        )
        setContent {

            val netStatus by connectivityObserver.observe().collectAsState(initial = ConnectivityObserver.Status.Unavailable)
            viewModel.there_is_net.value = netStatus == ConnectivityObserver.Status.Available

            Log.d("MitoNet", netStatus.name)
            buyerViewModel = hiltViewModel()
            CaravanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainApp(viewModel, contentResolver, paymentLauncher, args = null)
                }
            }
        }
    }

    private fun configStripe() {
        PaymentConfiguration.init(
            applicationContext,
            "pk_live_51LWUO5SACQklfQRYTPym8Fac0yQb87gR79X9aWkNucFBA78VuKEUpYvUF4yQpGoXPPqQI4y6k5CMZx4ett3xjdO000HqBFDA10"
            //"pk_test_51LWUO5SACQklfQRYruy9W29tWhUQ1gjBpke7xQEH6uoZ7Q1CcGlSzQyPBl6u9aMY4yWqlQJLXgMCbVCgszADY9VV00o6ORtDAr"
        )
    }

    private fun configAWS() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
    }

    private fun onPaymentResult(paymentResult: PaymentResult) {

        //buyerViewModel.makeOrder()
        
        when (paymentResult) {
            is PaymentResult.Completed -> {
                buyerViewModel.makeOrder()
            }
            is PaymentResult.Canceled -> {
                SnackbarManager.showMessage(R.string.no_pay)
            }
            is PaymentResult.Failed -> {
                "Failed: " + paymentResult.throwable.message
                SnackbarManager.showMessage(R.string.no_pay)
            }
        }
    }
}

