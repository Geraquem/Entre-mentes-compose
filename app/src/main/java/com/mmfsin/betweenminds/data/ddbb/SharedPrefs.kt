package com.mmfsin.betweenminds.data.ddbb

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mmfsin.betweenminds.utils.SP_PACKS_PURCHASED
import com.mmfsin.betweenminds.utils.SP_PACKS_SERVER
import com.mmfsin.betweenminds.utils.SP_QUESTIONS_SERVER
import com.mmfsin.betweenminds.utils.SP_RANGES_SERVER
import com.mmfsin.betweenminds.utils.SP_VERSION_SAVED
import javax.inject.Inject

class SharedPrefs @Inject constructor(
    private val prefs: SharedPreferences
) {
    /** VERSION */
    fun getVersionSaved(): Long = prefs.getLong(SP_VERSION_SAVED, -1)
    fun updateVersionSaved(value: Long) = prefs.edit { putLong(SP_VERSION_SAVED, value) }

    /** QUESTIONS SERVER */
    fun getQuestionsFromServer(): Boolean = prefs.getBoolean(SP_QUESTIONS_SERVER, true)
    fun updateQuestionsFromServer(value: Boolean) = prefs.edit { putBoolean(SP_QUESTIONS_SERVER, value) }

    /** RANGES SERVER */
    fun getRangesFromServer(): Boolean = prefs.getBoolean(SP_RANGES_SERVER, true)
    fun updateRangesFromServer(value: Boolean) = prefs.edit { putBoolean(SP_RANGES_SERVER, value) }

    /** PACKS SERVER */
    fun getPacksFromServer(): Boolean = prefs.getBoolean(SP_PACKS_SERVER, true)
    fun updatePacksFromServer(value: Boolean) = prefs.edit { putBoolean(SP_PACKS_SERVER, value) }

    /** PACKS PURCHASED */
    fun arePacksPurchased(): Boolean = prefs.getBoolean(SP_PACKS_PURCHASED, false)
    fun updatePacksPurchased(value: Boolean) = prefs.edit { putBoolean(SP_PACKS_PURCHASED, value) }


    fun restartValues(){
        prefs.edit {
            putBoolean(SP_QUESTIONS_SERVER, false)
            putBoolean(SP_RANGES_SERVER, false)
            putBoolean(SP_PACKS_SERVER, false)
        }
    }
}