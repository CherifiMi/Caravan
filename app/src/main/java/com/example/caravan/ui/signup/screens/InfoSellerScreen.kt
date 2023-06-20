package com.example.caravan.ui.signup.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.common.components.MyButton
import com.example.common.components.MyTextField
import com.example.caravan.ui.signup.SignUpViewModel
import com.example.common.theme.Typography

@Composable
fun InfoSellerScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    Column(verticalArrangement = Arrangement.SpaceBetween) {

        TopAppBar(title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "caravan",
                style = com.example.common.theme.Typography.h1,
                color = Color.White
            )
        })


        Column(Modifier.verticalScroll(rememberScrollState())) {

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Create Seller Account",
                style = com.example.common.theme.Typography.h1
            )

            com.example.common.components.MyTextField(
                state = viewModel.email,
                s = "Email",
                isEM = true
            )
            com.example.common.components.MyTextField(
                state = viewModel.password,
                s = "Password",
                isPW = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Personal Information",
                style = com.example.common.theme.Typography.h1
            )

            com.example.common.components.MyTextField(
                state = viewModel.first_name,
                s = "First Name"
            )
            com.example.common.components.MyTextField(state = viewModel.last_name, s = "Last Name")
            com.example.common.components.MyTextField(
                state = viewModel.brand_name,
                s = "Brand Name"
            )
            SelletTypeSelecter(viewModel)
            com.example.common.components.MyTextField(
                state = viewModel.phone,
                s = "Phone Number",
                isNum = true
            )


            Spacer(modifier = Modifier.height(32.dp))

            com.example.common.components.MyButton(text = "Create my account") {
                viewModel.CreateNewUser(2, navController)
            }

            viewModel.url.value.let {
                if (it.isNotEmpty()){
                    val uriHandler = LocalUriHandler.current
                    uriHandler.openUri(it)
                }
            }

            Spacer(modifier = Modifier.height(280.dp))
        }
    }
}


@Composable
fun SelletTypeSelecter(viewModel: SignUpViewModel) {


    val suggestions = listOf(
        "Company",
        "Wholeseller",
        "Distributor",
        "Trader"
    )
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (viewModel.expanded.value)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown


    Column() {

        OutlinedTextField(
            enabled = false,
            textStyle = com.example.common.theme.Typography.h3,
            value = viewModel.selectedText.value,
            onValueChange = {
                viewModel.selectedText.value = it
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledLabelColor = Color(0xFF6F6F6F),
                disabledBorderColor = Color(0xFF6F6F6F),
                disabledTextColor = Color.Black,

                ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null

                ) {
                    viewModel.expanded.value = !viewModel.expanded.value
                },
            label = {
                Text("Seller Type")
            },
            trailingIcon = {
                Icon(imageVector = icon, "contentDescription",
                    modifier = Modifier.clickable {
                        viewModel.expanded.value = !viewModel.expanded.value
                    })
            }
        )

        DropdownMenu(
            expanded = viewModel.expanded.value,
            onDismissRequest = { viewModel.expanded.value = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    viewModel.selectedText.value = label
                }) {
                    Text(text = label, style = com.example.common.theme.Typography.h3)
                }
            }
        }
    }
}