package com.example.buyer.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.caravan.R
import com.example.caravan.domain.model.Product
import com.example.caravan.theme.*
import com.example.buyer.presentation.BuyerViewModel
import com.example.buyer.presentation.components.BuyProductPopUp
import com.stripe.android.payments.paymentlauncher.PaymentLauncher


@Composable
fun BuyerProductScreen(
    navController: NavHostController?,
    args: Bundle?,
    viewModel: BuyerViewModel = hiltViewModel(),
    paymentLauncher: PaymentLauncher?,
    userId: String
) {

    val currantItem = viewModel.getCurrentProduct(args?.getString("index") ?: "")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BottomLayer(currantItem, viewModel, navController!!, userId)
        BuyProductPopUp(currantItem, viewModel, navController!!, paymentLauncher!!, userId)
    }


}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomLayer(
    currantItem: Product,
    viewModel: BuyerViewModel,
    navController: NavHostController,
    userId: String
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    viewModel.buyOrAddToCartSheet.value = true
                },
                backgroundColor = com.example.common.theme.PinkRed
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.cart_full),
                    contentDescription = "Add To Cart",
                    tint = com.example.common.theme.white
                )
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = currantItem.imageUrls[viewModel.thisImage.value],
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ImagePickerCard(currantItem.imageUrls[0], 0, viewModel)
                    ImagePickerCard(currantItem.imageUrls[1], 1, viewModel)
                    ImagePickerCard(currantItem.imageUrls[2], 2, viewModel)
                    ImagePickerCard(currantItem.imageUrls[3], 3, viewModel)
                }
            }

            Card(
                shape = RoundedCornerShape(100.dp),
                backgroundColor = com.example.common.theme.LightGrey,
                modifier = Modifier
                    .padding(150.dp, 16.dp)
                    .fillMaxWidth()
                    .height(4.dp)
            ) {}

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = currantItem.name ,
                style = TextStyle(
                    fontFamily = com.example.common.theme.Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp
                )
            )

            Spacer(modifier = Modifier.padding(8.dp))

            LazyRow(){
                item {
                    Spacer(modifier = Modifier.width(8.dp))
                }
                items(items = currantItem.cat){ item ->
                    SubCatCard(item)
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SR ",
                    color = com.example.common.theme.PinkRed,
                    style = TextStyle(
                        fontFamily = com.example.common.theme.Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = (currantItem.newPrice).toString() ,
                    style = TextStyle(
                        fontFamily = com.example.common.theme.Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                )

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = "RS " + (currantItem.initPrice).toString(),
                    textDecoration = TextDecoration.LineThrough,
                    style = com.example.common.theme.Typography.h3,
                    color = com.example.common.theme.LightGrey
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                modifier = Modifier.padding(16.dp),
                text = currantItem.content ,
                style = TextStyle(
                    fontFamily = com.example.common.theme.Montserrat,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            )

        }
    }
}

@Composable
fun ImagePickerCard(url: String, i: Int, viewModel: BuyerViewModel) {
    Spacer(modifier = Modifier.padding(2.dp))

    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if (viewModel.thisImage.value == i) com.example.common.theme.PinkRed else com.example.common.theme.LightGrey
        ),
        modifier = Modifier
            .size(width = 64.dp, height = 54.dp)
            .clip(RoundedCornerShape(15.dp))
            .clickable {
                viewModel.thisImage.value = i
            }
    ) {

        AsyncImage(
            contentScale = ContentScale.Crop,
            model = url,
            contentDescription = null
        )

    }

    Spacer(modifier = Modifier.padding(2.dp))
}


@Composable
fun SubCatCard(item: String) {


    Spacer(modifier = Modifier.width(8.dp))

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp)),
        border = BorderStroke(2.dp, com.example.common.theme.PinkRed)
    ) {
        Text(
            modifier = Modifier.padding(16.dp, 8.dp),
            text = item,
            style = com.example.common.theme.Typography.h4
        )
    }

}
