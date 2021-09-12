package com.example.sweefttodolist.dagger

import android.content.SharedPreferences
import com.example.sweefttodolist.App
import com.example.sweefttodolist.utils.PreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Sopo Vardidze on 06.09.21
 */
@Singleton
@Component(modules = [AppModule::class, ActivityBinderModule::class, AndroidInjectionModule::class, FireBaseModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    fun prefManager(): SharedPreferences?

    fun preferencesHelper(): PreferencesHelper?

    fun getAuth(): FirebaseAuth?

    fun getFireStore(): FirebaseFirestore?

}