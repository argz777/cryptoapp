package com.argz.cryptoapp.pages.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.argz.cryptoapp.CoinFilter

val coinFilterType = mapOf(
    "All" to CoinFilter.ALL,
    "Favorites" to CoinFilter.FAVORITES
)

@Composable
fun ChipHeader(
    currentChipSelected: MutableState<Int>,
    onChipSelect: (CoinFilter) -> Unit,
) {
    val titles = coinFilterType.keys

    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    ) {
        titles.forEachIndexed { index, title ->
            FilterChip(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .animateContentSize(),
                onClick = {
                    currentChipSelected.value = index
                    onChipSelect(coinFilterType[title]!!)
                },
                label = {
                    Text(title)
                },
                selected = (index == currentChipSelected.value),
            )
        }
    }
}