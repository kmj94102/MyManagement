package com.example.mymanagement.util

enum class SubwayLine(
    val code: String,
    val routeCode: String,
    val lineName: String,
    val color: Long
) {
    LINE_01("1001", "1", "1호선", 0xFF0D3692),
    LINE_02("1002", "2", "2호선", 0xFF33A23D),
    LINE_03("1003", "3", "3호선", 0xFFFE5D10),
    LINE_04("1004", "4", "4호선", 0xFF00A2D1),
    LINE_05("1005", "5", "5호선", 0xFF8B50A4),
    LINE_06("1006", "6", "6호선", 0xFFC55C1D),
    LINE_07("1007", "7", "7호선", 0xFF54640D),
    LINE_08("1008", "8", "8호선", 0xFFF14C82),
    LINE_09("1009", "9", "9호선", 0xFFAA9872),
    LINE_63("1063", "??", "경의중앙", 0xFF80CAAA),
    LINE_65("1065", "공항철도", "공항철도", 0xFF3681B7),
    LINE_67("1067", "??", "경춘선", 0xFF32C6A6),
    LINE_75("1075", "??", "수인분당선", 0xFFFFCF33),
    LINE_77("1077", "??", "신분당선", 0xFFFF8C00),
    LINE_91("1091", "??", "자기부상", 0xFFFFB156),
    LINE_92("1092", "우이신설선", "우이신설경전철", 0xFFFFB156),
    LINE_99("1099", "의정부경전철", "의정부경전철", 0xFFFF8C00),
    UNKNOWN("", "", "???", 0XFF17181D);

    companion object {
        fun getSubwayLineByCode(code: String) =
            SubwayLine.values().firstOrNull { it.code == code } ?: UNKNOWN
        fun getLineColorByRouteCode(routeCode: String) =
            SubwayLine.values().firstOrNull { it.routeCode == routeCode }?.color ?: UNKNOWN.color
    }
}