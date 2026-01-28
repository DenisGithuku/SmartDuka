package com.githukudenis.smartduka.ui.screens.home

import android.R.id.icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.githukudenis.smartduka.designsystem.ui.LocalDimens
import com.githukudenis.smartduka.ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    val statItems = listOf(
        StatItem(
            title = R.string.todays_sales,
            icon = R.drawable.ic_bar_chart,
            value = state.todayTotalSales ?: 0.0
        ),
        StatItem(
            title = R.string.weekly_sales,
            icon = R.drawable.ic_bar_chart,
            value = state.weeklyTotalSales ?: 0.0
        ),
        StatItem(
            title = R.string.total_profit,
            icon = R.drawable.ic_bar_chart,
            value = state.weeklyTotalSales ?: 0.0
        ),
        StatItem(
            title = R.string.low_stock,
            icon = R.drawable.ic_bar_chart,
            value = state.lowStockProducts.size.toDouble()
        )
    )

    Column(
        modifier = modifier.fillMaxSize().padding(LocalDimens.current.mediumPadding),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.mediumPadding)
    ) {
        GreetingTitle()
        Stats(
            statItems = statItems
        )
    }
}

@Composable
fun GreetingTitle(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.smallPadding)
    ) {
        Text(
            text = stringResource(R.string.greeting_title, "Denis", "Githuku"),
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
        )
        Text(
            text = stringResource(R.string.business_summary),
            style = MaterialTheme.typography.labelMedium,
            modifier = modifier
        )
    }
}

@Composable
fun Stats(modifier: Modifier = Modifier, statItems: List<StatItem>) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(LocalDimens.current.smallPadding)
        ) {
            Text(
                text = "Total Sales",
                style = MaterialTheme.typography.titleSmall
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(LocalDimens.current.smallPadding),
            ) {
                items(statItems.size) {
                    StatItem(statItem = statItems[it])
                }
            }
        }
    }
}

data class StatItem(
    val title: Int,
    val icon: Int,
    val value: Double
)

@Composable
fun StatItem(statItem: StatItem, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.smallPadding),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = statItem.icon),
            contentDescription = stringResource(id = statItem.title),
            modifier = Modifier.size(LocalDimens.current.mediumIcon)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(LocalDimens.current.extraSmallPadding)
        ) {
            Text(
                text = stringResource(id = statItem.title),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = statItem.value.toString(),
                style = MaterialTheme.typography.titleSmall
            )
        }

    }
}