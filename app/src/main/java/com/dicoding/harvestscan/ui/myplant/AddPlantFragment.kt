package com.dicoding.harvestscan.ui.myplant

import PlantViewModel
import PlantViewModelFactory
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.local.room.Plant
import java.io.ByteArrayOutputStream

class AddPlantFragment : Fragment() {

    private lateinit var plantViewModel: PlantViewModel
    private lateinit var imageView: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var typeEditText: EditText
    private lateinit var botanicalNameEditText: EditText
    private lateinit var saveButton: Button
    private var imageUri: Uri? = null

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_PICK = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_plant, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = PlantViewModelFactory(application)
        plantViewModel = ViewModelProvider(this, viewModelFactory).get(PlantViewModel::class.java)

        imageView = view.findViewById(R.id.image_add_plant)
        nameEditText = view.findViewById(R.id.desc_name_plant)
        typeEditText = view.findViewById(R.id.ed_add_description)
        botanicalNameEditText = view.findViewById(R.id.desc_botanical_plant)
        saveButton = view.findViewById(R.id.save_add_plant)

        view.findViewById<Button>(R.id.btn_camera).setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }

        view.findViewById<Button>(R.id.btn_gallery).setOnClickListener {
            val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
        }

        saveButton.setOnClickListener {
            savePlant()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    imageView.setImageBitmap(imageBitmap)
                    imageUri = getImageUriFromBitmap(imageBitmap)
                }
                REQUEST_IMAGE_PICK -> {
                    imageUri = data?.data
                    imageView.setImageURI(imageUri)
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
        val name = nameEditText.text.toString()
        val type = typeEditText.text.toString()
        val botanicalName = botanicalNameEditText.text.toString()
        val image = imageUri.toString()

        if (name.isNotEmpty() && type.isNotEmpty() && botanicalName.isNotEmpty() && image.isNotEmpty()) {
            val plant = Plant(name = name, type = type, botanicalName = botanicalName, imageUri = image)
            plantViewModel.insert(plant)

            // Tampilkan notifikasi "Tanaman sudah ditambahkan"
            Toast.makeText(requireContext(), "Tanaman sudah ditambahkan", Toast.LENGTH_SHORT).show()

            // Berpindah ke fragment MyPlantFragment setelah menambahkan tanaman
            findNavController().navigate(R.id.navigation_my_plant)
        } else {
            // Tampilkan notifikasi jika ada input yang kosong
            Toast.makeText(requireContext(), "Semua input harus diisi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteConfirmationDialog(plant: Plant) {
        AlertDialog.Builder(requireContext())
            .setMessage("Anda yakin ingin menghapus tanaman ini?")
            .setPositiveButton("Ya") { dialog, which ->
                deletePlant(plant)
                Toast.makeText(requireContext(), "Tanaman telah dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    private fun deletePlant(plant: Plant) {
        plantViewModel.delete(plant)
    }
}
