package com.victorhbo.technicalchallengecatspictures.utils

class Constants private constructor(){
    object RETROFIT {
        const val HTTP_TIMEOUT: Long = 10000
        const val SEARCH_PARAMETER = "cats"
        val IMAGE_EXTENSIONS = listOf(".jpg", ".png")
        const val HEADERS = "Authorization: Client-ID 1ceddedc03a5d71"
        const val GET_PARAMETER = "gallery/search/"
        const val QUERY = "q"
        const val QUERY_PAGE = "page"
        const val URL = "https://api.imgur.com/3/"
    }

    object EXTRAS {
        const val NAME = "IMAGE_URL"
    }

    object MESSAGES {
        const val TIMEOUT_ERROR_TAG = "Timeout Error."
        const val TIMEOUT_ERROR_MSG = "Tempo limite expirado."
        const val API_ERROR_TAG = "API Error."
        const val API_ERROR_MSG = "Erro desconhecido."
        const val CONECTION_ERROR_TAG = "Erro de conexão."
        const val CONECTION_ERROR_MSG = "Verifique sua internet."
        const val ACCESS_ERROR_TAG = "API ERROR."
        const val ACCESS_ERROR_MSG = "Erro ao acessar a API."
        const val NO_IMAGE_TAG = "Carregamento da imagem."
        const val NO_IMAGE_MSG = "Nenhuma imagem"

    }

}