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
import com.example.common.components.MyButton
import com.example.common.components.MyTextField
import com.example.common.theme.PinkRed
import com.example.common.theme.Typography
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
                    style = com.example.common.theme.Typography.h1,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            })


            Text(
                modifier = Modifier.padding(vertical = 40.dp),
                style = com.example.common.theme.Typography.h2,
                text = "Login to Caravan",
                color = Color.Black
            )

            com.example.common.components.MyTextField(viewModel.email, "Email", isEM = true)

            com.example.common.components.MyTextField(viewModel.password, "Password", isPW = true)

            com.example.common.components.MyButton(
                text = "Continue with Email",
                text_color = Color.White,
                btn_color = com.example.common.theme.PinkRed,
            ) {
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

            com.example.common.components.MyButton(
                text = "Sign Up",
                text_color = com.example.common.theme.PinkRed,
                btn_color = Color.White,
                has_border = true,
            ) {
                navController.navigate(Screens.SelectUserType.route)
            }

            Spacer(modifier = Modifier.height(72.dp))
        }

    }
}