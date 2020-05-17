package id.smkcoding.smkcodingproject2


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.cardview_global.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.meninggal
import kotlinx.android.synthetic.main.fragment_home.sembuh
import kotlinx.android.synthetic.main.fragment_home.terkonfirmasi
import org.json.JSONException
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(
        view: View,
        @Nullable savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        detailnegaraa.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, DetailNegaraActivity::class.java)
            startActivity(intent) })

        getData()
    }

    private fun getData() {
        val request = StringRequest(
            Request.Method.GET, "https://api.covid19api.com/world/total",
            Response.Listener { response -> DataGlobal(response) },
            Response.ErrorListener { })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(request)
    }

    private fun DataGlobal(response: String) {

        try {
            val jsonObject = JSONObject(response)
            val TotalConfirmed = jsonObject.getString("TotalConfirmed")
            val TotalDeaths = jsonObject.getString("TotalDeaths")
            val TotalRecovered = jsonObject.getString("TotalRecovered")

            terkonfirmasi.setText(TotalConfirmed).toString()
            meninggal.setText(TotalDeaths).toString()
            sembuh.setText(TotalRecovered).toString()

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }


}
