package com.tta.fitnessapplication.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T: ViewBinding>: AppCompatActivity() {
    private var _binding: T? = null
    protected val binding: T
        get() = _binding as T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getDataBinding()
        setContentView(_binding!!.root)
        initViewModel()
        initView()
        addEvent()
        addObservers()
        initData()
    }

    abstract fun getDataBinding(): T

    open fun initViewModel() {}

    open fun addEvent() {}
    open fun initView() {}

    open fun addObservers() {}

    open fun initData() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}