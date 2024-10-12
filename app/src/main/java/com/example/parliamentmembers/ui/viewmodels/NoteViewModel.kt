/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * NoteViewModel is responsible for managing notes associated with a Parliament member
 * in the Parliament Members app. It retrieves, saves, and deletes notes from the local
 * data repository using the member's ID. The ViewModel provides a StateFlow to
 * observe the member's local data, including notes and favorite status, and
 * handles the data fetching asynchronously upon initialization and when notes are
 * updated or deleted.
 */

package com.example.parliamentmembers.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMemberLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepo: DataRepository
) : ViewModel() {
    private var hetekaId: String? = savedStateHandle.get<String>("param")
    private val _memberLocal = MutableStateFlow<ParliamentMemberLocal>(
        ParliamentMemberLocal(
            hetekaId = 0,
            favorite = false,
            note = null
        )
    )
    val memberLocal: StateFlow<ParliamentMemberLocal> = _memberLocal

    init { getData() }

    fun getData() = viewModelScope.launch {
        dataRepo.getMemberLocalWithId(hetekaId!!.toInt()).collect {
            if (it != null) _memberLocal.emit(it)
        }
    }
    fun saveNote(id: Int, note: String) = viewModelScope.launch { dataRepo.updateNoteWithId(id, note) }
    fun deleteNote(id: Int) = viewModelScope.launch { dataRepo.deleteNoteWithId(id) }
}