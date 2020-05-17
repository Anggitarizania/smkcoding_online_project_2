package id.smkcoding.smkcodingproject2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.smkcoding.smkcodingproject2.Adapter.DetailNegaraAdapter
import id.smkcoding.smkcodingproject2.Adapter.DetailProvinsiAdapter
import id.smkcoding.smkcodingproject2.Adapter.OnProvinsiItemClickListener
import id.smkcoding.smkcodingproject2.Data.DataNegaraItem
import id.smkcoding.smkcodingproject2.Data.ProvinsiItem
import id.smkcoding.smkcodingproject2.Util.tampilToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_detail_negara.*
import kotlinx.android.synthetic.main.activity_detail_provinsi.*
import kotlinx.android.synthetic.main.activity_detail_provinsi_indonesia.*
import retrofit2.Call
import retrofit2.Callback

class DetailProvinsiActivity : AppCompatActivity(), OnProvinsiItemClickListener {

    lateinit var pLoading: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_provinsi)

        backProvinsi.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, HomeFragment::class.java)
            startActivity(intent)
            finish()
        })

        pLoading = ProgressDialog.show(this@DetailProvinsiActivity, null, "Harap Tunggu...", true, false)
        val cm = this@DetailProvinsiActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        val snackBar = Snackbar.make(
            findViewById(R.id.detailProvinsiLayout), "Tidak ada koneksi internet",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Retry") {
            val intent = intent
            finish()
            startActivity(intent)
        }
        if (netInfo != null && netInfo.isConnected) {
            snackBar.dismiss()
            getData()
        } else {
            pLoading.dismiss()
            snackBar.show()
        }

    }


    private fun getData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<NegaraService>(httpClient)
        val call = apiRequest.getProvinsi()
        call.enqueue(object : Callback<List<ProvinsiItem>> {
            override fun onFailure(call: Call<List<ProvinsiItem>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<List<ProvinsiItem>>, response:
                retrofit2.Response<List<ProvinsiItem>>
            ) {
                when {
                    response.isSuccessful ->
                        when {
                            response.body()?.size != 0 ->
                                tampilDataProvinsi(response.body()!!)
                            else -> {
                                pLoading.dismiss()
                                tampilToast(this@DetailProvinsiActivity, "Berhasil")
                            }
                        }
                    else -> {
                        pLoading.dismiss()
                        tampilToast(this@DetailProvinsiActivity, "Gagal")
                    }
                }
            }
        })
    }

    private fun tampilDataProvinsi(Provinsi: List<ProvinsiItem>) {
        pLoading.dismiss()
        recyclerviewDetailProvinsi.layoutManager = LinearLayoutManager(this)
        recyclerviewDetailProvinsi.adapter = DetailProvinsiAdapter(this, Provinsi, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    override fun onItemClick(item: ProvinsiItem, position: Int) {
        val intent = Intent(this, DetailProvinsiIndonesiaActivity::class.java)
        intent.putExtra("provinsi", item.attributes.provinsi)
        intent.putExtra("positifprovinsi", item.attributes.kasusPosi.toString())
        intent.putExtra("meninggalprovinsi", item.attributes.kasusMeni.toString())
        intent.putExtra("sembuhprovinsi", item.attributes.kasusSemb.toString())
        startActivity(intent)
    }

}
