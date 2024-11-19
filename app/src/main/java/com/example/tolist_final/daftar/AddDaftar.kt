package com.example.tolist_final.daftar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tolist_final.data_class.Label
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.tolist_final.MainActivity
import com.example.tolist_final.R
import com.example.tolist_final.databinding.ActivityAddDaftarBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddDaftar : AppCompatActivity() {
    private lateinit var binding: ActivityAddDaftarBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupEdgeToEdge()

        databaseReference = FirebaseDatabase.getInstance().getReference("labels")

        // Find Views
        val editText: EditText = binding.editText
        val btnSave: Button = binding.btnAddList

        binding.ivBackArrow.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Agar tidak kembali ke AddPengingat ketika menekan tombol back
        }

        // Handle Save Button Click
        btnSave.setOnClickListener {
            val labelName = editText.text.toString().trim()
            if (labelName.isNotEmpty()) {
                val id = databaseReference.push().key // Generate unique ID
                id?.let {
                    val label = Label(id = it, name = labelName)
                    databaseReference.child(it).setValue(label)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Label berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                                editText.text.clear()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Gagal menambahkan label", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                Toast.makeText(this, "Label tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Helper Function for Edge-to-Edge UI
    private fun setupEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }
    }
}
