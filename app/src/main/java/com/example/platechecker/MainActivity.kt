package com.example.platechecker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.platechecker.databinding.ActivityMainBinding
import com.example.platechecker.viewmodel.PlateViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PlateViewModel by viewModels()
    private lateinit var adapter: PlateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PlateAdapter()
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = adapter

        binding.queryButton.setOnClickListener {
            val plateNumber = binding.plateInput.text.toString().trim()
            if (plateNumber.isNotEmpty()) {
                viewModel.queryPlate(plateNumber)
            }
        }

        binding.markUsedButton.setOnClickListener {
            val plateNumber = binding.plateInput.text.toString().trim()
            if (plateNumber.isNotEmpty()) {
                viewModel.markAsUsed(plateNumber)
            }
        }

        viewModel.queryResult.observe(this) { result ->
            binding.resultText.text = result
        }

        lifecycleScope.launchWhenStarted {
            viewModel.allRecords.collectLatest { records ->
                adapter.submitList(records)
            }
        }
    }
}