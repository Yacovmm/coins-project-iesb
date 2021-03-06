package com.example.iesbcoinapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.iesbcoinapp.core.utils.ResponseWrapper
import com.example.iesbcoinapp.core.utils.ViewState
import com.example.iesbcoinapp.data.CoinRepository
import com.example.iesbcoinapp.domain.CoinRepositoryImpl
import com.example.iesbcoinapp.domain.entities.CoinEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {

    fun favouriteCoin(id: Int, status: Boolean) = viewModelScope.launch {
        repository.favouriteCoin(id, status)
    }

    val coinsData = repository.getCoinsFromDb()
        .onStart {
            ViewState.Loading
        }
        .catch {
            ViewState.Error("Error")
        }
        .asLiveData()
}
