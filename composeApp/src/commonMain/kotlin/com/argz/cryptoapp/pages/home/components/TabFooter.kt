package com.argz.cryptoapp.pages.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabFooter(
    currentPage: MutableState<Int>,
) {
    val pages = mapOf(
        "Home" to Icons.Outlined.Home,
        "Portfolio" to Icons.Outlined.AccountBalanceWallet
    )

    Column {
        PrimaryTabRow(
            selectedTabIndex = currentPage.value,
            indicator = {},
        ) {
            pages.keys.forEachIndexed { index, title ->
                Tab(
                    selected = currentPage.value == index,
                    onClick = {
                        currentPage.value = index
                    },
                    text = {
                        Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    },
                    icon = {
                        Icon(imageVector = pages[title]!!, "")
                    }
                )
            }
        }
    }
}