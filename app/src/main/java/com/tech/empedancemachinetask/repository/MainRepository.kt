package com.tech.empedancemachinetask.repository

import com.tech.empedancemachinetask.models.RootResponse
import com.tech.empedancemachinetask.models.CategoryResponse
import com.tech.empedancemachinetask.retrofit.ApiInterface
import com.tech.empedancemachinetask.sealed_classes.ApiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val apiInterface: ApiInterface) {

    private var cachedCategories: CategoryResponse? = null
    private var cachedRoots: RootResponse? = null

    suspend fun getCategories(forceRefresh: Boolean = false): ApiResult<CategoryResponse> {
        if (!forceRefresh && cachedCategories != null) {
            return ApiResult.Success(cachedCategories!!)
        }
        return try {
            val response = apiInterface.getCategories()
            if (response.isSuccessful && response.body() != null) {
                cachedCategories = response.body()
                ApiResult.Success(cachedCategories!!)
            } else {
                ApiResult.Error(response.message() ?: "Unknown Error")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Network Error", e)
        }
    }


    suspend fun getRoots(forceRefresh: Boolean = false): ApiResult<RootResponse> {
        if (!forceRefresh && cachedRoots != null) {
            return ApiResult.Success(cachedRoots!!)
        }
        return try {
            val response = apiInterface.getServices()
            if (response.isSuccessful && response.body() != null) {
                cachedRoots = response.body()
                ApiResult.Success(cachedRoots!!)
            } else {
                ApiResult.Error(response.message() ?: "Unknown Error")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Network Error", e)
        }
    }


}
