/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * This file contains utility functions and objects for the Parliament Members
 * application. It provides a way to access the ParliamentMembersApplication
 * instance and a factory for creating view models with necessary dependencies.
 */

package com.example.parliamentmembers.ui.viewmodels

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.parliamentmembers.ParliamentMembersApplication

fun CreationExtras.pmApplication(): ParliamentMembersApplication =
    (this[APPLICATION_KEY] as ParliamentMembersApplication)

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                pmApplication().container.dataRepo
            )
        }

        initializer {
            MemberListViewModel(
                this.createSavedStateHandle(),
                pmApplication().container.dataRepo,
            )
        }

        initializer {
            MemberViewModel(
                this.createSavedStateHandle(),
                pmApplication().container.dataRepo
            )
        }

        initializer {
            NoteViewModel(
                this.createSavedStateHandle(),
                pmApplication().container.dataRepo
            )
        }

        initializer {
            TopBarViewModel(
                pmApplication().container.dataRepo
            )
        }
    }
}