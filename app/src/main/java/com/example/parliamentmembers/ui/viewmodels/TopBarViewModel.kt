/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * TopBarViewModel is responsible for managing the theme state of the TopBar
 * in the Parliament Members app. It retrieves the current theme state from the
 * data repository and provides a StateFlow to observe changes in the
 * dark theme setting. The ViewModel also includes a method to toggle
 * the theme, updating the repository accordingly.
 */

package com.example.parliamentmembers.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parliamentmembers.data.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TopBarViewModel(
    private val dataRepo: DataRepository
) : ViewModel() {
    private var _isDarkTheme = MutableStateFlow<Boolean>(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    init {
        viewModelScope.launch {
            dataRepo.isDarkThemeFlow.collect { _isDarkTheme.emit(it) }
        }
    }

    fun toggleTheme() = viewModelScope.launch { dataRepo.toggleTheme() }
}