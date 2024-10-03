package com.example.parliamentmembers.ui.screens

import TopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.ui.AppViewModelProvider

@Composable
fun MemberListScreen(
    navCtrl: NavController,
    backStackEntry: NavBackStackEntry,
    viewModel: MemberListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val party = backStackEntry.arguments?.getString("param") ?: "default"
    val members = List(20) { "mem${it}"}

    Scaffold(
        topBar = { TopBar("List", true, onNavigateUp = { navCtrl.navigateUp() }) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            items(members) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                        .clickable { navCtrl.navigate(EnumScreens.MEMBER.withParam(it)) }
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

class MemberListViewModel(
    private val dataRepo: DataRepository,
): ViewModel() {

}
