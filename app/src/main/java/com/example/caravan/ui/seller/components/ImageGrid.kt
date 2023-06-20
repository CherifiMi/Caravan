package com.example.caravan.ui.seller.components

import android.content.ContentResolver
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.caravan.theme.PinkRed
import com.example.caravan.ui.seller.SellerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageGrid(viewModel: SellerViewModel, cn: ContentResolver) {

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center

    ) {

        ImageCard(x = viewModel.image0, viewModel = viewModel, cn)
        ImageCard(x = viewModel.image1, viewModel = viewModel, cn)
        ImageCard(x = viewModel.image2, viewModel = viewModel, cn)
        ImageCard(x = viewModel.image3, viewModel = viewModel, cn)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ImageCard(x: MutableState<String>, viewModel: SellerViewModel, cn: ContentResolver) {

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.uploadImageGetUrl(x, uri, cn = cn)
        }
    }

    Card(
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(2.dp, PinkRed),
        modifier = Modifier
            .padding(4.dp)
            .size(80.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                launcher.launch("image/*")
            }
    ) {
        AsyncImage(model = x.value, contentDescription = null, contentScale = ContentScale.Crop)
    }
}