package com.example.basicproject3.auth

import android.content.ContentValues.TAG
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
import com.example.basicproject3.MainActivity
import com.example.basicproject3.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
//    private lateinit var currentUser: FirebaseAuth
//    private lateinit var data: FirebaseFirestore
//    private lateinit var uid: String
//    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()
        /*currentUser = FirebaseAuth.getInstance()
        val uid = currentUser.uid*/
        val data = Firebase.firestore
//        database = FirebaseDatabase.getInstance()

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // Set click listener for button
        binding.btnSignUp.setOnClickListener {
            // Handle button click event

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
                                "email" to email
                            )

                            data.collection("users").document(uid!!)
                                .set(user)
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                            val intent = Intent(activity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        handleSignUpLinkClick()

        return rootView
    }

    private fun handleSignUpLinkClick() {
        // Handle when click Sign up
        val text = "Don't have an account? Sign in"
        val ssb = SpannableStringBuilder(text)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = true
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

    /*companion object {
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {

            }
    }*/
}