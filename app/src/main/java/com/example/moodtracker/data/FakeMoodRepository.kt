package com.example.moodtracker.data

object FakeMoodRepository {
    val moodList = mutableListOf<Mood>()

    fun addMood(entry: Mood){
        moodList.add(0, entry)
    }

    fun removeMood(entry: Mood){
        moodList.remove(entry)
    }
}