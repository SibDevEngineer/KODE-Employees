package com.example.kodeemployees.presentation.screens.users.sort_users

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kodeemployees.R
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.databinding.FragmentSortUsersBinding
import com.example.kodeemployees.presentation.extensions.getParcelableData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SortUsersFragment : BottomSheetDialogFragment(R.layout.fragment_sort_users) {
    private val binding by viewBinding(FragmentSortUsersBinding::bind)

    private val currentSortingType by getParcelableData<SortUsersType>(CURRENT_SORT_TYPE_KEY)

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            when (currentSortingType) {
                SortUsersType.ALPHABET -> vSortByAlphabetBtn.isChecked = true
                else -> vSortByBirthdateBtn.isChecked = true
            }

            vSortBtnGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.vSortByAlphabetBtn -> sendResult(SortUsersType.ALPHABET)
                    else -> sendResult(SortUsersType.BIRTHDATE)
                }
            }
        }
    }

    private fun sendResult(sortingType: SortUsersType) {
        setFragmentResult(REQUEST_SORT_KEY, bundleOf(SORT_TYPE_KEY to sortingType))
        findNavController().popBackStack()
    }

    companion object {
        private const val REQUEST_SORT_KEY = "REQUEST_SORT_KEY"
        private const val SORT_TYPE_KEY = "SORT_TYPE_KEY"
        private const val CURRENT_SORT_TYPE_KEY = "CURRENT_SORT_TYPE_KEY"

    }
}