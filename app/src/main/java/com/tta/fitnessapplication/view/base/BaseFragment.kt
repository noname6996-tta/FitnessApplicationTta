package com.tta.fitnessapplication.view.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.awesomedialog.AwesomeDialog
import com.example.awesomedialog.background
import com.example.awesomedialog.body
import com.example.awesomedialog.icon
import com.example.awesomedialog.onPositive
import com.example.awesomedialog.title
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.tta.fitnessapplication.R
import com.tta.fitnessapplication.data.repository.RepositoryApi
import com.tta.fitnessapplication.data.utils.ConnectivityLiveData
import com.tta.fitnessapplication.data.utils.Constant
import com.tta.fitnessapplication.view.MainViewModel
import com.tta.fitnessapplication.view.MainViewModelFactory

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    private var _binding: T? = null
    protected var isConnect = false
    protected val binding: T
        get() = checkNotNull(_binding) {
            "Fragment $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }

    protected inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }
    val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_HEART_RATE_SUMMARY, FitnessOptions.ACCESS_READ)
        .build()

    protected lateinit var loginPreferences: SharedPreferences
    protected lateinit var loginPrefsEditor: SharedPreferences.Editor
    protected lateinit var mainViewModel: MainViewModel
    private lateinit var connectivityLiveData: ConnectivityLiveData

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkConnectInternet() {
        connectivityLiveData = ConnectivityLiveData(requireActivity().application)
        connectivityLiveData.observe(viewLifecycleOwner, Observer { isAvailable ->
            isConnect = when (isAvailable) {
                true -> {
                    true
                }
                false -> {
                    false
                }
            }
        })
    }
}