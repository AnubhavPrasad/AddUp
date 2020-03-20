package com.example.listmaker.DAY

data class Data(
    var value: String = "",
    var date: String = "",
    var month: String = "",
    var items:ItemData=ItemData(),
    var id: Int = 0
)