package com.example.tolist_final.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tolist_final.MainActivity
import com.example.tolist_final.daftar.AddDaftar
import com.example.tolist_final.data_class.Label
import com.example.tolist_final.databinding.FragmentHomeBinding
import com.example.tolist_final.pengingat.AddPengingat
import com.example.tolist_final.recycler.LabelAdapter
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private val labelsList = mutableListOf<Label>()
    private lateinit var labelAdapter: LabelAdapter
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Inisialisasi referensi Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("labels")

        // Setup RecyclerView
        labelAdapter = LabelAdapter(labelsList)
        binding.recyclerViewData.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = labelAdapter
        }

        // Fetch data dari Firebase
        fetchLabels()

        // Setup Search Bar
        setupSearchBar()

        // Tombol navigasi
        binding.addPengingatNav.setOnClickListener {
            val intent = Intent(requireContext(), AddPengingat::class.java)
            startActivity(intent)
            activity?.finish() // Menutup Activity yang memuat Fragment.
        }

        binding.addDaftarNav.setOnClickListener {
            val intent = Intent(requireContext(), AddDaftar::class.java)
            startActivity(intent)
            activity?.finish() // Menutup Activity yang memuat Fragment.
        }


        return binding.root
    }

    private fun fetchLabels() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                labelsList.clear()
                for (data in snapshot.children) {
                    val label = data.getValue(Label::class.java)
                    if (label != null) {
                        labelsList.add(label)
                    } else {
                        val rawData = data.getValue(String::class.java)
                        println("Data yang tidak sesuai: $rawData")
                    }
                }
                labelAdapter.updateData(labelsList)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Gagal memuat data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setupSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filteredLabels = labelsList.filter {
                    it.name?.contains(s.toString(), ignoreCase = true) == true
                }
                labelAdapter.updateData(filteredLabels)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
