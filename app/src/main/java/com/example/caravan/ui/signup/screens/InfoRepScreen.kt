package com.example.caravan.ui.signup.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.common.components.MyButton
import com.example.caravan.common.components.MyTextField
import com.example.caravan.ui.signup.SignUpViewModel
import com.example.caravan.theme.Typography

@Composable
fun InfoRepScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    Column(verticalArrangement = Arrangement.SpaceBetween) {

        TopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "caravan",
                style = Typography.h1,
                color = Color.White
            )
        })

        Column(Modifier.verticalScroll(rememberScrollState())) {

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Create Representative Account",
                style = Typography.h1
            )

            MyTextField(state = viewModel.email, s = "Email", isEM = true)
            MyTextField(state = viewModel.password, s = "Password", isPW = true)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Personal Information",
                style = Typography.h1
            )

            MyTextField(state = viewModel.first_name, s = "First Name")
            MyTextField(state = viewModel.last_name, s = "Last Name")
            MyTextField(state = viewModel.phone, s = "Phone Number", isNum = true)


            Spacer(modifier = Modifier.height(32.dp))

            MyButton(text = "Create my account") {
                viewModel.CreateNewUser(3, navController)
            }

            Spacer(modifier = Modifier.height(280.dp))
        }


    }
}
