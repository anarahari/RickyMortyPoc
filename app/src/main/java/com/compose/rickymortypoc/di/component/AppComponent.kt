package com.compose.rickymortypoc.di.component

import com.compose.common.module.ApolloClientModule
import com.compose.data.module.DataModule
import com.compose.presentation.viewmodel.ViewModelModule
import com.compose.rickymortypoc.BaseApplication
import com.compose.rickymortypoc.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApolloClientModule::class, DataModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(application: BaseApplication)
    fun inject(activity: MainActivity)
}
