package com.tech.empedancemachinetask.repository

import com.tech.empedancemachinetask.models.RootResponse
import com.tech.empedancemachinetask.models.CategoryResponse
import com.tech.empedancemachinetask.retrofit.ApiInterface
import com.tech.empedancemachinetask.sealed_classes.ApiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val apiInterface: ApiInterface) {


    suspend fun getCategories(): ApiResult<CategoryResponse> {
        return try {
            val response = apiInterface.getCategories()
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.message() ?: "Unknown Error")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Network Error", e)
        }
    }


    suspend fun getRoots(): ApiResult<RootResponse> {
        return try {
            val response = apiInterface.getServices()
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.message() ?: "Unknown Error")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Network Error", e)
        }
    }


}
