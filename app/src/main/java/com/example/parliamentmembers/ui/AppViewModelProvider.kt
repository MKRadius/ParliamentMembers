package com.example.parliamentmembers.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.parliamentmembers.ParliamentMembersApplication
import com.example.parliamentmembers.ui.screens.HomeViewModel
import com.example.parliamentmembers.ui.screens.MemberListViewModel
import com.example.parliamentmembers.ui.screens.MemberViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                pmApplication().container.dataRepo
            )
        }

        initializer {
            MemberListViewModel(
                pmApplication().container.dataRepo
            )
        }

        initializer {
            MemberViewModel(
                this.createSavedStateHandle(),
                pmApplication().container.dataRepo
            )
        }
    }
}

fun CreationExtras.pmApplication(): ParliamentMembersApplication =
    (this[APPLICATION_KEY] as ParliamentMembersApplication)