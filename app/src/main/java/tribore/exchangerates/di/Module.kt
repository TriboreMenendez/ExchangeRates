package tribore.exchangerates.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import tribore.exchangerates.data.network.CurrencyNetworkApi
import tribore.exchangerates.data.repository.CurrencyRepositoryImpl
import tribore.exchangerates.domain.usecase.GetListCurrencyUseCase
import javax.inject.Singleton

const val BASE_URL = "https://www.cbr-xml-daily.ru/"

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideGetMoshi() = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideGetRetrofit(moshi: Moshi) = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideGetNetworkApi(retrofit: Retrofit) =
        retrofit.create(CurrencyNetworkApi::class.java)

    @Provides
    @Singleton
    fun provideGetRepositoryImpl(networkApi: CurrencyNetworkApi) =
        CurrencyRepositoryImpl(networkApi)

    @Provides
    @Singleton
    fun provideGetListCurrencyUseCase(repositoryImpl: CurrencyRepositoryImpl) =
        GetListCurrencyUseCase(repositoryImpl)
}


