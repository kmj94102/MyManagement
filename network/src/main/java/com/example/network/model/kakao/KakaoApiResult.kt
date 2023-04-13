package com.example.network.model.kakao

data class KakaoApiResult <T> (
    val events: List<T>
)