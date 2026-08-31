package com.mmfsin.betweenminds.data.ddbb

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mmfsin.betweenminds.utils.SP_PACKS_SERVER
import com.mmfsin.betweenminds.utils.SP_QUESTIONS_SERVER
import com.mmfsin.betweenminds.utils.SP_RANGES_SERVER
import javax.inject.Inject

class SharedPrefs @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun getQuestionsFromServer(): Boolean = prefs.getBoolean(SP_QUESTIONS_SERVER, true)
    fun updateQuestionsFromServer(value: Boolean) = prefs.edit { putBoolean(SP_QUESTIONS_SERVER, value) }

    fun getRangesFromServer(): Boolean = prefs.getBoolean(SP_RANGES_SERVER, true)
    fun updateRangesFromServer(value: Boolean) = prefs.edit { putBoolean(SP_RANGES_SERVER, value) }

    fun getPacksFromServer(): Boolean = prefs.getBoolean(SP_PACKS_SERVER, true)
    fun updatePacksFromServer(value: Boolean) = prefs.edit { putBoolean(SP_PACKS_SERVER, value) }
}