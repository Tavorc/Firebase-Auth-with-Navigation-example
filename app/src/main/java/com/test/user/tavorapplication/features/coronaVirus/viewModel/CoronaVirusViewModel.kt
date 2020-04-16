package com.test.user.tavorapplication.features.coronaVirus.viewModel

import androidx.lifecycle.MutableLiveData
import com.test.user.tavorapplication.features.coronaVirus.network.CoronaVirusApiController
import com.test.user.tavorapplication.features.coronaVirus.network.response.CoronaVirusByCountryResponse
import com.test.user.tavorapplication.infrastructure.viewModel.BaseViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class CoronaVirusViewModel : BaseViewModel() {
    private var mCoronaVirusApiController: CoronaVirusApiController = CoronaVirusApiController()


    fun makeUserToSupplier(countryName : String) : MutableLiveData<CoronaVirusByCountryViewState> {

        val liveData = MutableLiveData<CoronaVirusByCountryViewState>()

        liveData.postValue(CoronaVirusByCountryViewState.Loading(true))
        mCoronaVirusApiController.getCoronaVirusCasesList(countryName).subscribe(object:
            Observer<ArrayList<CoronaVirusByCountryResponse>> {
            override fun onNext(response: ArrayList<CoronaVirusByCountryResponse>) {
                liveData.postValue(CoronaVirusByCountryViewState.Success(response))
            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onError(e: Throwable) {
                liveData.postValue(CoronaVirusByCountryViewState.Error(e))
            }

            override fun onComplete() {
            }
        })

        return liveData
    }
}