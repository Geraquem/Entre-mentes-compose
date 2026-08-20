package com.mmfsin.betweenminds.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_OFFLINE
import com.mmfsin.betweenminds.utils.openBedRockActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //        setContent { BMCTheme { NavigationMain() } }

//        this.openBedRockActivity(NAV_RANGES_OFFLINE)
        this.openBedRockActivity(NAV_QUESTIONS_OFFLINE)
    }
}