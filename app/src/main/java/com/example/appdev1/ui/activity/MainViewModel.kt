package com.example.appdev1.ui.activity

import com.example.appdev1.base.UIState
import com.example.appdev1.data.model.WebViewRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = WebViewRepository()
    private val _data = MutableLiveData<UIState<String?>>()
    val data: LiveData<UIState<String?>> = _data




     fun fetchData() {
        viewModelScope.launch {
            repository.fetchData { result ->
                if (result.isSuccess) {
                    _data.postValue(UIState.Success(result.getOrNull()))
                } else {
                    _data.postValue(result.getOrNull()?.let { UIState.Error(it) })
                }
            }
        }
    }
}