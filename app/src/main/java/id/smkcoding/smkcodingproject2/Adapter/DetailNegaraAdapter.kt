package id.smkcoding.smkcodingproject2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.smkcoding.smkcodingproject2.Data.Attributes
import id.smkcoding.smkcodingproject2.Data.DataNegaraItem
import id.smkcoding.smkcodingproject2.R

class DetailNegaraAdapter(
    private val context: Context, private val items:
    List<DataNegaraItem>, var clickListener: OnNregaraItemClickListener
) :
    RecyclerView.Adapter<DetailNegaraAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(R.layout.cardview_global, parent, false)
    )

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(items.get(position), clickListener)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

        var country = v.findViewById<TextView>(R.id.textnegara)

        fun initialize(item: DataNegaraItem, action:OnNregaraItemClickListener){
            country.text = item.attributes.countryRegion

            itemView.setOnClickListener { action.onItemClick(item, adapterPosition) }
        }

    }
}

interface OnNregaraItemClickListener{
    fun onItemClick(item : DataNegaraItem, position: Int)
}