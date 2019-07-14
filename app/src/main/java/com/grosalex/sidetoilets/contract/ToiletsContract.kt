package com.grosalex.sidetoilets.contract

import com.grosalex.sidetoilets.model.ToiletData
import com.grosalex.sidetoilets.model.Record

interface ToiletsContract {

    interface View {
        fun onError(message: String)
        fun onLoading()
        fun onBindToiletsList(list: List<ToiletData>)
    }

    interface Presenter {
        fun getToilets()
        fun refreshToilets()
    }

    interface Provider {
        interface OnToiletsFetched {
            fun onSuccess(list: List<Record>)
            fun onFailure(message: String)
        }

        fun getToilets(onToiletsFetched: OnToiletsFetched, forceRefresh:Boolean)
    }
}