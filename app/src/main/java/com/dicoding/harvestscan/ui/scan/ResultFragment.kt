package com.dicoding.harvestscan.ui.scan

import android.content.Intent
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

//    private lateinit var label : String
//    private lateinit var score : String
//    private lateinit var image : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val intent = Intent(requireActivity(), ResultFragment::class.java)
//        image = intent.getStringExtra(EXTRA_IMAGE_URI).toString()
//        label = intent.getStringExtra(EXTRA_RESULT_LABEL).toString()
//        score = intent.getStringExtra(EXTRA_RESULT_SCORE).toString()
//
//        image.let {
//            binding.resultImage.setImageURI(Uri.parse(it))
//        }
//
//        val result = getString(R.string.result_string, label, score)
//        result.let {
//            binding.resultText.text = it
//        }

//        mBookmarkViewModel = ViewModelProvider(this)[BookmarkViewModel::class.java]
//
//        mBookmarkViewModel.resultDeleteBookmark.observe(this){
//            binding.fabBookmark.setImageResource(R.drawable.ic_bookmark_border)
//        }
//
//        mBookmarkViewModel.resultSuccesBookmark.observe(this){
//            binding.fabBookmark.setImageResource(R.drawable.ic_bookmark_fill)
//        }
//        mBookmarkViewModel.findBookmarkItem(image){
//            binding.fabBookmark.setImageResource(R.drawable.ic_bookmark_fill)
//        }
//        binding.fabBookmark.setOnClickListener{
//            mBookmarkViewModel.setBookmark(image, label, score, true)
//        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT_LABEL = "extra_result_label"
        const val EXTRA_RESULT_SCORE = "extra_result_score"
    }

}