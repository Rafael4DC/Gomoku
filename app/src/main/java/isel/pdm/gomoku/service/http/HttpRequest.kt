package isel.pdm.gomoku.service.http

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class FetchException(message: String, cause: Throwable? = null) : Exception(message, cause)

open class Requests(
    private val jsonEncoder: Gson,
    private val client: OkHttpClient
) {
    private val baseURL = "http://localhost:8080/api"

    private fun getFullUrl(uri: String) = "$baseURL$uri"

    private fun <T> T.toJsonRequestBody(): RequestBody =
        jsonEncoder.toJson(this).toRequestBody("application/json".toMediaTypeOrNull())

    private fun createRequest(url: String, method: String, body: RequestBody? = null, token: String? = null): Request {
        val builder = Request.Builder()
            .url(url)
            .header("Connection", "keep-alive")
            .addHeader("Content-Type", "application/json")

        token?.let {
            builder.addHeader("Authorization", "Bearer $it")
        }

        when (method) {
            "POST" -> builder.post(body!!)
            "GET" -> {}
        }

        return builder.build()
    }

    private fun <T> sendRequest(request: Request, responseType: Class<T>): Result<T> {
        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw FetchException("Unexpected code $response")

                val body = response.body ?: throw FetchException("Response body is null")
                Result.success(jsonEncoder.fromJson(body.string(), responseType))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun <T, R> postRequest(uri: String, body: T, responseType: Class<R>, token: String? = null): Result<R> =
        sendRequest(createRequest(getFullUrl(uri), "POST", body.toJsonRequestBody(), token), responseType)

    fun <R> getRequest(uri: String, responseType: Class<R>, token: String? = null): Result<R> =
        sendRequest(createRequest(getFullUrl(uri), "GET", token = token), responseType)
}
