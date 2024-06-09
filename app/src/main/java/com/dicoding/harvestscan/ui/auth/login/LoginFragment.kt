package com.dicoding.harvestscan.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.pref.UserModel
import com.dicoding.harvestscan.databinding.FragmentLoginBinding
import com.dicoding.harvestscan.ui.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDontHaveAccount.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
        }
        setupAction()
        playAnimation()
    }
    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            viewModel.saveSession(UserModel(email, "sample_token"))
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("Yeah!")
                setMessage("Anda berhasil login.")
                setPositiveButton("Lanjut") { _, _ ->
                    val navController = findNavController()
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.navigation_login, true) // This will remove LoginFragment from the back stack
                        .build()
                    navController.navigate(R.id.navigation_home, null, navOptions)
                }
                create()
                show()
            }
        }
    }
    private fun playAnimation() {

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

}