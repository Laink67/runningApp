package com.laink.runningapp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.laink.runningapp.db.RunningDatabase
import com.laink.runningapp.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.laink.runningapp.other.Constants.KEY_NAME
import com.laink.runningapp.other.Constants.KEY_WEIGHT
import com.laink.runningapp.other.Constants.RUNNING_DATABASE_NAME
import com.laink.runningapp.other.Constants.SHARED_PREFERENCES_NAME
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

    @Singleton
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

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            MODE_PRIVATE // Only our app is allowed to read share preferences
        )

    @Singleton
    @Provides
    fun provideName(sharedPreferences: SharedPreferences) =
        sharedPreferences.getString(KEY_NAME, "") ?: "" // Weird getString function can return null

    @Singleton
    @Provides
    fun provideWeight(sharedPreferences: SharedPreferences) =
        sharedPreferences.getFloat(KEY_WEIGHT, 70F)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPreferences: SharedPreferences) =
        sharedPreferences.getBoolean(KEY_FIRST_TIME_TOGGLE, true)

}