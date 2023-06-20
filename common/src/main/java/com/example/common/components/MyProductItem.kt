package com.example.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.caravan.R
import com.example.caravan.domain.model.Product
import com.example.caravan.theme.*

@Composable
fun MyProductItem(item: Product, cart: Boolean = false, function: () -> Unit) {
    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = 2.dp,
        modifier = Modifier
            .padding(8.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { function() },
            contentAlignment = Alignment.TopStart
        ) {

            Column() {
                AsyncImage(
                    modifier = Modifier
                        .height(125.dp),
                    model = item.imageUrls[0],
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = item.name ,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    ), modifier = Modifier.padding(8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    Column() {
                        Text(
                            text = "SR " + item.newPrice.toString(),
                            style =
                            TextStyle(
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            ),
                        )

                        Text(
                            modifier = Modifier.alpha(if (item.newPrice!=item.initPrice) 1f else 0f),
                            text = "SR " + item.initPrice.toString() ,
                            style =
                            TextStyle(
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp
                            ),
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    //IconButton(onClick = { /**/ }) {
                    //    Icon(
                    //        modifier = Modifier.alpha(if(cart) 1f else 0f).padding(12.dp),
                    //        painter = painterResource(id = R.drawable.cart_full),
                    //        tint = LightGrey,
                    //        contentDescription = null
                    //    )
                    //}
                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
            if (item.newPrice!=item.initPrice){
                Box(
                    Modifier
                        .padding(8.dp)
                        .wrapContentWidth()
                        .background(shape = RoundedCornerShape(5.dp), color = PinkRed)
                ) {
                    val disP = 100 - ((item.newPrice.toFloat() / item.initPrice.toFloat()) * 100)
                    Text(
                        text = "${disP.toInt()}%",
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        style =
                        TextStyle(
                            color = Color.White,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }


    }

}





