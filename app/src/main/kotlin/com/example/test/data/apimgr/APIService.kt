package com.example.test.data.apimgr

import com.example.test.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class APIService private constructor() {
    companion object {
        private var mAccessToken = ""
        private var mLoggable = false
        private var apiService = APIService()
        fun setAccessToken(accessToken: String) {
            mAccessToken = accessToken
        }

        fun setLoggable(loggable: Boolean) {
            mLoggable = loggable
        }

        fun <T> build(clazz: Class<T>):T {
            return apiService.buildService(BuildConfig.API_SERVICE_URL, clazz)
        }
    }

    private fun <T> buildService(baseUrl: String, serviceClazz: Class<T>): T {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.followRedirects(false)
        httpClientBuilder.followSslRedirects(false)
        httpClientBuilder.addInterceptor {
            val request = it.request()
            val url = request.url.toString().replace("%3D", "=")
            val response = it.proceed(
                request.newBuilder().url(url).run {
                    addHeader(
                        "Accept-Language",
                        Locale.getDefault().language
                    )
                    addHeader(
                        "AccessToken",
                        mAccessToken
                    )
                    this
                }.build()
            )
            response
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        if (mLoggable) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

//        httpClientBuilder.cache(Cache(context.cacheDir, CACHE_SIZE_BYTES))

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()

        return retrofit.create(serviceClazz)
    }
}