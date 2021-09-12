package com.example.sweefttodolist.dagger

import com.example.sweefttodolist.view.activity.LoginActivity
import com.example.sweefttodolist.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Sopo Vardidze on 06.09.21
 */
@Module
abstract class ActivityBinderModule {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun loginActivity(): LoginActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}