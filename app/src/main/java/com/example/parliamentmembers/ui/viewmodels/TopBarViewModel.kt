/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * TopBarViewModel manages the theme state for the application, specifically
 * handling the dark theme preference. It uses a StateFlow to provide a
 * reactive way to observe the current theme state.
 *
 * The ViewModel initializes with a default theme state of light mode
 * (false). In the `init` block, it collects the dark theme preference
 * from the DataRepository and updates the `_isDarkTheme` StateFlow
 * accordingly, allowing the UI to react to changes in theme state.
 * The `toggleTheme()` function provides the ability to switch between
 * dark and light themes by invoking the corresponding method in the
 * DataRepository.
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