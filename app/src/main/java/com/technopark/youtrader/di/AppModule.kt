package com.technopark.youtrader.di

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.technopark.youtrader.R
import com.technopark.youtrader.database.AppDatabase
import com.technopark.youtrader.network.CryptoCurrencyApi
import com.technopark.youtrader.network.auth.AuthService
import com.technopark.youtrader.network.auth.AuthService.Companion.getDefaultValue
import com.technopark.youtrader.network.auth.FirebaseResponseState
import com.technopark.youtrader.network.auth.FirebaseService
import com.technopark.youtrader.network.auth.IAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    companion object {
        @Singleton
        @Provides
        fun provideDatabase(application: Application): AppDatabase {
            return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                "cryptoCurrencyDatabase"
            ).build()
        }

        @Singleton
        @Provides
        fun provideRetrofitInstance(application: Application): Retrofit {
            val client = OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            ).build()

            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

            return Retrofit.Builder()
                .baseUrl(application.resources.getString(R.string.base_url))
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        @Singleton
        @Provides
        fun provideCryptoCurrencyApi(retrofit: Retrofit): CryptoCurrencyApi =
            retrofit.create(CryptoCurrencyApi::class.java)

//        @Singleton
//        @Provides
//        fun provideFirebaseService(): IAuthService {
//            return FirebaseService()
//        }

        @Singleton
        @Provides
        fun provideFirebaseService(): AuthService {
            return FirebaseService().apply { getDefaultValue(FirebaseResponseState) }
        }
    }
}
