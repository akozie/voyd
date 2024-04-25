package com.example.voyd.data.remote.interceptors

import com.example.voyd.data.remote.annotations.AuthenticatedRequest
import com.example.voyd.domain.interactors.SessionManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder: Request.Builder = chain.request().newBuilder()
        val allAnnotations: ArrayList<Annotation> = arrayListOf(AuthenticatedRequest())
        allAnnotations.forEach { _ ->
            val annotation = grabAnnotation<Annotation>(chain.request())
            annotation?.let {
                addAppropriateTokenForEachAnnotation(requestBuilder, it)
            }
        }
        return chain.proceed(requestBuilder.build())
    }

    private inline fun <reified T> grabAnnotation(request: Request): T? {
        return request.tag(Invocation::class.java)
            ?.method()
            ?.annotations
            ?.filterIsInstance(T::class.java)
            ?.firstOrNull()
    }

    private fun addAppropriateTokenForEachAnnotation(
        requestBuilder: Request.Builder,
        annotation: Annotation,
    ): Request.Builder {
        when (annotation) {
            is AuthenticatedRequest -> {
                sessionManager.fetchToken()?.let {
                    requestBuilder.addHeader("Authorization", "Bearer $it")
                }
            }

            else -> {
                /* Do nothing */
            }
        }
        return requestBuilder
    }
}