package com.dre.receipt_recognition

import com.dre.core.navigation.NavigationFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ReceiptRecognitionViewModelModule {

    @Provides
    fun provideInitialState(): ReceiptRecognitionUiState = ReceiptRecognitionUiState("test")
}

@Module
@InstallIn(SingletonComponent::class)
interface ReceiptRecognitionModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindReceiptRecognitionNavigationFactory(factory: ReceiptRecognitionNavigationFactory): NavigationFactory
}