package com.mmfsin.betweenminds.domain.models

enum class GameType(val id: String) {
    QUESTIONS(id = "questions_type"),
    RANGES(id = "ranges_type"),
    RANKING(id = "ranking_type");

    companion object {
        fun getGameTypeById(id: String): GameType = entries.find { it.id == id } ?: QUESTIONS
    }
}