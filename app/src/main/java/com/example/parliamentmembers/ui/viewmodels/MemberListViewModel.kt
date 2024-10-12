/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * MemberListViewModel is responsible for managing the list of Parliament Members
 * (PM) based on a selected type, which can be either "party" or "constituency".
 * This ViewModel fetches data from the data repository and tracks the favorite
 * status of each member.
 *
 * The ViewModel maintains two properties, `type` and `selectedType`, retrieved
 * from the saved state handle, along with a MutableStateFlow `_pmList` that
 * holds a list of pairs, where each pair consists of a ParliamentMember and a
 * Boolean indicating whether the member is marked as a favorite. The init block
 * triggers the initial loading of the member list by calling `getPMList()`,
 * which fetches members based on the type and selected type, emitting the list
 * to `_pmList`. The `changeFavorite()` function allows toggling the favorite
 * status of a member by their ID and refreshes the member list accordingly.
 */

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
) : ViewModel() {
    private var type: String? = savedStateHandle.get<String>("type")
    private var selectedType: String? = savedStateHandle.get<String>("selected")
    private val _pmList = MutableStateFlow<List<Pair<ParliamentMember, Boolean>>>(listOf())
    val pmList: StateFlow<List<Pair<ParliamentMember, Boolean>>> = _pmList

    init { getPMList() }

    fun getPMList() {
        when (type) {
            "party" -> viewModelScope.launch {
                dataRepo.getAllPMWithParty(selectedType!!).collect { pmList ->
                    val pmWithFavorites = pmList.map { member ->
                        val isFavorite =
                            dataRepo.getFavoriteById(member.hetekaId).firstOrNull() ?: false
                        Pair(member, isFavorite)
                    }
                    _pmList.emit(pmWithFavorites)
                }
            }

            "constituency" -> viewModelScope.launch {
                if (selectedType == "Others") selectedType = ""
                dataRepo.getAllPMWithConstituency(selectedType!!).collect { pmList ->
                    val pmWithFavorites = pmList.map { member ->
                        val isFavorite = dataRepo.getFavoriteById(member.hetekaId).firstOrNull() ?: false
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