package com.example.tolist_final

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tolist_final.databinding.ActivityAddDaftarBinding
import com.example.tolist_final.databinding.ActivityTaskDetailBinding
import com.google.firebase.database.DatabaseReference

class TaskDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol kembali menggunakan binding
        binding.ivBackArrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Terima data dari Intent
        val labelName = intent.getStringExtra("LABEL_NAME")
        binding.labelNameDetail.text = labelName // Menggunakan binding untuk TextView
    }
}
