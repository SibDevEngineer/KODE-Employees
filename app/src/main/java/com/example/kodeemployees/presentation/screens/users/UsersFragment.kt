package com.example.kodeemployees.presentation.screens.users

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kodeemployees.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kodeemployees.app.appComponent
import com.example.kodeemployees.databinding.FragmentUsersBinding
import com.example.kodeemployees.presentation.UIState
import com.example.kodeemployees.presentation.extensions.dpToPx
import com.example.kodeemployees.presentation.screens.users.adapter.User
import com.example.kodeemployees.presentation.screens.users.adapter.UsersAdapter
import com.example.kodeemployees.presentation.screens.users.adapter.UsersItemDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Provider

class UsersFragment : Fragment(R.layout.fragment_users) {

    @Inject
    lateinit var viewModelProvider: Provider<UsersViewModel.Factory>
    private val viewModel: UsersViewModel by viewModels { viewModelProvider.get() }

    private val binding by viewBinding(FragmentUsersBinding::bind)

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        UsersAdapter { item -> onUserClick(item) }
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setAdapter()

            vSwipeRefreshLayout.setOnRefreshListener { refreshUsersList() }

            viewModel.usersStateFlow.onEach {
                adapter.items = it
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.uiStateFlow.onEach {
                showState(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            vRefreshTxtBtn.setOnClickListener { refreshUsersList() }
        }
    }

    /** Настройка адаптера RecyclerView */
    private fun setAdapter() {
        with(binding) {

            vRecyclerUsers.setAdapter(adapter)
            vRecyclerUsers.setLayoutManager(LinearLayoutManager(requireContext()))

            val space = 4.dpToPx()
            vRecyclerUsers.getRecyclerView().addItemDecoration(UsersItemDecoration(space))

            vRecyclerUsers.addVeiledItems(15) //добавление элементов скелетонов в список
        }
    }

    /** Обновление списка пользователей */
    private fun refreshUsersList() {
        viewModel.refreshUsersList()
    }

    /**
     * Функция для визуализации элементов экрана при изменении состояний
     */
    private fun showState(state: UIState) {
        with(binding) {
            vSwipeRefreshLayout.isRefreshing = state is UIState.Refreshing
            vErrorLayout.isVisible = state is UIState.Error

            if (state is UIState.Loading) vRecyclerUsers.veil()
            else vRecyclerUsers.unVeil()
        }
    }

    /** Обработка нажатия на пользователя в списке */
    private fun onUserClick(user: User) {
        Toast.makeText(requireContext(), "${user.userName}", Toast.LENGTH_SHORT).show()
    }

}