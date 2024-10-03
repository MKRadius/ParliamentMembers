package com.example.parliamentmembers.ui.screens

import TopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    backStackEntry: NavBackStackEntry,
    viewModel: MemberViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val member = backStackEntry.arguments?.getString("param") ?: "default"

    Scaffold(
        topBar = { TopBar("", true, onNavigateUp = { navCtrl.navigateUp() }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp).fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(200.dp)
            ) {
                Column(modifier = Modifier.weight(3f)) {
                    Text(text = "Some Title", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
                    Text(text = "Some description goes here.", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier.height(150.dp).background(Color.Gray).weight(2f),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "↑",
                    modifier = Modifier.clickable { /* Handle upvote action */ }
                )
                Text(text = "10", modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = "↓",
                    modifier = Modifier.clickable { /* Handle downvote action */ }
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color.LightGray)
                        .border(1.dp, Color.Black)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "Note 1")
                    Text(
                        text = "✖",
                        modifier = Modifier.padding(start = 8.dp).align(Alignment.CenterEnd)
                            .clickable { /* Handle remove action */ }
                    )
                }

                Button(
                    onClick = { /* Handle add note action */ },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text(text = "Add Note", modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
        }
    }
}

class MemberViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepo: DataRepository,
): ViewModel() {

}