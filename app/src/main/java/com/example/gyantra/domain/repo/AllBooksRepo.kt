package com.example.gyantra.domain.repo

import com.example.gyantra.common.BookCategoryModel
import com.example.gyantra.common.BookModel
import com.example.gyantra.common.ResultState
import kotlinx.coroutines.flow.Flow

interface AllBooksRepo {

    fun getAllBooks(): Flow<ResultState<List<BookModel>>>

    fun getAllCategory(): Flow<ResultState<List<BookCategoryModel>>>

    fun getAllBooksByCategory(category: String): Flow<ResultState<List<BookModel>>>
}