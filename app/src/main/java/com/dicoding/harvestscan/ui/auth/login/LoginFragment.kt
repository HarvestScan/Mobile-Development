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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dicoding.harvestscan.R
import com.dicoding.harvestscan.data.remote.Result
import com.dicoding.harvestscan.data.remote.response.User
import com.dicoding.harvestscan.databinding.FragmentLoginBinding
import com.dicoding.harvestscan.ui.MainViewModel
import com.dicoding.harvestscan.ui.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDontHaveAccount.setOnClickListener {
            view.findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
        }
        setupAction()
        observeViewModel()
        playAnimation()
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        loginProcess(result.data.userCredential.user)
                        showSuccessDialog(result.data.message, R.id.action_navigation_login_to_navigation_home)
                    }
                    is Result.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun loginProcess(data: User) {
        viewModel.saveSession(data)
        showToast(getString(R.string.login_success_message))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.loginUser(email, password)
        }
        binding.forgotPassword.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_navigation_login_to_navogation_forgot_password)
        }
    }

    private fun showSuccessDialog(message: String, navigate: Int) {
        val dialog = AlertDialog.Builder(requireActivity()).apply {
            setTitle(getString(R.string.yeay))
            setMessage(message)
            setPositiveButton(getString(R.string.tvcontinue)) { _, _ ->
                findNavController().navigate(navigate)
                mainViewModel.onHomeButtonClicked()
            }
            create()
        }.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val forgotPassword = ObjectAnimator.ofFloat(binding.forgotPassword, View.ALPHA, 1f).setDuration(100)
        val register = ObjectAnimator.ofFloat(binding.tvDontHaveAccount, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                forgotPassword,
                register,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
