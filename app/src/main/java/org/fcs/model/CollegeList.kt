package org.fcs.model

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class CollegeList (
    @SerializedName("Colleges") val colleges : List<College>
) : JsonConvertable