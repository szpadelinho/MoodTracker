package com.example.moodtracker.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mood (
    val description: String,
    val feeling: String,
    val type: String,
    val statements: MutableList<String>,
    val rating: Float,
    val importance: Boolean
): Parcelable