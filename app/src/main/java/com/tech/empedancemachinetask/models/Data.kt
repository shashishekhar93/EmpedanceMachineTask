package com.tech.empedancemachinetask.models


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bannerUrl")
    val bannerUrl: String,
    @SerializedName("children")
    val children: List<Children>,
    @SerializedName("commonDaysOfWarranty")
    val commonDaysOfWarranty: Any,
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("iconUrl")
    val iconUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isStatic")
    val isStatic: Boolean,
    @SerializedName("isVisible")
    val isVisible: Boolean,
    @SerializedName("issues")
    val issues: Any,
    @SerializedName("mobileBannerUrl")
    val mobileBannerUrl: String,
    @SerializedName("mobileIconUrl")
    val mobileIconUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("parentId")
    val parentId: Any,
    @SerializedName("sequenceId")
    val sequenceId: Int,
    @SerializedName("uiRoute")
    val uiRoute: Any,
    @SerializedName("updatedDate")
    val updatedDate: String
)