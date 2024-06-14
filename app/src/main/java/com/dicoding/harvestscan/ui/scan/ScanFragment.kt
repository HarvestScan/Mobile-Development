package com.dicoding.harvestscan.ui.scan

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.databinding.FragmentScanBinding
import com.dicoding.harvestscan.getImageUri
import com.dicoding.harvestscan.helper.ImageClassifierHelper
import com.dicoding.harvestscan.data.local.DiseaseData
import com.dicoding.harvestscan.data.local.room.ScanHistory
import com.dicoding.harvestscan.ui.ViewModelFactory
import com.dicoding.harvestscan.ui.auth.login.LoginViewModel

class ScanFragment : Fragment(), ImageClassifierHelper.ClassifierListener {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.history_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_history -> {
                        findNavController().navigate(R.id.action_navigation_scan_to_navigation_history)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnAnalyze.setOnClickListener {
            Log.d("ButtonAnalyze", "Tombol analyze diklik!")
            currentImageUri?.let {
                analyzeImage(it)
            } ?: run {
                showToast("Masukkan gambar terlebih dahulu")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            showToast(getString(R.string.no_media_selected))
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireActivity())
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivScanImage.setImageURI(it)
            binding.btnAnalyze.isVisible = true
            binding.tvInsertImage.text = getString(R.string.text_analyze)
        }
    }

    private fun analyzeImage(uri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = this
        )

        imageClassifierHelper.classifyStaticImage(uri)
    }

    private fun moveToResult(uri: Uri, label: String, score: Float, description: String, tips: String) {
        val action = ScanFragmentDirections.actionNavigationScanToNavigationResult()
        action.imageUri = uri.toString()
        action.resultLabel = label
        action.resultScore = score.toString()
        action.resultDescription = description
        action.resultTips = tips

        findNavController().navigate(action)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: String) {
        showToast(error)
    }

    override fun onResults(results: List<Pair<String, Float>>?) {
        results?.let { result ->
            if (result.isNotEmpty()) {
                val topResult = result[0]
                val diseaseInfo = DiseaseData.diseaseDetails[topResult.first]
                val description = diseaseInfo?.description ?: "No description available"
                val tips = diseaseInfo?.tips ?: "No tips available"
                Log.d("HasilScan", "${topResult.first}: ${topResult.second}")

                val scanHistory = ScanHistory(
                    label = topResult.first,
                    confidenceScore = topResult.second,
                    description = description,
                    tips = tips,
                    imageUri = currentImageUri.toString()
                )
                viewModel.addScanHistory(scanHistory)

                moveToResult(currentImageUri!!, topResult.first, topResult.second, description, tips)
            }
        }
    }

}
