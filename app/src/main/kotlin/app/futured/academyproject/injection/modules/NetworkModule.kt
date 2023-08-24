package app.futured.academyproject.injection.modules

import app.futured.academyproject.BuildConfig
import app.futured.academyproject.data.remote.ApiService
import app.futured.academyproject.tools.Constants.Api.BASE_PROD_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor {
            Timber.d(it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(
        okHttpClient: OkHttpClient,
        json: Json,
    ): ApiService {
        val converter = json.asConverterFactory("application/json".toMediaType())
        return Retrofit.Builder()
            .baseUrl(BASE_PROD_URL)
            .client(okHttpClient)
            .addConverterFactory(converter)
            .validateEagerly(BuildConfig.DEBUG)
            .build()
            .create()
    }
}
