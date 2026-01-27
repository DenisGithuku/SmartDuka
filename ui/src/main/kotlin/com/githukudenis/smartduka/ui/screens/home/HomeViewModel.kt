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
import com.githukudenis.smartduka.domain.model.Product
import com.githukudenis.smartduka.domain.model.Sale
import com.githukudenis.smartduka.domain.model.Shop
import com.githukudenis.smartduka.domain.model.Supplier
import com.githukudenis.smartduka.domain.repository.ProductRepository
import com.githukudenis.smartduka.domain.repository.SaleRepository
import com.githukudenis.smartduka.domain.repository.ShopRepository
import com.githukudenis.smartduka.domain.repository.SupplierRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val supplierRepository: SupplierRepository,
    private val saleRepository: SaleRepository,
    private val shopRepository: ShopRepository
) : ViewModel() {

    private var uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = uiState

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            combine(
                    productRepository.observeLowStock("shopId"),
                    saleRepository.observeSalesForShop("shopId"),
                    shopRepository.observeByUser("userId"),
                    supplierRepository.observeSuppliersForShop("shopId")
                ) { lowStockProducts, sales, shops, suppliers ->
                    buildUiState(
                        todayTotalSales = 0.0,
                        weeklyTotalSales = 0.0,
                        lowStockProducts = lowStockProducts,
                        shops = shops,
                        recentSales = sales,
                        suppliers = suppliers
                    )
                }
                .shareIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), replay = 1)
        }
    }

    private fun buildUiState(
        todayTotalSales: Double,
        weeklyTotalSales: Double,
        lowStockProducts: List<Product>,
        shops: List<Shop>,
        recentSales: List<Sale>,
        suppliers: List<Supplier>
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
