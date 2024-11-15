package com.compose.common.module

import com.apollographql.apollo.ApolloClient
import com.compose.common.CommonConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApolloClientModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        val apolloClient =
            ApolloClient
                .Builder()
                .serverUrl(CommonConstants.RICK_MORTY_URL)
                .build()
        return apolloClient
    }
}
