package com.grosalex.sidetoilets.presenter

import com.google.android.gms.maps.model.LatLng
import com.grosalex.sidetoilets.contract.ToiletsContract
import com.grosalex.sidetoilets.model.ToiletData
import com.grosalex.sidetoilets.model.Record

class ToiletsPresenter(private val view: ToiletsContract.View, private val provider: ToiletsContract.Provider) :
    ToiletsContract.Presenter,
    ToiletsContract.Provider.OnToiletsFetched {

    override fun onSuccess(list: List<Record>) {
        val toiletDatas: ArrayList<ToiletData> = ArrayList()
        list.forEach {
            val x = it.geometry?.coordinates?.get(1)
            val y = it.geometry?.coordinates?.get(0)
            val field = it.fields
            val manager = field?.manager ?: ""
            if (x != null && y != null) {
                toiletDatas.add(
                    ToiletData(
                        "${field?.laneNumber} ${field?.street}",
                        LatLng(x, y),
                        "${field?.openings}",
                        manager
                    )
                )
            }
        }
        view.onBindToiletsList(toiletDatas)
    }

    override fun onFailure(message: String) {
        view.onError(message)
    }

    override fun getToilets() {
        view.onLoading()
        provider.getToilets(this, false)
    }

    override fun refreshToilets() {
        view.onLoading()
        provider.getToilets(this, true)
    }
}