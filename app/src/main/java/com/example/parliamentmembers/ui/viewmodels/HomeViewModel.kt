/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * HomeViewModel is responsible for managing the UI-related data for the home screen
 * of the Parliament Members Android application. It provides a way to fetch and
 * display a list of parties or constituencies based on the selected type.
 *
 * The ViewModel maintains two state flows: _displayList, which holds the current
 * list of strings to be displayed, and _type, which represents the current sorting
 * type (either "party" or "constituency"). The init block triggers the initial
 * fetch of data by calling getList(), which retrieves data from the data repository
 * according to the current type. The setSortType() function allows updating the type
 * and subsequently fetches the relevant data again.
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