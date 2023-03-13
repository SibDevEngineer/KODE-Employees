package com.example.kodeemployees.presentation.screens.users.sort_users

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kodeemployees.R
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.databinding.FragmentSortUsersBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SortUsersFragment : BottomSheetDialogFragment(R.layout.fragment_sort_users) {
    private val binding by viewBinding(FragmentSortUsersBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            vSortBtnGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.vSortByAlphabetBtn -> sendResult(SortUsersType.ALPHABET)
                    else -> sendResult(SortUsersType.BIRTHDATE)
                }
            }
        }
    }

    private fun sendResult(sortingType: SortUsersType) {
        setFragmentResult(SORTING_KEY, bundleOf("bundleKey" to sortingType))
    }

    companion object {
        private const val SORTING_KEY = "SORTING_KEY"
    }
}