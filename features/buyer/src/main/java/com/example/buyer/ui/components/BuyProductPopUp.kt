package com.example.buyer.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.common.components.MyButton
import com.example.common.components.MyTextField
import com.example.caravan.domain.model.Product
import com.example.caravan.domain.navigation.Screens
import com.example.common.theme.LightGrey
import com.example.common.theme.Montserrat
import com.example.common.theme.PinkRed
import com.example.common.theme.Typography
import com.example.buyer.ui.BuyerViewModel
import com.stripe.android.payments.paymentlauncher.PaymentLauncher


@Composable
fun BuyProductPopUp(
    currantItem: Product,
    viewModel: BuyerViewModel,
    navController: NavHostController,
    paymentLauncher: PaymentLauncher,
    buyerId: String
) {

    viewModel.amountToBuy.value = currantItem.minOrder

    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = viewModel.buyOrAddToCartSheet.value,
        enter = slideIn(
            initialOffset = { fullSize ->
                IntOffset(0, fullSize.width / 2)
            },
            animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)

        ),
        exit = slideOut(
            targetOffset = { fullSize ->
                IntOffset(0, fullSize.width / 2)
            },
            animationSpec = tween(durationMillis = 300)
        ) + fadeOut()
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                Modifier
                    .weight(6f)
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { viewModel.buyOrAddToCartSheet.value = false }
            )

            Card(
                elevation = 10.dp,
                backgroundColor = Color.White,
                shape = RoundedCornerShape(25.dp, 25.dp, 0.dp, 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f)
            ) {
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {

                    Spacer(modifier = Modifier.padding(8.dp))

                    Column(Modifier.fillMaxWidth()) {

                        Text(text = "available: ${currantItem.amountInInv}", style = com.example.common.theme.Typography.h2 , color = Color.Black, modifier = Modifier.padding(start = 16.dp))

                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text(text = "RS ", style = com.example.common.theme.Typography.h3, color = Color.Black)
                            Text(
                                text = (currantItem.newPrice).toString(),
                                style = com.example.common.theme.Typography.h1,
                                color = com.example.common.theme.PinkRed
                            )
                            Text(text = " X ", style = com.example.common.theme.Typography.h1, color = com.example.common.theme.LightGrey)

                            Spacer(modifier = Modifier.padding(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 32.dp)
                            ) {

                                com.example.common.components.MyTextField(
                                    state = viewModel.amountToBuy,
                                    s = "Select Amount",
                                    isNum = true
                                )

                            }
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(modifier = Modifier.padding(16.dp))

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
                                text = ((currantItem.newPrice) * if (viewModel.amountToBuy.value.isEmpty()) 0 else viewModel.amountToBuy.value.toInt()).toString(),
                                style = TextStyle(
                                    fontFamily = com.example.common.theme.Montserrat,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 24.sp
                                )
                            )
                        }
                    }



                    Spacer(modifier = Modifier.padding(16.dp))

                    //buy add to cart button
                    Row(Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.weight(1f)) {
                            com.example.common.components.MyButton(
                                text = "Buy Now \n",
                                text_color = Color.White,
                                btn_color = com.example.common.theme.PinkRed,
                                end = 8.dp
                            ) {
                                if (viewModel.isAmountValid(
                                        viewModel.amountToBuy.value,
                                        currantItem.minOrder.toInt(),
                                        currantItem.amountInInv
                                    )
                                ) {
                                    viewModel.saveOrder(
                                        currantItem,
                                        viewModel.amountToBuy.value.toInt()
                                    )
                                    viewModel.buyProduct(
                                        linked = currantItem.sellerStripe,
                                        paymentLauncher,
                                        price = currantItem.newPrice,
                                        amount = viewModel.amountToBuy.value.toInt()
                                    )
                                    navController.navigate(Screens.HomeBuyer.route)
                                }
                            }
                        }

                        Box(modifier = Modifier.weight(1f)) {

                            com.example.common.components.MyButton(
                                text = "Add to Order Book",
                                has_border = true,
                                btn_color = Color.White,
                                text_color = com.example.common.theme.PinkRed,
                                start = 8.dp
                            ) {
                                if (viewModel.isAmountValid(
                                        viewModel.amountToBuy.value,
                                        currantItem.minOrder.toInt(),
                                        currantItem.amountInInv
                                    )
                                ) {
                                    viewModel.saveOrderToCart(
                                        currantItem = currantItem,
                                        amount = viewModel.amountToBuy.value.toInt(),
                                        buyerId
                                    )
                                    navController.navigate(Screens.HomeBuyer.route)
                                }
                            }
                        }
                    }


                    Spacer(modifier = Modifier.padding(16.dp))
                }

            }
        }

    }
}


