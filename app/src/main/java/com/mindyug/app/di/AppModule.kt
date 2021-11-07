package com.mindyug.app.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mindyug.app.data.data_source.StatDatabase
import com.mindyug.app.data.repository.StatDataRepositoryImpl
import com.mindyug.app.data.repository.UserDataRepositoryImpl
import com.mindyug.app.domain.model.StatData
import com.mindyug.app.domain.repository.StatDataRepository
import com.mindyug.app.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFirestoreInstance() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providesUsersList(onlineDatabase: FirebaseFirestore) = onlineDatabase.collection("users")

    @Provides
    @Singleton
    fun providesStorageReference() = FirebaseStorage.getInstance()
        .getReference("Users/")

    @Provides
    @Singleton
    fun provideUserDataRepository(
        usersList: CollectionReference,
        storageReference: StorageReference
    ): UserDataRepository {
        return UserDataRepositoryImpl(usersList, storageReference)
    }

    @Provides
    @Singleton
    fun providesNoteDatabase(app: Application): StatDatabase {
        return Room.databaseBuilder(
            app,
            StatDatabase::class.java,
            StatDatabase.DATABASE_NAME
        ).build()

    }

    @Provides
    @Singleton
    fun provideStatDataRepository(db: StatDatabase): StatDataRepository {
        return StatDataRepositoryImpl(db.statDao)
    }


}