/*
 * Date: 12th October 2024
 * Author: Khai Cao
 * Student ID: 2216586
 *
 * The AppContainer interface serves as a contract for providing access to the
 * application's data repository. It defines a property for the DataRepository,
 * which is responsible for managing data operations within the app.
 * The AppDataContainer class implements the AppContainer interface and provides
 * a concrete implementation for accessing the DataRepository. It initializes
 * the OfflineDataRepository using the provided context and DataStore for
 * managing preferences. The repository is lazily instantiated to improve performance.
 */

package com.example.parliamentmembers.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface AppContainer {
    val dataRepo: DataRepository
}

class AppDataContainer(context: Context, dataStore: DataStore<Preferences>): AppContainer {
    override val dataRepo: DataRepository by lazy {
        OfflineDataRepository(context, dataStore)
    }
}