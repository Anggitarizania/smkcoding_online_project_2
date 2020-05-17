package id.smkcoding.smkcodingproject2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.smkcoding.smkcodingproject2.R
import kotlinx.android.synthetic.main.activity_detail_negara_negara.*
import kotlinx.android.synthetic.main.activity_detail_negara_negara.NamaNegara
import kotlinx.android.synthetic.main.activity_detail_provinsi_indonesia.*

class DetailProvinsiIndonesiaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_provinsi_indonesia)

        backkProvinsi.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, DetailProvinsiActivity::class.java)
            startActivity(intent)
            finish() })

        NamaProvinsi.text = getIntent().getStringExtra("provinsi")
        positifProvinsi.text = getIntent().getStringExtra("positifprovinsi")
        meninggalProvinsi.text = getIntent().getStringExtra("meninggalprovinsi")
        sembuhProvinsi.text = getIntent().getStringExtra("sembuhprovinsi")

    }
}
