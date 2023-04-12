package com.appmovies.peliculas.data.api.responses

import com.google.gson.annotations.Expose
import java.util.Objects

class DetailResponse(
    @Expose val adult:Boolean,
    @Expose val backdrop_path:String?,
    @Expose val belongs_to_collection:Objects?,
    @Expose val budget:Int?,
    @Expose val genres:List<GenresResponse>?,
    @Expose val homepage:String?,
    @Expose val id:Int,
    @Expose val imdb_id:String?,
    @Expose val original_language:String,
    @Expose val original_title:String,
    @Expose val overview:String?,
    @Expose val popularity:Float,
    @Expose val poster_path:String?,
    @Expose val production_companies: List<CompaniesResponse>,
    @Expose val production_countries: List<ProductionContriesResponse>,
    @Expose val release_date: String,
    @Expose val revenue: Int,
    @Expose val runtime: Int?,
    @Expose val spoken_languages: List<SpokeLanguagesResponse>,
    @Expose val status:String,
    @Expose val tagline: String?,
    @Expose val title: String,
    @Expose val video: Boolean,
    @Expose val vote_average: Float,
    @Expose val vote_count: Int
)