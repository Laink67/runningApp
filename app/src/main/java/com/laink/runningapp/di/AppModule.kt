package com.laink.runningapp.di

import android.content.Context
import androidx.room.Room
import com.laink.runningapp.db.RunningDatabase
import com.laink.runningapp.other.Constants.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class) // In old Dagger we need to create this component by ourselves
// this components are used to determine when the objects inside of our appModule are created
// we use ApplicationComponent to declare our DB dependencies. It determines when our dependencies will be created and when destroyed
object AppModule {

    @Singleton // Means that only a single instance of this dependency will be created
    // in a single time (not to create instance of database in all places which we want to inject database)
    @Provides // to tell Dagger that this function should actually provide smth for us
    fun provideRunningDatabase(
        @ApplicationContext // this annotation insert application context
        context: Context
    ) = Room.databaseBuilder(
        context,
        RunningDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: RunningDatabase) =
        db.getRunDao() // Dagger automatically recognize how it can construct that db because of manual function
}