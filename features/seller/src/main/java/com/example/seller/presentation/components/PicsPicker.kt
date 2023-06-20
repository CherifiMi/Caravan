package com.example.seller.presentation.components

import android.content.ContentResolver
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.common.theme.Typography
import com.example.seller.presentation.SellerViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PicsPicker(viewModel: SellerViewModel, cn: ContentResolver) {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Images",
            style = com.example.common.theme.Typography.h1
        )

        ImageGrid(viewModel, cn)
    }
}