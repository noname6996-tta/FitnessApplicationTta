package com.tta.fitnessapplication.view.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.tta.fitnessapplication.data.utils.Constant

abstract class BaseFragment<T: ViewBinding>: Fragment() {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding as T

    protected lateinit var loginPreferences: SharedPreferences
    protected lateinit var loginPrefsEditor: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPreferences = requireActivity().getSharedPreferences(
            Constant.LOGIN_PREFS,
            AppCompatActivity.MODE_PRIVATE
        )
        loginPrefsEditor = loginPreferences.edit();
        initViewModel()
        initView()
        addEvent()
        addObservers()
        initData()
    }

    abstract fun getDataBinding(): T

    open fun initViewModel() {}

    open fun initView() {}

    open fun addEvent() {}

    open fun addObservers() {}

    open fun initData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}