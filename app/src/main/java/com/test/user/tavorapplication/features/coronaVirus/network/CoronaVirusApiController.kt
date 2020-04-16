package com.test.user.tavorapplication.features.coronaVirus.network

import com.test.user.tavorapplication.features.coronaVirus.network.CoronaVirusConstant.Companion.BASE_URL
import com.test.user.tavorapplication.features.coronaVirus.network.response.CoronaVirusByCountryResponse
import com.test.user.tavorapplication.infrastructure.network.ApiController
import io.reactivex.Observable

class CoronaVirusApiController : ApiController<CoronaVirusApi>() {
    override fun getEndpoint(): String {
        return BASE_URL
    }

    override fun getApiClass(): Class<CoronaVirusApi> {
        return CoronaVirusApi::class.java
    }

    fun getCoronaVirusCasesList(countryName : String): Observable<ArrayList<CoronaVirusByCountryResponse>> {
       return api.getCategoryQuestions(countryName)
    }
}