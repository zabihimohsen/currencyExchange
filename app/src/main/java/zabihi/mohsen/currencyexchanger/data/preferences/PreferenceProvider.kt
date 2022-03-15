package zabihi.mohsen.currencyexchanger.data.preferences

import android.content.Context
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton



//Providing access to Shared Preferences
@Singleton
class PreferenceProvider @Inject constructor(@ApplicationContext context : Context){
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStoredTag(key : String): String {
        return prefs.getString(key, "")!!
    }
    fun getBooleanStoredTag(key:String) :Boolean{
        return prefs.getBoolean(key,false)
    }
    fun setStoredTag(key:String,value:Boolean){
        prefs.edit().putBoolean(key, value).apply()

    }
    fun setStoredTag(key: String,value:String) {
        prefs.edit().putString(key, value).apply()
    }
}