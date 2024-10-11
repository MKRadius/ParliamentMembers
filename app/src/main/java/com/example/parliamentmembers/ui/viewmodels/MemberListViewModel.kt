package com.example.parliamentmembers.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parliamentmembers.data.DataRepository
import com.example.parliamentmembers.model.ParliamentMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MemberListViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataRepo: DataRepository,
): ViewModel() {
    private var type: String? = savedStateHandle.get<String>("type")
    private var name: String? = savedStateHandle.get<String>("name")
    private val _pmList = MutableStateFlow<List<Pair<ParliamentMember, Boolean>>>(listOf())
    val pmList: StateFlow<List<Pair<ParliamentMember, Boolean>>> = _pmList

    init { getPMList() }

    fun getPMList() {
        when (type) {
            "party" -> viewModelScope.launch {
                dataRepo.getAllPMWithParty(name!!).collect { pmList ->
                    val pmWithFavorites = pmList.map { member ->
                        val isFavorite =
                            dataRepo.getFavoriteById(member.hetekaId).firstOrNull() ?: false
                        Pair(member, isFavorite)
                    }
                    _pmList.emit(pmWithFavorites)
                }
            }

            "constituency" -> viewModelScope.launch {
                if (name == "Others") name = ""
                dataRepo.getAllPMWithConstituency(name!!).collect { pmList ->
                    val pmWithFavorites = pmList.map { member ->
                        val isFavorite =
                            dataRepo.getFavoriteById(member.hetekaId).firstOrNull() ?: false
                        Pair(member, isFavorite)
                    }
                    _pmList.emit(pmWithFavorites)
                }
            }
        }
    }

    fun changeFavorite(id: Int) = viewModelScope.launch {
        dataRepo.toggleFavorite(id)
        getPMList()
    }
}