package com.grosalex.sidetoilets.model

import com.google.android.gms.maps.model.LatLng

data class ToiletData(val title:String, val latLng: LatLng, val opening:String, val manager:String)