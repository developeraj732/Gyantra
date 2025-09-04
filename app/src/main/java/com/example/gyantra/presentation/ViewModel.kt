package com.example.gyantra.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gyantra.common.BookCategoryModel
import com.example.gyantra.common.BookModel
import com.example.gyantra.common.ResultState
import com.example.gyantra.domain.repo.AllBooksRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repo: AllBooksRepo) : ViewModel() {

    private val _state : MutableState<ItemState> = mutableStateOf(ItemState())
    val state : MutableState<ItemState> = _state

    fun getAllBooks() {

        viewModelScope.launch {
            repo.getAllBooks().collect {

                when(it) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _state.value = ItemState(items = it.data)
                    }

                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.message.toString())
                    }
                }

            }

        }
    }

    fun getAllCategory() {
        viewModelScope.launch {
            repo.getAllCategory().collect {
                when(it) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _state.value = ItemState(category = it.data)
                    }

                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.message.toString())
                    }
                }
            }
        }
    }

    fun getAllBooksByCategory(category: String) {

        viewModelScope.launch {
            repo.getAllBooksByCategory(category).collect {
                when(it) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _state.value = ItemState(items = it.data)
                    }

                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.message.toString())
                    }
                }

            }

        }

    }


}

data class ItemState(

    val isLoading : Boolean = false,
    val items : List<BookModel> = emptyList(),
    val error : String = "",
    val category : List<BookCategoryModel> = emptyList()

)