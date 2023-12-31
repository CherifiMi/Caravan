package com.example.seller.presentation.screens


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.caravan.R
import com.example.common.components.MyProductItem
import com.example.common.snackbar.SnackbarManager
import com.example.caravan.domain.navigation.Screens
import com.example.common.theme.PinkRed
import com.example.common.theme.Typography
import com.example.seller.presentation.SellerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SellerProductsScreen(
    navController: NavHostController,
    viewModel: SellerViewModel = hiltViewModel(),
    userId: String
) {

    viewModel.url.value.let {
        if (it.isNotEmpty()){
            val uriHandler = LocalUriHandler.current
            uriHandler.openUri(it)
        }
    }

    Scaffold(
        floatingActionButton =
        {
            FloatingActionButton(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 64.dp),
                backgroundColor = com.example.common.theme.PinkRed,
                onClick = {
                    val sellerActivated = viewModel.isSellerActive()
                    if (sellerActivated[0].toBooleanStrict()){
                        navController.navigate(Screens.ProductSeller.passItem(item = (-1).toString()))
                    }else{
                        com.example.common.snackbar.SnackbarManager.showMessage(R.string.payout)
                        sellerActivated[1].let {
                            viewModel.url.value = it
                        }
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        }
    ) {
        Column() {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier.alpha(if (!viewModel.loading.value) 0f else 1f),
                    text = "...",
                    style = com.example.common.theme.Typography.h1,
                    textAlign = TextAlign.Center
                )

                if (!viewModel.loading.value) {
                    if (viewModel.myProducts.value.isNullOrEmpty()) {

                        Text(
                            text = "You haven't created a product yet.",
                            style = com.example.common.theme.Typography.h1,
                            textAlign = TextAlign.Center
                        )

                    } else {
                        ProductsListScreen(viewModel, navController)
                    }
                }
            }
        }
    }

    viewModel.getSellerProducts(userId)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductsListScreen(viewModel: SellerViewModel, navController: NavHostController) {


    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(10.dp)
    ) {

        item(span = { GridItemSpan(2) }) {
            Column {

                Text(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    text = "Products: ${viewModel.myProducts.value?.size}",
                    style = com.example.common.theme.Typography.h1
                )
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Inventory: ${viewModel.getInvCount().value}",
                    style = com.example.common.theme.Typography.h1
                )
            }

        }

        itemsIndexed(items = viewModel.myProducts.value ?: listOf()) { index, item ->
            com.example.common.components.MyProductItem(item) {
                navController.navigate(Screens.ProductSeller.passItem(item = index.toString()))
            }
        }
    }
}