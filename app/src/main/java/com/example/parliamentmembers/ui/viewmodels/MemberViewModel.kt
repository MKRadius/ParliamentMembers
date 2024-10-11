package com.example.parliamentmembers.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMember
import com.example.parliamentmembers.model.ParliamentMemberExtra
import com.example.parliamentmembers.model.ParliamentMemberLocal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MemberViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepo: DataRepository,
): ViewModel() {
    private var hetekaId: String? = savedStateHandle.get<String>("param")
    private val _member = MutableStateFlow<ParliamentMember>(
        ParliamentMember(
            0,
            0,
            "lastname",
            "firstname",
            "party",
            false,
            ""
        )
    )
    private val _memberExtra = MutableStateFlow<ParliamentMemberExtra>(
        ParliamentMemberExtra(
            0,
            null,
            0,
            ""
        )
    )
    private val _memberLocal = MutableStateFlow<ParliamentMemberLocal>(
        ParliamentMemberLocal(
            0,
            false,
            null
        )
    )

    val member: StateFlow<ParliamentMember> = _member
    val memberExtra: StateFlow<ParliamentMemberExtra> = _memberExtra
    val memberLocal: StateFlow<ParliamentMemberLocal> = _memberLocal

    init { getData() }

    fun getData() = viewModelScope.launch {
        fetchMember()
        fetchMemberExtra()
        fetchMemberLocal()
    }

    private suspend fun fetchMember() {
        val member = dataRepo.getMemberWithId(hetekaId!!.toInt()).first()
        _member.emit(member)
    }

    private suspend fun fetchMemberExtra() {
        val memberExtra = dataRepo.getMemberExtraWithId(hetekaId!!.toInt()).first()
        _memberExtra.emit(memberExtra)
    }

    private suspend fun fetchMemberLocal() {
        val memberLocal = dataRepo.getMemberLocalWithId(hetekaId!!.toInt()).first()
        if (memberLocal != null) {
            _memberLocal.emit(memberLocal)
        }
    }

    fun changeFavorite(id: Int) = viewModelScope.launch {
        dataRepo.toggleFavorite(id)
        fetchMemberLocal()
    }
}