package com.example.parliamentmembers.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.ui.AppViewModelProvider

@Composable
fun MemberScreen(
    navCtrl: NavController,
    modifier: Modifier = Modifier,
    backStackEntry: NavBackStackEntry,
    viewModel: MemberViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val member = backStackEntry.arguments?.getString("param") ?: "default"

    Column {
        Text(
            text = "$member View",
            modifier = modifier
        )
        Button(onClick = { navCtrl.popBackStack() }) {
            Text(text = "Back")
        }
    }
}

class MemberViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepo: DataRepository,
): ViewModel() {

}