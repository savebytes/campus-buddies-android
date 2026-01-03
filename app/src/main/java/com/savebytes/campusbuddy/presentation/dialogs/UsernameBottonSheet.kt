package com.savebytes.campusbuddy.presentation.dialogs

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.savebytes.campusbuddy.databinding.LayoutUsernameBottomsheetBinding

class UsernameBottomSheet : BottomSheetDialogFragment() {

    private var _binding: LayoutUsernameBottomsheetBinding? = null
    private val binding get() = _binding!!

    private var onUsernameSelected: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutUsernameBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        // Username validation
        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateUsername(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Continue button click
        binding.btnContinue.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()

            if (validateUsername(username)) {
                onUsernameSelected?.invoke(username)
                dismiss()
            }
        }
    }

    private fun validateUsername(username: String): Boolean {
        return when {
            username.isEmpty() -> {
                binding.usernameInputLayout.error = "Username is required"
                false
            }
            username.length < 3 -> {
                binding.usernameInputLayout.error = "Username must be at least 3 characters"
                false
            }
            !username.matches(Regex("^[a-zA-Z0-9_]+$")) -> {
                binding.usernameInputLayout.error = "Use letters, numbers, and underscores only"
                false
            }
            else -> {
                binding.usernameInputLayout.error = null
                true
            }
        }
    }

    fun setOnUsernameSelectedListener(listener: (String) -> Unit) {
        onUsernameSelected = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "UsernameBottomSheet"

        fun newInstance(): UsernameBottomSheet {
            return UsernameBottomSheet()
        }
    }
}