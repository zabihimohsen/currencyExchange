package zabihi.mohsen.currencyexchanger.mainactivity

import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import zabihi.mohsen.currencyexchanger.data.Constants
import zabihi.mohsen.currencyexchanger.data.ExchangeApi
import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResponse
import zabihi.mohsen.currencyexchanger.data.preferences.PreferenceProvider
import zabihi.mohsen.currencyexchanger.util.Resource
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val api: ExchangeApi,
    private val preferenceProvider: PreferenceProvider

) :MainRepositoryInterface{
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
            Resource.Error(e.message?: "Some Error Occured! Try again Later")
        }
    }

    override fun getRateOfPair(response: ExchangeRateResponse,buy: String, sell: String,sellAmount:Float): String {
        return if(response.rates.containsKey(sell) && response.rates.containsKey(buy)){
            val sellRate = response.rates[sell]!!
            val buyRate = response.rates[buy]!!
            //round number
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.HALF_EVEN
            //check if there is commission or it is free
            var feeMsg = ""
            try{
                val totalTrades = preferenceProvider.getStoredTag(Constants.totalTradeNum).toInt()
                if(totalTrades>Constants.freeTradeNum){
                    feeMsg = " Commission Fee - ${df.format(Constants.tradeFee*sellAmount*0.01)} $sell"
                }
                preferenceProvider.setStoredTag(Constants.totalTradeNum,(totalTrades+1).toString())
                Log.d(TAG,"### $totalTrades")
            }catch (e:Exception){
                preferenceProvider.setStoredTag(Constants.totalTradeNum,"1")
                Log.d(TAG,"### ????")

            }
            val amount = df.format(sellAmount/(sellRate/buyRate))
            Log.d(TAG,"You have converted $sellAmount $sell to $amount $buy. $feeMsg")

            "You have converted $sellAmount $sell to $amount $buy. $feeMsg"
        }else {
            "Some Error happened"
        }
    }

}