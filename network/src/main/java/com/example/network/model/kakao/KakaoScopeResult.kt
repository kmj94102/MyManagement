package com.example.network.model.kakao

data class KakaoScopeResult(
    val scopes: List<KakaoScope>
)

data class KakaoScope(
    val id: String,
    val agreed: Boolean
)
