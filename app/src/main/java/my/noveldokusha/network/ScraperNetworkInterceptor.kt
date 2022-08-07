package my.noveldokusha.network

import okhttp3.Interceptor
import okhttp3.Response

private const val DEFAULT_USERAGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64)"

class UserAgentInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val hasNoUserAgent = originalRequest.header("User-Agent").isNullOrBlank()
        val modifiedRequest = if (hasNoUserAgent) {
            originalRequest
                .newBuilder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent", DEFAULT_USERAGENT)
                .build()
        } else originalRequest
        return chain.proceed(modifiedRequest)
    }
}