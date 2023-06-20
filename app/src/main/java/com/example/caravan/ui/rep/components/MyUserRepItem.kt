package com.example.caravan.ui.rep.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caravan.theme.Montserrat
import com.example.caravan.theme.PinkRed
import com.example.caravan.theme.Typography

@Composable
fun MyUserRepItem(name: String, brand: String, function: () -> Unit) {

    val nameList = name.split(" ")
    val firstChars = "${nameList[0].first().toString().capitalize()}${
        nameList[1].first().toString().capitalize()
    }"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(100.dp))
            .clickable { function() }
    ) {
        Card(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(100.dp),
            backgroundColor = PinkRed
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = firstChars,
                    style = Typography.h1,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = brand,
                style = TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
            )

            Text(
                text = name,
                style = TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                )
            )
        }
    }
}

@Preview
@Composable
fun pre() {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MyUserRepItem(name = "mito w", brand = "the dd") {

        }
    }
}