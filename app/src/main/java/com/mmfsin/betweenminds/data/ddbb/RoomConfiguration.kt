package com.mmfsin.betweenminds.data.ddbb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmfsin.betweenminds.data.ddbb.daos.PacksDAO
import com.mmfsin.betweenminds.data.ddbb.daos.QuestionsDAO
import com.mmfsin.betweenminds.data.ddbb.daos.RangesDAO
import com.mmfsin.betweenminds.data.models.QuestionDTO

@Database(entities = [QuestionDTO::class], version = 1)
abstract class RoomConfiguration : RoomDatabase() {
    abstract fun questionsDAO(): QuestionsDAO
    abstract fun rangesDAO(): RangesDAO
    abstract fun packsDAO(): PacksDAO
}