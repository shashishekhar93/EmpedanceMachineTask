package com.tech.empedancemachinetask.retrofit

import com.tech.empedancemachinetask.models.CategoryResponse
import com.tech.empedancemachinetask.models.RootResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    //https://api.ippo.co/api/categories/root
    @GET("categories/root")//it will give me services which are shown with icons such as drill.
    suspend fun getServices(): Response<RootResponse>


    //https://api.ippo.co/api/categories
    @GET("categories")//gives me categories which are in banner form.
    suspend fun getCategories(): Response<CategoryResponse>


}
