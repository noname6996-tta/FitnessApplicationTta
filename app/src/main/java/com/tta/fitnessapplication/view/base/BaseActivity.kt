package com.tta.fitnessapplication.view.base

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.tta.fitnessapplication.data.repository.RepositoryApi
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.view.MainViewModel
import com.tta.fitnessapplication.view.MainViewModelFactory

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    private var _binding: T? = null
    protected val binding: T
        get() = checkNotNull(_binding) {
            "Activity $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }

    protected lateinit var loginPreferences: SharedPreferences
    protected lateinit var loginPrefsEditor: SharedPreferences.Editor
    protected lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getDataBinding()
        setContentView(binding.root)
        loginPreferences = getSharedPreferences(
            Constant.LOGIN_PREFS, AppCompatActivity.MODE_PRIVATE
        )
        loginPrefsEditor = loginPreferences.edit()
        val repositoryApi = RepositoryApi()
        val viewModelFactory = MainViewModelFactory(repositoryApi)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
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
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}