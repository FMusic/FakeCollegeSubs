package org.fcs.model

import com.google.gson.annotations.SerializedName

data class College (
    @SerializedName("CollegeName") val collegeName : String,
    @SerializedName("Real") val real : List<String>,
    @SerializedName("Fake") val fake : List<String>
)