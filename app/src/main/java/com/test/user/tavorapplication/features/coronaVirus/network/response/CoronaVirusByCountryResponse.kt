package com.test.user.tavorapplication.features.coronaVirus.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoronaVirusByCountryResponse (
    @SerializedName("Country")
    @Expose
    var country: String? = null ,

    @SerializedName("Province")
    @Expose
    var province: String? = null,

    @SerializedName("Lat")
    @Expose
    var lat: Double? = null,

    @SerializedName("Lon")
    @Expose
    var lon: Double? = null,

    @SerializedName("Date")
    @Expose
    var date: String? = null,

    @SerializedName("Case")
    @Expose
    var case: Double? = null,

    @SerializedName("Status")
    @Expose
    var status: String? = null
)