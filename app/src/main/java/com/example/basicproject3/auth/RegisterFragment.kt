package com.example.basicproject3.auth

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.basicproject3.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        handleSignUpButtonClick()
        handleSignInLinkClick()

        return binding.root
    }

    private fun handleSignUpButtonClick() {
        auth = FirebaseAuth.getInstance()
        val db: FirebaseFirestore = Firebase.firestore
        binding.btnSignUp.setOnClickListener {
            val name = binding.etSignUpUsername.text.toString().trim()
            val email = binding.etSignUpEmail.text.toString().trim()
            val password = binding.etSignUpPassword.text.toString().trim()
            val passwordConfirm = binding.etConfirmPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (password == passwordConfirm) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val uid = auth.currentUser?.uid

                            val user = hashMapOf(
                                "name" to name,
                            )

                            db.collection("users").document(uid!!).set(user)
                            val intent = Intent(activity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSignInLinkClick() {
        // Handle when click Sign up
        val text = "Don't have an account? Sign in"
        val ssb = SpannableStringBuilder(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
                ds.color = ds.linkColor
            }
        }
        val startIndex = text.indexOf("Sign in")
        val endIndex = startIndex + "Sign in".length
        ssb.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.txtSignInLink.text = ssb
        binding.txtSignInLink.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}