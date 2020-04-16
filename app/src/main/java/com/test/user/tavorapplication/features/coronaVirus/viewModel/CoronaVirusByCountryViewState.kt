package com.test.user.tavorapplication.features.coronaVirus.viewModel

import com.test.user.tavorapplication.features.coronaVirus.network.response.CoronaVirusByCountryResponse

sealed class CoronaVirusByCountryViewState {
    class Error(var message: Throwable) : CoronaVirusByCountryViewState()

    class Success(var response: ArrayList<CoronaVirusByCountryResponse>) : CoronaVirusByCountryViewState()

    class Loading(var isLoading: Boolean) : CoronaVirusByCountryViewState()
}