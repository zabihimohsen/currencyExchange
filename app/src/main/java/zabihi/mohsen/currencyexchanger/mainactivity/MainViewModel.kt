package zabihi.mohsen.currencyexchanger.mainactivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import zabihi.mohsen.currencyexchanger.data.Constants
import zabihi.mohsen.currencyexchanger.util.DispatcherProvider
import zabihi.mohsen.currencyexchanger.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.*
import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResponse

const val TAG = "MainViewModel"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : MainRepositoryInterface,
    private val dispatchers : DispatcherProvider,
) : ViewModel(){
    sealed class  CurrencyEvent{
        class Message(val result : String) : CurrencyEvent()
        object LoadingStatus : CurrencyEvent()
        object Init : CurrencyEvent()
    }
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Init)
    val conversion :StateFlow<CurrencyEvent> = _conversion
    private lateinit var lastRatesResponse   :ExchangeRateResponse
    private var lastResponseTimeStamp : Long = 0
    fun convert(
        amountString : String,
        sellCurrency : String,
        buyCurrency : String,
    ){
        val sellAmount = amountString.toFloatOrNull()
        if(sellAmount == null){
            _conversion.value = CurrencyEvent.Message("Please enter a valid amount")
            return
        }
        if(! ::lastRatesResponse.isInitialized){
            _conversion.value = CurrencyEvent.Message("Connection Error!")
            return
        }
        _conversion.value = CurrencyEvent.Message(repository.getRateOfPair(lastRatesResponse,buyCurrency,sellCurrency,sellAmount))


    }
    fun repeatFun(): Job {
        return  viewModelScope.launch(dispatchers.io) {
            while(isActive) {
                when(val rateResponse = repository.getExchangeRates(Constants.exchangeApiKey)){
                    is Resource.Success -> {
                        if(lastResponseTimeStamp<rateResponse.data!!.timestamp) {
                            Log.d(TAG,"### euro to aed : ${rateResponse.data.rates["AED"]}")
                            lastRatesResponse = rateResponse.data
                            lastResponseTimeStamp = rateResponse.data.timestamp
                        }else{
                            //dump data because it is redundant
                        }
                    }else -> {
                        //dump data
                    }
                }
                delay(Constants.apiCallInterval)
            }
        }
    }
}