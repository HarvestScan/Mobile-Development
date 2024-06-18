package com.dicoding.harvestscan.ui.scan

import android.net.Uri
import android.os.Bundle
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
import java.io.File
import java.io.FileOutputStream

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
            currentImageUri?.let {
                analyzeImage(it)
            } ?: run {
                showToast(getString(R.string.no_media_selected))
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
            binding.ivScanImage.setImageURI(it)
            binding.btnAnalyze.isVisible = true
            binding.tvInsertImage.text = getString(R.string.text_analyze)

            val savedImageUri = saveImageToInternalStorage(it)

            savedImageUri?.let { _ ->
            } ?: run {
                showToast("Failed to save image")
            }
        }
    }

    private fun analyzeImage(uri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            classifierListener = this
        )

        imageClassifierHelper.classifyStaticImage(uri)
    }

    private fun moveToResult(uri: Uri, label: String, score: String, description: String, tips: String) {
        val action = ScanFragmentDirections.actionNavigationScanToNavigationResult()
        action.imageUri = uri.toString()
        action.resultLabel = label
        action.resultScore = score
        action.resultDescription = description
        action.resultTips = tips

        findNavController().navigate(action)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun saveImageToInternalStorage(uri: Uri): Uri? {
        val context = requireContext()
        val inputStream = context.contentResolver.openInputStream(uri)

        val currentTime = System.currentTimeMillis()
        val fileName = "scanned_image_$currentTime.jpg"

        val file = File(context.filesDir, fileName)

        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        return if (file.exists()) Uri.fromFile(file) else null
    }

    override fun onError(error: String) {
        showToast(error)
    }

    override fun onResults(results: List<Pair<String, Float>>?) {
        results?.let { result ->
            if (result.isNotEmpty()) {
                val topResult = result[0]
                val confidenceScore = "${(topResult.second * 100).toInt()}%"
                val diseaseInfo = DiseaseData.diseaseDetails[topResult.first]
                val description = diseaseInfo?.description?.let { requireContext().getString(it) } ?: getString(R.string.no_description_available)
                val tips = diseaseInfo?.tips?.let { requireContext().getString(it) } ?: getString(R.string.no_tips_available)

                val savedImageUri = saveImageToInternalStorage(currentImageUri!!)

                savedImageUri?.let {
                    val scanHistory = ScanHistory(
                        label = topResult.first,
                        confidenceScore = confidenceScore,
                        description = description,
                        tips = tips,
                        imageUri = it.toString()
                    )

                    viewModel.addScanHistory(scanHistory)
                    moveToResult(it, topResult.first, confidenceScore, description, tips)
                } ?: run {
                    showToast(getString(R.string.failed_to_save_image))
                }
            }
        }

    }

}
