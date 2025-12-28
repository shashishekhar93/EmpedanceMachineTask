package com.tech.empedancemachinetask.models

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("data")
    val `data`: List<Children>,
    @SerializedName("error")
    val error: Any,
    @SerializedName("status")
    val status: Int
)
