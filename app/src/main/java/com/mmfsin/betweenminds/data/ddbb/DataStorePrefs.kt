package com.mmfsin.betweenminds.data.ddbb

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.mmfsin.betweenminds.utils.DS_SELECTED_QUESTIONS_PACK
import com.mmfsin.betweenminds.utils.DS_SELECTED_RANGES_PACK
import com.mmfsin.betweenminds.utils.DS_SELECTED_RANKINGS_PACK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePrefs @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun getSelectedQuestionsPack(): Flow<Int> =
        dataStore.data.map { preferences ->
            preferences[SELECTED_QUESTIONS_PACK] ?: 0
        }

    suspend fun updateSelectedQuestionsPack(value: Int) {
        dataStore.edit { preferences ->
            preferences[SELECTED_QUESTIONS_PACK] = value
        }
    }

    fun getSelectedRangesPack(): Flow<Int> =
        dataStore.data.map { preferences ->
            preferences[SELECTED_RANGES_PACK] ?: 0
        }

    suspend fun updateSelectedRangesPack(value: Int) {
        dataStore.edit { preferences ->
            preferences[SELECTED_RANGES_PACK] = value
        }
    }

    fun getSelectedRankingsPack(): Flow<Int> =
        dataStore.data.map { preferences ->
            preferences[SELECTED_RANKINGS_PACK] ?: 0
        }

    suspend fun updateSelectedRankingsPack(value: Int) {
        dataStore.edit { preferences ->
            preferences[SELECTED_RANKINGS_PACK] = value
        }
    }

    companion object {
        val SELECTED_QUESTIONS_PACK = intPreferencesKey(DS_SELECTED_QUESTIONS_PACK)
        val SELECTED_RANGES_PACK = intPreferencesKey(DS_SELECTED_RANGES_PACK)
        val SELECTED_RANKINGS_PACK = intPreferencesKey(DS_SELECTED_RANKINGS_PACK)
    }
}