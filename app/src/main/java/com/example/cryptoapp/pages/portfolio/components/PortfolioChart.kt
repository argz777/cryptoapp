package com.example.cryptoapp.pages.portfolio.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.cryptoapp.model.PortfolioCoin
import com.example.cryptoapp.utils.getFraction

@Composable
fun PortfolioChart(
    portfolioCoins: SnapshotStateList<PortfolioCoin>
){
    val chartData = PieChartData(
        slices = portfolioCoins.map { it
            PieChartData.Slice(
                label = "${it.coin!!.symbol}",
                value = getFraction(portfolioCoins, it),
                color = Color(it.color),
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
        config,
    )
}