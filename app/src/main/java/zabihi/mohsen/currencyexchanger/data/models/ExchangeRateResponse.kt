package zabihi.mohsen.currencyexchanger.data.models


import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: HashMap<String,Double>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long
)