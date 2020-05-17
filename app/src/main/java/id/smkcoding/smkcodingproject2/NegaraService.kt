package id.smkcoding.smkcodingproject2

import id.smkcoding.smkcodingproject2.Data.DataNegaraItem
import id.smkcoding.smkcodingproject2.Data.IndonesiaItem
import id.smkcoding.smkcodingproject2.Data.ProvinsiItem
import retrofit2.Call
import retrofit2.http.GET

interface NegaraService {
    @GET("/")
    fun getDataNegara(): Call<List<DataNegaraItem>>

    @GET("/indonesia")
    fun getIndonesia(): Call<List<IndonesiaItem>>

    @GET("/indonesia/provinsi")
    fun getProvinsi(): Call<List<ProvinsiItem>>
}