package id.smkcoding.smkcodingproject2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class LoginActivity : AppCompatActivity() {

    lateinit var pLoading: ProgressDialog
    lateinit var sharedPrefManager: SharedPrefManager
    private var Email : String = ""
    private var Password : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPrefManager = SharedPrefManager(this)

        daftar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnmasukapk.setOnClickListener(View.OnClickListener {
            Email = loginemail.text.toString()
            Password = loginpassword.text.toString()

            when{
                Email.isEmpty() -> loginemail.error = "Username tidak boleh kosong"
                Password.isEmpty() -> loginpassword.error = "Password tidak boleh kosong"
                else -> {
                    pLoading =  ProgressDialog.show(this@LoginActivity, null, "Harap Tunggu...", true, false)
                    val cm = this@LoginActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val netInfo = cm.activeNetworkInfo
                    val snackBar = Snackbar.make(
                        findViewById(R.id.layoutlogin), "Tidak ada koneksi internet",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Retry") {
                        val intent = intent
                        finish()
                        startActivity(intent)
                    }
                    if (netInfo != null && netInfo.isConnected) {
                        snackBar.dismiss()
                        getLogin(Email, Password)
                    } else {
                        pLoading.dismiss()
                        snackBar.show()
                    }
                }
            }

        })

        if (sharedPrefManager.getSPSudahLogin()!!) {
            startActivity(
                Intent(this@LoginActivity, HomeActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }
    }


    private fun getLogin(Email: String, Password: String) {
        // untuk link data json POST
        val request = object : StringRequest(
            Request.Method.POST, "https://kuystayhealty.000webhostapp.com/index.php/android_new/login",
            Response.Listener { response -> Login(response) },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                // untuk data POST2an yang dikirim
                params["EMAIL"] = Email
                params["PASSWORD"] = Password
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }


    private fun Login(response: String) {

        try {
            pLoading.dismiss()
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("STATUS") == "TRUE") {
                val message = jsonObject.getString("message")
                val email = jsonObject.getString("email")
                val id = jsonObject.getString("id_user")

                Toast.makeText(this@LoginActivity, "" + message, Toast.LENGTH_SHORT).show()
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true)
                startActivity(
                    Intent(this@LoginActivity, HomeActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                finish()
            }
            if (jsonObject.getString("STATUS") == "FALSE") {
                pLoading.dismiss()
                val message = jsonObject.getString("message")
                Toast.makeText(this@LoginActivity, "" + message, Toast.LENGTH_SHORT).show()
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}
