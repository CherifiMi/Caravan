package com.example.caravan.ui.seller.components

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.caravan.domain.model.Cat
import com.example.common.theme.LightGrey
import com.example.common.theme.PinkRed
import com.example.common.theme.Typography
import com.example.caravan.ui.seller.SellerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CatsPicker(viewModel: SellerViewModel) {
    Column(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Categories",
            style = com.example.common.theme.Typography.h1
        )


        for (i in viewModel.myCats){
            CatItem(i, viewModel)
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CatItem(i: Cat, viewModel: SellerViewModel) {

    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = i.name,
        style = com.example.common.theme.Typography.h3
    )

    LazyRow(){
        item {
            Spacer(modifier = Modifier.width(8.dp))
        }
        items(items = i.subCats){ item ->
            SubCatCard(item, viewModel)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SubCatCard(item: String, viewModel: SellerViewModel) {

    var isSelected = remember { mutableStateOf(true) }

    isSelected.value = viewModel.cats.contains(item)

    Spacer(modifier = Modifier.width(8.dp))

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                if (!isSelected.value) {
                    viewModel.cats.add(item)
                    isSelected.value = !isSelected.value
                } else {
                    viewModel.cats.remove(item)
                    isSelected.value = !isSelected.value
                }

                Log.d(viewModel.tag, viewModel.cats.toString())
            },
        border = BorderStroke(2.dp, if (isSelected.value) com.example.common.theme.PinkRed else com.example.common.theme.LightGrey)
    ) {
        Text(
            modifier = Modifier.padding(16.dp, 8.dp),
            text = item,
            style = com.example.common.theme.Typography.h4
        )
    }

}