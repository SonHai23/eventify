package com.example.basicproject3.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicproject3.R
import com.example.basicproject3.data.model.Image
import com.example.basicproject3.data.model.PopularEvents
import com.example.basicproject3.databinding.FragmentPopularEventBinding
import com.example.basicproject3.databinding.FragmentUserBinding
import com.example.basicproject3.ui.adapters.PopularEventsAdapter

class PopularEventFragment : Fragment() {
    private var _binding: FragmentPopularEventBinding? = null
    private val binding get() = _binding!!
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<PopularEvents>
    lateinit var imageID: Array<Int>
    lateinit var heading: Array<String>

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularEventBinding.inflate(inflater, container, false)

        imageID = arrayOf(
            R.drawable.edm1,
            R.drawable.edm2,
            R.drawable.edm3,
            R.drawable.edm4,
            R.drawable.edm5,
            R.drawable.edm6,
            R.drawable.edm7,
            R.drawable.edm8,
            R.drawable.edm9,
        )

        heading = arrayOf(
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
            "Edm",
        )

        newRecyclerView = binding.rvPopularEvent
        newRecyclerView.layoutManager = LinearLayoutManager(activity)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<PopularEvents>()
        getPopularEvents()

        return binding.root
    }

    private fun getPopularEvents() {
        for (i in imageID.indices) {
            val popularEvents = PopularEvents(imageID[i], heading[i])
            newArrayList.add(popularEvents)
        }
        newRecyclerView.adapter = PopularEventsAdapter(newArrayList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}