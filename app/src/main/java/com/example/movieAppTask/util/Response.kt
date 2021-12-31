package  com.example.movieAppTask.util

data class Response<out T>(
    val status: Status? = null,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> success(data: T): Response<T> =
            Response(status = Status.SUCCESS, data = data)

        fun <T> error(message: String): Response<T> =
            Response(status = Status.ERROR,message = message)

        fun <T> loading(): Response<T> =
            Response(status = Status.LOADING)

        fun <T> emptyData(): Response<T> =
            Response(status = Status.EmptyState)
    }
}