package com.jobinlawrance.pics.application

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by jobinlawrance on 5/9/17.
 */
@Module
class NetModule(val baseUrl: String, val headersMap: HashMap<String, String>) {

    @Provides
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
    fun provideStethoInterceptor(): StethoInterceptor = StethoInterceptor()

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor, stethoInterceptor: StethoInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(stethoInterceptor)
                .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }
}