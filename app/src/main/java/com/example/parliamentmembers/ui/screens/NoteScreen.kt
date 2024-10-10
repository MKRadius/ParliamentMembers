package com.example.parliamentmembers.ui.screens

import TopBar
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMemberLocal
import com.example.parliamentmembers.ui.AppViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    navCtrl: NavController,
    noteVM: NoteViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val navBackStackEntry = navCtrl.currentBackStackEntryAsState()
    val memberLocal: ParliamentMemberLocal by noteVM.memberLocal.collectAsState()
    var noteText by remember { mutableStateOf("") }

    LaunchedEffect(navBackStackEntry) { noteVM.getData() }

    Scaffold(
        topBar = { TopBar("Note", true, onNavigateUp = { navCtrl.navigateUp() }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            TextField(
                value = noteText,
                onValueChange = { noteText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .weight(0.33f),
                label = { Text("Edit Note") }
            )

            if (noteText != memberLocal.note) {
                Button(
                    onClick = {
                        noteVM.saveNote(memberLocal.hetekaId, noteText)
                        navCtrl.navigateUp()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }

            if (memberLocal.note != "") {
                Button(
                    onClick = {
                        noteVM.deleteNote(memberLocal.hetekaId)
                        navCtrl.navigateUp()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

class NoteViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepo: DataRepository
) : ViewModel() {
    private var hetekaId: String? = savedStateHandle.get<String>("param")
    private val _memberLocal = MutableStateFlow<ParliamentMemberLocal>(
        ParliamentMemberLocal(
            0,
            false,
            null
        )
    )
    val memberLocal: StateFlow<ParliamentMemberLocal> = _memberLocal

    init { getData() }

    fun getData() = viewModelScope.launch {
        dataRepo.getMemberLocalWithId(hetekaId!!.toInt()).collect { _memberLocal.emit(it) }
        Log.e("DBG", "${_memberLocal.value}")
    }
    fun saveNote(id: Int, note: String) = viewModelScope.launch { dataRepo.updateNoteWithId(id, note) }
    fun deleteNote(id: Int) = viewModelScope.launch { dataRepo.deleteNoteWithId(id) }
}