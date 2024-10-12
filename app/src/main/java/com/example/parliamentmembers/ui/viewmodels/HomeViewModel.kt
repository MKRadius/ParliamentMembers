/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * HomeViewModel is responsible for managing the state related to
 * the display list of parties or constituencies based on the selected sort type.
 * HomeViewModel fetches the relevant data from the DataRepository and provides the display
 * list and the sort type as StateFlow objects. It allows users to change the sort type,
 * triggering a data fetch for the corresponding list.
 */

package com.example.parliamentmembers.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parliamentmembers.data.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataRepo: DataRepository,
) : ViewModel() {
    private val _displayList = MutableStateFlow<List<String>>(listOf(""))
    private val _type = MutableStateFlow<String>("party")

    val displayList: StateFlow<List<String>> = _displayList
    val type: StateFlow<String> = _type

    init { getList() }

    private fun getList() = viewModelScope.launch {
        when (_type.value) {
            "party" -> dataRepo.getParties().collect { _displayList.emit(it) }
            "constituency" -> {
                dataRepo.getConstituencies().collect { constituencies ->
                    val filteredList = constituencies.map { if (it.isEmpty()) "Others" else it }
                    _displayList.emit(filteredList)
                }
            }
        }
    }

    fun setSortType(type: String) = viewModelScope.launch {
        _type.emit(type)
        getList()
    }
}