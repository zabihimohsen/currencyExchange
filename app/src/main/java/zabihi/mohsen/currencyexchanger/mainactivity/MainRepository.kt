package zabihi.mohsen.currencyexchanger.mainactivity

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import zabihi.mohsen.currencyexchanger.data.Constants
import zabihi.mohsen.currencyexchanger.data.ExchangeApi
import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResponse
import zabihi.mohsen.currencyexchanger.util.Resource
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val api: ExchangeApi
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
}