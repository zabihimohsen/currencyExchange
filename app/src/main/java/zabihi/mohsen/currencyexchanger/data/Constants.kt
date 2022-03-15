package zabihi.mohsen.currencyexchanger.data

//Object for providing const values
object Constants {
    const val exchangeBaseUrl = "http://api.exchangeratesapi.io/v1/"
    const val exchangeApiKey = "aa098cdc4df6ef6ec60795266a5d39eb"
    const val latestEndPoint = "latest"
    const val freeTradeNum = 5 // number of free trades each user will have
    const val tradeFee = 0.7 // amount of fee in percentage, applied after 5th trade of user
    const val totalTradeNum = "trades" // key used for saving total trades number of a user
    const val apiCallInterval :Long = 5000 //time in milli seconds to call api again
    const val eurKey = "EUR"
    const val initBalanceAmount = 1000.0 // Amount of Euros each user will get by default at first install
    const val launchedBefore = "launchedBefore" // Key used for checking if app is launched before or no
}