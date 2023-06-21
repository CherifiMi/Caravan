package com.example.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.common.R
import com.example.common.theme.Typography


@Composable
fun MyTopBar(
    navController: NavHostController,
    isCartV: Boolean = false,
    humClicked: () -> Unit = {},
    function: () -> Unit = {}
) {

    TopAppBar(
        elevation = 2.dp,
        modifier = Modifier.clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {


                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text =  stringResource(R.string.app_name),
                    style = Typography.h1,
                    textAlign = TextAlign.Start,
                    color = Color.White
                )
            }
            if (isCartV) {

                IconButton(onClick = { function() }) {
                    Icon(
                        modifier = Modifier
                            .padding(12.dp)
                            .size(30.dp),
                        painter = painterResource(id = R.drawable.cart_emp),
                        contentDescription = ""
                    )
                }

            }
        }
    }

}