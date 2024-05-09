package ui.stock.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Composable
fun AddStockScreen(
    searchViewModel: SearchEngineViewModel,
    inventoryViewModel: InventoryViewModel
) {

    var isAddStock by remember {
        mutableStateOf(false)
    }

    Scaffold {
        Button(
            onClick = {
                isAddStock = true
            }
        ) {
            Text("Adjustment Stock")
        }

        if (isAddStock) {
            AddNewStock(
                searchViewModel = searchViewModel,
                inventoryViewModel = inventoryViewModel,
                callback = {
                    isAddStock = false
                }
            )
        }

    }
}