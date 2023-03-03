package com.example.network.model

data class BusApiResult <T> (
    val response: BusApiResultContents<T>
)

data class BusApiResultContents <T> (
    val header: BusApiResultHeader,
    val body: BusApiResultBody<T>
)

/**
 * @param resultCode 결과 코드
 * @param resultMsg 결과 메세지
 * **/
data class BusApiResultHeader(
    val resultCode: String,
    val resultMsg: String
)

/**
 * @param numOfRows 한 페이지 결과 수
 * @param pageNo 페이지 수
 * @param totalCount 데이터 총 개수
 * **/
data class BusApiResultBody <T> (
    val items: BusApiResultItem<T>,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int,
)

data class BusApiResultItem <T> (
    val item: T
)