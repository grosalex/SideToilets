package com.grosalex.sidetoilets.provider

import com.grosalex.sidetoilets.App
import com.grosalex.sidetoilets.api.ApiCallBack
import com.grosalex.sidetoilets.contract.ToiletsContract
import com.grosalex.sidetoilets.response.ToiletsResponse

class ToiletsProvider : ToiletsContract.Provider {
    override fun getToilets(onToiletsFetched: ToiletsContract.Provider.OnToiletsFetched, forceRefresh: Boolean) {
        val dataRepositoryRecords = App.get().dataRepository.records
        if (!forceRefresh && !dataRepositoryRecords.isNullOrEmpty()) {
            onToiletsFetched.onSuccess(dataRepositoryRecords)
            return
        }

        App.get().service.getToilets().enqueue(object : ApiCallBack<ToiletsResponse>() {
            override fun onSuccess(body: ToiletsResponse?) {
                val records = body?.records
                if (!records.isNullOrEmpty()) {
                    App.get().dataRepository.records = records
                    onToiletsFetched.onSuccess(records)
                } else {
                    onToiletsFetched.onFailure("EMPTY")
                }
            }

            override fun onAnyError(error: String) {
                onToiletsFetched.onFailure(error)
            }
        })
    }
}