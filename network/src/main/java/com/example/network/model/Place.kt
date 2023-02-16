package com.example.network.model

import com.google.gson.annotations.SerializedName

data class PlaceResult(
    val documents: List<Place>
)

/**
 * 장소
 * @param placeName 장소명, 업체명
 * @param x X 좌표값, 경위도인 경우 longitude (경도)
 * @param y Y 좌표값, 경위도인 경우 latitude(위도)
 * @param categoryGroupCode 중요 카테고리만 그룹핑한 카테고리 그룹 코드
 * ###
 * 카테고리 종류
 * * SW8 : 지하철역
 * * PO3 : 공공기관
 * * 그 외 @see "https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword-request-category-group-code"
 * **/
data class Place(
    @SerializedName("category_group_code")
    val categoryGroupCode: String,
    @SerializedName("place_name")
    val placeName: String,
    val x: Float,
    val y: Float
)