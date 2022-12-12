package com.example.e10_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.e10_fragment.databinding.Frag1Binding
import com.example.e10_fragment.databinding.Frag2Binding

class Fragment2 : Fragment() {

    private var mBinding: Frag2Binding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    mBinding = Frag2Binding.inflate(layoutInflater)

    return binding.root
    }
}