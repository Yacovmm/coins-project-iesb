package com.example.iesbcoinapp.di

import androidx.recyclerview.widget.DiffUtil
import com.example.iesbcoinapp.BuildConfig
import com.example.iesbcoinapp.core.utils.Constants.BASE_URL
import com.example.iesbcoinapp.data.CoinRepository
import com.example.iesbcoinapp.data.database.CoinDao
import com.example.iesbcoinapp.data.network.ApiService
import com.example.iesbcoinapp.data.network.interceptors.HeaderInterceptor
import com.example.iesbcoinapp.data.network.utils.RetrofitWrapper
import com.example.iesbcoinapp.data.network.utils.RetrofitWrapperImpl
import com.example.iesbcoinapp.domain.CoinRepositoryImpl
import com.example.iesbcoinapp.domain.entities.CoinEntity
import com.example.iesbcoinapp.presentation.main.MainAdapter
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .build()
        }
    }


    @Provides
    @Singleton
    fun provideRetrofitInstance(
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitWrapper(): RetrofitWrapper = RetrofitWrapperImpl()

    @Provides
    @Singleton
    fun provideRepository(
        service: ApiService,
        dao: CoinDao,
        retrofitWrapper: RetrofitWrapper
    ): CoinRepository = CoinRepositoryImpl(service, dao, retrofitWrapper)


    @Provides
    @Singleton
    fun provideDiffUtilCallback() : DiffUtil.ItemCallback<CoinEntity> = object : DiffUtil.ItemCallback<CoinEntity>() {
            override fun areItemsTheSame(
                oldItem: CoinEntity,
                newItem: CoinEntity
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: CoinEntity,
                newItem: CoinEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }

    @Provides
    fun provideMainAdapter(
        diffUtil: DiffUtil.ItemCallback<CoinEntity>
    ): MainAdapter = MainAdapter(diffUtil)

}