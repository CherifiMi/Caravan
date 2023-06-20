package com.example.caravan.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.R
import com.example.caravan.common.components.MyButton
import com.example.caravan.common.components.MyTextField
import com.example.caravan.theme.GoogleBlue
import com.example.caravan.theme.PinkRed
import com.example.caravan.theme.Typography
import com.example.caravan.domain.navigation.Screens


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopAppBar(title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "caravan",
                    style = Typography.h1,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            })


            Text(
                modifier = Modifier.padding(vertical = 40.dp),
                style = Typography.h2,
                text = "Login to Caravan",
                color = Color.Black
            )

            MyTextField(viewModel.email, "Email", isEM = true)

            MyTextField(viewModel.password, "Password", isPW = true)

            MyButton(
                text = "Continue with Email",
                text_color = Color.White,
                btn_color = PinkRed,
            ){
                viewModel.onSignInClick(navController)

            }

            /*Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.or),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            MyButton(
                text = "Continue with Google",
                text_color = Color.White,
                btn_color = GoogleBlue,
            ){ viewModel.getuser()}*/

        }

        //____
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .padding(16.dp),
                painter = painterResource(id = R.drawable.dont),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            MyButton(
                text = "Sign Up",
                text_color = PinkRed,
                btn_color = Color.White,
                has_border = true,
            ){
                navController.navigate(Screens.SelectUserType.route)
            }

            Spacer(modifier = Modifier.height(72.dp))
        }

    }
}