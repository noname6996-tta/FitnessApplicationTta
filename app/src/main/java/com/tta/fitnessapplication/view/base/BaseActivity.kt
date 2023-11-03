package com.tta.fitnessapplication.view.base

import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.tta.fitnessapplication.data.repository.RepositoryApi
import com.tta.fitnessapplication.data.utils.ConnectivityLiveData
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.view.MainViewModel
import com.tta.fitnessapplication.view.MainViewModelFactory

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    private var _binding: T? = null
    private var isConnect = false
    private lateinit var dialog: Dialog
    protected val binding: T
        get() = checkNotNull(_binding) {
            "Activity $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }

    protected lateinit var loginPreferences: SharedPreferences
    protected lateinit var loginPrefsEditor: SharedPreferences.Editor
    protected lateinit var mainViewModel: MainViewModel
    private lateinit var connectivityLiveData: ConnectivityLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getDataBinding()
        setContentView(binding.root)
        setupLoading()
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
        checkConnectInternet()
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

    private fun checkConnectInternet(){
        connectivityLiveData= ConnectivityLiveData(application)
        connectivityLiveData.observe(this, Observer {isAvailable->
            isConnect = when(isAvailable) {
                true-> {
                    true
                }

                false-> {
                    false
                }
            }
        })
    }

    private fun setupLoading() {
        dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(ProgressBar(this))
    }

    fun showLoading() {
        dialog.show() // to show this dialog
    }

    fun hideLoading() {
        dialog.dismiss() // to hide this dialog
    }
}