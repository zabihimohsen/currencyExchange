package zabihi.mohsen.currencyexchanger.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.internal.connection.Exchange
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import zabihi.mohsen.currencyexchanger.data.Constants
import zabihi.mohsen.currencyexchanger.data.ExchangeApi
import zabihi.mohsen.currencyexchanger.mainactivity.MainRepository
import zabihi.mohsen.currencyexchanger.mainactivity.MainRepositoryInterface
import zabihi.mohsen.currencyexchanger.util.DispatcherProvider
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideExchangeRateApi(): ExchangeApi {
        //log request
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(10,TimeUnit.SECONDS)
            .connectTimeout(10,TimeUnit.SECONDS)
            .build()
        return  Retrofit.Builder()
            .baseUrl(Constants.exchangeBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ExchangeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMainRepositoryInterface( api : ExchangeApi): MainRepositoryInterface = MainRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers() : DispatcherProvider = object : DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }
}