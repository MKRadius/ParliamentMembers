package com.example.parliamentmembers.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.ui.AppViewModelProvider

@Composable
fun HomeScreen(
    navCtrl: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // TO DO: HomeScreen for updating PM information (loading screen)
    // Nav to PartyScreen when done fetching
    // PartyScreen doesnt have back button, others do

    val parties = listOf("party1", "party2", "party3")

    Column {
        Text(
            text = "Party View",
            modifier = modifier
        )

        parties.forEach {
            Button(onClick = { navCtrl.navigate(EnumScreens.MEMBERLIST.withParam(it))}) {
                Text(it)
            }
        }
    }
}

class HomeViewModel(
    private val dataRepo: DataRepository,
): ViewModel() {

}

