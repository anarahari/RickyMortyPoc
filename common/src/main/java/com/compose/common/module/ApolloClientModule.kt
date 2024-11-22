package com.compose.common.module

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.compose.common.CommonConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
object ApolloClientModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val httpClient = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()

        return ApolloClient.Builder().serverUrl(CommonConstants.RICK_MORTY_URL)
            .okHttpClient(httpClient)
            .build()
    }
}
