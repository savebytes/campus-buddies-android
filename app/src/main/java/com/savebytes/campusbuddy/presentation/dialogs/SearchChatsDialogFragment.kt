package com.savebytes.campusbuddy.presentation.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.savebytes.campusbuddy.databinding.DialogSearchChatsBinding

class SearchChatsDialogFragment(
    private val onSearch: (String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: DialogSearchChatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSearchChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etSearch.requestFocus()

        binding.etSearch.doAfterTextChanged { text ->
            val query = text?.toString() ?: ""
            onSearch(query)
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnClear.setOnClickListener {
            binding.etSearch.text?.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}