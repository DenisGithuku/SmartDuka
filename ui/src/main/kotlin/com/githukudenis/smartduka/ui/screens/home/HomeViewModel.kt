/*
* Copyright 2026 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.smartduka.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.smartduka.domain.model.PaymentStatus
import com.githukudenis.smartduka.domain.model.Product
import com.githukudenis.smartduka.domain.model.Sale
import com.githukudenis.smartduka.domain.repository.ProductRepository
import com.githukudenis.smartduka.domain.repository.SaleRepository
import com.githukudenis.smartduka.domain.repository.ShopRepository
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val saleRepository: SaleRepository,
    private val shopRepository: ShopRepository
) : ViewModel() {

    private var uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = uiState.asStateFlow()

    init {
        loadHomeData()
    }

    //    private fun loadHomeData() {
    //        viewModelScope.launch {
    //            val shopId = shopRepository.getShop().shopId
    //
    //            combine(
    //                    productRepository.observeLowStock(shopId),
    //                    saleRepository.getSalesBetween(todayRange().first, todayRange().second), //
    // today's sales
    //                    saleRepository.getSalesBetween(
    //                        thisWeekRange().first,
    //                        thisWeekRange().second
    //                    ), // this week's sales,
    //                    saleRepository.observeSalesForShop(shopId)
    //                ) { lowStockProducts, todaysSales, thisWeeksSales, recentSales ->
    //                    buildUiState(
    //                        todayTotalSales = todaysSales.sumOf { it.total },
    //                        weeklyTotalSales = thisWeeksSales.sumOf { it.total },
    //                        lowStockProducts = lowStockProducts,
    //                        recentSales = recentSales
    //                    )
    //                }
    //                .shareIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000),
    // replay = 1)
    //        }
    //    }
    private fun loadHomeData() {
        viewModelScope.launch {
            //            val shopId = shopRepository.getShop().shopId

            combine(
                    //                    productRepository.observeLowStock(shopId),
                    flowOf(FakeHomeData.lowStockProducts),
                    //                    saleRepository.getSalesBetween(todayRange().first,
                    // todayRange().second), // today's sales
                    flowOf(FakeHomeData.todayTotalSales),
                    //                    saleRepository.getSalesBetween(
                    //                        thisWeekRange().first,
                    //                        thisWeekRange().second
                    //                    ), // this week's sales,
                    flowOf(FakeHomeData.weeklyTotalSales),
                    //
                    //                    saleRepository.observeSalesForShop(shopId)
                    flowOf(FakeHomeData.recentSales)
                ) { lowStockProducts, todaysSales, thisWeeksSales, recentSales ->
                    buildUiState(
                        todayTotalSales = todaysSales,
                        weeklyTotalSales = thisWeeksSales,
                        lowStockProducts = lowStockProducts,
                        recentSales = recentSales
                    )
                }
                .shareIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), replay = 1)
        }
    }

    fun todayRange(): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)

        val start = today.atStartOfDay(zone).toInstant().toEpochMilli()

        val end = today.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()

        return start to end
    }

    fun thisWeekRange(): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)

        val startOfWeek = today.with(DayOfWeek.MONDAY)

        val start = startOfWeek.atStartOfDay(zone).toInstant().toEpochMilli()

        val end = startOfWeek.plusWeeks(1).atStartOfDay(zone).toInstant().toEpochMilli()

        return start to end
    }

    private fun buildUiState(
        todayTotalSales: Double,
        weeklyTotalSales: Double,
        lowStockProducts: List<Product>,
        recentSales: List<Sale>
    ): HomeUiState {
        val recentSales =
            recentSales.map {
                RecentSale(saleId = it.saleId, productName = "", amount = it.total, timestamp = it.date)
            }
        return HomeUiState(
            todayTotalSales = todayTotalSales,
            weeklyTotalSales = weeklyTotalSales,
            lowStockProducts = lowStockProducts,
            recentSales = recentSales
        )
    }
}

class FakeHomeData {
    companion object {
        val todayTotalSales: Double = 3450.75
        val weeklyTotalSales: Double = 12000.0
        val lowStockProducts: List<Product> =
            listOf(
                Product(
                    productId = "prod-1",
                    shopId = "shop-1",
                    name = "Bananas",
                    price = 100.0,
                    description = "Sweet bananas",
                    archived = false,
                    costPrice = 35.50,
                    stockQuantity = 20,
                    lowStockThreshold = 3
                ),
                Product(
                    productId = "prod-2",
                    shopId = "shop-1",
                    name = "Milk",
                    price = 80.0,
                    description = "High quality milk",
                    archived = false,
                    costPrice = 25.50,
                    stockQuantity = 40,
                    lowStockThreshold = 5
                )
            )
        val recentSales: List<Sale> =
            listOf(
                Sale(
                    saleId = "sale-1",
                    shopId = "shop-1",
                    date = 1L,
                    total = 100.0,
                    paymentStatus = PaymentStatus.PAID
                ),
                Sale(
                    saleId = "sale-2",
                    shopId = "shop-1",
                    date = 1L,
                    total = 100.0,
                    paymentStatus = PaymentStatus.PAID
                )
            )
    }
}
