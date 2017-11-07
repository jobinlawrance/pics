package com.jobinlawrance.pics.di.application

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jobinlawrance.downloadprogressinterceptor.DownloadProgressInterceptor
import com.jobinlawrance.downloadprogressinterceptor.ProgressEventBus
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by jobinlawrance on 5/9/17.
 */
@Module
@Singleton
class NetModule(val baseUrl: String, val headersMap: HashMap<String, String>) {

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()

            //add all the headers
            for ((key, value) in headersMap) {
                requestBuilder.addHeader(key, value)
            }

            requestBuilder.method(original.method(), original.body())
            val request = requestBuilder.build()

            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideStethoInterceptor(): StethoInterceptor = StethoInterceptor()

    @Provides
    @Singleton
    fun provideDownloadEventBus(): ProgressEventBus = ProgressEventBus()

    @Provides
    @Singleton
    fun provideDownloadProgressInterceptor(progressEventBus: ProgressEventBus): DownloadProgressInterceptor =
            DownloadProgressInterceptor(progressEventBus)

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor,
                            stethoInterceptor: StethoInterceptor,
                            downloadInterceptor: DownloadProgressInterceptor): OkHttpClient {

        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(stethoInterceptor)
                .addNetworkInterceptor(downloadInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }
}