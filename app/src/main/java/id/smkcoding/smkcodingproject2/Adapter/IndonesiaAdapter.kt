package id.smkcoding.smkcodingproject2.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.smkcoding.smkcodingproject2.Data.IndonesiaItem
import id.smkcoding.smkcodingproject2.DetailProvinsiActivity
import id.smkcoding.smkcodingproject2.R

class IndonesiaAdapter(
    private val context: Context, private val items:
    List<IndonesiaItem>
) :
    RecyclerView.Adapter<IndonesiaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.cardview_indonesia, parent, false)
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.positif.text = items[position].positif
        holder.sembuh.text = items[position].sembuh
        holder.meninggal.text = items[position].meninggal
        holder.pemulihan.text = items[position].dirawat
        holder.detail.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, DetailProvinsiActivity::class.java)
            context.startActivity(intent)
        })
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var positif = v.findViewById<TextView>(R.id.terkonfirmasiIndonesia)
        var sembuh = v.findViewById<TextView>(R.id.sembuhIndonesia)
        var meninggal= v.findViewById<TextView>(R.id.meninggalIndonesia)
        var pemulihan = v.findViewById<TextView>(R.id.pemulihanIndonesia)
        var detail = v.findViewById<LinearLayout>(R.id.detailprovinsi)

    }
}