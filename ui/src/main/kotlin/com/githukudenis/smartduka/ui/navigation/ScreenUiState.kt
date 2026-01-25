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
package com.githukudenis.smartduka.ui.navigation

import com.githukudenis.smartduka.ui.R

data class ScreenUiState(
    val showNavIcon: Boolean = false,
    val showBottomBar: Boolean = false,
    val showFab: Boolean = false,
    val titleRes: Int? = null
)

fun getScreenUiState(route: String?): ScreenUiState {
    return when (route) {
        SmartDukaDestination.Home.route ->
            ScreenUiState(
                showNavIcon = false,
                showBottomBar = true,
                showFab = true,
                titleRes = R.string.home
            )
        SmartDukaDestination.Products.route ->
            ScreenUiState(
                showNavIcon = true,
                showBottomBar = true,
                showFab = true,
                titleRes = R.string.products
            )
        SmartDukaDestination.SalesHome.route ->
            ScreenUiState(
                showNavIcon = true,
                showBottomBar = false,
                showFab = true,
                titleRes = R.string.sales
            )
        SmartDukaDestination.SalesNew.route ->
            ScreenUiState(
                showNavIcon = true,
                showBottomBar = false,
                showFab = false,
                titleRes = R.string.new_sale
            )
        SmartDukaDestination.Settings.route ->
            ScreenUiState(
                showNavIcon = true,
                showBottomBar = false,
                showFab = false,
                titleRes = R.string.settings
            )
        else -> ScreenUiState()
    }
}
