package com.example.endclothing.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.endclothing.data.Product
import com.example.endclothing.utils.LoadState
import com.example.endclothing.network.repo.ProductRepository
import kotlinx.coroutines.*
import java.lang.Exception

class MainProductViewModel(val repository: ProductRepository): ViewModel() {


    private val _loadingState = MutableLiveData<LoadState>()
    val loadState: LiveData<LoadState>
    get() = _loadingState

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val products = MutableLiveData<List<Product>>()
    val productResults = repository.results

    init {
        getProducts()
//        getResultTest()
    }

    private fun getProducts(){

        viewModelScope.launch {
            try {
                _loadingState.postValue(LoadState.LOADING)
                repository.getProducts()
                _loadingState.value = LoadState.SUCCESS

            }catch (error: Exception){
                _loadingState.value = LoadState.error(error.localizedMessage)
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}