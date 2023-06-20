package com.example.caravan.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.caravan.theme.Typography


@Composable
fun MyTextField(
    state: MutableState<String>,
    s: String,
    isPW: Boolean = false,
    isNum: Boolean = false,
    isEM: Boolean = false,
) {
    OutlinedTextField(
        textStyle = Typography.h3,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp, top = 0.dp),
        value = state.value,
        label = { Text(s) },
        singleLine = true,
        onValueChange = {
            state.value = it
        },
        visualTransformation = if (!isPW) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions =
        if (isPW) KeyboardOptions(keyboardType = KeyboardType.Password)
        else if (isNum) KeyboardOptions(keyboardType = KeyboardType.Phone)
        else if (isEM) KeyboardOptions(keyboardType = KeyboardType.Email)
        else KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}