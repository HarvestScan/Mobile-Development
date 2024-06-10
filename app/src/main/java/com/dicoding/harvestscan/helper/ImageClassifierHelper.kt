package com.dicoding.harvestscan.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.dicoding.harvestscan.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp

class ImageClassifierHelper(
    private var threshold: Float = 0.1f,
    private var maxResults: Int = 3,
    private val modelName: String = "plant_disease.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }
//    private fun setupImageClassifier() {
//        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
//            .setScoreThreshold(threshold)
//            .setMaxResults(maxResults)
//        val baseOptionsBuilder = BaseOptions.builder()
//            .setNumThreads(4)
//        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())
//
//        try {
//            imageClassifier = ImageClassifier.createFromFileAndOptions(
//                context,
//                modelName,
//                optionsBuilder.build()
//            )
//        } catch (e: IllegalStateException) {
//            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
//            Log.e(TAG, e.message.toString())
//        }
//    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            classifierListener?.onError("error")
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyStaticImage(uri: Uri) {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }.copy(Bitmap.Config.ARGB_8888, true)

        bitmap?.let {
            // Preprocess the image
            val processedImage = preprocessImage(bitmap)

            // Classify the image
            val results = imageClassifier?.classify(processedImage)
            classifierListener?.onResults(results)
        }
    }

    private fun preprocessImage(bitmap: Bitmap): TensorImage {
        // Resize the image to match the input size of the model (224x224)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

        // Convert Bitmap to TensorImage and normalize pixel values
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0f, 255f)) // Normalization options
            .build()
        val inputImage = TensorImage(DataType.FLOAT32)
        inputImage.load(resizedBitmap)
        return imageProcessor.process(inputImage)
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: List<Classifications>?)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}