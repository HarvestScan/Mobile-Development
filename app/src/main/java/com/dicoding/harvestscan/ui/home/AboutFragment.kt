package com.dicoding.harvestscan.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.harvestscan.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AboutFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
    override fun getTheme() = R.style.BottomSheetTransparent
}