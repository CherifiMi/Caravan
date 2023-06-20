package com.example.caravan.ui.buyer.screens


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.caravan.R
import com.example.caravan.common.components.MyProductItem
import com.example.caravan.common.components.MyTopBar
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.theme.*
import com.example.caravan.common.components.SideMenu
import com.example.caravan.ui.buyer.BuyerViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BuyerHomeScreen(
    navController: NavHostController,
    viewModel: BuyerViewModel = hiltViewModel(),
    userId: String
) {

    runBlocking {
        viewModel.getProducts()
    }

    Box(modifier = Modifier.fillMaxSize()) {

        val state = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                MyTopBar(
                    isCartV = true,
                    navController = navController,
                    function = { navController.navigate(Screens.CartBuyer.route) },
                    humClicked = {
                        scope.launch {
                            state.drawerState.open()
                        }
                    }
                )
            },
            drawerContent = { SideMenu(navController) },
            scaffoldState = state
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OurProductsWithSearch(viewModel)
                Spacer(modifier = Modifier.padding(10.dp))
                ProductCategory(viewModel)
                Spacer(modifier = Modifier.padding(10.dp))
                ProductGrid(viewModel, navController)
            }
        }

        CatsPopUp(viewModel)
    }

}

@Composable
fun CatsPopUp(viewModel: BuyerViewModel) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = viewModel.catsPopUp.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    viewModel.catsPopUp.value = false
                }) {

            Card(
                elevation = 10.dp,
                shape = RoundedCornerShape(5),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 200.dp, horizontal = 52.dp)
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                ) {

                    item {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Categories",
                            style = Typography.h1
                        )
                    }

                    items(viewModel.myCats) { item ->

                        Text(
                            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
                            text = item.name,
                            style = Typography.h3
                        )

                        LazyRow() {
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            items(items = item.subCats) { item ->

                                Spacer(modifier = Modifier.width(8.dp))

                                Card(
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable {
                                            viewModel.selectedSubCat(item)
                                        },
                                    backgroundColor = PinkRed
                                ) {
                                    Text(
                                        modifier = Modifier.padding(16.dp, 8.dp),
                                        text = item,
                                        color = Color.White,
                                        style = Typography.h4
                                    )
                                }

                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }

            }

        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OurProductsWithSearch(viewModel: BuyerViewModel) {
    var search by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 30.dp)
    ) {
        TextField(
            modifier = Modifier.scale(scaleY = 0.9F, scaleX = 1F),
            textStyle = Typography.h3,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = lightbox,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
            value = search,
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            onValueChange = { search = it },
            placeholder = {
                Text(
                    text = "Search Products",
                    style = Typography.h3,
                    color = Color.LightGray
                )
            },
            leadingIcon = {

                Icon(

                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))
                        .clickable { viewModel.search(search) }
                        .padding(8.dp),
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = lightblack
                )

            },
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.search(search)
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.width(5.dp))
        Card(
            modifier = Modifier
                .width(64.dp)
                .padding(start = 16.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            IconButton(onClick = {
                viewModel.catsPopUp.value = true
                Log.d("BUYER", "cats btn clicked")
            }
            ) {
                Icon(
                    painter = painterResource(R.drawable.filter_list),
                    contentDescription = "Filter Icon",
                    modifier = Modifier.size(20.dp, 20.dp)
                )

            }
        }

    }


}

@Composable
fun ProductCategory(viewModel: BuyerViewModel) {
    val itemList = viewModel.myCats

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(
                rememberScrollState()
            )
    ) {
        Spacer(modifier = Modifier.padding(8.dp))

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    viewModel.resetUserList()
                },
            border = BorderStroke(
                2.dp,
                if (viewModel.selectedCat.value == -1) PinkRed else LightGrey
            )
        ) {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(24.dp)
            )

            Text(
                modifier = Modifier.padding(16.dp, 8.dp),
                text = "All",
                style = Typography.h3
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        itemList.forEachIndexed { index, i ->

            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        viewModel.selectedCat.value = index
                        viewModel.changeMyList()
                    },
                border = BorderStroke(
                    2.dp,
                    if (viewModel.selectedCat.value == index) PinkRed else LightGrey
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            .size(24.dp),
                        model = i.picUrl,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(16.dp, 8.dp),
                        text = i.name,
                        style = Typography.h3
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }

}


@Composable
fun ProductGrid(viewModel: BuyerViewModel, navController: NavHostController) {

    if (viewModel.x.value.size > 0) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Top,
            contentPadding = PaddingValues(8.dp)
        ) {

            items(items = viewModel.x.value) { item ->
                MyProductItem(item!!, true) {
                    navController.navigate(Screens.ProductBuyer.passItem(index = item.id?: ""))
                }
            }
        }
    } else {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "there is no data",
                style = Typography.h1,
                textAlign = TextAlign.Center
            )
        }
    }

}

