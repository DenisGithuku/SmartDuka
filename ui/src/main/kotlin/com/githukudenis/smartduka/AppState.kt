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
package com.githukudenis.smartduka

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

data class AppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState
) {
    fun navigate(route: String, popupTo: String? = null, inclusive: Boolean = false) {
        navController.navigate(route) { popupTo?.let { popUpTo(it) { this.inclusive = inclusive } } }
    }

    val startDestination: String
        get() = SmartDukaDestination.Splash.route

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun popBackStack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): AppState =
    remember(navController, snackbarHostState) {
        AppState(navController = navController, snackbarHostState = snackbarHostState)
    }

sealed class SmartDukaDestination(open val route: String) {
    data object Home : SmartDukaDestination(route = "home")

    data object Products : SmartDukaDestination(route = "products")

    data object ProductDetail : SmartDukaDestination(route = "products/{productId}") {
        fun createRoute(productId: String) = "products/$productId"
    }

    data object Suppliers : SmartDukaDestination(route = "suppliers")

    data object Settings : SmartDukaDestination(route = "settings")

    data object Splash : SmartDukaDestination(route = "splash")

    data object Sales : SmartDukaDestination("sales")

    data object SalesHome : SmartDukaDestination("sales/home")

    data object SalesNew : SmartDukaDestination("sales/new")

    data object SalesDetails : SmartDukaDestination("sales/{saleId}") {
        fun createRoute(saleId: String) = "sales/$saleId"
    }
}
