package com.victorhbo.technicalchallengecatspictures.models

data class ImageData(
    val id: String,
    val title: String?,
    val description: String?,
    val images: List<Image>
)