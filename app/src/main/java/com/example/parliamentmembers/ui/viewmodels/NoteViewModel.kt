/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * NoteViewModel manages the note-taking functionality for a specific
 * Parliament Member identified by the `hetekaId` parameter. It
 * retrieves and maintains the local data for the member, including
 * any associated notes and favorite status.
 *
 * The ViewModel initializes with a placeholder for the local member data.
 * In the `init` block, the `getData()` function is called to fetch
 * the local information for the specified member using the `hetekaId`.
 * This information is emitted as a StateFlow, allowing the UI to observe
 * changes. The `saveNote()` method allows users to update the note for
 * the member, while the `deleteNote()` method provides functionality
 * to remove the note from the database.
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