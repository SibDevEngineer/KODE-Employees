package com.example.kodeemployees.presentation.screens.users

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kodeemployees.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kodeemployees.app.appComponent
import com.example.kodeemployees.databinding.FragmentUsersBinding
import javax.inject.Inject
import javax.inject.Provider

class UsersFragment : Fragment(R.layout.fragment_users) {

    @Inject
    lateinit var viewModelProvider: Provider<UsersViewModel.Factory>

    private val viewModel: UsersViewModel by viewModels { viewModelProvider.get() }

    private val viewBinding by viewBinding(FragmentUsersBinding::bind)

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }


}