package com.shakircam.flowtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.shakircam.flowtest.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.liveDataBt.setOnClickListener {
            mainViewModel.triggerLiveData()
        }

        binding.stateFlowBt.setOnClickListener {
            mainViewModel.triggerStateFlowData()
        }

        binding.shareFlowBt.setOnClickListener {
            mainViewModel.triggerShareFlowData()
        }

        binding.flowBt.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.triggerFlow().collectLatest {
                    binding.flowTx.text = it
                }
            }
        }

        observeData()

    }

    private fun observeData(){

        mainViewModel.liveData.observe(this){
            binding.liveDataTx.text = it
            Snackbar.make(
                binding.root,
                it,
                Snackbar.LENGTH_LONG
            ).show()
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.stateFlow.collectLatest {
                 binding.stateFlowTx.text = it
                Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.shareFlow.collectLatest {
                 binding.shareFlowTx.text = it
                Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }


}