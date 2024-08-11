package com.example.artworkgallery.dtos

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.time.LocalDate

data class ArtworkDto(
    val id: Int,
    @DrawableRes val artworkImage: Int,
    @StringRes val artworkTitle: Int,
    val artworkAuthor: String,
    val artworkReleaseDate: LocalDate
)
