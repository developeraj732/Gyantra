package com.example.gyantra.data.di

import com.example.gyantra.data.RepoImpl.AllBooksRepoImpl
import com.example.gyantra.domain.repo.AllBooksRepo
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideRealtimeDatabase() : FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage() : FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideAllBooksRepo(firebaseDatabase: FirebaseDatabase) : AllBooksRepo {
        return AllBooksRepoImpl(firebaseDatabase)

    }

}