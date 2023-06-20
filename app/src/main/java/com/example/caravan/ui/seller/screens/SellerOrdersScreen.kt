package com.example.caravan.ui.seller.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.domain.model.Order
import com.example.common.theme.Montserrat
import com.example.common.theme.PinkRed
import com.example.common.theme.Typography
import com.example.caravan.ui.seller.SellerViewModel
import okhttp3.internal.userAgent


@Composable
fun SellerOrdersScreen(
    viewModel: SellerViewModel = hiltViewModel(),
    navController: NavHostController,
    userId: String
) {

    viewModel.getMyOrders(userId)

    if (viewModel.myOrders.value == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "...",
                style = com.example.common.theme.Typography.h1,
                textAlign = TextAlign.Center
            )
        }
    }
    else if (viewModel.myOrders.value!!.isEmpty()){
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "You have 0 Orders",
                style = com.example.common.theme.Typography.h1,
                textAlign = TextAlign.Center
            )
        }
    }
    else {

        LazyColumn(Modifier.fillMaxSize()) {
            items(items = viewModel.myOrders.value?.toList()?.reversed() ?: listOf()) { item ->
                OrderCard(item, viewModel, userId)
            }

            item {
                Spacer(modifier = Modifier.padding(32.dp))
            }

        }

    }
}

@Composable
fun OrderCard(item: Order, viewModel: SellerViewModel, userId: String) {

    val boldS = TextStyle(
        fontFamily = com.example.common.theme.Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
    val thinS = TextStyle(
        fontFamily = com.example.common.theme.Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    )

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start
    ) {
        val brr = LocalHapticFeedback.current
        Card(
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(2.dp, com.example.common.theme.PinkRed),
            modifier = Modifier
                .pointerInput(Unit){
                    detectTapGestures(
                        onLongPress = {
                            Log.d("onLongPress", "onLongPress")
                            viewModel.deleteOrderByKey(item.id!!, userId)
                            brr.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    )
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Order: ", style = boldS, color = com.example.common.theme.PinkRed)
                    Text(text = item.amount.toString(), style = boldS, color = Color.Black)
                    Text(text = " of ", style = thinS, color = Color.Black)
                    Text(text = viewModel.getProductNameById(item.productId), style = boldS, color = Color.Black)
                }

                val buyer = viewModel.getBuyerByKey(item.buyer)!!

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Buyer Name: ", style = thinS, color = Color.Black)
                        Text(text = buyer.owner, style = boldS, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Buyer Brand: ", style = thinS, color = Color.Black)
                        Text(text = buyer.brand, style = boldS, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Buyer Address: ", style = thinS, color = Color.Black)
                        Text(text = buyer.address, style = boldS, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Buyer Phone: ", style = thinS, color = Color.Black)
                        Text(text = buyer.phone, style = boldS, color = Color.Black)
                    }

                }
            }


        }
    }
}