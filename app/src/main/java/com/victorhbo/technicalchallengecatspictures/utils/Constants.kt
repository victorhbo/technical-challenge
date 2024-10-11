package com.victorhbo.technicalchallengecatspictures.utils

object Constants {
    const val HTTP_TIMEOUT: Long = 10000
    const val SEARCH_PARAMETER = "cats"
    val IMAGE_EXTENSIONS = listOf(".jpg", ".png")

    const val HEADERS = "Authorization: Client-ID 1ceddedc03a5d71"
    const val GET_PARAMETER = "gallery/search/"
    const val QUERY = "q"
    const val URL = "https://api.imgur.com/3/"

    const val EXTRAS_NAME = "IMAGE_URL"

    const val TIMEOUT_ERROR = "Timeout Error"
    const val TIMEOUT_ERROR_MSG = "Tempo limite expirado"
    const val API_ERROR = "API Error"
    const val API_ERROR_MSG = "Erro desconhecido"
}