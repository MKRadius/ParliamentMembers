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
fun MemberListScreen(
    navCtrl: NavController,
    modifier: Modifier = Modifier,
    backStackEntry: NavBackStackEntry,
    viewModel: MemberListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val party = backStackEntry.arguments?.getString("param") ?: "default"
    val members = listOf("mem1", "mem2", "mem3")

    Column {
        Text(
            text = "$party View",
            modifier = modifier
        )

        Button(onClick = { navCtrl.popBackStack() }) {
            Text(text = "Back")
        }

        members.forEach {
            Button(onClick = { navCtrl.navigate(EnumScreens.MEMBER.withParam(it))}) {
                Text(it)
            }
        }
    }
}

class MemberListViewModel(
    private val dataRepo: DataRepository,
): ViewModel() {

}
