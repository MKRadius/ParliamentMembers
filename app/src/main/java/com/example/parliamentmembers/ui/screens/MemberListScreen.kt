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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.ui.AppViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun MemberListScreen(
    navCtrl: NavController,
    backStackEntry: NavBackStackEntry,
    memListVM: MemberListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val party = backStackEntry.arguments?.getString("param") ?: "Members"
    val pmList: List<ParliamentMember> by memListVM.pmList.collectAsState()

    Scaffold(
        topBar = { TopBar(party.uppercase(), true, onNavigateUp = { navCtrl.navigateUp() }) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {
            items(pmList) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                        .clickable { navCtrl.navigate(EnumScreens.MEMBER.withParam(it.hetekaId.toString())) }
                ) {
                    Text(
                        text = it.firstname,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

class MemberListViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepo: DataRepository,
): ViewModel() {
    private var party: String? = savedStateHandle.get<String>("param")
    private val _pmList = MutableStateFlow<List<ParliamentMember>>(listOf())
    val pmList: StateFlow<List<ParliamentMember>> = _pmList

    init {
        viewModelScope.launch {
            dataRepo.getAllPMWithParty(party!!).collect { _pmList.emit(it) }
        }
    }
}
