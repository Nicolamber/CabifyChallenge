package com.nlambertucci.cabifychallenge.di

import com.nlambertucci.cabifychallenge.common.Constants
import com.nlambertucci.cabifychallenge.data.remote.DiscountFakeApi
import com.nlambertucci.cabifychallenge.data.remote.MockedImageApi
import com.nlambertucci.cabifychallenge.data.remote.ProductsApi
import com.nlambertucci.cabifychallenge.data.repository.CheckoutRepositoryImpl
import com.nlambertucci.cabifychallenge.data.repository.DiscountRepositoryImpl
import com.nlambertucci.cabifychallenge.data.repository.ImageRepositoryImpl
import com.nlambertucci.cabifychallenge.data.repository.ProductRepositoryImpl
import com.nlambertucci.cabifychallenge.domain.repository.CheckoutRepository
import com.nlambertucci.cabifychallenge.domain.repository.DiscountRepository
import com.nlambertucci.cabifychallenge.domain.repository.ImageRepository
import com.nlambertucci.cabifychallenge.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getApiInstance(): ProductsApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()
            .create(ProductsApi::class.java)
    }

    @Provides
    @Singleton
    fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideProductsRepository(api: ProductsApi): ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideFakeDiscountApi(): DiscountFakeApi {
        return DiscountFakeApi()
    }

    @Provides
    @Singleton
    fun providesDiscountRepository(discountApi: DiscountFakeApi): DiscountRepository {
        return DiscountRepositoryImpl(discountApi)
    }

    @Provides
    @Singleton
    fun provideImageApi(): MockedImageApi {
        return MockedImageApi()
    }


    @Provides
    @Singleton
    fun providesImageRepository(imageApi: MockedImageApi): ImageRepository {
        return ImageRepositoryImpl(imageApi)
    }

    @Provides
    @Singleton
    fun providesCheckoutRepository(): CheckoutRepository {
        return CheckoutRepositoryImpl()
    }
}