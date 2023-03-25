package com.example.kodeemployees.presentation.screens.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kodeemployees.R
import com.example.kodeemployees.databinding.FragmentUserDetailBinding
import com.example.kodeemployees.presentation.extensions.getParcelableData
import com.example.kodeemployees.presentation.models.User
import java.text.SimpleDateFormat
import java.util.*

class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {
    private val binding by viewBinding(FragmentUserDetailBinding::bind)
    private val user by getParcelableData<User>(USER_KEY)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val imgUrl = user?.avatarUrl
            Glide.with(vAvatar)
                .load(imgUrl)
                .error(R.drawable.img_mock_photo)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(vAvatar)

            vNameUserDet.text = user?.userName
            vTagUser.text = user?.userTag ?: ""

            vProfession.text = user?.profession
                ?: requireContext().getString(R.string.user_detail_screen_empty_profession)

            vPhoneBtn.text =
                user?.phone ?: requireContext().getString(R.string.user_detail_screen_empty_phone)

            vAge.text = calculateUserAge()

            val formatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
            vBirthdate.text = user?.birthdate?.let { formatter.format(it) }

            vPhoneBtn.setOnClickListener { onPhoneCall() }
            vToolbarDetail.setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun onPhoneCall() {
        val phoneNumber = user?.phone

        if (phoneNumber != null && phoneNumber.isNotEmpty()) {
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:$phoneNumber")

            try {
                startActivity(phoneIntent)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateUserAge(): String {
        val calNow = Calendar.getInstance()
        val calUser = Calendar.getInstance().apply { time = user?.birthdate ?: Date() }
        val userAge = calNow.get(Calendar.YEAR) - calUser.get(Calendar.YEAR)

        return requireContext().resources.getQuantityString(
            R.plurals.age, userAge, userAge
        )
    }

    companion object {
        private const val USER_KEY = "USER_KEY"
    }
}
