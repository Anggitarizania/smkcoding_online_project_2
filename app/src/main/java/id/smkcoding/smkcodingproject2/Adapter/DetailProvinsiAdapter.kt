package id.smkcoding.smkcodingproject2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.smkcoding.smkcodingproject2.Data.DataNegaraItem
import id.smkcoding.smkcodingproject2.Data.Provinsi
import id.smkcoding.smkcodingproject2.Data.ProvinsiItem
import id.smkcoding.smkcodingproject2.R

class DetailProvinsiAdapter(
    private val context: Context, private val items :
            List<ProvinsiItem>, var clickListener: OnProvinsiItemClickListener) :
        RecyclerView.Adapter<DetailProvinsiAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.cardview_provinsi, parent, false)
    )

    override fun getItemCount(): Int {
        return  items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(items.get(position), clickListener)
    }

    class ViewHolder (v: View) : RecyclerView.ViewHolder(v){
        var provinsi = v.findViewById<TextView>(R.id.textprovinsi)
        fun initialize(itemm: ProvinsiItem, action:OnProvinsiItemClickListener){
            provinsi.text = itemm.attributes.provinsi
            itemView.setOnClickListener { action.onItemClick(itemm, adapterPosition) }
        }
    }

}

interface OnProvinsiItemClickListener{
    fun onItemClick(item : ProvinsiItem, position: Int)
}