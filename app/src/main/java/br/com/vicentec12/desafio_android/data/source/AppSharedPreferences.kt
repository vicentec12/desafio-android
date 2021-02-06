package br.com.vicentec12.desafio_android.data.source

import android.content.Context
import android.content.SharedPreferences
import br.com.vicentec12.desafio_android.data.source.transfer.TransferRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSharedPreferences @Inject constructor(private val mContext: Context) {

    private fun getEditorSharedPreferences(): SharedPreferences.Editor {
        val preferences = mContext.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        return preferences.edit()
    }

    private fun setValueSharedPreference(key: String, value: Int) {
        val editor = getEditorSharedPreferences()
        editor.putInt(key, value)
        editor.apply()
    }

    // GETTERS

    fun getShowMyBalance() =
        mContext.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
            .getInt(PREFERENCE_SHOW_MY_BALANCE, 0)

    // SETTERS

    fun setShowMyBalance(value: Int) {
        setValueSharedPreference(PREFERENCE_SHOW_MY_BALANCE, value)
    }

    // DELETES

    fun deleteSharedPreferences(context: Context, key: String?, preference: String?) {
        val preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.remove(preference)
        editor.apply()
    }

    fun deleteAllSharedPreferences() {
        val editor = getEditorSharedPreferences()
        editor.remove(PREFERENCE_SHOW_MY_BALANCE)
        editor.apply()
    }

    companion object {

        private const val PREFERENCES_KEY = "app_preferences"
        private const val PREFERENCE_SHOW_MY_BALANCE = "show_my_balance"

        private var INSTANCE: AppSharedPreferences? = null

        @JvmStatic
        fun getInstance(mContext: Context) =
            INSTANCE ?: synchronized(TransferRepository::class.java) {
                INSTANCE ?: AppSharedPreferences(mContext.applicationContext).also { INSTANCE = it }
            }


        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }

    }


}