package com.dicoding.harvestscan.ui.myplant

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.local.room.Plant
import com.dicoding.harvestscan.databinding.FragmentAddPlantBinding
import com.dicoding.harvestscan.ui.ViewModelFactory
import java.io.ByteArrayOutputStream

class AddPlantFragment : Fragment() {

    private var _binding: FragmentAddPlantBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null
    private val plantViewModel: MyPlantViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_PICK = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPlantBinding.inflate(inflater, container, false)

        binding.btnCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }

        binding.btnGallery.setOnClickListener {
            val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
        }

        binding.saveAddPlant.setOnClickListener {
            savePlant()
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    binding.imageAddPlant.setImageBitmap(imageBitmap)
                    imageUri = getImageUriFromBitmap(imageBitmap)
                }
                REQUEST_IMAGE_PICK -> {
                    imageUri = data?.data
                    binding.imageAddPlant.setImageURI(imageUri)
                }
            }
        }
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

    private fun savePlant() {
        val name = binding.descNamePlant.text.toString()
        val type = binding.edAddDescription.text.toString()
        val botanicalName = binding.descBotanicalPlant.text.toString()
        val image = imageUri.toString()

        if (name.isNotEmpty() && type.isNotEmpty() && botanicalName.isNotEmpty() && image.isNotEmpty()) {
            val plant = Plant(name = name, type = type, botanicalName = botanicalName, imageUri = image)
            plantViewModel.insert(plant)

            showCustomToast(getString(R.string.plant_has_been_added))
            findNavController().navigate(R.id.navigation_my_plant)
        } else {
            showCustomToast(getString(R.string.all_inputs_must_be_filled))
        }
    }

    private fun showCustomToast(message: String) {
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        val view = toast.view

        val text = view?.findViewById<TextView>(android.R.id.message)
        text?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
