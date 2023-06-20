package com.example.signup.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.common.theme.LightGrey
import com.example.common.theme.PinkRed
import com.example.common.theme.Typography

@Composable
fun UserCard(painterResource: Painter, s: String, i: Int, state: MutableState<Int>) {

    val selected = i == state.value

    Box(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .border(
                color = if (selected) com.example.common.theme.PinkRed else com.example.common.theme.LightGrey,
                shape = RoundedCornerShape(6.dp),
                width = 2.dp
            )
            .clickable { state.value = i},
        contentAlignment = Alignment.TopEnd
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .size(16.dp),
            painter = painterResource(id = if (selected) R.drawable.selected_user else R.drawable.unselected_user),
            contentDescription = null
        )
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(40.dp),
                painter = painterResource,
                contentDescription = null
            )
            Text(text = s, style = com.example.common.theme.Typography.h3, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}