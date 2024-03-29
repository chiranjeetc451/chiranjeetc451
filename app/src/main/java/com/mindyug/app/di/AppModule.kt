package com.mindyug.app.di

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mindyug.app.data.data_source.StatDatabase
import com.mindyug.app.data.preferences.*
import com.mindyug.app.data.repository.PointRepositoryImpl
import com.mindyug.app.data.repository.StatDataRepositoryImpl
import com.mindyug.app.data.repository.UserDataRepositoryImpl
import com.mindyug.app.domain.repository.PointRepository
import com.mindyug.app.domain.repository.StatDataRepository
import com.mindyug.app.domain.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideWorkManager(
        @ApplicationContext context: Context
    ) = WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideUserDataRepository(
        usersList: FirebaseFirestore,
        storageReference: StorageReference
    ): UserDataRepository {
        return UserDataRepositoryImpl(usersList, storageReference)
    }

    @Provides
    @Singleton
    fun providePointRepository(
        usersList: FirebaseFirestore,
    ): PointRepository {
        return PointRepositoryImpl(usersList)
    }

    @Provides
    @Singleton
    fun providesStorageReference() = FirebaseStorage.getInstance()
        .getReference("Users/")

    @Provides
    @Singleton
    fun providesStatDatabase(app: Application): StatDatabase {
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

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context): UserLoginState {
        return UserLoginStateImpl(context)
    }

    @Singleton
    @Provides
    fun providePointPreferenceManager(@ApplicationContext context: Context): PointSysUtils {
        return PointSysUtilsImpl(context)
    }

    @Singleton
    @Provides
    fun providesSharedPrefs(@ApplicationContext context: Context) = SharedPrefs(context)

}