@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cryptoapp.pages.portfolio

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.cryptoapp.MainViewModel
import com.example.cryptoapp.model.PortfolioCoin
import com.example.cryptoapp.pages.portfolio.components.AddCoinDialog
import com.example.cryptoapp.pages.portfolio.components.PortfolioCoinContent
import com.example.cryptoapp.pages.portfolio.components.UpdateCoinDialog
import com.example.cryptoapp.utils.getFraction
import com.example.cryptoapp.utils.getRandomColor

@Composable
fun PortfolioPage(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val showAddCoinDialog = remember { mutableStateOf(false) }
    val showUpdateCoinDialog = remember { mutableStateOf(false) }
    val portfolioCoins by mainViewModel.portfolioCoins.collectAsState()

    if(showAddCoinDialog.value){
        AddCoinDialog(
            showAddCoinDialog = showAddCoinDialog,
            mainViewModel = mainViewModel,
        )
    }

    if(showUpdateCoinDialog.value){
        UpdateCoinDialog(
            showUpdateCoinDialog = showUpdateCoinDialog,
            mainViewModel = mainViewModel,
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddCoinDialog.value = true
                },
            ) {
                Icon(Icons.Filled.Add, "Add a Coin")
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(portfolioCoins.isNotEmpty()){
                PortfolioChart(
                    portfolioCoins
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    portfolioCoins.forEach {
                        item(key = it.coin!!.id) {
                            PortfolioCoinContent(
                                portfolioCoin = it,
                                onClick = {
                                    mainViewModel.selectedCoin = it
                                    showUpdateCoinDialog.value = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private const val TAG = "PortfolioPage"
@Composable
fun PortfolioChart(
    portfolioCoins: SnapshotStateList<PortfolioCoin>
){
    portfolioCoins.map {
        Log.d(TAG, "PortfolioChart: ${it.coin!!.name} = ${getFraction(portfolioCoins, it)}")
    }
    Log.d(TAG, "PortfolioChart: ------------")
    val chartData = PieChartData(
        slices = portfolioCoins.map { it
            PieChartData.Slice(
                label = "${it.coin!!.name}(${it.coin!!.symbol})",
                value = getFraction(portfolioCoins, it),
                color = getRandomColor(),
            )
        },
        plotType = PlotType.Donut,
    )

    val config = PieChartConfig(
        strokeWidth = 120f,
        activeSliceAlpha = .9f,
        isAnimationEnable = true,
        labelVisible = true,
        labelColor = Color.DarkGray,
    )

    DonutPieChart(
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
            .padding(16.dp),
        chartData,
        config
    )
}