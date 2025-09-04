package com.example.gyantra.data.RepoImpl

import com.example.gyantra.common.BookCategoryModel
import com.example.gyantra.common.BookModel
import com.example.gyantra.common.ResultState
import com.example.gyantra.domain.repo.AllBooksRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AllBooksRepoImpl @Inject constructor(val firebaseDatabase: FirebaseDatabase) : AllBooksRepo {
    override fun getAllBooks(): Flow<ResultState<List<BookModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items : List<BookModel> = emptyList()
                items = snapshot.children.map {
                    it.getValue<BookModel>()!!
                }

                trySend(ResultState.Success(items))

            }

            override fun onCancelled(error: DatabaseError) {

                trySend(ResultState.Error(Exception(error.message)))
            }

        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }

    }

    override fun getAllCategory(): Flow<ResultState<List<BookCategoryModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items: List<BookCategoryModel> = emptyList()
                items = snapshot.children.map {
                    it.getValue<BookCategoryModel>()!!
                }

                trySend(ResultState.Success(items))


            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(Exception(error.message)))
            }
        }

        firebaseDatabase.reference.child("BookCategory").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getAllBooksByCategory(category: String): Flow<ResultState<List<BookModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var items : List<BookModel> = emptyList()
                items = snapshot.children.map {
                    it.getValue<BookModel>()!!
                }.filter {
                    it.category == category
                }

                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(Exception(error.message)))
            }
        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

}