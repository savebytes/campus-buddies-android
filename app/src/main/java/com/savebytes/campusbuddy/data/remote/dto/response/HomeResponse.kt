package com.savebytes.campusbuddy.data.remote.dto.response

import com.savebytes.campusbuddy.domain.model.HomeFilmzyItem


data class HomeResponse(
    val trending: List<HomeFilmzyItem>,
    val topRatedMovies: List<HomeFilmzyItem>,
    val topRatedTvSeries: List<HomeFilmzyItem>,
    val popularMovies: List<HomeFilmzyItem>,
    val popularTvSeries: List<HomeFilmzyItem>,
    val genres: List<String>
)