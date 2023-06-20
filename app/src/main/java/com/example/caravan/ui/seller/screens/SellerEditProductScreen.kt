package com.example.caravan.ui.seller.screens

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.R
import com.example.caravan.common.components.MyButton
import com.example.caravan.common.components.MyTextField
import com.example.caravan.theme.Typography
import com.example.caravan.ui.seller.SellerViewModel
import com.example.caravan.ui.seller.components.CatsPicker
import com.example.caravan.ui.seller.components.PicsPicker


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SellerEditProductScreen(
    navController: NavHostController?,
    viewModel: SellerViewModel = hiltViewModel(),
    cn: ContentResolver,
    args: Bundle?,
    userId: String
) {
    val itemId = args?.getString("item") ?: ""
    viewModel.setCurrentItemValues(itemId, userId)

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 2.dp,
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
                            text = "caravan",
                            style = Typography.h1,
                            textAlign = TextAlign.Start,
                            color = Color.White
                        )
                    }

                    if (itemId.toInt()!=-1){
                        IconButton(onClick = { viewModel.deleteThisProduct(navController) }) {
                            Icon(
                                modifier = Modifier.padding(12.dp),
                                painter = painterResource(id = R.drawable.delete),
                                contentDescription = ""
                            )
                        }
                    }

                }
            }
        }
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            PicsPicker(viewModel, cn)

            CatsPicker(viewModel)


            Text(
                modifier = Modifier.padding(16.dp),
                text = "Info",
                style = Typography.h1
            )

            MyTextField(state = viewModel.name, s = "Product name")
            MyTextField(state = viewModel.content, s = "Product Description")
            MyTextField(state = viewModel.minOrder, s = "Minimum Order Amount", isNum = true)
            MyTextField(state = viewModel.inv, s = "Product Amount in Inventory", isNum = true)
            MyTextField(state = viewModel.fPrice, s = "Original Price",  isNum = true)
            MyTextField(state = viewModel.sPrice, s = "Discounted Price",  isNum = true)

            Spacer(modifier = Modifier.padding(16.dp))

            MyButton(text = if (itemId.toInt()!=-1)"Update Product" else "Create a New Product") {

                if (itemId.toInt()==-1){
                    viewModel.createNewProduct(userId){
                        navController?.popBackStack()
                    }
                }else{
                    viewModel.updateProduct(userId){
                        navController?.popBackStack()
                    }
                }
            }

            Spacer(modifier = Modifier.padding(100.dp))
        }
    }

}


