package id.smkcoding.smkcodingproject2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {

    lateinit var pLoading: ProgressDialog
    lateinit var sharedPrefManager: SharedPrefManager
    private var Nama : String = ""
    private var Email : String = ""
    private var Password : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btndaftarakun.setOnClickListener(View.OnClickListener {
            Nama = registernama.text.toString()
            Email = registeremail.text.toString()
            Password = registerpassword.text.toString()

            when{
                Nama.isEmpty() -> registernama.error = "Nama tidak boleh kosong"
                Email.isEmpty() -> registeremail.error = "Email tidak boleh kosong"
                Password.isEmpty() -> registerpassword.error = "Password tidak boleh kosong"
                else -> {
                    pLoading =  ProgressDialog.show(this@RegisterActivity, null, "Harap Tunggu...", true, false)
                    val cm = this@RegisterActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val netInfo = cm.activeNetworkInfo
                    val snackBar = Snackbar.make(
                        findViewById(R.id.daftarlayout), "Tidak ada koneksi internet",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Retry") {
                        val intent = intent
                        finish()
                        startActivity(intent)
                    }
                    if (netInfo != null && netInfo.isConnected) {
                        snackBar.dismiss()
                        getRegister(Nama, Email, Password)
                    } else {
                        pLoading.dismiss()
                        snackBar.show()
                    }
                }
            }

        })
    }

    private fun getRegister(Nama: String, Email: String, Password: String) {
        // untuk link data json POST
        val request = object : StringRequest(
            Request.Method.POST, "https://kuystayhealty.000webhostapp.com/index.php/android_new/register",
            Response.Listener { response -> Register(response) },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                // untuk data POST2an yang dikirim
                params["NAMA"] = Nama
                params["EMAIL"] = Email
                params["PASSWORD"] = Password
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    private fun Register(response: String) {

        try {
            pLoading.dismiss()
            val jsonObject = JSONObject(response)
            if (jsonObject.getString("STATUS") == "TRUE") {
                val message = jsonObject.getString("message")
                val id = jsonObject.getJSONObject("data").getString("id_user")
                val nama = jsonObject.getJSONObject("data").getString("nama")
                val email = jsonObject.getJSONObject("data").getString("email")
                val password = jsonObject.getJSONObject("data").getString("password")

                Toast.makeText(this@RegisterActivity, "" + message, Toast.LENGTH_SHORT).show()
                startActivity(
                    Intent(this@RegisterActivity, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                finish()


            }
            if (jsonObject.getString("STATUS") == "FALSE") {
                pLoading.dismiss()
                val message = jsonObject.getString("message")
                Toast.makeText(this@RegisterActivity, "" + message, Toast.LENGTH_SHORT).show()
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}
