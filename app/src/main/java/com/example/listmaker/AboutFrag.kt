package com.example.listmaker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.listmaker.databinding.FragmentAboutBinding

class AboutFrag : Fragment() {
    lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_about,container,false)
        binding.aboutTool.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_aboutFrag_to_mainPage)
        }



        return binding.root
    }


}
