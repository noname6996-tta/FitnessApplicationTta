package com.tta.fitnessapplication.data.model.map

data class ModelMap (
    val htmlAttributions: List<Any?>? = null,
    val nextPageToken: String? = null,
    val results: List<Result>? = null,
    val status: String? = null
)

data class Result (
    val businessStatus: String? = null,
    val geometry: Geometry? = null,
    val icon: String? = null,
    val iconBackgroundColor: String? = null,
    val iconMaskBaseURI: String? = null,
    val name: String? = null,
    val openingHours: OpeningHours? = null,
    val photos: List<Photo>? = null,
    val placeID: String? = null,
    val plusCode: PlusCode? = null,
    val rating: Double? = null,
    val reference: String? = null,
    val scope: String? = null,
    val types: List<String>? = null,
    val userRatingsTotal: Long? = null,
    val vicinity: String? = null,
    val permanentlyClosed: Boolean? = null
)


data class Geometry (
    val location: Location? = null,
    val viewport: Viewport? = null
)

data class Location (
    val lat: Double? = null,
    val lng: Double? = null
)

data class Viewport (
    val northeast: Location? = null,
    val southwest: Location? = null
)

data class OpeningHours (
    val openNow: Boolean? = null
)

data class Photo (
    val height: Long? = null,
    val htmlAttributions: List<String>? = null,
    val photoReference: String? = null,
    val width: Long? = null
)

data class PlusCode (
    val compoundCode: String? = null,
    val globalCode: String? = null
)
