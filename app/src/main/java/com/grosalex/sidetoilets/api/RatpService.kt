package com.grosalex.sidetoilets.api

import com.grosalex.sidetoilets.response.ToiletsResponse
import retrofit2.Call
import retrofit2.http.GET

interface RatpService {

    @GET("records/1.0/search/?dataset=sanisettesparis2011&rows=1000")
    fun getToilets():Call<ToiletsResponse>
    companion object{
        const val ROOT ="https://data.ratp.fr/api/"
    }
}