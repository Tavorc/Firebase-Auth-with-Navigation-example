package com.test.user.tavorapplication.features.coronaVirus.network

import com.test.user.tavorapplication.features.coronaVirus.network.CoronaVirusConstant.Companion.COUNTRY_LIVE
import com.test.user.tavorapplication.features.coronaVirus.network.CoronaVirusConstant.Companion.COUNTRY_NAME
import com.test.user.tavorapplication.features.coronaVirus.network.response.CoronaVirusByCountryResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CoronaVirusApi {

    @GET(COUNTRY_LIVE)
    fun getCategoryQuestions(@Path(COUNTRY_NAME) countryName :String) : Observable<ArrayList<CoronaVirusByCountryResponse>>
}