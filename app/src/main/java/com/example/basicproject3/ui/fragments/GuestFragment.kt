package com.example.basicproject3.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.basicproject3.R
import com.example.basicproject3.auth.LoginActivity
import com.example.basicproject3.auth.RegisterActivity
import com.example.basicproject3.databinding.FragmentGuestBinding
import com.example.basicproject3.ui.viewmodels.GuestViewModel
import com.google.firebase.auth.FirebaseAuth

class GuestFragment : Fragment() {
    private var _binding: FragmentGuestBinding? = null
    private val binding get() = _binding!!
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*val homeViewModel =
            ViewModelProvider(this).get(GuestViewModel::class.java)*/

        _binding = FragmentGuestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textProfile
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            // Nếu người dùng đã đăng nhập, tiếp tục với app
            findNavController().navigate(R.id.navigate_to_user_fragment)
        } else {
            // Nếu người dùng chưa đăng nhập, chuyển hướng đến màn hình đăng nhập
            binding.btnSignInLink.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}