package org.fcs.model

import com.google.gson.Gson

interface JsonConvertable {
    fun toJSON(): String = Gson().toJson(this)
}

inline fun <reified T: JsonConvertable> String.toObject(): T = Gson().fromJson(this, T::class.java)