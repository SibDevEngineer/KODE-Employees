package com.example.kodeemployees.presentation.screens.user_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kodeemployees.R
import com.example.kodeemployees.app.appComponent
import com.example.kodeemployees.core.extensions.extraNonNull
import com.example.kodeemployees.databinding.FragmentUserDetailBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {
    private val userId by extraNonNull<String>(USER_ID_KEY)

    @Inject
    lateinit var viewModelFactory: UserDetailViewModelFactory.Factory
    private val viewModel: UserDetailViewModel by viewModels {
        viewModelFactory.create(userId)
    }

    private val binding by viewBinding(FragmentUserDetailBinding::bind)

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            viewModel.userDetailStateFlow.onEach {
                vNameUserDet.text = it.userName
                vTagUser.text = it.userTag
                vProfession.text = it.profession
                vPhoneBtn.text = it.phone
                vAge.text = it.age
                vBirthdate.text = it.birthdate

                val imgUrl = it.avatarUrl
                Glide.with(vAvatar)
                    .load(imgUrl)
                    .error(R.drawable.img_mock_photo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(vAvatar)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            vPhoneBtn.setOnClickListener {
                onPhoneCall(viewModel.getPhoneNumber())
            }
            vToolbarDetail.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun onPhoneCall(phoneNumber: String?) {
        if (!phoneNumber.isNullOrEmpty()) {
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:$phoneNumber")

            try {
                startActivity(phoneIntent)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val USER_ID_KEY = "USER_KEY"
    }
}
