package id.smkcoding.smkcodingproject2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.smkcoding.smkcodingproject2.Adapter.DetailNegaraAdapter
import id.smkcoding.smkcodingproject2.Adapter.OnNregaraItemClickListener
import id.smkcoding.smkcodingproject2.Data.DataNegaraItem
import id.smkcoding.smkcodingproject2.Util.tampilToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_detail_negara.*
import kotlinx.android.synthetic.main.activity_detail_negara_negara.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*
import kotlin.collections.ArrayList

class DetailNegaraActivity : AppCompatActivity(), OnNregaraItemClickListener {

    lateinit var pLoading: ProgressDialog
    val displayList = ArrayList<DataNegaraItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_negara)

        backgloball.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
            finish() })

        pLoading =  ProgressDialog.show(this@DetailNegaraActivity, null, "Harap Tunggu...", true, false)
        val cm = this@DetailNegaraActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        val snackBar = Snackbar.make(
            findViewById(R.id.detailNegaraLayout), "Tidak ada koneksi internet",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Retry") {
            val intent = intent
            finish()
            startActivity(intent)
        }
        if (netInfo != null && netInfo.isConnected) {
            snackBar.dismiss()
            getDataNegara()
        } else {
            pLoading.dismiss()
            snackBar.show()
        }
    }

    private fun getDataNegara() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<NegaraService>(httpClient)
        val call = apiRequest.getDataNegara()
        call.enqueue(object : Callback<List<DataNegaraItem>> {
            override fun onFailure(call: Call<List<DataNegaraItem>>, t: Throwable) {
            }
            override fun onResponse(call: Call<List<DataNegaraItem>>, response:
            retrofit2.Response<List<DataNegaraItem>>
            ) {
                when {
                    response.isSuccessful ->
                        when {

                            response.body()?.size != 0 ->
                                tampilDataNegara(response.body()!!)
                            else -> {
                                pLoading.dismiss()
                                tampilToast(this@DetailNegaraActivity, "Berhasil")
                            }
                        }
                    else -> {
                        pLoading.dismiss()
                        tampilToast(this@DetailNegaraActivity, "Gagal")
                    }
                }
            }
        })
    }
    private fun tampilDataNegara(DataNegara: List<DataNegaraItem>) {
        pLoading.dismiss()
        recyclerviewDetailNegara.layoutManager = LinearLayoutManager(this)
        recyclerviewDetailNegara.adapter = DetailNegaraAdapter(this, DataNegara, this)
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    override fun onItemClick(item: DataNegaraItem, position: Int) {
        val intent = Intent(this, DetailNegaraNegaraActivity::class.java)
        intent.putExtra("negara", item.attributes.countryRegion)
        intent.putExtra("positidunia", item.attributes.confirmed.toString())
        intent.putExtra("dirawtdunia", item.attributes.active.toString())
        intent.putExtra("sembuhdunia", item.attributes.recovered.toString())
        intent.putExtra("meninggaldunia", item.attributes.deaths.toString())
        startActivity(intent)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}


