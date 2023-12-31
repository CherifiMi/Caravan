package com.example.seller.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.caravan.domain.model.BottomNavItem
import com.example.common.theme.Typography

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onClick: (BottomNavItem) -> Unit
) {

    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(modifier = modifier) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                selectedContentColor = Color.White,
                onClick = { onClick(item) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(modifier = Modifier.size(25.dp ),painter = item.icon, contentDescription = null)
                        Text(text = item.name, textAlign = TextAlign.Center, style = com.example.common.theme.Typography.h4)
                    }
                }
            )
        }
    }

}
