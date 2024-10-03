package com.example.parliamentmembers.ui.screens

import TopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.ui.AppViewModelProvider

@Composable
fun HomeScreen(
    navCtrl: NavController,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // TO DO: HomeScreen for updating PM information (loading screen)
    // Nav to PartyScreen when done fetching
    // PartyScreen doesnt have back button, others do

    val parties = listOf("party1", "party2", "party3")

    Scaffold(
        topBar = { TopBar("", false, onNavigateUp = { navCtrl.navigateUp() }) }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(paddingValues)
        ) {
            items(parties) {
                Box(
                    modifier = Modifier.padding(8.dp).size(120.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                        .clickable { navCtrl.navigate(EnumScreens.MEMBERLIST.withParam(it)) }
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

class HomeViewModel(
    private val dataRepo: DataRepository,
): ViewModel() {

}

