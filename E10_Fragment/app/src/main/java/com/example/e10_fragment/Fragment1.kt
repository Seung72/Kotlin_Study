package com.example.e10_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e10_fragment.databinding.Frag1Binding

class Fragment1 : Fragment() {

    private var mBinding: Frag1Binding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    mBinding = Frag1Binding.inflate(layoutInflater)

    return binding.root
    }
}