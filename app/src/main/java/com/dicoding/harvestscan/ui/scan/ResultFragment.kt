package com.dicoding.harvestscan.ui.scan

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val image = ResultFragmentArgs.fromBundle(arguments as Bundle).imageUri
        val label = ResultFragmentArgs.fromBundle(arguments as Bundle).resultLabel
        val score = ResultFragmentArgs.fromBundle(arguments as Bundle).resultScore
        val description = ResultFragmentArgs.fromBundle(arguments as Bundle).resultDescription
        val tips = ResultFragmentArgs.fromBundle(arguments as Bundle).resultTips

        image.let {
            binding.resultImage.setImageURI(Uri.parse(it))
        }

        val result = getString(R.string.result_string, label, score)
        result.let {
            binding.resultText.text = it
        }

        binding.resultDesc.text = description
        binding.resultTips.text = tips
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
