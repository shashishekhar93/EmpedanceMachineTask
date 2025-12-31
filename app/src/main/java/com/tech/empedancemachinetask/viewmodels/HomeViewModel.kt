package com.tech.empedancemachinetask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.empedancemachinetask.common.NetworkHelper
import com.tech.empedancemachinetask.models.CategoryResponse
import com.tech.empedancemachinetask.models.RootResponse
import com.tech.empedancemachinetask.repository.MainRepository
import com.tech.empedancemachinetask.sealed_classes.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _categoriesState = MutableLiveData<ApiResult<CategoryResponse>>()
    val categoriesState: LiveData<ApiResult<CategoryResponse>> = _categoriesState
    private val _rootState = MutableLiveData<ApiResult<RootResponse>>()
    val rootState: LiveData<ApiResult<RootResponse>> = _rootState

    private val _uiEvent = Channel<String>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        observeNetworkConnection()
    }

    private fun observeNetworkConnection() {
        viewModelScope.launch {
            var isFirstEmission = true
            networkHelper.observeNetworkStatus().collect { isConnected ->
                if (isConnected) {
                    if (isFirstEmission) {
                        getCategories()
                        getRoots()
                    } else {
                        _uiEvent.send("Internet Connected")
                        retryFetchingData()
                    }
                } else {
                    _uiEvent.send("No Internet Connection")
                }
                isFirstEmission = false
            }
        }
    }

    private fun retryFetchingData() {
        if (_categoriesState.value is ApiResult.Error || _categoriesState.value == null) {
            getCategories()
        }
        if (_rootState.value is ApiResult.Error || _rootState.value == null) {
            getRoots()
        }
    }

    fun getCategories(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _categoriesState.value = ApiResult.Loading
            val result = repository.getCategories(isRefresh)
            _categoriesState.value = result
        }
    }

    fun getRoots(isRefresh: Boolean = false) {
        viewModelScope.launch {
            _rootState.value = ApiResult.Loading
            val result = repository.getRoots(isRefresh)
            _rootState.value = result
        }
    }
}
