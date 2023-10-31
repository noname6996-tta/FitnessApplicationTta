package com.tta.fitnessapplication.view.fragment

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.tta.fitnessapplication.databinding.FragmentChangePasswordBinding
import com.tta.fitnessapplication.view.base.BaseFragment

class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>() {
    private var userEmail = ""
    override fun getDataBinding(): FragmentChangePasswordBinding {
        return FragmentChangePasswordBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val email = user?.email
        if (email != null) {
            userEmail = email
            binding.edtEmail.setText(userEmail)
            // Email của người dùng hiện tại
            // Hãy sử dụng biến email để thực hiện các hành động mong muốn
        } else {
            // Không tìm thấy email của người dùng hiện tại
            // Xử lý lỗi tại đây
            Toast.makeText(
                requireContext(),
                "An error occurred. Please try again later",
                Toast.LENGTH_SHORT
            ).show()
        }

        val credential = EmailAuthProvider.getCredential(email.toString(), "mật khẩu cũ")

        binding.view.setOnClickListener {
            // Xác thực mật khẩu cũ
            user?.reauthenticate(credential)?.addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    // Nếu xác thực thành công, tiến hành thay đổi mật khẩu
                    val newPassword = binding.edtNewPassword.text.toString().trim()
                    val newAgainPassword = binding.edtAgainNewPassword.text.toString().trim()
                    if (newPassword.trim() == newAgainPassword.trim()) {
                        user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                // Thay đổi mật khẩu thành công
                                // Hãy thực hiện các hành động mong muốn sau khi mật khẩu được thay đổi
                                Toast.makeText(
                                    requireContext(),
                                    "Change password success",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().popBackStack()
                            } else {
                                // Thay đổi mật khẩu không thành công
                                // Xử lý lỗi tại đây
                                Toast.makeText(
                                    requireContext(),
                                    "An error occurred. Please try again later",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "The newly entered password does not match",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Xác thực mật khẩu cũ không thành công
                    // Xử lý lỗi tại đây
                    Toast.makeText(
                        requireContext(),
                        "The current password you entered is incorrect",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.viewBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}