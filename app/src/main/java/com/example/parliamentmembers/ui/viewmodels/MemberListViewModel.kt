/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * MemberListViewModel is responsible for managing the state of
 * the member list for the parliament members' screen. It retrieves
 * member data from the DataRepository based on selected criteria
 * (party or constituency), and maintains the state of the list
 * along with the favorite status of each member.
 * The ViewModel initializes by fetching the member list and
 * provides functionality to toggle favorite status
 * and update the state of image availability in local storage.
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
    private val _isImageOnLocalStates = MutableStateFlow<List<Boolean>?>(null)

    val pmList: StateFlow<List<Pair<ParliamentMember, Boolean>>> = _pmList
    val isImageOnLocalStates: StateFlow<List<Boolean>?> = _isImageOnLocalStates

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

    fun updateImageState(index: Int, bool: Boolean) {
        val currentStates = _isImageOnLocalStates.value?.toMutableList()
        if (currentStates != null) {
            if (index < currentStates.size) {
                currentStates[index] = bool
                _isImageOnLocalStates.value = currentStates
            }
        }
    }
}