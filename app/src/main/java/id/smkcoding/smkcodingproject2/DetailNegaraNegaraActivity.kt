package id.smkcoding.smkcodingproject2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.smkcoding.smkcodingproject2.R
import kotlinx.android.synthetic.main.activity_detail_negara_negara.*

class DetailNegaraNegaraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_negara_negara)

        backNegaraa.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, DetailNegaraActivity::class.java)
            startActivity(intent)
            finish() })

        NamaNegara.text = getIntent().getStringExtra("negara")
        positifDunia.text = getIntent().getStringExtra("positidunia")
        dirawatDunia.text = getIntent().getStringExtra("dirawtdunia")
        sembuhDunia.text = getIntent().getStringExtra("sembuhdunia")
        meninggalDunia.text = getIntent().getStringExtra("meninggaldunia")
    }
}
