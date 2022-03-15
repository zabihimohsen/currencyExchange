package zabihi.mohsen.currencyexchanger.mainactivity

import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.internal.connection.Exchange
import okhttp3.logging.HttpLoggingInterceptor
import zabihi.mohsen.currencyexchanger.data.Constants
import zabihi.mohsen.currencyexchanger.data.ExchangeApi
import zabihi.mohsen.currencyexchanger.data.db.BalanceEntity
import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateCalculation
import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResponse
import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResult
import zabihi.mohsen.currencyexchanger.data.preferences.PreferenceProvider
import zabihi.mohsen.currencyexchanger.util.Resource
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val api: ExchangeApi,
    private val preferenceProvider: PreferenceProvider

) :MainRepositoryInterface{
    //Get rates from remote server
    override suspend fun getExchangeRates(accessKey: String): Resource<ExchangeRateResponse> {
        return try{
            val response = api.getConversionRates(Constants.exchangeApiKey)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        }catch ( e : Exception){
            Resource.Error(e.message?: "")
        }
    }
    //Calculate commission fee
    override fun getCommissionFee(response: ExchangeRateResponse, buy: String, sell: String):Double{
        var fee = 0.0
        try{
            val totalTrades = preferenceProvider.getStoredTag(Constants.totalTradeNum).toInt()
            if(totalTrades>Constants.freeTradeNum){
                fee = Constants.tradeFee
            }
            preferenceProvider.setStoredTag(Constants.totalTradeNum,(totalTrades+1).toString())
        }catch (e:Exception){
            preferenceProvider.setStoredTag(Constants.totalTradeNum,"1")

        }
        return fee
    }
    //Checks and calculates the exchane action
     fun getRateForPair(response: ExchangeRateResponse,erCalculation: ExchangeRateCalculation,sellAmount:Float):ExchangeRateResult{
         return if(response.rates.containsKey(erCalculation.source.name) && response.rates.containsKey(erCalculation.target.name)){
            val sellRate = response.rates[erCalculation.source.name]!!
            val buyRate = response.rates[erCalculation.target.name]!!
            //format for rounding number
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.HALF_EVEN
            //check if there is commission or it is free
            var feeMsg = ""
            val commission : Float =  (df.format(getCommissionFee(response,erCalculation.target.name,erCalculation.source.name)*sellAmount*0.01)).toFloatOrNull() ?: 0.00f
            if(commission >= 0.00f){
                //check if the balance is enough for commission + amount entered by user
                if(commission + sellAmount > erCalculation.source.balance){
                    return ExchangeRateResult(ExchangeRateCalculation(
                        BalanceEntity(erCalculation.source.name,erCalculation.source.balance),
                        BalanceEntity(erCalculation.target.name,erCalculation.target.balance),
                    ),"Balance is not enough",false)
                }
                feeMsg = " Commission Fee - $commission ${erCalculation.source.name}"
            }
            val amount = df.format(sellAmount/(sellRate/buyRate))
             ExchangeRateResult(ExchangeRateCalculation(
                 BalanceEntity(erCalculation.source.name,erCalculation.source.balance-(commission+sellAmount)),
                 BalanceEntity(erCalculation.target.name,erCalculation.target.balance+amount.toDouble())
             ),"You have converted $sellAmount ${erCalculation.source.name} to $amount ${erCalculation.target.name}. $feeMsg",true)
        }else {
             ExchangeRateResult(ExchangeRateCalculation(
                 BalanceEntity(erCalculation.source.name,erCalculation.source.balance),
                 BalanceEntity(erCalculation.target.name,erCalculation.target.balance)
             ),"Some Error happened",false)
        }
    }
    //Check if app is launched before
    fun isLaunchedBefore():Boolean{
       return preferenceProvider.getBooleanStoredTag(Constants.launchedBefore)
    }
    //set flag in shared preferences showing app is launched before
    fun setFirstLaunchTag(){
        preferenceProvider.setStoredTag(Constants.launchedBefore,true)
    }


}