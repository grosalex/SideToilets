package com.grosalex.sidetoilets.contract

import com.grosalex.sidetoilets.model.MarkerData
import com.grosalex.sidetoilets.model.Record

interface ToiletsContract {

    interface View {
        fun onError(message: String)
        fun onLoading()
        fun onBindToiletsList(list: List<MarkerData>)
    }

    interface Presenter {
        fun getToilets()
    }

    interface Provider {
        interface OnToiletsFetched {
            fun onSuccess(list: List<Record>)
            fun onFailure(message: String)
        }

        fun getToilets(onToiletsFetched: OnToiletsFetched)
    }
}