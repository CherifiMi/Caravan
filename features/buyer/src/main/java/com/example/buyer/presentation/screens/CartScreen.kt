package com.example.buyer.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.common.components.MyButton
import com.example.caravan.domain.model.SavedCartOrder
import com.example.caravan.domain.navigation.Screens
import com.example.common.theme.Montserrat
import com.example.common.theme.PinkRed
import com.example.common.theme.Typography
import com.example.buyer.presentation.BuyerViewModel
import com.example.caravan.ui.errors.NoNetScreen
import com.example.common.domain.model.SavedCartOrder
import com.example.common.screens.errors.NoNetScreen
import com.stripe.android.payments.paymentlauncher.PaymentLauncher


val items = listOf(
    SavedCartOrder(
        id = null,
        name = "myoeowae erw e",
        price = 2000,
        amount = 12,
        firstPicUrl = "https://images.frandroid.com/wp-content/uploads/2022/03/nothing-phone1-kv-1920x1080-1.jpg",
        sellerId = "",
        buyerId = "",
        productId = "",
        sellerStripe = ""
    ),
    SavedCartOrder(
        id = null,
        name = "myoeowae erw e",
        price = 2000,
        amount = 12,
        firstPicUrl = "https://images.frandroid.com/wp-content/uploads/2022/03/nothing-phone1-kv-1920x1080-1.jpg",
        sellerId = "",
        buyerId = "",
        productId = "",
        sellerStripe = ""
    ),
    SavedCartOrder(
        id = null,
        name = "myoeowae erw e",
        price = 2000,
        amount = 12,
        firstPicUrl = "https://images.frandroid.com/wp-content/uploads/2022/03/nothing-phone1-kv-1920x1080-1.jpg",
        sellerId = "",
        buyerId = "",
        productId = "",
        sellerStripe = ""
    )

)

@Composable
fun CartScreen(
    navController: NavHostController?,
    userId: String,
    viewModel: BuyerViewModel = hiltViewModel(),
) {
    val savedItems = viewModel.getAllSavedCardOrders()

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            elevation = 2.dp,
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = com.example.common.theme.PinkRed
        ) {
            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "caravan",
                style = com.example.common.theme.Typography.h1,
                textAlign = TextAlign.Start,
                color = Color.White
            )
        }

        if (savedItems.isNullOrEmpty()) {
            NoNetScreen()
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {

                //orders
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .weight(9f)
                ) {
                    items(items = savedItems) { item ->
                        CartOrderItem(item = item)
                    }
                }


                //buy
                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    com.example.common.components.MyButton(text = "Buy Now") {
                        navController?.navigate(Screens.HomeBuyer.route)
                        viewModel.buyCartOrders(paymentLauncher)
                    }
                }

            }
        }
    }
}



@Composable
fun CartOrderItem(item: SavedCartOrder) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Card(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            AsyncImage(
                model = item.firstPicUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .height(52.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.name,
                style = TextStyle(
                    fontFamily = com.example.common.theme.Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                maxLines = 1
            )

            Row(Modifier.fillMaxWidth()) {

                val pStyle = TextStyle(
                    fontFamily = com.example.common.theme.Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = com.example.common.theme.PinkRed
                )

                val bStyle = TextStyle(
                    fontFamily = com.example.common.theme.Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Text(
                    text = (item.price / 100f).toString(),
                    style = bStyle,
                )
                Text(
                    text = " RS ",
                    style = pStyle,
                )

                Text(
                    text = " x ",
                    style = bStyle,
                )

                Text(
                    text = item.amount.toString(),
                    style = bStyle,
                )

                Text(
                    text = " Piece ",
                    style = pStyle,
                )


                Text(
                    text = " = ",
                    style = pStyle,
                )

                Text(
                    text = (item.amount * (item.price / 100f)).toString(),
                    style = bStyle,
                )


                Text(
                    text = " RS ",
                    style = pStyle,
                )
            }
        }

    }
}

