package com.example.caravan.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.caravan.theme.PinkRed
import com.example.caravan.theme.Typography

@Composable
fun MyButton(
    text: String,
    text_color: Color = Color.White,
    btn_color: Color = PinkRed,
    has_border: Boolean = false,
    start: Dp = 16.dp, end: Dp = 16.dp,
    onClick: ()-> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = start, end = end)
            .border(
                width = if (has_border) 2.dp else 0.dp,
                color =if (has_border) PinkRed else Color.Transparent ,
                shape = RoundedCornerShape(100.dp)
            )
            .background(color = PinkRed, shape = RoundedCornerShape(100.dp)),
        colors = ButtonDefaults.buttonColors(backgroundColor = btn_color),
        shape = RoundedCornerShape(100.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = text,
            textAlign = TextAlign.Center,
            style = Typography.h3,
            color = text_color
        )
    }
}