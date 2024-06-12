package com.dicoding.harvestscan.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.dicoding.harvestscan.R
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class ImageClassifierHelper(
    private val context: Context,
    private val classifierListener: ClassifierListener?
) {
    private var interpreter: Interpreter? = null
    private var inputImageBuffer: TensorImage? = null
    private var outputProbabilityBuffer: TensorBuffer? = null
    private var probabilityProcessor: TensorProcessor? = null
    private lateinit var labels: List<String>

    init {
        setupInterpreter()
    }

    private fun setupInterpreter() {
        try {
            val model = FileUtil.loadMappedFile(context, "plant_disease.tflite")
            interpreter = Interpreter(model)
            labels = FileUtil.loadLabels(context, "label.txt")

            inputImageBuffer = TensorImage(interpreter!!.getInputTensor(0).dataType())

            outputProbabilityBuffer = TensorBuffer.createFixedSize(
                interpreter!!.getOutputTensor(0).shape(),
                interpreter!!.getOutputTensor(0).dataType()
            )

            probabilityProcessor = TensorProcessor.Builder().add(NormalizeOp(0.0f, 1.0f)).build()

        } catch (e: Exception) {
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyStaticImage(uri: Uri) {
        if (interpreter == null) {
            setupInterpreter()
        }

        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(127.5f, 127.5f)) // Normalize to [-1, 1]
            .build()

        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }.copy(Bitmap.Config.ARGB_8888, true)

        bitmap?.let {
            inputImageBuffer = imageProcessor.process(TensorImage.fromBitmap(it))

            interpreter?.run(
                inputImageBuffer!!.buffer, outputProbabilityBuffer!!.buffer.rewind()
            )

            val labeledProbability = TensorLabel(labels, probabilityProcessor!!.process(outputProbabilityBuffer)).mapWithFloatValue

            val result = getTopResult(labeledProbability)

            classifierListener?.onResults(result)
        }
    }

    private fun getTopResult(labelProb: Map<String, Float>): List<Pair<String, Float>> {
        return labelProb.entries
            .sortedByDescending { it.value }
            .take(3)
            .map { Pair(it.key, it.value) }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(results: List<Pair<String, Float>>?)
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
