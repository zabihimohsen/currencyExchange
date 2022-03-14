package zabihi.mohsen.currencyexchanger.mainactivity

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import zabihi.mohsen.currencyexchanger.R
import zabihi.mohsen.currencyexchanger.data.Constants
import zabihi.mohsen.currencyexchanger.data.models.Rates
import zabihi.mohsen.currencyexchanger.util.DispatcherProvider
import zabihi.mohsen.currencyexchanger.util.Resource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : MainRepositoryInterface,
    private val dispatchers : DispatcherProvider,
) : ViewModel(){
    sealed class  CurrencyEvent{
        class Success(val result : String) : CurrencyEvent()
        class Failure(val error : String) : CurrencyEvent()
        object LoadingStatus : CurrencyEvent()
        object Init : CurrencyEvent()
    }
    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Init)
    val conversion :StateFlow<CurrencyEvent> = _conversion

    fun convert(
        amountString : String,
        sellCurrency : String,
        buyCurrency : String,
    ){
        val sellAmount = amountString.toFloatOrNull()
        if(sellAmount == null){
            _conversion.value = CurrencyEvent.Failure("Please enter a valid amount")
            return
        }
        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.LoadingStatus
            when(val rateResponse = repository.getExchangeRates(Constants.exchangeApiKey)){
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(rateResponse.message ?: "An error happened")
                is Resource.Success -> {
                    val rates = rateResponse.data!!.rates
                    val rate = getRateForCurrency(buyCurrency,rates)
                    if(rate == null){
                        _conversion.value = CurrencyEvent.Failure("An error happened")
                    }else{
                        val converted = rate //TODO
                        _conversion.value = CurrencyEvent.Success("$sellAmount $sellCurrency converted by rate of $rate to $buyCurrency")
                    }
                }
            }
        }
    }
    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        //TODO("Add All Rates Here")
//        "CAD" -> rates.cAD
//        "HKD" -> rates.hKD
//        "ISK" -> rates.iSK
        "EUR" -> rates.eUR
        "AED" -> rates.aED
//        "PHP" -> rates.pHP
//        "DKK" -> rates.dKK
//        "HUF" -> rates.hUF
//        "CZK" -> rates.cZK
//        "AUD" -> rates.aUD
//        "RON" -> rates.rON
//        "SEK" -> rates.sEK
//        "IDR" -> rates.iDR
//        "INR" -> rates.iNR
//        "BRL" -> rates.bRL
//        "RUB" -> rates.rUB
//        "HRK" -> rates.hRK
//        "JPY" -> rates.jPY
//        "THB" -> rates.tHB
//        "CHF" -> rates.cHF
//        "SGD" -> rates.sGD
//        "PLN" -> rates.pLN
//        "BGN" -> rates.bGN
//        "CNY" -> rates.cNY
//        "NOK" -> rates.nOK
//        "NZD" -> rates.nZD
//        "ZAR" -> rates.zAR
        "USD" -> rates.uSD
//        "MXN" -> rates.mXN
//        "ILS" -> rates.iLS
//        "GBP" -> rates.gBP
//        "KRW" -> rates.kRW
//        "MYR" -> rates.mYR
        else -> null
    }
}