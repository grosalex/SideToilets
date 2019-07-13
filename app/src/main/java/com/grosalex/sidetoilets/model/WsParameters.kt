package com.grosalex.sidetoilets.model

data class WsParameters(
    val timezone:String?,
    val rows:Int?,
    val format:String?,
    val dataset: List<String>?
)