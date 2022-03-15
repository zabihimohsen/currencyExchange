package zabihi.mohsen.currencyexchanger.mainactivity

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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
import zabihi.mohsen.currencyexchanger.data.db.BalanceEntity
import zabihi.mohsen.currencyexchanger.data.db.RoomAppDb
import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateCalculation
import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResponse
import zabihi.mohsen.currencyexchanger.data.preferences.PreferenceProvider
import java.math.RoundingMode
import java.text.DecimalFormat

const val TAG = "MainViewModel"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository : MainRepository,
    private val dispatchers : DispatcherProvider,
    private val app : Application,
) : AndroidViewModel(app) {
    sealed class CurrencyEvent {
        class Message(val result: String) : CurrencyEvent()
        object LoadingStatus : CurrencyEvent()
        object Init : CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Init)
    val conversion: StateFlow<CurrencyEvent> = _conversion
    private lateinit var lastRatesResponse: ExchangeRateResponse
    private var lastResponseTimeStamp: Long = 0
    var balances: MutableLiveData<List<BalanceEntity>> = MutableLiveData()


    init {
        initBalance()
        getBalances()

    }

    fun convert(
        amountString: String,
        sellCurrency: String,
        buyCurrency: String,
    ) {
        val sellAmount = amountString.toFloatOrNull()
        if (sellAmount == null) {
            _conversion.value = CurrencyEvent.Message("Please enter a valid amount")
            return
        }
        if (!::lastRatesResponse.isInitialized) {
            _conversion.value = CurrencyEvent.Message("Connection Error!")
            return
        }
        val balanceDao = RoomAppDb.getAppDatabase((getApplication()))?.balanceDao()
        val sourceEntity = balanceDao?.getBalanceInfo(sellCurrency) ?: BalanceEntity(sellCurrency,0.0)
        val targetEntity = balanceDao?.getBalanceInfo(buyCurrency) ?: BalanceEntity(buyCurrency,0.0)


        val result = repository.getRateForPair(lastRatesResponse,
            ExchangeRateCalculation(sourceEntity,targetEntity),sellAmount)

        if(result.success){
            insertOrUpdateBalance(result.rates.source)
            insertOrUpdateBalance(result.rates.target)
        }
        _conversion.value = CurrencyEvent.Message(result.message)

//        _conversion.value = CurrencyEvent.Message(getRateOfPair(lastRatesResponse,buyCurrency,sellCurrency,sellAmount))


//        val response =
//            repository.getRateForPair(lastRatesResponse, ExchaneRateCalcuation(BalanceEntity()), sellAmount)
//        when (response) {
//            is Resource.Success -> {
//                _conversion.value = CurrencyEvent.Message(response.data!!)
//            }
//
//            else -> {
//                _conversion.value = CurrencyEvent.Message(response.data ?: "swwe")
//
//
//            }
//        }


    }

    fun repeatFun(): Job {
        return viewModelScope.launch(dispatchers.io) {
            while (isActive) {
                when (val rateResponse = repository.getExchangeRates(Constants.exchangeApiKey)) {
                    is Resource.Success -> {
                        if (lastResponseTimeStamp < rateResponse.data!!.timestamp) {
                            lastRatesResponse = rateResponse.data
                            lastResponseTimeStamp = rateResponse.data.timestamp
                        } else {
                            //dump data because it is redundant
                        }
                    }
                    else -> {
                        //dump data
                    }
                }
                delay(Constants.apiCallInterval)
            }
        }
    }

    private fun getBalances() {
        val balanceDao = RoomAppDb.getAppDatabase((getApplication()))?.balanceDao()
        val list = balanceDao?.getBalancesInfo()
        balances.postValue(list!!)
    }

    fun getBalancesObservers(): MutableLiveData<List<BalanceEntity>> {
        return balances
    }

    private fun insertBalance(entity: BalanceEntity) {
        val balanceDao = RoomAppDb.getAppDatabase(getApplication())?.balanceDao()
        balanceDao?.insertBalance(entity)
        getBalances()
    }
    private fun insertOrUpdateBalance(entity: BalanceEntity){
        val balanceDao = RoomAppDb.getAppDatabase(getApplication())?.balanceDao()
        balanceDao?.insertReplace(entity)
        getBalances()
    }
    fun updateBalance(entity: BalanceEntity) {
        val balanceDao = RoomAppDb.getAppDatabase(getApplication())?.balanceDao()
        balanceDao?.updateBalance(entity)
        getBalances()
    }
    //Check if it is first launch, add 1000 euro to the account
    private fun initBalance(){

        if(!repository.isLaunchedBefore()){
            insertBalance(BalanceEntity(Constants.eurKey,Constants.initBalanceAmount))
            repository.setFirstLaunchTag()
        }
    }
}
