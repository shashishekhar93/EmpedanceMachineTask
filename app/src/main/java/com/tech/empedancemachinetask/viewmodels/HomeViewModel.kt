package com.tech.empedancemachinetask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.empedancemachinetask.models.RootResponse
import com.tech.empedancemachinetask.models.CategoryResponse
import com.tech.empedancemachinetask.repository.MainRepository
import com.tech.empedancemachinetask.sealed_classes.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _categoriesState = MutableLiveData<ApiResult<CategoryResponse>>()
    val categoriesState: LiveData<ApiResult<CategoryResponse>> = _categoriesState
    private val _rootState = MutableLiveData<ApiResult<RootResponse>>()
    val rootState: LiveData<ApiResult<RootResponse>> = _rootState

    init {
        getCategories()
        getRoots()
    }
    fun getCategories() {
        viewModelScope.launch {
            _categoriesState.value = ApiResult.Loading
            val result = repository.getCategories()
            _categoriesState.value = result
        }
    }
    fun getRoots() {
        viewModelScope.launch {
            _rootState.value = ApiResult.Loading
            val result = repository.getRoots()
            _rootState.value = result
        }
    }
}
