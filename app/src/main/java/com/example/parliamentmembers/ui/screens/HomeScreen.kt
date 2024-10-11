package com.example.parliamentmembers.ui.screens

import TopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.ui.viewmodels.AppViewModelProvider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.parliamentmembers.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navCtrl: NavController,
    homeVM: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val displayList: List<String> by homeVM.displayList.collectAsState()
    val type: String by homeVM.type.collectAsState()

    Scaffold(
        topBar = { TopBar("", false, onNavigateUp = { navCtrl.navigateUp() }) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                        .background(if (type == "party") Color.LightGray else Color.Transparent, RoundedCornerShape(16.dp))
                        .clickable { homeVM.setSortType("party") }
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Party",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                        .background(if (type == "constituency") Color.LightGray else Color.Transparent, RoundedCornerShape(16.dp))
                        .clickable { homeVM.setSortType("constituency") }
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Constituency",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
            ) {
                items(displayList) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(120.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                            .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                            .clickable { navCtrl.navigate(EnumScreens.MEMBERLIST.withParams(type, it.toString())) }
                    ) {
                        Text(
                            text = when (type) {
                                "party" -> it.uppercase()
                                "constituency" -> it.replaceFirstChar { char -> char.uppercase() }.replace("-", "-\n")
                                else -> it
                            },
                            modifier = Modifier.align(Alignment.Center).padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}