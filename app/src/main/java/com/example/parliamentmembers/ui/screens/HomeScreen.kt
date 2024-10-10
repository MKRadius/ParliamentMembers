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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.ui.AppViewModelProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navCtrl: NavController,
    homeVM: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // TO DO: HomeScreen for updating PM information (loading screen)
    // Nav to PartyScreen when done fetching
    // PartyScreen doesnt have back button, others do

    val parties: List<String> by homeVM.parties.collectAsState()

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
                        text = it.uppercase(),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

class HomeViewModel(
    dataRepo: DataRepository,
): ViewModel() {
    private val _parties = MutableStateFlow<List<String>>(listOf(""))
    val parties: StateFlow<List<String>> = _parties

    init {
        viewModelScope.launch {
            dataRepo.getParties().collect { _parties.value = it }
        }
    }
}

