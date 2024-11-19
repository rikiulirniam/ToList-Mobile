package com.example.tolist_final.pengingat

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tolist_final.MainActivity
import com.example.tolist_final.R
import com.example.tolist_final.data_class.Reminder
import com.example.tolist_final.databinding.ActivityAddPengingatBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class AddPengingat : AppCompatActivity() {
    private lateinit var binding: ActivityAddPengingatBinding;
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddPengingatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur padding berdasarkan insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Listener untuk DatePicker
        binding.dateInput.setOnClickListener {
            showDatePicker()
        }

        // Listener untuk TimePicker
        binding.timeInput.setOnClickListener {
            showTimePicker()
        }

        binding.cancelButton.setOnClickListener{
            // Kembali ke MainActivity setelah menyimpan data
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Agar tidak kembali ke AddPengingat ketika menekan tombol back
        }

        // Listener untuk Save Button
        binding.saveButton.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val notes = binding.notesInput.text.toString()
            val date = binding.dateInput.text.toString()
            val time = binding.timeInput.text.toString()

            if (title.isNotEmpty() && notes.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {

                val reminder = Reminder(
                    title = title,
                    notes = notes,
                    date = date,
                    time = time,
                    categoryId = "sfseffewf"
                )

                // Simpan data ke database atau proses lainnya
                saveReminderToDatabase(reminder)
                // Kembali ke MainActivity setelah menyimpan data
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Agar tidak kembali ke AddPengingat ketika menekan tombol back
            } else {
                Toast.makeText(this, "Mohon selesaikan pengisian data terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveReminderToDatabase(reminder: Reminder) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("reminders")


        val reminderId = databaseReference.push().key ?: return
        databaseReference.child(reminderId).setValue(reminder)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            binding.dateInput.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            binding.timeInput.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
        }, hour, minute, true)

        timePickerDialog.show()
    }
}