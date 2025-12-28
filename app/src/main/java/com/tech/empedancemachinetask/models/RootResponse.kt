package com.tech.empedancemachinetask.models


import com.google.gson.annotations.SerializedName

data class RootResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Any,
    @SerializedName("status")
    val status: Int
)