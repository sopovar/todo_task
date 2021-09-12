package com.example.sweefttodolist.dagger

import android.content.Context
import android.content.SharedPreferences
import com.example.sweefttodolist.App
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sopo Vardidze on 06.09.21
 */
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: App): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("Todo", Context.MODE_PRIVATE)
    }
}