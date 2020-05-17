package id.smkcoding.smkcodingproject2

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {

    internal var sp: SharedPreferences
    internal var spEditor: SharedPreferences.Editor

    companion object {

        val SP_SMKCODING = "spSMKCoding"

        val SP_SUDAH_LOGIN = "spSudahLogin"
    }



//    val spNama: String
//        get() = sp.getString(SP_NAMA, "")

    init {
        sp = context.getSharedPreferences(SP_SMKCODING, Context.MODE_PRIVATE)
        spEditor = sp.edit()
    }

    fun saveSPString(keySP: String, value: String) {
        spEditor.putString(keySP, value)
        spEditor.commit()
    }

    fun saveSPInt(keySP: String, value: Int) {
        spEditor.putInt(keySP, value)
        spEditor.commit()
    }

    fun saveSPBoolean(keySP: String, value: Boolean) {
        spEditor.putBoolean(keySP, value)
        spEditor.commit()
    }
    fun getSPSudahLogin(): Boolean? {
        return sp.getBoolean(SP_SUDAH_LOGIN, false)
    }


}
