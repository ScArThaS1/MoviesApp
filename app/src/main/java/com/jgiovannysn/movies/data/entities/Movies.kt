package com.jgiovannysn.movies.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movies(
    @SerializedName("results") val results: List<Movie>
) : Serializable