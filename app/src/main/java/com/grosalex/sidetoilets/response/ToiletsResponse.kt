package com.grosalex.sidetoilets.response

import com.grosalex.sidetoilets.model.Record
import com.grosalex.sidetoilets.model.WsParameters

data class ToiletsResponse(
    val nhits: Int?,
    val parameters: WsParameters?,
    val records: List<Record>
    )