package com.example.kodeemployees.presentation.screens.users

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.kodeemployees.R
import com.example.kodeemployees.app.appComponent
import com.example.kodeemployees.core.extensions.dpToPx
import com.example.kodeemployees.core.extensions.getSerializableData
import com.example.kodeemployees.core.extensions.gone
import com.example.kodeemployees.core.extensions.setTint
import com.example.kodeemployees.core.extensions.show
import com.example.kodeemployees.core.extensions.showIf
import com.example.kodeemployees.core.extensions.textChangesWithDebounce
import com.example.kodeemployees.data.models.SortUsersType
import com.example.kodeemployees.databinding.FragmentUsersBinding
import com.example.kodeemployees.domain.models.Department
import com.example.kodeemployees.presentation.screens.users.adapter.UsersAdapter
import com.example.kodeemployees.presentation.screens.users.adapter.UsersItemDecoration
import com.example.kodeemployees.presentation.screens.users.models.UserItemUi
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cоздаем слушатель и подписываемся на результат из SortUsersFragment
        setFragmentResultListener(REQUEST_SORT_KEY) { _, bundle ->
            val selectedSortingType = bundle.getSerializableData<SortUsersType>(SORT_TYPE_KEY)
            selectedSortingType?.let { viewModel.changeSortingType(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setAdapter()

            viewModel.departmentsStateFlow.onEach {
                initDepartmentsTabs(it)
                initTabsListener(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.screenStateFlow.onEach { state ->
                showState(state)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            vSwipeRefreshLayout.setOnRefreshListener {
                viewModel.refreshUsersList()
            }

            vSortUsersBtn.setOnClickListener {
                onSortUsersBtnClick()
            }
            vRefreshTxtBtn.setOnClickListener {
                viewModel.refreshUsersList()
            }
            vClearTxtBtn.setOnClickListener {
                vSearchEditText.text?.clear()
            }
            vCancelTxtBtn.setOnClickListener {
                vSearchEditText.text?.clear()
                vSearchEditText.clearFocus()
                hideKeyboard()
            }

            vSearchEditText.textChangesWithDebounce(DEBOUNCE_MILLIS)
                .onEach { viewModel.onSearchTextChanged(it.toString().trim()) }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            vSearchEditText.setOnFocusChangeListener { _, isFocused ->
                vCancelTxtBtn.showIf(isFocused)
                vClearTxtBtn.showIf(isFocused)
                vSortUsersBtn.showIf(!isFocused)
                vSearchImg.setTint(if (isFocused) R.color.black else R.color.gray2)
            }
        }
    }

    /** Настройка адаптера RecyclerView */
    private fun setAdapter() {
        with(binding) {
            vRecyclerUsers.adapter = adapter
            adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW

            val space = 4.dpToPx()
            vRecyclerUsers.addItemDecoration(UsersItemDecoration(space))
        }
    }

    private fun initDepartmentsTabs(departmentsList: List<Department>) {
        with(binding) {
            departmentsList.forEach { department ->
                vTabLayout.addTab(
                    vTabLayout.newTab().setText(
                        resources.getString(department.title)
                    )
                )
            }
        }
    }

    private fun initTabsListener(departmentsList: List<Department>) {
        binding.vTabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val department = departmentsList[tab?.position!!]
                viewModel.onDepartmentSelected(department)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    /** Функция визуализации изменения состояний */
    private fun showState(state: ScreenState<List<UserItemUi>>) {
        with(binding) {
            vSwipeRefreshLayout.isRefreshing = false

            when (state) {
                is ScreenState.Data -> {
                    adapter.items = state.data
                    vErrorLayout.gone()
                    vRecyclerUsers.show()
                }

                else -> {
                    vErrorLayout.show()
                    vRecyclerUsers.gone()

                    vErrorImg.show()
                    Glide.with(vErrorImg)
                        .load(R.drawable.img_state_error)
                        .into(vErrorImg)

                    vErrorTitle.text = getString(R.string.users_screen_state_error_title)
                    vErrorMsg.text = getString(R.string.users_screen_state_error_msg)
                    vRefreshTxtBtn.show()
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(binding.vSearchEditText.windowToken, 0)
    }

    /** Обработка нажатия на пользователя в списке */
    private fun onUserClick(userId: String) {
        val bundle = bundleOf(USER_ID_KEY to userId)
        findNavController().navigate(R.id.action_usersFragment_to_userDetailFragment, bundle)
    }

    private fun onSortUsersBtnClick() {
        val currentSortingType = viewModel.getCurrentSortType()

        //передаем текущее значение типа сортировки для корректного отображения в списке выбора
        val bundle = bundleOf(CURRENT_SORT_TYPE_KEY to currentSortingType)
        findNavController().navigate(R.id.action_usersFragment_to_sortUsersFragment, bundle)
    }

    companion object {
        private const val REQUEST_SORT_KEY = "REQUEST_SORT_KEY"
        private const val SORT_TYPE_KEY = "SORT_TYPE_KEY"
        private const val CURRENT_SORT_TYPE_KEY = "CURRENT_SORT_TYPE_KEY"
        private const val USER_ID_KEY = "USER_KEY"

        private const val DEBOUNCE_MILLIS = 300L
    }
}