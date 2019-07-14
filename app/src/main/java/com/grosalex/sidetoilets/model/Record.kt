package com.grosalex.sidetoilets.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Record(
    @PrimaryKey @SerializedName("recordid") val recordId: String,
    @SerializedName("datasetid") val dataSetId: String?,
    @SerializedName("record_timestamp") val recordTimeStamp: String?,
    val fields: Field?,
    val geometry: Geometry?
)

data class Field(
    @SerializedName("objectid") val objectId: Int?,
    @SerializedName("geom_x_y") val geomXY: Array<Double>?,
    @SerializedName("numero_de_voie") val laneNumber: String?,
    @SerializedName("gestionnaire") val manager: String?,
    val source: String?,
    val y: Double?,
    val x: Double?,
    @SerializedName("nom_de_voie") val street: String?,
    @SerializedName("identifiant") val identifier: String?,
    @SerializedName("arrondissement") val borough: String?,
    @SerializedName("horaires_ouverture") val openings: String?
)

data class Geometry(
    val type: String?,
    val coordinates: Array<Double>?
)