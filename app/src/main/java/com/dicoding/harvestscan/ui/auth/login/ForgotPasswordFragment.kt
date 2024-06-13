package com.dicoding.harvestscan.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.remote.Result
import com.dicoding.harvestscan.databinding.FragmentForgotPasswordBinding
import com.dicoding.harvestscan.ui.ViewModelFactory

class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAction()
        observeViewModel()
        playAnimation()
    }
    private fun setupAction() {
        binding.forgotPasswordButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            viewModel.forgotPassword(email)
        }
    }

    private fun observeViewModel(){
        viewModel.forgotPasswordResult.observe(viewLifecycleOwner){ result ->
            when(result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    showSuccessDialog(result.data.message, R.id.action_navigation_login_to_navigation_home)
                }
                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessDialog(message: String, navigate: Int) {
        val dialog = AlertDialog.Builder(requireActivity()).apply {
            setTitle("Yeay!")
            setMessage(message)
            setPositiveButton("Continue") { _, _ ->
                findNavController().navigate(navigate)
            }
            create()
        }.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleForgotPassword, View.ALPHA, 1f).setDuration(100)
        val description = ObjectAnimator.ofFloat(binding.titleDescription, View.ALPHA, 1f).setDuration(100)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val forgotPasswordBtn = ObjectAnimator.ofFloat(binding.forgotPasswordButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                description,
                emailTextView,
                emailEditTextLayout,
                forgotPasswordBtn
            )
            startDelay = 100
        }.start()
    }

}