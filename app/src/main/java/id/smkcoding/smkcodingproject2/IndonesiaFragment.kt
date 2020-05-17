package id.smkcoding.smkcodingproject2


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.smkcoding.smkcodingproject2.Data.IndonesiaItem
import id.smkcoding.smkcodingproject2.Adapter.IndonesiaAdapter
import id.smkcoding.smkcodingproject2.Util.tampilToast
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_indonesia.*
import retrofit2.Call
import retrofit2.Callback


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class IndonesiaFragment : Fragment() {


    lateinit var pLoading: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indonesia, container, false)


    }

    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        pLoading =  ProgressDialog.show(context, null, "Harap Tunggu...", true, false)
        getData()
    }

    private fun getData() {
        val httpClient = httpClient()
        val apiRequest = apiRequest<NegaraService>(httpClient)
        val call = apiRequest.getIndonesia()
        call.enqueue(object : Callback<List<IndonesiaItem>> {
            override fun onFailure(call: Call<List<IndonesiaItem>>, t: Throwable) {
            }
            override fun onResponse(call: Call<List<IndonesiaItem>>, response:
            retrofit2.Response<List<IndonesiaItem>>
            ) {
                when {
                    response.isSuccessful ->
                        when {
                            response.body()?.size != 0 ->
                                tampilIndonesia(response.body()!!)
                            else -> {
                                pLoading.dismiss()
                                tampilToast(context!!, "Berhasil")
                            }
                        }
                    else -> {
                        pLoading.dismiss()
                        tampilToast(context!!, "Gagal")
                    }
                }
            }
        })
    }
    private fun tampilIndonesia(Indonesia: List<IndonesiaItem>) {
        pLoading.dismiss()
        recyclerviewIndonesia.layoutManager = LinearLayoutManager(context)
        recyclerviewIndonesia.adapter = IndonesiaAdapter(context!!, Indonesia)
    }
    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
